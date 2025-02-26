package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;

@Entity
public class Gestore extends UtenteRegistrato {

    public Gestore() {}

    public Gestore(String username, String nomeUtente, String cognome, String email, String password,
                   Luogo luogo) {
        super(username, nomeUtente, cognome, email, password, luogo, Ruolo.GESTORE);
    }

    public ResponseEntity<String> rimuoviProdottiScaduti() {
        return HandlerPiattaforma.getInstance().rimuoviProdottiScaduti();
    }

    public ResponseEntity<String> autorizzaAccount(int utenteId) {
        return HandlerPiattaforma.getInstance().autorizzaAccount(utenteId);
    }

}
