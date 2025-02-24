package cs.unicam.filiera_agricola;

import org.springframework.http.ResponseEntity;
import java.util.List;

// @Entity ??
public interface Utente {

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    ResponseEntity<Object> visualizzaContenuti();
    // List<Prodotto> visualizzaContenuti();

    // Metodo per la visualizzazione della descrizione del prodotto
    ResponseEntity<Object> visualizzaDescrizione(int id);

    // Login (non da implementare)
    boolean autenticazione();

    // Signup (non da implementare)
    boolean registrazione();
}
