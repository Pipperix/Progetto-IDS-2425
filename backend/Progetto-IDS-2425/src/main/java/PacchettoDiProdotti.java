import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class PacchettoDiProdotti {

    private Set<Prodotto> pacchetto = new HashSet<>();
    private int id;
    private String nome;

    public PacchettoDiProdotti(int id, String nome, Set<Prodotto> pacchetto) {
        if (id <= 0) throw new IllegalArgumentException("L'id deve essere maggiore di 0.");
        this.id = id;
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
        this.pacchetto = pacchetto;
    }

    // Getter
    public Set<Prodotto> getPacchetto() {
        return pacchetto;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Setter
    public void setPacchetto(Set<Prodotto> pacchetto) {
        this.pacchetto = Objects.requireNonNull(pacchetto, "Il pacchetto non può essere nullo.");
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("L'id deve essere maggiore di 0.");
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
    }
}
