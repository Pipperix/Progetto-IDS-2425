package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Prodotti.*;
import cs.unicam.filiera_agricola.Prodotti.Ordine;
import cs.unicam.filiera_agricola.Utenti.*;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class Acquirente extends UtenteRegistrato {
/*
    @ElementCollection
    private Map<Prodotto, Integer> carrello = new HashMap<>();
    @ElementCollection
    private Set<Ordine> ordini = new LinkedHashSet<>();


    @OneToMany(mappedBy = "acquirente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Carrello> carrello = new HashSet<>();

    @OneToMany(mappedBy = "acquirente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Ordine> ordini = new LinkedHashSet<>();

 */

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    public Acquirente() {}

    public Acquirente(String username, String nome, String cognome, String email, String password,
                      Luogo luogo) {
        super(username, nome, cognome, email, password, luogo, Ruolo.ACQUIRENTE);
    }

    /*
    public void aggiungiProdotto(Prodotto prodotto, int quantità){
        carrello.put(prodotto, carrello.getOrDefault(prodotto, 0) + quantità);
    }

    // Aggiungi prodotto al carrello
    public void aggiungiProdotto(Prodotto prodotto, int quantità) {
        Carrello item = new Carrello(this, prodotto, quantità);
        carrello.add(item);  // Aggiungi il prodotto con la quantità al carrello
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

    // Rimuovi prodotto dal carrello
    public void rimuoviProdotto(Prodotto prodotto, int quantita) {
        for (Carrello item : carrello) {
            if (item.getProdotto().equals(prodotto)) {
                int nuovaQuantita = item.getQuantita() - quantita;
                if (nuovaQuantita > 0) {
                    item.setQuantita(nuovaQuantita);  // Aggiorna la quantità
                } else {
                    carrello.remove(item);  // Rimuovi l'item se la quantità è 0 o inferiore
                }
                break;
            }
        }
    }

    public void selezionaPagamento(MetodoPagamento metodo){
        this.metodoPagamento = metodo;
    }


    public void confermaAcquisto() {
        HandlerOrdine handlerOrdine = new HandlerOrdine();
        // Crea la mappa dei prodotti nel carrello
        Map<Prodotto, Integer> prodottiNelCarrello = new HashMap<>();
        for (Carrello item : carrello) {
            prodottiNelCarrello.put(item.getProdotto(), item.getQuantita());
            // Effettua il pagamento
            HandlerPagamenti.getInstance().effettuaPagamento(this.getUsername(), metodoPagamento);
            // Creazione nuovo ordine con i prodotti nel carrello
            handlerOrdine.creaOrdine(this.getId(), prodottiNelCarrello, metodoPagamento);
            this.svuotaCarrello();
        }
    }

    public void svuotaCarrello() {
        carrello.clear();
    }

    public Set<Carrello> getCarrello() {return carrello;}

    public Set<Ordine> getOrdini() {return ordini;}

    public MetodoPagamento getMetodoPagamento() {return metodoPagamento;}

     */
}