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
    private String descrizione;
    private int quantita;
    private boolean approvato;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    //@JsonBackReference(value = "prodotto-descrizione")
    @JsonBackReference
    private Prodotto prodotto;

    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonManagedReference(value = "descrizione-certificazioni")
    @JsonManagedReference
    private Set<Certificazione> certificazioni = new HashSet<>();

    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonManagedReference(value = "descrizione-processi")
    @JsonManagedReference
    private Set<ProcessoTrasformazione> processiTrasformazione = new HashSet<>();

    public Descrizione() {}

    public Descrizione(String descrizione, int quantita, boolean approvato) {
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.approvato = approvato;
    }

    public Descrizione(String descrizione, int quantita, Set<Certificazione> certificazioni,
                       Set<ProcessoTrasformazione> processitrasformazione) {
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.certificazioni = certificazioni;
        this.processiTrasformazione = processitrasformazione;
    }

    public String getDescrizione() {
        return descrizione;
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

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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

//    public void rimuoviCertificazione(Certificazione certificazione) {
//        certificazioni.remove(certificazione);
//        certificazione.setDescrizione(null);
//    }

    public void aggiungiProcessoTrasformazione(ProcessoTrasformazione processo) {
        processiTrasformazione.add(processo);
        processo.setDescrizione(this);
    }

//    public void rimuoviProcessoTrasformazione(ProcessoTrasformazione processo) {
//        processiTrasformazione.remove(processo);
//        processo.setDescrizione(null);
//    }
}
