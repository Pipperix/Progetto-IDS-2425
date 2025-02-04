import java.time.LocalDate;

public class Trasformatore extends Venditore {

    // Costruttore estende Venditore
    public Trasformatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                         LocalDate dataDiNascita, String partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    // Aggiungi ProcessoTrasformazione
    public void aggiungiProcessoTrasformazione(ProdottoTrasformato prodotto, String nome, String descrizione) {
        prodotto.getProcesso().getTrasformazioni().add(new ProcessoTrasformazione(nome, descrizione));
    }

    // Aggiungi Certificazione al ProdottoTrasformato
    public void aggiungiCertificazione(ProdottoTrasformato prodotto, int id, String nome, String descrizione) {
        prodotto.getDescrizioneProdotto().setCertificazione(new Certificazione(id, nome, descrizione));
    }

    @Override
    public void creaProdotto(int id, String nome, DescrizioneProdotto descrizioneProdotto, int quantita) {
        ProdottoTrasformato prodottoTrasformato = new ProdottoTrasformato(id, nome, descrizioneProdotto, quantita);
        // Aggiungi ProdottoTrasformato al db
        // new ProdottoTrasformato(id, nome, descrizioneProdotto, quantita);
    }

    public void eliminaProdotto(ProdottoTrasformato prodotto) {
        // Elimina prodotto (prodotto.getId())
        // Logica db
    }

    public void modificaProdotto(ProdottoTrasformato prodotto, String descrizione, double prezzo) {
        prodotto.setDescrizioneProdotto(new DescrizioneProdotto(descrizione, prezzo));
    }

    public void modificaPrezzo(ProdottoTrasformato prodotto, double prezzo) {
        prodotto.getDescrizioneProdotto().setPrezzo(prezzo);
    }

    public void modificaDescrizione(ProdottoTrasformato prodotto, String descrizione) {
        prodotto.getDescrizioneProdotto().setDescrizione(descrizione);
    }

}
