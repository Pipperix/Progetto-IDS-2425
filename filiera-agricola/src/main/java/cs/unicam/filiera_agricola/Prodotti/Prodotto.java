package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Vendita.Venditore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private double prezzo;
    private LocalDate dataScadenza;

    @OneToOne(mappedBy = "prodotto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Descrizione descrizione;

    @ManyToMany(mappedBy = "prodotti")
    @JsonBackReference
    //@JsonIgnore
    private Set<PacchettoDiProdotti> pacchetti = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "venditore_id")
    @JsonBackReference // Evita la serializzazione del venditore nel JSON del prodotto
    private Venditore venditore;

    public Prodotto() {}

    public Prodotto(String nome, double prezzo, LocalDate dataScadenza) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.dataScadenza = dataScadenza;
        //this.venditore = venditore;
    }

    public Prodotto(String nome, double prezzo, LocalDate dataScadenza, Descrizione descrizione) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.dataScadenza = dataScadenza;
        this.descrizione = descrizione;
        //this.pacchetti = prodotto.getPacchetti();
        //this.venditore = prodotto.getVenditore();
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

    public Set<PacchettoDiProdotti> getPacchetti() {
        return pacchetti;
    }

    public Venditore getVenditore() {
        return venditore;
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

    public void setPacchetti(Set<PacchettoDiProdotti> pacchetti) {
        this.pacchetti = pacchetti;
    }

    public void setCertificazione(Certificazione certificazione) {
        this.descrizione.aggiungiCertificazione(certificazione);
    }

    public void setProcessoTrasformazione(ProcessoTrasformazione processoTrasformazione) {
        this.descrizione.aggiungiProcessoTrasformazione(processoTrasformazione);
    }

    public void setVenditore(Venditore venditore) {
        this.venditore = venditore;
        venditore.creaProdotto(this); // Imposta il riferimento bidirezionale
    }

}
