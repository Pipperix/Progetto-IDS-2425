package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/eventi")
public class HandlerEventi {

    private static HandlerEventi instance = new HandlerEventi();
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public HandlerEventi() {}

    @Autowired
    public HandlerEventi(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public static HandlerEventi getInstance() {
        return instance;
    }

    @PostMapping(value = "/{animatoreId}/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> creaEvento(@PathVariable int animatoreId, @RequestBody Evento evento) {
        // Recupero l'utente in base all'ID
        Optional<UtenteRegistrato> utenteOpt = utentiRepository.findById(animatoreId);
        if (utenteOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Utente non trovato.");
        }

        // Verifico se l'utente è effettivamente un Animatore
        UtenteRegistrato utente = utenteOpt.get();
        if (!(utente instanceof Animatore)) {
            return ResponseEntity.badRequest().body("L'id fornito non corrisponde a un Animatore valido.");
        }

        // Se l'utente è un Animatore, lo castiamo e associamo all'evento
        Animatore animatore = (Animatore) utente;
        evento.setAnimatore(animatore);  // Imposto l'animatore sull'evento

        // Salvo l'evento nel database
        eventoRepository.save(evento);
        return ResponseEntity.ok("Evento aggiunto");
    }

    @GetMapping(value = "/tutti")
    public ResponseEntity<List<Evento>> getTuttiEventi() {
        List<Evento> eventi = eventoRepository.findAll();
        return ResponseEntity.ok(eventi);
    }

    @GetMapping(value = "/eventobyId/{id}")
    public ResponseEntity<Object> getEventoById(@PathVariable int id) {
        if (eventoRepository.existsById(id))
            return ResponseEntity.ok(eventoRepository.findById(id).get());
        else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    @PutMapping(value = "/modifica/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modificaEvento(@PathVariable int id, @RequestBody Evento eventoModificato) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);

        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();

            if (eventoModificato.getNomeEvento() != null) {
                evento.setNomeEvento(eventoModificato.getNomeEvento());
            }
            if (eventoModificato.getTipo() != null) {
                evento.setTipo(eventoModificato.getTipo());
            }
            if (eventoModificato.getDescrizioneEvento() != null) {
                evento.setDescrizioneEvento(eventoModificato.getDescrizioneEvento());
            }
            if (eventoModificato.getDataInizio() != null) {
                evento.setDataInizio(eventoModificato.getDataInizio());
            }
            if (eventoModificato.getDataFine() != null) {
                evento.setDataFine(eventoModificato.getDataFine());
            }
            if (eventoModificato.getLuogo() != null) {
                evento.setLuogo(eventoModificato.getLuogo());
            }
            if (eventoModificato.getCapienzaPersone() > 0) {
                evento.setCapienzaPersone(eventoModificato.getCapienzaPersone());
            }
            eventoRepository.save(evento);
            return ResponseEntity.ok("Evento modificato con successo.");
        } else {
            return ResponseEntity.badRequest().body("Evento non trovato.");
        }
    }

    @DeleteMapping(value = "/elimina/{id}")
    public ResponseEntity<String> eliminaEvento(@PathVariable int id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return ResponseEntity.ok("Evento eliminato");
        }
        else
            return ResponseEntity.badRequest().body("Evento non esistente");
    }

    @PostMapping("/prenota")
    public ResponseEntity<String> prenotaEvento(@RequestParam int eventoId, @RequestParam String username) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<UtenteRegistrato> utenteOpt = utentiRepository.findByUsername(username);

        if (eventoOpt.isEmpty() || utenteOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Evento o utente non trovato.");
        }

        Evento evento = eventoOpt.get();
        UtenteRegistrato utente = utenteOpt.get();

        if (evento.prenotazione(utente)) {
            eventoRepository.save(evento);  // Salva l'evento aggiornato
            return ResponseEntity.ok("Prenotazione avvenuta con successo.");
        } else {
            return ResponseEntity.badRequest().body("Posti esauriti.");
        }
    }


    @GetMapping("/{eventoId}/prenotati")
    public ResponseEntity<Set<UtenteRegistrato>> getPrenotati(@PathVariable int eventoId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        if (eventoOpt.isPresent()) {
            return ResponseEntity.ok(eventoOpt.get().getUtentiPrenotati());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @GetMapping("/{eventoId}/prenotati")
    public ResponseEntity<Set<UtenteRegistrato>> getPrenotati(@PathVariable int eventoId) {
        return ResponseEntity.ok(eventoRepository.findById(eventoId).get().getUtentiPrenotati());
    }

     */

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



