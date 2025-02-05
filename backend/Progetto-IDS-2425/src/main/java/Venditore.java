import java.time.LocalDate;

public abstract class Venditore extends UtenteRegistrato{

    // Possono essere nullabili (il venditore ha partita iva propria o quella dell'azienda)
    private String partitaIva;
    private Azienda azienda;

    // Costruttore estende UtenteRegistrato
    public Venditore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                     LocalDate dataDiNascita, String partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
        this.partitaIva = partitaIva;
        this.azienda = azienda;
    }

    // Getters
    public String getPartitaIva() { return partitaIva; }
    public Azienda getAzienda() { return azienda; }

    // Setters
    public void setPartitaIva(String partitaIva) { this.partitaIva = partitaIva; }
    public void setAzienda(Azienda azienda) { this.azienda = azienda; }

    // Creazione Prodotto
    public void creaProdotto(int id, String nome, String descrizione, double prezzo, int quantita) {
        Prodotto prodotto = new Prodotto(id, nome, descrizione, prezzo, quantita);
        prodotto.setProcessiTrasformazione(null); // Il prodotto non ha processi di trasformazione
        // Aggiungi 'prodotto' al db
        // Logica db
    }

    // Eliminazione Prodotto
    public void eliminaProdotto(Prodotto prodotto) {
        // Elimina prodotto dal db (prodotto.getId())
        // Logica db
    }

    // Modifica Nome del prodotto
    public void modificaNome(Prodotto prodotto, String nome) {
        prodotto.setNome(nome);
    }

    // Modifica Descrizione del prodotto
    public void modificaDescrizione(Prodotto prodotto, String descrizione) {
        prodotto.setDescrizione(descrizione);
    }

    // Modifica Prezzo del prodotto
    public void modificaPrezzo(Prodotto prodotto, double prezzo) {
        prodotto.setPrezzo(prezzo);
    }

    // Modifica Quantità del prodotto
    public void modificaQuantita(Prodotto prodotto, int quantita) {
        prodotto.setQuantita(quantita);
    }

}
