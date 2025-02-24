package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Prodotti.HandlerProdotti;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato implements Utente {

    @Id // chiave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincrementato dal DB
    private int id;

    @Column(unique = true) // username unico
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private LocalDate dataDiNascita;
    @Embedded
    private Indirizzo indirizzo;
    @Column(nullable = false)
    private boolean autorizzato = false;
    @Enumerated(EnumType.STRING) // Salviamo il ruolo come stringa nel database
    private Ruolo ruolo = Ruolo.UTENTE; // Default: UTENTE

    // Costruttore vuoto richiesto da JPA
    public UtenteRegistrato() {}

    // Costruttore
    public UtenteRegistrato(String username, String nome, String cognome, String email, String password, Indirizzo indirizzo,
                            LocalDate dataDiNascita, Ruolo ruolo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.indirizzo = indirizzo;
        this.dataDiNascita = dataDiNascita;
        this.autorizzato = false; // non necessario
        this.ruolo = ruolo;
    }

    // Logout
    public boolean disconnessione() {
        HandlerUtenti.getInstance().disconnessione(this.getUsername());
        return true;
    }

    // Login
    @Override
    public boolean autenticazione() {
        HandlerUtenti.getInstance().autenticazione(this.getUsername(), this.getPassword());
        return true;
    }

    // Signup (utente già registrato)
    @Override
    public boolean registrazione() {
        HandlerUtenti.getInstance().registrazione(UtenteRegistrato.this);
        return true;
    }

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    @Override
    public ResponseEntity<Object>  visualizzaContenuti() {
        return HandlerProdotti.getInstance().getProdotti();
    }

    // Metodo per la visualizzazione della descrizione del prodotto
    @Override
    public ResponseEntity<Object> visualizzaDescrizione(int id) {
        return HandlerProdotti.getInstance().getProdotto(id);
    }

    // Metodo per la modifica dei dati dell'utente
    public ResponseEntity<String> modificaDatiUtente(UtenteRegistrato nuovoUtente) {
        return HandlerUtenti.getInstance().modificaDatiUtente(this.getId(), nuovoUtente);
    }

    public boolean isAutorizzato() {
        return autorizzato;
    }

    public void setAutorizzato(boolean autorizzato) {
        this.autorizzato = autorizzato;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public Indirizzo getIndirizzo() { return indirizzo; }
    public LocalDate getDataDiNascita() { return dataDiNascita; }
    public String getPassword() { return password; }
    public Ruolo getRuolo() { return ruolo; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setEmail(String email) { this.email = email; }
    public void setIndirizzo(Indirizzo indirizzo) { this.indirizzo = indirizzo; }
    public void setDataDiNascita(LocalDate dataDiNascita) { this.dataDiNascita = dataDiNascita; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }

}
