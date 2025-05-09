package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Certificazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String dettagliCertificazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descrizione_id")
    @JsonBackReference(value = "descrizione-certificazioni")
    private Descrizione descrizione;

    public Certificazione() {}

    public Certificazione(String nome, String dettagliCertificazione) {
        this.nome = nome;
        this.dettagliCertificazione = dettagliCertificazione;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Descrizione getDescrizione() {
        return descrizione;
    }

    public String getDettagliCertificazione() {
        return dettagliCertificazione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(Descrizione descrizione) {
        this.descrizione = descrizione;
    }

    public void setDettagliCertificazione(String dettagliCertificazione) {
        this.dettagliCertificazione = dettagliCertificazione;
    }
}
