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

    // da capire
    public void selezionaPagamento(MetodoDiPagamento metodo) {
        this.metodoDiPagamento = metodo;
        System.out.println("Metodo di pagamento selezionato: " + metodo);
    }

    public void confermaAcquisto() {
        if (carrello.isEmpty()) {
            System.out.println("Il carrello è vuoto. Aggiungi prodotti prima di confermare l'acquisto.");
        }

        // Aggiorna le quantità disponibili --> logica DB !
        for (Map.Entry<Prodotto, Integer> entry : carrello.entrySet()) {
            Prodotto prodotto = entry.getKey();
            int quantitaRichiesta = entry.getValue();
            prodotto.setQuantita(prodotto.getQuantita() - quantitaRichiesta);
        }

        // Creazione dell'oggetto Ordine
        // L'ID dell'ordine è un numero casuale per il momento, sarà poi sostituito da un ID univoco ottenuto
        // attraverso il database
        Ordine nuovoOrdine = new Ordine(LocalDateTime.now(), 001, Map.copyOf(carrello));
        System.out.println("Ordine confermato! Riepilogo:\n" + nuovoOrdine);

        // Svuotare il carrello
        svuotaCarrello();
    }

    // aggiungere una stampa che mi dice anche ora e luogo dell'evento
    public void prenotaEvento(Evento evento) {
        System.out.println("Evento prenotato: " + evento);
    }

}

