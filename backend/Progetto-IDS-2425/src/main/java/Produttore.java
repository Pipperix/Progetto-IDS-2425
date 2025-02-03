import java.time.LocalDate;

public class Produttore extends Venditore {

    // Costruttore estende Venditore
    public Produttore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita,
                      String partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    // Aggiungi Certificazione al Prodotto
    public void aggiungiCertificazione(Prodotto prodotto, int id, String nome, String descrizione) {
        // Aggiungere certificazione al db
        prodotto.getDescrizioneProdotto().setCertificazione(new Certificazione(id, nome, descrizione));
    }
}
