package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
import cs.unicam.filiera_agricola.Vendita.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtentiController{

    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private UtentiService utentiService;


    @GetMapping(value = "/tutti")
    public ResponseEntity<List<UtenteRegistrato>> getTuttiUtenti() {
        try {
            List<UtenteRegistrato> utenti = utentiService.getTuttiUtenti();
            return ResponseEntity.ok(utenti);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/registrazione")
    public ResponseEntity<Object> registrazione(@RequestBody UtenteRegistrato nuovoUtente) {
        try {
            utentiService.registrazione(nuovoUtente);
            return ResponseEntity.ok("Registrazione completata come " + nuovoUtente.getRuolo() + ". " +
                    "Attendi l'approvazione da parte di un gestore.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> autenticazione(@RequestParam String username, @RequestParam String password) {
        try {
            utentiService.autenticazione(username, password);
            return ResponseEntity.ok("Login effettuato con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> disconnessione(@RequestParam String username) {
        try {
            utentiService.disconnessione(username);
            return ResponseEntity.ok("Logout effettuato con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/modifica-dati/{id}")
    public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
        try {
            utentiService.modificaDatiUtente(id, nuovoUtente);
            return ResponseEntity.ok("Dati utente modificati con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping ("/mappa")
    public ResponseEntity<List<Luogo>> visualizzaMappa() {
        try {
            List<Luogo> mappa = utentiService.visualizzaMappa();
            return ResponseEntity.ok(mappa);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/condividi")
    public ResponseEntity<String> condivisioneSuSocial(@RequestParam int prodottoId, @RequestParam Social social,
                                                       @RequestParam String username, @RequestParam String password) {

        try {
            utentiService.condivisioneSuSocial(prodottoId, social, username, password);
            return ResponseEntity.ok("Prodotto condiviso con successo su " + social);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
