package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Prodotti.HandlerProdotti;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Entity
public class Curatore extends UtenteRegistrato {

    // costruttore vuoto necessario per JPA
    public Curatore() {}

    public Curatore(String username, String nome, String cognome, String email, String password, Luogo luogo) {
        super(username, nome, cognome, email, password, luogo, Ruolo.CURATORE);
    }

    @Transactional // garantisce che l'approvazione sia salvata correttamente
    public ResponseEntity<String> approvaContenuto(Prodotto prodotto) {
        return HandlerProdotti.getInstance().approvaContenuto(prodotto.getId());
    }
}
