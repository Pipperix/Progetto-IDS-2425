import java.time.LocalDate;
import java.util.HashSet;

public class Trasformatore extends Venditore {

    // Costruttore estende Venditore
    public Trasformatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                         LocalDate dataDiNascita, String partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    // Creazione Prodotto Trasformato
    public void creaProdottoTrasformato(int id, String nome, String descrizione, double prezzo, int quantita) {
        Prodotto prodottoTrasformato = new Prodotto(id, nome, descrizione, prezzo, quantita);
        prodottoTrasformato.setProcessiTrasformazione(new HashSet<>());
    }

    // Aggiungi Processo di Trasformazione al Prodotto
    public void aggiungiProcessoTrasformazione(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        prodottoTrasformato.getProcessiTrasformazione().add(processoTrasformazione);
    }

    // Rimuovi Processo di Trasformazione dal Prodotto
    public void rimuoviProcessoTrasformazione(Prodotto prodottoTrasformato, ProcessoTrasformazione processoTrasformazione) {
        prodottoTrasformato.getProcessiTrasformazione().remove(processoTrasformazione);
    }

    // Aggiungi Certificazione al Prodotto
    public void aggiungiCertificazione(Prodotto prodottoTrasformato, Certificazione certificazione) {
        prodottoTrasformato.setCertificazione(certificazione);
    }

    // Modifica Certificazione del Prodotto
    public void modificaCertificazione(Prodotto prodottoTrasformato, Certificazione certificazione) {
        prodottoTrasformato.setCertificazione(certificazione);
    }

    // Rimuovi Certificazione dal Prodotto
    public void rimuoviCertificazione(Prodotto prodottoTrasformato) {
        prodottoTrasformato.setCertificazione(null);
    }

}
