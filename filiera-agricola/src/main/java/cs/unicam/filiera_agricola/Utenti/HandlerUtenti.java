package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Vendita.Indirizzo;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/utenti")
    public class HandlerUtenti {

        private static HandlerUtenti instance = new HandlerUtenti();
        @Autowired
        private UtentiRepository utentiRepository;
        @Autowired
        private ProdottiRepository prodottiRepository;

        public HandlerUtenti() {}

        @Autowired
        public HandlerUtenti(UtentiRepository utentiRepository, ProdottiRepository prodottiRepository) {
            this.utentiRepository = utentiRepository;
            this.prodottiRepository = prodottiRepository;
        }

        public static HandlerUtenti getInstance() {
            return instance;
        }

        @GetMapping(value = "/tutti")
        public ResponseEntity<List<UtenteRegistrato>> getTuttiUtenti() {
            List<UtenteRegistrato> utenti = utentiRepository.findAll();
            return ResponseEntity.ok(utenti);
        }

        @PostMapping(value = "/registrazione")
        public ResponseEntity<Object> registrazione(@RequestBody UtenteRegistrato nuovoUtente) {
            // Controllo se username è già in uso
            if (utentiRepository.findByUsername(nuovoUtente.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Utente già registrato. Esegui l'autenticazione");
            }
            // Impostare autorizzato a false automaticamente
            nuovoUtente.setAutorizzato(false);

            // Se il ruolo non è specificato, assegna il valore predefinito UTENTE
            if (nuovoUtente.getRuolo() == null) {
                nuovoUtente.setRuolo(Ruolo.UTENTE);
                utentiRepository.save(nuovoUtente);
            }
                // Salvare l'utente nel database
                utentiRepository.save(nuovoUtente);
                return ResponseEntity.ok("Registrazione completata come " + nuovoUtente.getRuolo() + ". " +
                        "Attendi l'approvazione da parte di un gestore.");
        }

        @PostMapping("/login")
        public ResponseEntity<Object> autenticazione(@RequestParam String username, @RequestParam String password) {
                Optional<UtenteRegistrato> user = utentiRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                return ResponseEntity.ok("Login effettuato");
            }
            return ResponseEntity.badRequest().body("Credenziali errate o utente non registrato");
        }

        @PostMapping("/logout")
        public ResponseEntity<Object> disconnessione(@RequestParam String username) {
                Optional<UtenteRegistrato> user = utentiRepository.findByUsername(username);
            if (user.isPresent()) {
                return ResponseEntity.ok("Logout effettuato");
            }
            return ResponseEntity.badRequest().body("Utente non trovato");
        }

        @PutMapping("/modifica-dati/{id}")
        public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
            // Recupera l'utente dal database
            Optional<UtenteRegistrato> utenteOpt = utentiRepository.findById(id);

            if (utenteOpt.isPresent()) {
                UtenteRegistrato utente = utenteOpt.get();

                // Aggiorna i dati dell'utente
                if (nuovoUtente.getUsername() != null) {
                    utente.setUsername(nuovoUtente.getUsername());  // Modifica lo userrname
                }
                if (nuovoUtente.getNomeUtente() != null) {
                    utente.setNomeUtente(nuovoUtente.getNomeUtente());  // Modifica il nome
                }
                if (nuovoUtente.getCognome() != null) {
                    utente.setCognome(nuovoUtente.getCognome());  // Modifica il cognome
                }
                if (nuovoUtente.getEmail() != null) {
                    utente.setEmail(nuovoUtente.getEmail());  // Modifica l'email
                }
                if (nuovoUtente.getPassword() != null) {
                    utente.setPassword(nuovoUtente.getPassword());  // Modifica la password
                }
                if (nuovoUtente.getLuogo() != null) {
                    utente.setLuogo(nuovoUtente.getLuogo());  // Modifica l'indirizzo
                }
                // Salva l'utente aggiornato
                utentiRepository.save(utente);
                return ResponseEntity.ok("Dati utente aggiornati con successo.");
            } else {
                return ResponseEntity.badRequest().body("Utente non trovato.");
            }
        }

        @GetMapping ("/mappa")
        public ResponseEntity<List<Luogo>> visualizzaMappa() {
            List<Luogo> mappa = new ArrayList<>();
            // Recupera gli utenti che sono venditori
            List<UtenteRegistrato> utenti = utentiRepository.findAll();
            for (UtenteRegistrato utente : utenti) {
                //if (utente instanceof Venditore venditore) {
                if (utente.getRuolo() == Ruolo.PRODUTTORE || utente.getRuolo() == Ruolo.TRASFORMATORE ||
                        utente.getRuolo() == Ruolo.DISTRIBUTORE || utente.getRuolo() == Ruolo.VENDITORE) {
                    // Aggiungi il luogo del venditore
                    mappa.add(utente.getLuogo());
                }
            }
            return ResponseEntity.ok(mappa);
        }

        @PostMapping("/condividi")
        public ResponseEntity<String> condivisioneSuSocial(@RequestParam int prodottoId, @RequestParam Social social,
                                                           @RequestParam String username, @RequestParam String password) {

            // Recupera il prodotto dal database
            Optional<Prodotto> prodottoOpt = prodottiRepository.findById(prodottoId);
            if (prodottoOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Prodotto non trovato.");
            }

            // Recupera l'utente registrato dal database
            Optional<UtenteRegistrato> utente = utentiRepository.findByUsername(username);
            //if (utente.isEmpty() || !utente.get().getPassword().equals(password)) {
            if (utente.isEmpty()) {
                return ResponseEntity.badRequest().body("Credenziali errate o utente non registrato");
            }

            // Restituisce la risposta di successo
            return ResponseEntity.ok("Contenuto condiviso con successo su " + social);
        }


        @PostConstruct
        public void init() {
            if (utentiRepository.count() == 0) { // Evita duplicati
                Indirizzo indirizzo1 = new Indirizzo("Via Napoli", "3", "34677", "Napoli");
                Indirizzo indirizzo2 = new Indirizzo("Via Torino", "4", "46976", "Torino");

                UtenteRegistrato utente1 = new UtenteRegistrato("chiamer", "Chiara", "Medeiros",
                        "chiara.medei@libero.it", "passwordkia", new Luogo("Cantiani SRL", indirizzo1,
                        43.717899, 10.408900), Ruolo.PRODUTTORE);
                        //LocalDate.of(2003, 5, 6)
                utentiRepository.save(utente1);

                UtenteRegistrato utente2 = new UtenteRegistrato("pipperix", "Fabio", "Fazio",
                        "fabietto68@libero.it", "thegreaterone", new Luogo("Gazzettino s.p.a.", indirizzo2,
                        67.568997, 31.354687), Ruolo.TRASFORMATORE);

                utentiRepository.save(utente2);

                UtenteRegistrato utente3 = new UtenteRegistrato("acquirente", "Luca", "Rossi",
                        "luca.rossi@gmail.com", "12345", new Luogo("Piazza Galli", indirizzo1,
                        43.717899, 10.408900), Ruolo.ACQUIRENTE);
                utentiRepository.save(utente3);

            }
        }

}
