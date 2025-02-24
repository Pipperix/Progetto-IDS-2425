package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;
import java.util.List;
import jakarta.persistence.*;

@Entity
public Trasformatore extends Venditore{
    public Trasformatore() {}

    public Trasformatore(String partitaIva) {
        super(partitaIva); // Richiama il costruttore della superclasse Venditore
    }

    public void aggiungiProcessoTrasformato(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        processoTrasformazione.setProdotto(prodottoTrasformato);
        prodottoTrasformato.getProcessiTrasformazione().add(processoTrasformazione);
    }

    public void rimuoviProcessoTrasformazione(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        prodottoTrasformato.getProcessiTrasformazione().remove(processoTrasformazione);
    }

    public void aggiungiCertificazione(Prodotto prodotto, Certificazione certificazione) {
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().aggiungiCertificazione(certificazione);
        }
    }

    public void rimuoviCertificazione(Prodotto prodotto, Certificazione certificazione) {
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().getCertificazioni().remove(certificazione);
        }
    }

    public Prodotto creaProdottoTrasformato(String nome, double prezzo, int quantita) {
        Prodotto prodottoTrasformato = new Prodotto(nome, prezzo, null);
        this.getProdottiCreati().add(prodottoTrasformato);
        return prodottoTrasformato;
    }
}