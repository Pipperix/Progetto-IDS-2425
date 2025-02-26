package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import jakarta.persistence.*;

@Entity
public class CarrelloItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "acquirente_id")
    private Acquirente acquirente;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int quantita;

    public CarrelloItem() {}

    public CarrelloItem(Acquirente acquirente, Prodotto prodotto, int quantita) {
        this.acquirente = acquirente;
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    // Getters
    public int getId() {
        return id;
    }
    public Acquirente getAcquirente() {
        return acquirente;
    }
    public Prodotto getProdotto() {
        return prodotto;
    }
    public int getQuantita() {
        return quantita;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }
    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

}
