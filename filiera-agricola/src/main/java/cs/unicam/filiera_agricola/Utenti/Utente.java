package cs.unicam.filiera_agricola.Utenti;

import org.springframework.http.ResponseEntity;

public interface Utente {

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    void visualizzaContenuti();

    // Metodo per la visualizzazione della descrizione del prodotto
    void visualizzaDescrizione(int id);

    // Login
    boolean autenticazione();

    // Signup
    boolean registrazione();

    void visualizzaMappa();
}
