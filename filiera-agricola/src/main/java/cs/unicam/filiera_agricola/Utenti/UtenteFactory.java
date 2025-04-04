package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
import cs.unicam.filiera_agricola.Eventi.Animatore;
import cs.unicam.filiera_agricola.Piattaforma.Curatore;
import cs.unicam.filiera_agricola.Piattaforma.Gestore;
import cs.unicam.filiera_agricola.Vendita.*;

public class UtenteFactory {

    public static UtenteRegistrato creaUtente(Ruolo ruolo, String username, String nomeUtente, String cognome,
                                              String email, String password, Luogo luogo, String partitaIva) {
        switch (ruolo) {
            //case UTENTE:
                //return new UtenteRegistrato(username, nomeUtente, cognome, email, password, luogo);
            case ANIMATORE:
                return new Animatore(username, nomeUtente, cognome, email, password, luogo);
            case VENDITORE:
                return new Venditore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case PRODUTTORE:
                return new Produttore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case TRASFORMATORE:
                return new Trasformatore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case DISTRIBUTORE:
                return new Distributore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case ACQUIRENTE:
                return new Acquirente(username, nomeUtente, cognome, email, password, luogo);
            case GESTORE:
                return new Gestore(username, nomeUtente, cognome, email, password, luogo);
            case CURATORE:
                return new Curatore(username, nomeUtente, cognome, email, password, luogo);
            default:
                return new UtenteRegistrato(username, nomeUtente, cognome, email, password, luogo, ruolo);
                //throw new IllegalArgumentException("Ruolo non valido: " + ruolo);
        }
    }
}

