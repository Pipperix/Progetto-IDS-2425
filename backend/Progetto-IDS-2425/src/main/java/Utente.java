public interface Utente {

    void visualizzaContenuti();


    //default DescrizioneProdotto visualizzaDescrizione() { return new DescrizioneProdotto(descrizione, prezzo); }


    DescrizioneProdotto visualizzaDescrizione();


    void autenticazione();


    void registrazione();
}
