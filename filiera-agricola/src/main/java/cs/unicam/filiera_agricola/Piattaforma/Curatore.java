package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
public class Curatore extends UtenteRegistrato {

    // costruttore vuoto necessario per JPA
    public Curatore() {}

    public Curatore(String username, String nomeUtente, String cognome, String email, String password, Luogo luogo) {
        super(username, nomeUtente, cognome, email, password, luogo, Ruolo.CURATORE);
    }

    @Transactional // garantisce che l'approvazione sia salvata correttamente
    public void approvaContenuto(Prodotto prodotto, ProdottiController prodotti) {
        prodotti.approvaContenuto(prodotto.getId());
    }

    public void getProdottiDaVerificare(ProdottiController prodotti) {
        prodotti.getProdottiDaVerificare();
    }
}
