package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private Prodotto prodotto;

    @OneToMany(mappedBy = "descrizione", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Certificazione> certificazioni = new ArrayList<>();

    public Descrizione() {}

    public Descrizione(String descrizione, int quantita, boolean approvato) {
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.approvato = approvato;
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

    public List<Certificazione> getCertificazioni() {
        return certificazioni;
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

    public void setCertificazioni(List<Certificazione> certificazioni) {
        this.certificazioni = certificazioni;
    }

    public void aggiungiCertificazione(Certificazione certificazione) {
        certificazioni.add(certificazione);
        certificazione.setDescrizione(this);
    }
}
