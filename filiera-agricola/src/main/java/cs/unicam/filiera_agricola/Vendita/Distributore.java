package cs.unicam.filiera_agricola.Vendita;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Prodotti.PacchettoDiProdotti;
import jakarta.persistence.*;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Distributore extends Venditore{
    @OneToMany(mappedBy = "pacchetto_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PacchettoDiProdotti> pacchettiCreati = new HashSet<>();

    public Distributore() {}

    public Distributore(String partitaIva) {
        super(partitaIva); // Richiama il costruttore della superclasse Venditore
    }

    public void creaPacchettoDiProdotti(Set<Prodotto> prodotti){
        PacchettoDiProdotti pacchetto = new PacchettoDiProdotti("nome");
        pacchettiCreati.add(pacchetto);
    }
    public void eliminaPacchetto(PacchettoDiProdotti pacchetto){
        pacchettiCreati.remove(pacchetto);
    }
    public void modificaNome(PacchettoDiProdotti pacchetto, String nome) {
        pacchetto.setNome(nome);
    }
    public void modificaDescrizione(PacchettoDiProdotti pacchetto, String descrizione){
        pacchetto.setDescrizione(descrizione);
    }
    public void modificaPrezzo(PacchettoDiProdotti pacchetto, double prezzo){
        pacchetto.setPrezzo(prezzo);
    }

    public Set<PacchettoDiProdotti> getPacchettiCreati() {
        return pacchettiCreati;
    }
}
