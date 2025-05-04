package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Eventi.EventiController;
import cs.unicam.filiera_agricola.Prodotti.*;
import cs.unicam.filiera_agricola.Utenti.*;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Acquirente extends UtenteRegistrato {

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    public Acquirente() {}

    public Acquirente(String username, String nome, String cognome, String email, String password,
                      Luogo luogo) {
        super(username, nome, cognome, email, password, luogo, Ruolo.ACQUIRENTE);
    }

    public void getCarrello(CarrelloController carrello) {
        carrello.getCarrello(this.getId());
    }

    public void aggiungiProdotto(CarrelloController carrello, int prodottoId, int quantita) {
        carrello.aggiungiProdotto(this.getId(), prodottoId, quantita);
    }

    public void rimuoviProdotto(CarrelloController carrello, int prodottoId) {
        carrello.rimuoviProdotto(this.getId(), prodottoId);
    }

    public void svuotaCarrello(CarrelloController carrello) {
        carrello.svuotaCarrello(this.getId());
    }

    public void calcolaTotale(CarrelloController carrello) {
        carrello.calcolaTotale(this.getId());
    }

    public void effettuaPagamento(CarrelloController carrello, MetodoPagamento metodoPagamento) {
        carrello.effettuaPagamento(this.getUsername(), metodoPagamento);
    }

    public void prenotaEvento(EventiController eventi, int eventoId) {
        eventi.prenotaEvento(eventoId, this.getUsername());
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }
}