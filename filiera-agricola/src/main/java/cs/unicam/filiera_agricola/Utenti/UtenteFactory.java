package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Vendita.*;

public interface UtenteFactory {

    UtenteRegistrato creaUtente(String username, String nomeUtente, String cognome,
                                       String email, String password, Luogo luogo);

}

