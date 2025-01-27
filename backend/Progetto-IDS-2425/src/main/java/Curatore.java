import java.time.LocalDate;

public class Curatore extends UtenteRegistrato {

    public Curatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
    }


    public boolean approvaContenuto(Prodotto prodotto) {
        return true;
    }
}
