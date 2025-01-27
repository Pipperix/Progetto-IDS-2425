import java.time.LocalDate;

public class Venditore extends UtenteRegistrato{

    // entrambi gli attributi sono annullabili
    private long partitaIva;
    private Azienda azienda;


    public Venditore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                     LocalDate dataDiNascita, long partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
        this.partitaIva = partitaIva;
        this.azienda = azienda;
    }


    public Prodotto creaContenuto(int id, String nome, DescrizioneProdotto descrizioneProdotto) {
        return new Prodotto(id, nome, descrizioneProdotto);
    }


    public void eliminaContenuto(Prodotto prodotto) {
    }

    /*
    public DescrizioneProdotto modificaContenuto(Prodotto prodotto) {
        return new DescrizioneProdotto(prodotto);
    }
     */

    // forse meglio distinguere modificaDescrizione e modificaPrezzo ??
    public DescrizioneProdotto modificaContenuto(String descrizione, double prezzo) {
        return new DescrizioneProdotto(descrizione, prezzo);
    }
}
