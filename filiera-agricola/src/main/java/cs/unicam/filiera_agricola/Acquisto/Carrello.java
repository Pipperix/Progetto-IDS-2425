package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Carrello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "acquirente_id", nullable = false)
    private Acquirente acquirente;

    @ManyToMany
    @JoinTable(
            name = "carrello_prodotti",
            joinColumns = @JoinColumn(name = "carrello_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    private List<Prodotto> prodotti = new ArrayList<>();

    //private int quantita;

    public Carrello() {}

    public Carrello(Acquirente acquirente) {
        this.acquirente = acquirente;
        this.prodotti = new ArrayList<>();
        //this.quantita = quantita;
    }

    // Getters
    public int getId() {
        return id;
    }
    public Acquirente getAcquirente() {
        return acquirente;
    }
    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }
    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

}
