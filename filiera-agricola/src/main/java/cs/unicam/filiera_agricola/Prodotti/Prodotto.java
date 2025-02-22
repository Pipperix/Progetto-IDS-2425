package cs.unicam.filiera_agricola.Prodotti;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private double prezzo;
    private LocalDate dataScadenza;

    @OneToOne(mappedBy = "prodotto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Descrizione descrizione;

    public Prodotto() {}

    public Prodotto(String nome, double prezzo, LocalDate dataScadenza) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.dataScadenza = dataScadenza;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Descrizione getDescrizione() {
        return descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setDescrizione(Descrizione descrizione) {
        this.descrizione = descrizione;
        descrizione.setProdotto(this); // Imposta il riferimento bidirezionale
    }
}
