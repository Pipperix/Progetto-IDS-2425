package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Venditore extends UtenteRegistrato {
    @Id
    private int id;
    private String partitaIva;

    @OneToMany(mappedBy = "venditore", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Prodotto> prodottiCreati = new HashSet<>();

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "azienda_id", referencedColumnName = "id", nullable = true)
    private Azienda azienda;


    public Venditore() {}

    public Venditore(String username, String nomeUtente, String cognome, String email, String password,
                     Luogo luogo, String partitaIva) {
        super(username, nomeUtente, cognome, email, password, luogo, Ruolo.VENDITORE);
        this.partitaIva = partitaIva;
    }

    public void creaProdotto(String nome, double prezzo, LocalDate dataScadenza) {
        Prodotto nuovoProdotto = new Prodotto(nome, prezzo, dataScadenza);
        nuovoProdotto.setVenditore(this);  // Imposta il venditore del prodotto
        prodottiCreati.add(nuovoProdotto);
    }

    public void creaProdotto (Prodotto prodotto) {
        prodotto.setVenditore(this);
        prodottiCreati.add(prodotto);
    }

    public void eliminaProdotto(Prodotto prodotto){
        prodottiCreati.remove(prodotto);
        prodotto.setVenditore(null);
    }

    public void modificaNome(Prodotto prodotto, String nome){
        if (prodottiCreati.contains(prodotto)) {
            prodotto.setNome(nome);
        }
    }

    public void modificaDescrizione(Prodotto prodotto, String descrizione){
        if (prodottiCreati.contains(prodotto) && prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().setDescrizione(descrizione);
        }
    }

    public void modificaPrezzo(Prodotto prodotto, double prezzo){
        if (prodottiCreati.contains(prodotto)) {
            prodotto.setPrezzo(prezzo);
        }
    }

    public void modificaQuantita(Prodotto prodotto, int quantita){
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().setQuantita(quantita);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Set<Prodotto> getProdottiCreati() {
        return prodottiCreati;
    }
}