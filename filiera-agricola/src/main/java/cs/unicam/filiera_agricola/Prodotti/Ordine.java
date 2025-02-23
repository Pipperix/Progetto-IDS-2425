package cs.unicam.filiera_agricola.Prodotti;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate istante;
    private Map<Object, Integer> prodotti;

    public Ordine() {}

    public Ordine(Map<Object, Integer> prodotti) {
        this.prodotti = prodotti;
        this.istante = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public LocalDate getIstante() {
        return istante;
    }

    public Map<Object, Integer> getProdotti() {
        return prodotti;
    }

    public void setProdotti(Map<Object, Integer> prodotti) {
        this.prodotti = prodotti;
    }
}
