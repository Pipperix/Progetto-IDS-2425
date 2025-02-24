package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

@Entity
public class Gestore extends UtenteRegistrato {

    public Gestore() {}

    public Gestore(String username, String nome, String cognome, String email, String password,
                   Luogo luogo, LocalDate dataDiNascita) {
        super(username, nome, cognome, email, password, luogo, dataDiNascita, Ruolo.GESTORE);
    }

    public ResponseEntity<String> rimuoviProdottiScaduti() {
        return HandlerPiattaforma.getInstance().rimuoviProdottiScaduti();
    }

    public ResponseEntity<String> autorizzaAccount(int id) {
        return HandlerPiattaforma.getInstance().autorizzaAccount(id);
    }

}
