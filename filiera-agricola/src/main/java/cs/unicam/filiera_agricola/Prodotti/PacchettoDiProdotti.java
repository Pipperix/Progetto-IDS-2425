package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
public class PacchettoDiProdotti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // TODO: legare a Prodotto
    private int id;
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "pacchetto_prodotto", // Nome della tabella di join
            joinColumns = @JoinColumn(name = "pacchetto_id"), // Colonna per PacchettoDiProdotti
            inverseJoinColumns = @JoinColumn(name = "prodotto_id") // Colonna per Prodotto
    )
    @JsonManagedReference
    private Set<Prodotto> prodotti = new HashSet<>();

    public PacchettoDiProdotti() {}

    public PacchettoDiProdotti(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Set<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProdotti(Set<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        this.prodotti.add(prodotto);
        prodotto.getPacchetti().add(this);
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        this.prodotti.remove(prodotto);
        prodotto.getPacchetti().remove(this);
    }
}
