package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ProcessoTrasformazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String dettagliProcesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descrizione_id")
    @JsonBackReference(value = "descrizione-processi")
    private Descrizione descrizione;

    public ProcessoTrasformazione() {}

    public ProcessoTrasformazione(String nome, String dettagliProcesso) {
        this.nome = nome;
        this.dettagliProcesso = dettagliProcesso;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDettagliProcesso() {
        return dettagliProcesso;
    }

    public Descrizione getDescrizione() {
        return descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDettagliProcesso(String dettagliProcesso) {
        this.dettagliProcesso = dettagliProcesso;
    }

    public void setDescrizione(Descrizione descrizione) {
        this.descrizione = descrizione;
    }

}
