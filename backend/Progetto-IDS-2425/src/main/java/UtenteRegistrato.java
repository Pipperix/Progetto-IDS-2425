import java.time.LocalDate;
import java.util.List;

public abstract class UtenteRegistrato implements Utente {

    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private Indirizzo indirizzo;
    private LocalDate dataDiNascita;

    // Costruttore
    public UtenteRegistrato(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.indirizzo = indirizzo;
        this.dataDiNascita = dataDiNascita;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public Indirizzo getIndirizzo() { return indirizzo; }
    public LocalDate getDataDiNascita() { return dataDiNascita; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setEmail(String email) { this.email = email; }
    public void setIndirizzo(Indirizzo indirizzo) { this.indirizzo = indirizzo; }
    public void setDataDiNascita(LocalDate dataDiNascita) { this.dataDiNascita = dataDiNascita; }

    // Logout (non da implementare)
    public boolean logOut() {
        // Logica di logout
        return true;
    }

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    @Override
    public List<Prodotto> visualizzaContenuti() {
        // Query da db per visualizzare tutti i Prodotti presenti nel sistema
        // findAll()
        return null;
    }

    // Metodo per la visualizzazione della descrizione del prodotto
    @Override
    public String visualizzaDescrizione(Prodotto prodotto) {
        // Scelgo un prodotto con descrizione da visualizzare
        // Riprendo l'id del prodotto?
        return prodotto.toString(); // Cosa ritorno?
    }

    // Login
    @Override
    public boolean autenticazione() {
        return true;
    }

    // Signup (utente già registrato)
    @Override
    public boolean registrazione() {
        return false;
    }

}
