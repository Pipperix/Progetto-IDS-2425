import java.util.Objects;

public class Luogo {

    private String nome;
    private Indirizzo indirizzo;
    private Posizione posizione;

    public Luogo(String nome, Indirizzo indirizzo, Posizione posizione) {
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
        this.indirizzo = Objects.requireNonNull(indirizzo, "L'indirizzo non può essere nullo.");
        this.posizione = Objects.requireNonNull(posizione, "La posizione non può essere nulla.");
    }

    // Getter
    public String getNome() {
        return nome;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = Objects.requireNonNull(indirizzo, "L'indirizzo non può essere nullo.");
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = Objects.requireNonNull(posizione, "La posizione non può essere nulla.");
    }
}
