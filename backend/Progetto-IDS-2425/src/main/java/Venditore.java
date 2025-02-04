import java.time.LocalDate;

public abstract class Venditore extends UtenteRegistrato{

    // Entrambi gli attributi sono annullabili (?)
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
    public void creaProdotto(int id, String nome, DescrizioneProdotto descrizioneProdotto, int quantita) {
        // Aggiungi prodotto al db
        // Logica db
    }
    public void creaProdotto(int id, String nome, String descrizione, double prezzo, int quantita) {
        Prodotto prodotto = new Prodotto(id, nome, new DescrizioneProdotto(descrizione, prezzo), quantita);
        // Aggiungi prodotto al db
        // Logica db
    }

    // Eliminazione Prodotto
    public void eliminaProdotto(Prodotto prodotto) {
        // Elimina prodotto (prodotto.getId())
        // Logica db
    }

    // Modifica Contenuto del prodotto (descrizione e prezzo)
    public void modificaProdotto(Prodotto prodotto, String descrizione, double prezzo) {
        prodotto.setDescrizioneProdotto(new DescrizioneProdotto(descrizione, prezzo));
    }

    // Modifica Prezzo del prodotto
    public void modificaPrezzo(Prodotto prodotto, double prezzo) {
        prodotto.getDescrizioneProdotto().setPrezzo(prezzo);
    }

    // Modifica Descrizione del prodotto
    public void modificaDescrizione(Prodotto prodotto, String descrizione) {
        prodotto.getDescrizioneProdotto().setDescrizione(descrizione);
    }

}
