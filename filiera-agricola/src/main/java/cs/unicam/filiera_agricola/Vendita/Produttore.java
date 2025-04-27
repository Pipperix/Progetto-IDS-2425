package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Produttore extends Venditore {

    @Id
    private int id;

    public Produttore() {
    }

    public Produttore(String username, String nomeUtente, String cognome, String email, String password,
                      Luogo luogo, String partitaIva) {
        super(username, nomeUtente, cognome, email, password, luogo, partitaIva);
    }

    public void aggiungiCertificazione(ProdottiController prodotti, int id, Certificazione certificazione) {
        prodotti.aggiungiCertificazione(id, certificazione);
    }

    public void eliminaCertificazione(ProdottiController prodotti, int id, int certificazioneId) {
        prodotti.eliminaCertificazione(id, certificazioneId);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
