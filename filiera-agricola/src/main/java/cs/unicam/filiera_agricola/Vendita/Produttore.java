package cs.unicam.filiera_agricola.Vendita;

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

    /*
    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Certificazione> certificazioniCreate = new HashSet<>();

     */

    public Produttore() {
    }

    public Produttore(String username, String nomeUtente, String cognome, String email, String password,
                      Luogo luogo, String partitaIva) {
        super(username, nomeUtente, cognome, email, password, luogo, partitaIva);
    }

    public void aggiungiCertificazione(Prodotto prodotto, Certificazione certificazione) {
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().aggiungiCertificazione(certificazione);
            //certificazioniCreate.add(certificazione);
        }
    }

    public void modificaCertificazione(Prodotto prodotto, Certificazione nuovaCertificazione) {
        if (prodotto.getDescrizione() != null) {
            for (Certificazione cert : prodotto.getDescrizione().getCertificazioni()) {
                if (cert.getId() == nuovaCertificazione.getId()) {
                    cert.setNome(nuovaCertificazione.getNome());
                    break;
                }
            }
        }
    }

    public void rimuoviCertificazione(Prodotto prodotto, Certificazione certificazione) {
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().getCertificazioni().remove(certificazione);
            //certificazioniCreate.remove(certificazione);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
