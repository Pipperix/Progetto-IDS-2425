package cs.unicam.filiera_agricola.Vendita;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;
import cs.unicam.filiera_agricola.Prodotti.PacchettoDiProdotti;
import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import jakarta.persistence.*;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Distributore extends Venditore{

    @OneToMany(mappedBy = "distributore", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PacchettoDiProdotti> pacchettiCreati = new HashSet<>();

    public Distributore() {}

    public Distributore(String username, String nomeUtente, String cognome, String email, String password, Luogo luogo, String partitaIva) {
        super(username, nomeUtente, cognome, email, password, luogo, partitaIva);
    }

    public void addPacchetto(ProdottiController prodotti) {
        prodotti.addPacchetto(this.getId(), new PacchettoDiProdotti());
    }

    public void updatePacchetto(ProdottiController prodotti, int id, PacchettoDiProdotti pacchettoModificato) {
        prodotti.updatePacchetto(id, pacchettoModificato);
    }

    public void deletePacchetto(ProdottiController prodotti, int id) {
        prodotti.deletePacchetto(id);
    }

    public Set<PacchettoDiProdotti> getPacchettiCreati() {
        return pacchettiCreati;
    }
}
