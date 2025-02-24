package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Vendita.Indirizzo;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import cs.unicam.filiera_agricola.Vendita.Posizione;
import cs.unicam.filiera_agricola.Vendita.Venditore;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/utenti")
    //@Service
    public class HandlerUtenti {

        private static HandlerUtenti instance = new HandlerUtenti();
        @Autowired
        private UtenteRepository utenteRepository;
        @Autowired
        private ProdottiRepository prodottoRepository;

        public HandlerUtenti() {}

        @Autowired
        public HandlerUtenti(UtenteRepository utenteRepository) {
            this.utenteRepository = utenteRepository;
        }

        public static HandlerUtenti getInstance() {
            return instance;
        }

        @GetMapping(value = "/tutti")
        public ResponseEntity<List<UtenteRegistrato>> getTuttiUtenti() {
            List<UtenteRegistrato> utenti = utenteRepository.findAll();
            return ResponseEntity.ok(utenti);
        }

        @PostMapping(value = "/registrazione")
        public ResponseEntity<Object> registrazione(@RequestBody UtenteRegistrato nuovoUtente) {
        //public ResponseEntity<Object> registrazione(UtenteRegistrato nuovoUtente) {
            // Controllo se username è già in uso
            if (utenteRepository.findByUsername(nuovoUtente.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Utente già registrato. Esegui l'autenticazione");
            }
            // Impostare autorizzato a false automaticamente
            nuovoUtente.setAutorizzato(false);

            // Se il ruolo non è specificato, assegna il valore predefinito UTENTE
            if (nuovoUtente.getRuolo() == null) {
                nuovoUtente.setRuolo(Ruolo.UTENTE);
                utenteRepository.save(nuovoUtente);
            }
                // Salvare l'utente nel database
                utenteRepository.save(nuovoUtente);
                return ResponseEntity.ok("Registrazione completata come " + nuovoUtente.getRuolo() + ". " +
                        "Attendi l'approvazione da parte di un gestore.");
        }

        @PostMapping("/login")
        public ResponseEntity<Object> autenticazione(@RequestParam String username, @RequestParam String password) {
        //public ResponseEntity<Object> autenticazione(String username, String password) {
                Optional<UtenteRegistrato> user = utenteRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                return ResponseEntity.ok("Login effettuato");
            }
            return ResponseEntity.badRequest().body("Credenziali errate o utente non registrato");
        }

        @PostMapping("/logout")
        public ResponseEntity<Object> disconnessione(@RequestParam String username) {
        //public ResponseEntity<Object> disconnessione(String username) {
                Optional<UtenteRegistrato> user = utenteRepository.findByUsername(username);
            if (user.isPresent()) {
                return ResponseEntity.ok("Logout effettuato");
            }
            return ResponseEntity.badRequest().body("Utente non trovato");
        }

        @PutMapping("/modifica-dati/{id}")
        public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
        //public ResponseEntity<String> modificaDatiUtente(int id, UtenteRegistrato nuovoUtente) {
            // Recupera l'utente dal database
            Optional<UtenteRegistrato> utenteOpt = utenteRepository.findById(id);

            if (utenteOpt.isPresent()) {
                UtenteRegistrato utente = utenteOpt.get();

                // Aggiorna i dati dell'utente
                if (nuovoUtente.getUsername() != null) {
                    utente.setUsername(nuovoUtente.getUsername());  // Modifica il nome
                }
                if (nuovoUtente.getNome() != null) {
                    utente.setNome(nuovoUtente.getNome());  // Modifica il nome
                }
                if (nuovoUtente.getCognome() != null) {
                    utente.setCognome(nuovoUtente.getCognome());  // Modifica il nome
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
                utenteRepository.save(utente);
                return ResponseEntity.ok("Dati utente aggiornati con successo.");
            } else {
                return ResponseEntity.badRequest().body("Utente non trovato.");
            }
        }

        @GetMapping ("/mappa")
        public ResponseEntity<List<Luogo>> visualizzaMappa() {
            List<Luogo> mappa = new ArrayList<>();
            // Recupera gli utenti che sono venditori
            List<UtenteRegistrato> utenti = utenteRepository.findAll();
            for (UtenteRegistrato utente : utenti) {
                if (utente instanceof Venditore venditore) {
                    // Aggiungi il luogo del venditore
                    mappa.add(venditore.getLuogo());
                }
            }
            return ResponseEntity.ok(mappa);
        }

        @PostMapping("/condividi")
        public ResponseEntity<String> condivisioneSuSocial(@RequestParam int prodottoId, @RequestParam Social social,
                                                           @RequestParam String username, @RequestParam String password) {
            // Recupera il prodotto dal database
            Optional<Prodotto> prodottoOpt = prodottoRepository.findById(prodottoId);
            if (prodottoOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Prodotto non trovato.");
            }

            // Recupera l'utente registrato dal database
            UtenteRegistrato utente = utenteRepository.findByUsername(username).orElseThrow();
            if (!utente.registrazione()) {
                return ResponseEntity.badRequest().body("Credenziali errate o utente non registrato");
            }

            // Restituisce la risposta di successo
            return ResponseEntity.ok("Contenuto condiviso con successo su " + social.name());
        }


        @PostConstruct
        public void init() {
            if (utenteRepository.count() == 0) { // Evita duplicati
                Indirizzo indirizzo1 = new Indirizzo("Via Napoli", "3", "34677", "Napoli");
                Indirizzo indirizzo2 = new Indirizzo("Via Torino", "4", "46976", "Torino");

                Posizione posizione1 = new Posizione(43.717899, 10.408900);
                Posizione posizione2 = new Posizione(43.717899, 10.408900);

                UtenteRegistrato utente1 = new UtenteRegistrato("chiamer", "Chiara", "Medeiros",
                        "chiara.medei@libero.it", "passwordkia", new Luogo("Cantiani SRL",
                        posizione1, indirizzo1), Ruolo.PRODUTTORE);
                        //LocalDate.of(2003, 5, 6)

                UtenteRegistrato utente2 = new UtenteRegistrato("pipperix", "Fabio", "Fazio",
                        "fabietto68@libero.it", "thegreaterone", new Luogo("Gazzettino s.p.a.",
                        posizione2, indirizzo2), Ruolo.TRASFORMATORE);

                utenteRepository.save(utente1);
                utenteRepository.save(utente2);
            }
        }

}
