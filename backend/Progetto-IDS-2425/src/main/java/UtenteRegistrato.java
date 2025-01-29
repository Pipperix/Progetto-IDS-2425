import java.time.LocalDate;

public class UtenteRegistrato implements Utente {

    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private Indirizzo indirizzo;
    private LocalDate dataDiNascita;


    public UtenteRegistrato(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.indirizzo = indirizzo;
        this.dataDiNascita = dataDiNascita;
    }


    public void logOut() {


    }


    @Override
    public void visualizzaContenuti() {
    }


    @Override
    public DescrizioneProdotto visualizzaDescrizione() {
        return null;
    }


    @Override
    public void autenticazione() {


    }


    @Override
    public void registrazione() {


    }
}
