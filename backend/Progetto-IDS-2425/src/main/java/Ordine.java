import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ordine {

    // Metto object come chiave della mappa almeno posso avere sia Prodotto che Pacchetto Prodotti
    Map<Object, Integer> prodotti = new HashMap<>();
    private LocalDateTime istante;
    private int id;

    public Ordine(LocalDateTime istante, int id, Map<Object, Integer> prodotti) {
        this.istante = Objects.requireNonNull(istante, "L'istante non può essere nullo.");
        if (id <= 0) throw new IllegalArgumentException("L'id deve essere maggiore di 0.");
        this.id = id;
        this.prodotti = prodotti;
    }

    // Getter
    public Map<Object, Integer> getProdotti() {
        return prodotti;
    }

    public LocalDateTime getIstante() {
        return istante;
    }

    public int getId() {
        return id;
    }

    // Setter
    public void setProdotti(Map<Object, Integer> prodotti) {
        this.prodotti = Objects.requireNonNull(prodotti, "La lista dei prodotti non può essere nulla.");
    }

    public void setIstante(LocalDateTime istante) {
        this.istante = Objects.requireNonNull(istante, "L'istante non può essere nullo.");
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("L'id deve essere maggiore di 0.");
        this.id = id;
    }
}
