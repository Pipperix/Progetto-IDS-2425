package cs.unicam.filiera_agricola.Utenti;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cs.unicam.filiera_agricola.Eventi.Evento;
import cs.unicam.filiera_agricola.Eventi.HandlerEventi;
import cs.unicam.filiera_agricola.FilieraAgricolaFacade;
import cs.unicam.filiera_agricola.Prodotti.HandlerProdotti;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UtenteRegistrato implements Utente {

    @Id // chiave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincrementato dal DB
    private int id;

    @Column(unique = true) // username unico
    private String username;
    //@Column(insertable = false, updatable = false) // per evitare duplicati nella colonna
    private String nomeUtente;
    private String cognome;
    private String email;
    private String password;
    //private LocalDate dataDiNascita;
    @Embedded
    private Luogo luogo;
    @Column(nullable = false)
    private boolean autorizzato = false;
    @Enumerated(EnumType.STRING) // Salviamo il ruolo come stringa nel database
    private Ruolo ruolo = Ruolo.UTENTE; // Default: UTENTE

    @ManyToMany(mappedBy = "utentiPrenotati")
    @JsonBackReference
    private Set<Evento> eventiPrenotati = new HashSet<>();

    //private FilieraAgricolaFacade facade;

    // Costruttore vuoto richiesto da JPA
    public UtenteRegistrato() {}

    // Costruttore
    public UtenteRegistrato(String username, String nomeUtente, String cognome, String email, String password,
                            Luogo luogo, Ruolo ruolo) {
        this.username = username;
        this.nomeUtente = nomeUtente;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.luogo = luogo;
        this.autorizzato = false; // non necessario
        this.ruolo = ruolo;
    }

    // Logout
    public boolean disconnessione(FilieraAgricolaFacade facade) {
        facade.disconnessione(this.getUsername());
        return true;
    }

    // Login
    @Override
    public boolean autenticazione() {
        FilieraAgricolaFacade.getInstance().autenticazione(this.getUsername(), this.getPassword());
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

    @Override
    public ResponseEntity<Object> visualizzaDescrizione(int id) {
        return HandlerProdotti.getInstance().getProdotto(id);
    }

    public ResponseEntity<String> modificaDatiUtente(UtenteRegistrato nuovoUtente) {
        return HandlerUtenti.getInstance().modificaDatiUtente(this.getId(), nuovoUtente);
    }

    public ResponseEntity<String> condivisioneSuSocial(int prodottoId, Social social) {
        return HandlerUtenti.getInstance().condivisioneSuSocial(prodottoId, social, username, password);
    }

    @Override
    public void visualizzaMappa() {
        HandlerUtenti.getInstance().visualizzaMappa();
    }

    public void prenotaEvento(int eventoId, String username) {
        HandlerEventi.getInstance().prenotaEvento(eventoId, username);
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
    public String getNomeUtente() { return nomeUtente; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public Luogo getLuogo() { return luogo; }
    public String getPassword() { return password; }
    public Ruolo getRuolo() { return ruolo; }
    public Set<Evento> getEventiPrenotati() { return eventiPrenotati; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setEmail(String email) { this.email = email; }
    public void setLuogo(Luogo indirizzo) { this.luogo = indirizzo; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }
    public void setEventiPrenotati(Set<Evento> eventiPrenotati) { this.eventiPrenotati = eventiPrenotati; }

}
