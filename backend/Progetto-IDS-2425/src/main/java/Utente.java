import java.util.List;

public interface Utente {

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    List<Prodotto> visualizzaContenuti();

    // Metodo per la visualizzazione della descrizione del prodotto
    String visualizzaDescrizione(Prodotto prodotto);

    // Login (non da implementare)
    boolean autenticazione();

    // Signup (non da implementare)
    boolean registrazione();

}
