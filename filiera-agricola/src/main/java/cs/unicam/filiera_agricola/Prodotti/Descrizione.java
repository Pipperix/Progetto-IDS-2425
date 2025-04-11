package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Descrizione {
    @Id
    @Column(name = "prodotto_id")
    private int id;
    private String dettaglio;
    private int quantita;
    @Column
    private boolean approvato = false;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference(value = "prodotto-descrizione") // Questo è il lato "back" della relazione
    private Prodotto prodotto;

    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "descrizione-certificazioni")
    private Set<Certificazione> certificazioni = new HashSet<>();

    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference(value = "descrizione-processi")
    private Set<ProcessoTrasformazione> processiTrasformazione = new HashSet<>();

    public Descrizione() {
    }

    public Descrizione(String dettaglio, int quantita) {
        this.dettaglio = dettaglio;
        this.quantita = quantita;
        this.approvato = false;
    }

    /*
    public Descrizione(String dettaglio, int quantita, Set<Certificazione> certificazioni,
                       Set<ProcessoTrasformazione> processiTrasformazione) {
        this.dettaglio = dettaglio;
        this.quantita = quantita;
        this.certificazioni = certificazioni;
        this.processiTrasformazione = processiTrasformazione;
    }
     */

    public String getDettaglio() {
        return dettaglio;
    }

    public int getQuantita() {
        return quantita;
    }

    public boolean isApprovato() {
        return approvato;
    }

    public Set<Certificazione> getCertificazioni() {
        return certificazioni;
    }

    public Set<ProcessoTrasformazione> getProcessiTrasformazione() {
        return processiTrasformazione;
    }

    public void setDettaglio(String dettaglio) {
        this.dettaglio = dettaglio;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setApprovato(boolean approvato) {
        this.approvato = approvato;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void setCertificazioni(Set<Certificazione> certificazioni) {
        this.certificazioni = certificazioni;
    }

    public void setProcessiTrasformazione(Set<ProcessoTrasformazione> processiTrasformazione) {
        this.processiTrasformazione = processiTrasformazione;
    }

    public void aggiungiCertificazione(Certificazione certificazione) {
        certificazioni.add(certificazione);
        certificazione.setDescrizione(this);
    }

    public void rimuoviCertificazione(Certificazione certificazione) {
        certificazioni.remove(certificazione);
        certificazione.setDescrizione(null);
    }

    public void aggiungiProcessoTrasformazione(ProcessoTrasformazione processo) {
        processiTrasformazione.add(processo);
        processo.setDescrizione(this);
    }

    public void rimuoviProcessoTrasformazione(ProcessoTrasformazione processo) {
       processiTrasformazione.remove(processo);
       processo.setDescrizione(null);
    }
}
