package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Venditore extends UtenteRegistrato{
    private String partitaIva;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "azienda_id", referencedColumnName = "id", nullable = true)
    private Azienda azienda;

    @OneToMany(mappedBy = "prodotto_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Prodotto> prodottiCreati = HashSet<>();

    public Venditore() {}

    public Venditore(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public int getId() {
        return id;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public List<Prodotto> getProdottiCreati() {
        return prodottiCreati;
    }

    public void creaProdotto(String nome, double prezzo, LocalDate dataScadenza){
        Prodotto nuovoProdotto = new Prodotto(nome, prezzo, dataScadenza);
        nuovoProdotto.setVenditore(this);  // Imposta il venditore del prodotto
        prodottiCreati.add(nuovoProdotto);
    }
    public void eliminaProdotto(Prodotto prodotto){
        prodottiCreati.remove(prodotto);
        prodotto.setVenditore(null);
    }
    public void modificaNome(Prodotto prodotto, String nome){
        if (prodottiCreati.contains(prodotto)) {
            prodotto.setNome(nome);
        }
    }
    public void modificaDescrizione(Prodotto prodotto, String descrizione){
        if (prodottiCreati.contains(prodotto) && prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().setDettaglio(descrizione);
        }
    }
    public void modificaPrezzo(Prodotto prodotto, double prezzo){
        if (prodottiCreati.contains(prodotto)) {
            prodotto.setPrezzo(prezzo);
        }
    }
    public void modificaQuantita(Prodotto prodotto, int quantita){
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().setQuantita(quantita);
        }
    }
}