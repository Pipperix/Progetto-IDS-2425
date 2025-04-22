package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Service
public class EventiService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public EventiService() {}

    @Autowired
    public EventiService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public void creaEvento(int animatoreId, Evento evento) {
        // Recupero l'utente in base all'ID
        UtenteRegistrato utente = utentiRepository.findById(animatoreId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        // Verifico se l'utente è effettivamente un Animatore
        if (utente.getRuolo() !=Ruolo.ANIMATORE) {
            throw new RuntimeException("L'id fornito non corrisponde all'id di un animatore");
        }
        // Verifico se l'animatore è stato autorizzato dal Gestore
        if (!utente.isAutorizzato()) {
            throw new RuntimeException("L'account dell'animatore non è stato ancora autorizzato. " +
                    "Attendi l'autorizzazione del gestore.");
        }
        // Se l'utente è un Animatore, lo castiamo e associamo all'evento
        Animatore animatore = (Animatore) utente;
        evento.setAnimatore(animatore);  // Imposto l'animatore sull'evento

        // Salvo l'evento nel database
        eventoRepository.save(evento);
    }

    public List<Evento> getTuttiEventi() {
        List<Evento> eventi = eventoRepository.findAll();
        if (eventi.isEmpty()) {
            throw new RuntimeException("Nessun evento trovato");
        }
        return eventi;
    }

    public Evento getEventoById(int id) {
        return eventoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }


    public void modificaEvento(int id, Evento eventoModificato) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

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
    }


    public void eliminaEvento(int id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        eventoRepository.delete(evento);  // Elimina l'evento dal database
    }


    public void prenotaEvento(int eventoId, String username) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        UtenteRegistrato utente = utentiRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (evento.prenotazione(utente)) {
            eventoRepository.save(evento);  // Salva l'evento aggiornato
        } else {
            throw new RuntimeException("Prenotazione non riuscita: posti esauriti o utente già prenotato");
        }
    }


    public Set<UtenteRegistrato> getPrenotati(int eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        return evento.getUtentiPrenotati();
    }
}



