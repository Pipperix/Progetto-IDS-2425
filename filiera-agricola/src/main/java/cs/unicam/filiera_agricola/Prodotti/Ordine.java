package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Vendita.Acquirente;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDateTime istante;

    @ElementCollection
    @CollectionTable(name = "ordine_prodotti_pacchetti", joinColumns = @JoinColumn(name = "ordine_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "quantita")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Object, Integer> prodotti = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "acquirente_id", nullable = false)
    private Acquirente acquirente;


    public Ordine() {}

    public Ordine(LocalDateTime istante, Acquirente acquirente) {
        this.istante = istante;
        this.acquirente = acquirente;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getIstante() {
        return istante;
    }

    public Map<Object, Integer> getProdotti() {
        return prodotti;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public void setIstante(LocalDateTime istante) {
        this.istante = istante;
    }

    public void setProdotti(Map<Object, Integer> prodotti) {
        this.prodotti = prodotti;
    }

    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }

    // Metodo di utilità per aggiungere un prodotto/pacchetto all'ordine
    public void aggiungiProdottoPacchetto(Object prodotto, int quantita) {
        this.prodotti.put(prodotto, quantita);
    }
}
