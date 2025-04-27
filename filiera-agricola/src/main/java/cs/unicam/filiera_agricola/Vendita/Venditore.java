package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import jakarta.persistence.*;
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

    public void creaProdotto(ProdottiController prodotti) {
        prodotti.addProdotto(this.getId(), new Prodotto());
    }

    public void eliminaProdotto(ProdottiController prodotti, int id) {
        prodotti.deleteProdotto(id);
    }

    public void modificaProdotto(ProdottiController prodotti, int id, Prodotto prodottoModificato) {
        prodotti.updateProdotto(id, prodottoModificato);
    }

    public void modificaQuantita(ProdottiController prodotti, int id, int quantita) {
        prodotti.modificaQuantita(id, quantita);
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