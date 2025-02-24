package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Vendita.Indirizzo;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import cs.unicam.filiera_agricola.Vendita.Posizione;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventi")
public class HandlerEventi {

    private static HandlerEventi instance = new HandlerEventi();
    @Autowired
    private EventoRepository eventoRepository;

    public HandlerEventi() {}

    @Autowired
    public HandlerEventi(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public static HandlerEventi getInstance() {
        return instance;
    }

    @PostMapping(value = "/crea")
    public ResponseEntity<String> creaEvento(@RequestBody Evento evento) {
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

    @PutMapping(value = "/modifica/{id}")
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
            if (eventoModificato.getDescrizione() != null) {
                evento.setDescrizione(eventoModificato.getDescrizione());
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

    @PostConstruct
    public void init() {
        if (eventoRepository.count() == 0) { // Evita duplicati
            Indirizzo indirizzo1 = new Indirizzo("Via Roma", "1", "60022", "Roma" );
            Indirizzo indirizzo2 = new Indirizzo("Via Firenze", "2", "50023", "Firenze");

            Posizione posizione1 = new Posizione(43.717899, 10.408900);
            Posizione posizione2 = new Posizione(43.717899, 10.408900);

            Evento evento1 = new Evento("Fiera Bio", "Agricoltura", "Esposizione di prodotti biologici",
                    LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(11),
                    new Luogo("Piazza Centrale", posizione1, indirizzo1), 100);

            Evento evento2 = new Evento("Degustazione Vini", "Enologia", "Evento per amanti del vino",
                    LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6),
                    new Luogo("Cantina Rossi", posizione2, indirizzo2), 50);

            eventoRepository.save(evento1);
            eventoRepository.save(evento2);
        }
    }
}



