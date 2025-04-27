package cs.unicam.filiera_agricola.Utenti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs.unicam.filiera_agricola.Eventi.EventiController;
import cs.unicam.filiera_agricola.Eventi.Evento;
import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import cs.unicam.filiera_agricola.Prodotti.ProdottiService;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import jakarta.persistence.*;
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
    private String nomeUtente;
    private String cognome;
    private String email;
    private String password;
    @Embedded
    private Luogo luogo;
    @Column(nullable = false)
    private boolean autorizzato = false;
    @Enumerated(EnumType.STRING) // Salviamo il ruolo come stringa nel database
    private Ruolo ruolo = Ruolo.UTENTE; // Default: UTENTE

    @ManyToMany(mappedBy = "utentiPrenotati")
    @JsonIgnore
    private Set<Evento> eventiPrenotati = new HashSet<>();


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
    public void disconnessione(UtentiController utenti) {
        utenti.disconnessione(this.username);
    }

    // Login
    @Override
    public boolean autenticazione() {
        UtentiService utenti = new UtentiService();
        return utenti.autenticazione(this.username, this.password);
    }

    // Signup (utente già registrato)
    @Override
    public boolean registrazione() {
        UtentiService utenti = new UtentiService();
        return utenti.registrazione(this);
    }

    // Metodo per la visualizzazione dei contenuti (prodotti) presenti nel sistema
    @Override
    public void visualizzaContenuti() {
        ProdottiController prodotti = new ProdottiController();
        prodotti.getProdotti();
    }

    @Override
    public void visualizzaDescrizione(int id) {
        //FilieraAgricolaFacade.getInstance().getProdotto(id);
        ProdottiService prodottiService = new ProdottiService();
        prodottiService.getProdotto(id);
    }

    public void modificaDatiUtente(UtentiController utenti, UtenteRegistrato nuovoUtente) {
        utenti.modificaDatiUtente(this.getId(), nuovoUtente);
    }

    public void condivisioneSuSocial(UtentiController utenti, int prodottoId, Social social) {
        utenti.condivisioneSuSocial(prodottoId, social, this.username, this.password);
    }

    @Override
    public void visualizzaMappa() {
        UtentiController utenti = new UtentiController();
        utenti.visualizzaMappa();
    }

    public void getTuttiEventi(EventiController eventi) {
        eventi.getTuttiEventi();
    }

    public void getEventoById(EventiController eventi, int id) {
        eventi.getEventoById(id);
    }

    public void getEventiAnimatore(EventiController eventi, int id) {
        eventi.getEventiAnimatore(id);
    }

    public void prenotaEvento(EventiController eventi, int eventoId) {
        eventi.prenotaEvento(eventoId, this.getUsername());
    }

    public void getPacchetti(ProdottiController prodotti) {
        prodotti.getPacchetti();
    }

    public void getPacchetto(ProdottiController prodotti, int id) {
        prodotti.getPacchetto(id);
    }

    public void getProdottiVenditore(ProdottiController prodotti, int id) {
        prodotti.getProdottiVenditore(id);
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
    public boolean isAutorizzato() {
        return autorizzato;
    }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setEmail(String email) { this.email = email; }
    public void setLuogo(Luogo indirizzo) { this.luogo = indirizzo; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }
    public void setEventiPrenotati(Set<Evento> eventiPrenotati) { this.eventiPrenotati = eventiPrenotati; }
    public void setAutorizzato(boolean autorizzato) {
        this.autorizzato = autorizzato;
    }

}
