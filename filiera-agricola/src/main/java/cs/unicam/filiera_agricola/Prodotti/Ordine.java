package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
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

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @ElementCollection
    @CollectionTable(name = "ordine_prodotti_pacchetti", joinColumns = @JoinColumn(name = "ordine_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "quantita")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Prodotto, Integer> prodotti = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "acquirente_id")
    private Acquirente acquirente;


    public Ordine() {}

    public Ordine(LocalDateTime istante, Acquirente acquirente, MetodoPagamento metodoPagamento) {
        this.istante = istante;
        this.acquirente = acquirente;
        this.metodoPagamento = metodoPagamento;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getIstante() {
        return istante;
    }

    public Map<Prodotto, Integer> getProdotti() {
        return prodotti;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public void setIstante(LocalDateTime istante) {
        this.istante = istante;
    }

    public void setProdotti(Map<Prodotto, Integer> prodotti) {
        this.prodotti = prodotti;
    }

    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }

}
