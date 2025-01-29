import java.time.LocalDate;

public class Produttore extends Venditore {

    public Produttore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita,
                      long partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }

    public Certificazione aggiungiCertificazione(int id, String nome, String descrizione) {
        return new Certificazione(id, nome, descrizione);
    }
}
