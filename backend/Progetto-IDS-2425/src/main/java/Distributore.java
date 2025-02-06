import java.time.LocalDate;
import java.util.Set;

public class Distributore extends Venditore {

    public Distributore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                        LocalDate dataDiNascita, String partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    public PacchettoDiProdotti creaPacchettoDiProdotti(Set<Prodotto> prodotti) {
        return new PacchettoDiProdotti(prodotti);
    }
}
