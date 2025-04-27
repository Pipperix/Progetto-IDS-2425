package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Acquisto.Acquirente;
import cs.unicam.filiera_agricola.Eventi.Animatore;
import cs.unicam.filiera_agricola.Piattaforma.Gestore;
import cs.unicam.filiera_agricola.Piattaforma.Curatore;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import java.util.Arrays;

public class UtenteBaseFactory implements UtenteFactory {
    private final Ruolo ruolo;

    public UtenteBaseFactory(Ruolo ruolo) {
        if (Arrays.asList(Ruolo.VENDITORE, Ruolo.PRODUTTORE, Ruolo.TRASFORMATORE, Ruolo.DISTRIBUTORE).contains(ruolo)) {
            throw new IllegalArgumentException("Ruolo non valido per UtenteBaseFactory");
        }
        this.ruolo = ruolo;
    }

    @Override
    public UtenteRegistrato creaUtente(String username, String nomeUtente, String cognome,
                                       String email, String password, Luogo luogo) {
        return switch (ruolo) {
            case ANIMATORE -> new Animatore(username, nomeUtente, cognome, email, password, luogo);
            case ACQUIRENTE -> new Acquirente(username, nomeUtente, cognome, email, password, luogo);
            case GESTORE -> new Gestore(username, nomeUtente, cognome, email, password, luogo);
            case CURATORE -> new Curatore(username, nomeUtente, cognome, email, password, luogo);
            default -> new UtenteRegistrato(username, nomeUtente, cognome, email, password, luogo, ruolo);
        };
    }
}