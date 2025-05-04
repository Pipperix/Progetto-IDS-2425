package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiController;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;

@Entity
public class Gestore extends UtenteRegistrato {

    public Gestore() {}

    public Gestore(String username, String nomeUtente, String cognome, String email, String password,
                   Luogo luogo) {
        super(username, nomeUtente, cognome, email, password, luogo, Ruolo.GESTORE);
    }

    public void rimuoviProdottiScaduti(PiattaformaController piattaforma) {
        piattaforma.rimuoviProdottiScaduti();
    }

    public void autorizzaAccount(int utenteId, PiattaformaController piattaforma) {
        piattaforma.autorizzaAccount(utenteId);
    }

    public void getUtentiDaAutorizzare(UtentiController utenti) {
        utenti.getUtentiDaAutorizzare();
    }

}
