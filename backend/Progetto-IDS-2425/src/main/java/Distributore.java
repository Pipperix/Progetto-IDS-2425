import java.time.LocalDate;

public class Distributore extends Venditore {

    public Distributore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                        LocalDate dataDiNascita, long partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    public PacchettoDiProdotti creaPacchettoDiProdotti(int id, String nome) {
        return new PacchettoDiProdotti(id, nome);
    }
}
