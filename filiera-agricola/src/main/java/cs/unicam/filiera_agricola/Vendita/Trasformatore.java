package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.ProcessoTrasformazione;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Trasformatore extends Venditore{

    public Trasformatore() {}

    public Trasformatore(String username, String nome, String cognome, String email, String password, Luogo luogo, String partitaIva) {
        super(username, nome, cognome, email, password, luogo, partitaIva);
    }

    public void aggiungiProcessoTrasformato(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        prodottoTrasformato.setProcessoTrasformazione(processoTrasformazione);
        ///prodottoTrasformato.getProcessiTrasformazione().add(processoTrasformazione);
    }

    public void rimuoviProcessoTrasformazione(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        prodottoTrasformato.getDescrizione().getProcessiTrasformazione().remove(processoTrasformazione);
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

    public Prodotto creaProdottoTrasformato(String nome, double prezzo, LocalDate dataScadenza) {
        Prodotto prodottoTrasformato = new Prodotto(nome, prezzo, null);
        this.getProdottiCreati().add(prodottoTrasformato);
        return prodottoTrasformato;
    }
}