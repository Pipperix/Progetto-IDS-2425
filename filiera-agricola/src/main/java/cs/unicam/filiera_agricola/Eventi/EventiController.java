package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import cs.unicam.filiera_agricola.Vendita.Indirizzo;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private EventiService eventiService;


    @PostMapping(value = "/{animatoreId}/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> creaEvento(@PathVariable int animatoreId, @RequestBody Evento evento) {
        try {
            eventiService.creaEvento(animatoreId, evento);
            return ResponseEntity.ok("Evento creato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/tutti")
    public ResponseEntity<Object> getTuttiEventi() {
        try {
            List<Evento> eventi = eventiService.getTuttiEventi();
            return ResponseEntity.ok(eventi);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/eventobyId/{id}")
    public ResponseEntity<Object> getEventoById(@PathVariable int id) {
        try {
            Evento evento = eventiService.getEventoById(id);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/modifica/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaEvento(@PathVariable int id, @RequestBody Evento eventoModificato) {
        try {
            eventiService.modificaEvento(id, eventoModificato);
            return ResponseEntity.ok("Evento modificato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/elimina/{id}")
    public ResponseEntity<String> eliminaEvento(@PathVariable int id) {
        try {
            eventiService.eliminaEvento(id);
            return ResponseEntity.ok("Evento eliminato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/prenota")
    public ResponseEntity<String> prenotaEvento(@RequestParam int eventoId, @RequestParam String username) {
        try {
            UtenteRegistrato utente = utentiRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            eventiService.prenotaEvento(eventoId, utente.getUsername());
            return ResponseEntity.ok("Prenotazione effettuata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{eventoId}/prenotati")
    public ResponseEntity<Object> getPrenotati(@PathVariable int eventoId) {
        try {
           Set<UtenteRegistrato> prenotati = eventiService.getPrenotati(eventoId);
            return ResponseEntity.ok(prenotati);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/{animatoreId}")
    public ResponseEntity<Object> getEventiAnimatore(@PathVariable int animatoreId) {
        try {
            List<Evento> eventi = eventiService.getEventiAnimatore(animatoreId);
            return ResponseEntity.ok(eventi);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostConstruct
    public void init() {
        if (eventoRepository.count() == 0) { // Evita duplicati
            Indirizzo indirizzo1 = new Indirizzo("Via Roma", "1", "60022", "Roma" );
            Indirizzo indirizzo2 = new Indirizzo("Via Firenze", "2", "50023", "Firenze");

            //Posizione posizione1 = new Posizione(43.717899, 10.408900);
            //Posizione posizione2 = new Posizione(43.717899, 10.408900);


            Evento evento1 = new Evento("Fiera Bio", "Agricoltura", "Esposizione di prodotti biologici",
                    LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(11),
                    new Luogo("Piazza Centrale", indirizzo1, 43.717899, 10.408900), 100);

            Evento evento2 = new Evento("Degustazione Vini", "Enologia", "Evento per amanti del vino",
                    LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6),
                    new Luogo("Cantina Rossi", indirizzo2, 43.717899, 10.408900), 50);

            eventoRepository.save(evento1);
            eventoRepository.save(evento2);
        }
    }
}



