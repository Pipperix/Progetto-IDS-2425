package cs.unicam.filiera_agricola.Eventi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomeEvento;
    private String tipo;
    private String descrizioneEvento;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    @Embedded
    private Luogo luogo;
    private int capienzaPersone;

    @ManyToOne
    @JoinColumn(name = "animatoreId", insertable=false, updatable=false)
    private Animatore animatore; // Riferimento all'animatore

    @ManyToMany
    @JoinTable(
            name = "prenotazioni_evento",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    @JsonManagedReference
    private Set<UtenteRegistrato> utentiPrenotati = new HashSet<>();

    // se c'è disponibilità di posti all'evento, l'utente viene aggiunto alla lista degli utenti prenotati
    public boolean prenotazione(UtenteRegistrato utente) {
        if (capienzaPersone > 0) {
            utentiPrenotati.add(utente);
            utente.getEventiPrenotati().add(this); // assegnamento bidirezionale
            capienzaPersone--;
            return true;
        }
        return false;
    }

    public Evento() {}

    public Evento(String nomeEvento, String tipo, String descrizioneEvento, LocalDateTime dataInizio, LocalDateTime dataFine,
                  Luogo luogo, int capienzaPersone) {
        this.nomeEvento = nomeEvento;
        this.tipo = tipo;
        this.descrizioneEvento = descrizioneEvento;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.luogo = luogo;
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

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public int getCapienzaPersone() {
        return capienzaPersone;
    }

    public Animatore getAnimatore() { return animatore; }

    public Set<UtenteRegistrato> getUtentiPrenotati() {
        return utentiPrenotati;
    }

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

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public void setCapienzaPersone(int capienzaPersone) {
        this.capienzaPersone = capienzaPersone;
    }

    public void setAnimatore(Animatore animatore) { this.animatore = animatore; }

}
