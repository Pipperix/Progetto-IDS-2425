import java.time.LocalDate;
import java.time.LocalDateTime;

public class Animatore extends UtenteRegistrato {


    public Animatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
    }


    public Evento creaEvento(String nome, String tipo, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                             Luogo luogo, int capienzaPersone) {
        return new Evento(nome, tipo, descrizione, dataInizio, dataFine, luogo, capienzaPersone);
    }
}
