package cs.unicam.filiera_agricola.Vendita;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import jakarta.persistence.*;

@Entity
public class Acquirente extends Venditore {

    public Acquirente() {
    }

    public Acquirente(String username, String nome, String cognome, String email, String password,
                      Luogo luogo, String partitaIva) {
        super(username, nome, cognome, email, password, luogo, "123456789");
    }

}