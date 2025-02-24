package cs.unicam.filiera_agricola.Utenti;

import org.springframework.http.ResponseEntity;

public interface Utente {

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    ResponseEntity<Object> visualizzaContenuti();
    // List<Prodotto> visualizzaContenuti();

    // Metodo per la visualizzazione della descrizione del prodotto
    ResponseEntity<Object> visualizzaDescrizione(int id);

    // Login
    boolean autenticazione();

    // Signup
    boolean registrazione();
}
