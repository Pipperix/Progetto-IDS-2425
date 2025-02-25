package cs.unicam.filiera_agricola.Vendita;
import cs.unicam.filiera_agricola.Prodotti.*;
import cs.unicam.filiera_agricola.Utenti.HandlerUtenti;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Acquirente extends UtenteRegistrato {

    @ElementCollection
    private Map<Prodotto, Integer> carrello = new HashMap<>();
    @ElementCollection
    private Set<Ordine> ordini = new LinkedHashSet<>();
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    public Acquirente(String username, String nome, String cognome, String email, String password,
                      Luogo luogo) {
        super(username, nome, cognome, email, password, luogo, Ruolo.ACQUIRENTE);
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantità){
        carrello.put(prodotto, carrello.getOrDefault(prodotto, 0) + quantità);
    }

    public void rimuoviProdotto(Prodotto prodotto, int quantità){
        if (carrello.containsKey(prodotto)) {
            int nuovaQuantita = carrello.get(prodotto) - quantità;
            if (nuovaQuantita > 0) {
                carrello.put(prodotto, nuovaQuantita);
            } else {
                carrello.remove(prodotto);
            }
        }
    }

    public void selezionaPagamento(MetodoPagamento metodo){
        this.metodoPagamento = metodo;
    }

    public void confermaAcquisto(){
        HandlerOrdine handlerOrdine = new HandlerOrdine();
        Map<Prodotto, Integer> prodottiNelCarrello = this.getCarrello();
        HandlerPagamenti.getInstance().effettuaPagamento(this, metodoPagamento);
        // Creazione nuovo ordine con i prodotti nel carrello
        handlerOrdine.creaOrdine(this.getId(), prodottiNelCarrello, metodoPagamento);
        this.svuotaCarrello();
    }

    public void svuotaCarrello(){
        carrello.clear();
    }

    public Map<Prodotto, Integer> getCarrello() {return carrello;}

    public Set<Ordine> getOrdini() {return ordini;}

    public MetodoPagamento getMetodoPagamento() {return metodoPagamento;}
}