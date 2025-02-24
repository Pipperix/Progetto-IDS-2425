package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Prodotti.HandlerProdotti;
import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Entity
public class Curatore extends UtenteRegistrato {

    // costruttore vuoto necessario per JPA
    public Curatore() {}

    public Curatore(String username, String nome, String cognome, String email, String password, Indirizzo indirizzo,
                    LocalDate dataDiNascita) {
        super(username, nome, cognome, email, password, indirizzo, dataDiNascita, Ruolo.CURATORE);
    }

    @Transactional // garantisce che l'approvazione sia salvata correttamente
    public ResponseEntity<String> approvaContenuto(Prodotto prodotto) {
        return HandlerProdotti.getInstance().approvaContenuto(prodotto.getId());
    }
}
