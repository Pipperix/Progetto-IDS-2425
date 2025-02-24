package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Vendita.Indirizzo;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Animatore extends UtenteRegistrato {

    // orphanremoval = true: se un Animatore viene rimosso, anche i suoi eventi verranno eliminati automaticamente
    @OneToMany(mappedBy = "animatore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventiCreati;

    //private final HandlerEventi handlerEventi = new HandlerEventi();

    public Animatore() {
        super(); // Necessario per JPA
    }

    public Animatore(String username, String nome, String cognome, String email, String password,
                     Luogo luogo, LocalDate dataDiNascita) {
        super(username, nome, cognome, email, password, luogo, dataDiNascita, Ruolo.ANIMATORE);
        this.eventiCreati = new ArrayList<>();
    }

    public ResponseEntity<String> creaEvento(Evento evento) {
        return HandlerEventi.getInstance().creaEvento(evento);
        // return handlerEventi.creaEvento(evento);
    }

    public ResponseEntity<String> modificaEvento() {
        Evento nuovoEvento = new Evento();
        return HandlerEventi.getInstance().modificaEvento(this.getId(), nuovoEvento);
    }

    public ResponseEntity<String> eliminaEvento(Evento evento) {
        return HandlerEventi.getInstance().eliminaEvento(evento.getId());
    }

    public List<Evento> getEventiCreati() {
        return eventiCreati;
    }
}
