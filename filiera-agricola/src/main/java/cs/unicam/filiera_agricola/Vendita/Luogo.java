package cs.unicam.filiera_agricola.Vendita;
import jakarta.persistence.Embeddable;

@Embeddable
public class Luogo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posizione_id", referencedColumnName = "id")
    private Posizione posizione;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "indirizzo_id", referencedColumnName = "id")
    private Indirizzo indirizzo;

    public Luogo() {}

    public Luogo(String nome, Posizione posizione, Indirizzo indirizzo) {
        this.nome = nome;
        this.posizione = posizione;
        this.indirizzo = indirizzo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }
}