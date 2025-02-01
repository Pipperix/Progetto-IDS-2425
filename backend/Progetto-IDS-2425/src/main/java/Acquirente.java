import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Acquirente extends UtenteRegistrato {
    Map<Prodotto, Integer> carrello = new HashMap<>();
    private MetodoDiPagamento metodoDiPagamento;

    public Acquirente(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                      LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
    }

    public void aggiungiProdotto (Prodotto prodotto, int quantita) {
        if (quantita <= prodotto.getQuantita()) {
            // per non sovrascrivere le quantità già presenti, aggiungiamo la quantità richiesta a
            // quella già presente nel carrello (quantità + 0 se l'oggetto non era presente nel carrello)
            carrello.put(prodotto, carrello.getOrDefault(prodotto, 0) + quantita);
            System.out.println(quantita + "x " + prodotto.getNome() + " aggiunto al carrello.");
        } else {
            System.out.println("Quantità non disponibile");
        }
    }

    public void rimuoviProdotto (Prodotto prodotto) {
        if (carrello.containsKey(prodotto)) {
            carrello.remove(prodotto);
            System.out.println(prodotto.getNome() + " rimosso dal carrello.");
        } else {
            System.out.println("Il prodotto non è nel carrello.");
        }
    }

    public void svuotaCarrello() {
        carrello.clear();
        System.out.println("Carrello svuotato.");
    }

    public void selezionaPagamento(MetodoDiPagamento metodo) {
        this.metodoDiPagamento = metodo;
        System.out.println("Metodo di pagamento selezionato: " + metodo);
    }

    public void confermaAcquisto() {
        if (carrello.isEmpty()) {
            System.out.println("Il carrello è vuoto. Aggiungi prodotti prima di confermare l'acquisto.");
        }

        // Aggiorna le quantità disponibili
        for (Map.Entry<Prodotto, Integer> entry : carrello.entrySet()) {
            Prodotto prodotto = entry.getKey();
            int quantitaRichiesta = entry.getValue();
            prodotto.setQuantita(prodotto.getQuantita() - quantitaRichiesta);
        }

        // Creazione dell'oggetto Ordine
        // L'ID dell'ordine è un numero casuale per il momento, sarà poi sostituito da un ID univoco ottenuto
        // attraverso il database
        Ordine nuovoOrdine = new Ordine(LocalDateTime.now(), (int) (Math.random() * 1000));
        System.out.println("Ordine confermato! Riepilogo:\n" + nuovoOrdine);

        // Svuotare il carrello
        svuotaCarrello();
    }

}

/* metodo per me non necessario per controllare nuovamente la disponibilità dei prodotti
    for (Map.Entry<Prodotto, Integer> entry : carrello.entrySet()) {
        Prodotto prodotto = entry.getKey();
        int quantitaRichiesta = entry.getValue();

            if (quantitaRichiesta > prodotto.getQuantita()) {
        System.out.println("Errore: la quantità richiesta per " + prodotto.getNome() + " non è più disponibile.");
        return;
        }
        }

 */
