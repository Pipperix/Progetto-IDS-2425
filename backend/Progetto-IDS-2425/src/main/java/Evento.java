import java.time.LocalDateTime;
import java.util.Objects;

public class Evento {

    private String nome;
    private String tipo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Luogo luogo;
    private String descrizione;
    private int capienzaPersone;


    public Evento(String nome, String tipo, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                  Luogo luogo, int capienzaPersone) {
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.dataInizio = Objects.requireNonNull(dataInizio, "La data d'inizio non può essere nullo.");
        this.dataFine = dataFine;
        this.luogo = Objects.requireNonNull(luogo, "Il luogo non può essere nullo.");
        this.capienzaPersone = capienzaPersone;
    }

    // Getter
    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public Luogo getLuogo() {
        return luogo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getCapienzaPersone() {
        return capienzaPersone;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome, "Il nome non può essere nullo.");
    }

    public void setTipo(String tipo) {
        this. tipo = tipo;
    }

    public void setDataInizio(LocalDateTime dataInizio) {
        this.dataInizio = Objects.requireNonNull(dataInizio, "La data d'inizio non può essere nullo.");
    }

    public void setDataFine(LocalDateTime dataFine) {
        this.dataFine = dataFine;
    }

    public void setLuogo(Luogo luogo) {
        this.luogo = Objects.requireNonNull(luogo, "Il luogo non può essere nullo.");
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setCapienzaPersone(int capienzaPersone) {
        this.capienzaPersone = capienzaPersone;
    }
}
