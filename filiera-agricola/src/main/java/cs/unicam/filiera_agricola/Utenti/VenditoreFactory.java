package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Vendita.*;

import java.util.Arrays;

import static cs.unicam.filiera_agricola.Utenti.Ruolo.*;

public class VenditoreFactory implements UtenteFactory {
    private final Ruolo ruolo;

    public VenditoreFactory(Ruolo ruolo) {
        if (!Arrays.asList(VENDITORE, PRODUTTORE, TRASFORMATORE, DISTRIBUTORE).contains(ruolo)) {
            throw new IllegalArgumentException("Ruolo non valido per VenditoreFactory");
        }
        this.ruolo = ruolo;
    }

    @Override
    public UtenteRegistrato creaUtente(String username, String nomeUtente, String cognome,
                                       String email, String password, Luogo luogo) {
        throw new UnsupportedOperationException("Usa il metodo con partitaIva per creare un venditore");
    }

    public UtenteRegistrato creaUtente(String username, String nomeUtente, String cognome,
                                       String email, String password, Luogo luogo, String partitaIva) {

        return switch (ruolo) {
            case PRODUTTORE -> new Produttore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case TRASFORMATORE -> new Trasformatore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            case DISTRIBUTORE -> new Distributore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
            default -> new Venditore(username, nomeUtente, cognome, email, password, luogo, partitaIva);
        };
    }
}

