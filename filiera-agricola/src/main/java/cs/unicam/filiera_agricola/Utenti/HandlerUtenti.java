package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
import cs.unicam.filiera_agricola.Eventi.Animatore;
import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Vendita.*;
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

            // Se il ruolo è VENDITORE o sotto-ruolo (come Trasformatore), ottieni la partita IVA
            String partitaIva = null;
            if (nuovoUtente.getRuolo() == Ruolo.PRODUTTORE || nuovoUtente.getRuolo() == Ruolo.TRASFORMATORE ||
                    nuovoUtente.getRuolo() == Ruolo.DISTRIBUTORE) {
                partitaIva = ((Venditore) nuovoUtente).getPartitaIva(); // Cast per accedere alla partitaIva
            }

            // Creazione dell'utente specifico basato sul ruolo
            UtenteRegistrato utenteSpecifico = UtenteFactory.creaUtente(
                    nuovoUtente.getRuolo(),
                    nuovoUtente.getUsername(),
                    nuovoUtente.getNomeUtente(),
                    nuovoUtente.getCognome(),
                    nuovoUtente.getEmail(),
                    nuovoUtente.getPassword(),
                    nuovoUtente.getLuogo(),
                    partitaIva
            );

            // Salvataggio dell'utente nel database
            utentiRepository.save(utenteSpecifico);

            return ResponseEntity.ok("Registrazione completata come " + utenteSpecifico.getRuolo() + ". " +
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

                Produttore utente1 = new Produttore("chiamer", "Chiara", "Medeiros",
                        "chiara.medei@libero.it", "passwordkia", new Luogo("Cantiani SRL", indirizzo1,
                        43.717899, 10.408900), "IT123456789");
                        //LocalDate.of(2003, 5, 6)
                utentiRepository.save(utente1);

                Trasformatore utente2 = new Trasformatore("pipperix", "Fabio", "Fazio",
                        "fabietto68@libero.it", "thegreaterone", new Luogo("Gazzettino s.p.a.", indirizzo2,
                        67.568997, 31.354687), "IT123456789");

                utentiRepository.save(utente2);

                Acquirente utente3 = new Acquirente("acquirente", "Luca", "Rossi",
                        "luca.rossi@gmail.com", "12345", new Luogo("Piazza Galli", indirizzo1,
                        43.717899, 10.408900));
                utentiRepository.save(utente3);

            }
        }

}
