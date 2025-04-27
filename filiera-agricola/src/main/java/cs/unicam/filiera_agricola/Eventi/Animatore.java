package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Animatore extends UtenteRegistrato {

    // orphanremoval = true: se un Animatore viene rimosso, anche i suoi eventi verranno eliminati automaticamente
    @OneToMany(mappedBy = "animatore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventiCreati;

    public Animatore() {
        super(); // Necessario per JPA
    }

    public Animatore(String username, String nomeUtente, String cognome, String email, String password,
                     Luogo luogo) {
        super(username, nomeUtente, cognome, email, password, luogo, Ruolo.ANIMATORE);
        this.eventiCreati = new ArrayList<>();
    }

    public List<Evento> getEventiCreati() {
        return eventiCreati;
    }

    public void creaEvento(EventiController eventi) {
        eventi.creaEvento(this.getId(), new Evento());
    }

    public void modificaEvento(EventiController eventi, int id, Evento eventoModificato) {
        eventi.modificaEvento(id, eventoModificato);
    }

    public void eliminaEvento(EventiController eventi, int id) {
        eventi.eliminaEvento(id);
    }

    public void getPrenotati(EventiController eventi, int id) {
        eventi.getPrenotati(id);
    }
}
