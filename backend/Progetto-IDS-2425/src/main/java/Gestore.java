import java.time.LocalDate;

public class Gestore extends UtenteRegistrato {

    public Gestore(int id, String username, String nome, String cognome, String email,
                   Indirizzo indirizzo, LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
    }
}
