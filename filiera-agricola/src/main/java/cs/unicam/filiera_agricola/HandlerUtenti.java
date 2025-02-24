package cs.unicam.filiera_agricola;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    @RestController
    //@CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/utenti")
    public class HandlerUtenti {

        private static HandlerUtenti instance = new HandlerUtenti();
        @Autowired
        private UtenteRepository utenteRepository;

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
            Optional<UtenteRegistrato> user = utenteRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                return ResponseEntity.ok("Login effettuato");
            }
            return ResponseEntity.badRequest().body("Credenziali errate o utente non registrato");
        }

        @PostMapping("/logout")
        public ResponseEntity<Object> disconnessione(@RequestParam String username) {
            Optional<UtenteRegistrato> user = utenteRepository.findByUsername(username);
            if (user.isPresent()) {
                return ResponseEntity.ok("Logout effettuato");
            }
            return ResponseEntity.badRequest().body("Utente non trovato");
        }

        @PutMapping("/modifica-dati/{id}")
        public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
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
                if (nuovoUtente.getIndirizzo() != null) {
                    utente.setIndirizzo(nuovoUtente.getIndirizzo());  // Modifica l'indirizzo
                }
                if (nuovoUtente.getDataDiNascita() != null) {
                    utente.setDataDiNascita(nuovoUtente.getDataDiNascita());  // Modifica la data di nascita
                }

                // Salva l'utente aggiornato
                utenteRepository.save(utente);

                return ResponseEntity.ok("Dati utente aggiornati con successo.");
            } else {
                return ResponseEntity.badRequest().body("Utente non trovato.");
            }
        }

        @PostConstruct
        public void init() {
            if (utenteRepository.count() == 0) { // Evita duplicati
                Indirizzo indirizzo1 = new Indirizzo("Via Napoli", "3", "Napoli", 54677);
                Indirizzo indirizzo2 = new Indirizzo("Via Torino", "4", "Torino", 46976);

                UtenteRegistrato utente1 = new UtenteRegistrato("chiamer", "Chiara", "Medeiros",
                        "chiara.medei@libero.it", "passwordkia", indirizzo1, LocalDate.of(2003, 5, 6),
                        Ruolo.UTENTE);

                UtenteRegistrato utente2 = new UtenteRegistrato("pipperix", "Fabio", "Fazio",
                        "fabietto68@libero.it", "thegreaterone", indirizzo2, LocalDate.of(1968, 3, 19),
                        Ruolo.ANIMATORE);

                utenteRepository.save(utente1);
                utenteRepository.save(utente2);
            }
        }

}
