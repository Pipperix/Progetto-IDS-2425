package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomeEvento;
    private String tipo;
    private String descrizione;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    @Embedded
    private Luogo luogo;
    private int capienzaPersone;

    @ManyToOne
    @JoinColumn(name = "animatore_id", insertable=false, updatable=false)
    private Animatore animatore; // Riferimento all'animatore

    public Evento() {}

    public Evento(String nomeEvento, String tipo, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                  Luogo luogo, int capienzaPersone) {
        this.nomeEvento = Objects.requireNonNull(nomeEvento, "Il nome non può essere nullo.");
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.dataInizio = Objects.requireNonNull(dataInizio, "La data d'inizio non può essere nullo.");
        this.dataFine = dataFine;
        this.luogo = Objects.requireNonNull(luogo, "Il luogo non può essere nullo.");
        this.capienzaPersone = capienzaPersone;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getNomeEvento() {
        return nomeEvento;
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

    public Animatore getAnimatore() { return animatore; }

    // Setter
    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = Objects.requireNonNull(nomeEvento, "Il nome non può essere nullo.");
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

    public void setAnimatore(Animatore animatore) { this.animatore = animatore; }

}
