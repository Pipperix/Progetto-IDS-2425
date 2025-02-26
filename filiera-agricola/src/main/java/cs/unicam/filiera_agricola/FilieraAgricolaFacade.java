package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Eventi.Evento;
import cs.unicam.filiera_agricola.Eventi.HandlerEventi;
import cs.unicam.filiera_agricola.Prodotti.*;
import cs.unicam.filiera_agricola.Piattaforma.HandlerPiattaforma;
import cs.unicam.filiera_agricola.Utenti.HandlerUtenti;
import cs.unicam.filiera_agricola.Utenti.Social;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.HandlerPagamenti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FilieraAgricolaFacade {

    private final HandlerEventi handlerEventi;
    private final HandlerOrdine handlerOrdine;
    private final HandlerProdotti handlerProdotti;
    private final HandlerPiattaforma handlerPiattaforma;
    private final HandlerUtenti handlerUtenti;
    private final HandlerPagamenti handlerPagamenti;

    private static FilieraAgricolaFacade instance = new FilieraAgricolaFacade(HandlerEventi.getInstance(), HandlerOrdine.getInstance(),
            HandlerProdotti.getInstance(), HandlerPiattaforma.getInstance(), HandlerUtenti.getInstance(), HandlerPagamenti.getInstance());

    public static FilieraAgricolaFacade getInstance() {
        return instance;
    }

    @Autowired // per Dependency Injection
    public FilieraAgricolaFacade (HandlerEventi handlerEventi, HandlerOrdine handlerOrdine,
                                  HandlerProdotti handlerProdotti, HandlerPiattaforma handlerPiattaforma,
                                  HandlerUtenti handlerUtenti, HandlerPagamenti handlerPagamenti) {
        this.handlerEventi = handlerEventi;
        this.handlerOrdine = handlerOrdine;
        this.handlerProdotti = handlerProdotti;
        this.handlerPiattaforma = handlerPiattaforma;
        this.handlerUtenti = handlerUtenti;
        this.handlerPagamenti = handlerPagamenti;
    }

    // HandlerEventi
    public void creaEvento(Evento evento) {
        handlerEventi.creaEvento(evento);
    }
    public void getTuttiEventi() {
        handlerEventi.getTuttiEventi();
    }
    public void getEventoById(int id) {
        handlerEventi.getEventoById(id);
    }
    public void modificaEvento(int eventoId, Evento eventoModificato) {
        handlerEventi.modificaEvento(eventoId, eventoModificato);
    }
    public void eliminaEvento(int eventoId) {
        handlerEventi.eliminaEvento(eventoId);
    }
    public void prenotaEvento(int eventoId, String username) {
        handlerEventi.prenotaEvento(eventoId, username);
    }
    public void getPrenotati(int id) {
        handlerEventi.getPrenotati(id);
    }

    // HandlerOrdine
    public void creaOrdine(int acquirenteId, Map<Prodotto, Integer> prodotti, MetodoPagamento metodoPagamento) {
        handlerOrdine.creaOrdine(acquirenteId, prodotti, metodoPagamento);
    }
    public void getOrdini(int acquirenteId) {
        handlerOrdine.getOrdini(acquirenteId);
    }
    public void notificaOrdine(int ordineId) {
        handlerOrdine.notificaOrdine(ordineId);
    }

    // HandlerProdotti
    public void getProdotti() {
        handlerProdotti.getProdotti();
    }
    public void getProdotto(int id) {
        handlerProdotti.getProdotto(id);
    }
    public void addProdotto(Prodotto prodotto) {
        handlerProdotti.addProdotto(prodotto);
    }
    public void deleteProdotto(int id) {
        handlerProdotti.deleteProdotto(id);
    }
    public void updateProdotto(int id, Prodotto prodotto) {
        handlerProdotti.updateProdotto(id, prodotto);
    }
    public void getPacchetti() {
        handlerProdotti.getPacchetti();
    }
    public void getPacchetto(int id) {
        handlerProdotti.getPacchetto(id);
    }
    public void addPacchetto(PacchettoDiProdotti pacchettoDiProdotti) {
        handlerProdotti.addPacchetto(pacchettoDiProdotti);
    }
    public void deletePacchetto(int id) {
        handlerProdotti.deletePacchetto(id);
    }
    public void updatePacchetto(int id, PacchettoDiProdotti pacchettoDiProdotti) {
        handlerProdotti.updatePacchetto(id, pacchettoDiProdotti);
    }
    public void approvaContenuto(int id) {
        handlerProdotti.approvaContenuto(id);
    }
    public void modificaQuantita(int id, int quantita) {
        handlerProdotti.modificaQuantita(id, quantita);
    }

    // HandlerPiattaforma
    public void rimuoviProdottiScaduti() {
        handlerPiattaforma.rimuoviProdottiScaduti();
    }
    public void autorizzaAccount(int id) {
        handlerPiattaforma.autorizzaAccount(id);
    }

    // HandlerUtenti
    //@GetMapping(value = "/utenti/tutti")
    public void getTuttiUtenti() {
        handlerUtenti.getTuttiUtenti();
    }
    //@PostMapping(value = "/utenti/registrazione")
    public void registrazione(UtenteRegistrato nuovoUtente) {
        handlerUtenti.registrazione(nuovoUtente);
    }
    //@PostMapping("/utenti/login")
    //public ResponseEntity<Object> autenticazione(@RequestParam String username, @RequestParam String password) {
    public void autenticazione(String username, String password) {
        handlerUtenti.autenticazione(username, password);
    }
    //@PostMapping("/utenti/logout")
    //public ResponseEntity<Object> disconnessione(@RequestParam String username) {
    public void disconnessione(String username) {
        handlerUtenti.disconnessione(username);
    }
    //@PutMapping("/utenti/modifica-dati/{id}")
    //public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
    public void modificaDatiUtente(int id, UtenteRegistrato nuovoUtente) {
        handlerUtenti.modificaDatiUtente(id, nuovoUtente);
    }
    //@GetMapping("/utenti/mappa")
    public void visualizzaMappa() {
        handlerUtenti.visualizzaMappa();
    }
    //@PostMapping("/utenti/condividi")
    //public ResponseEntity<String> condivisioneSuSocial(@RequestParam int prodottoId, @RequestParam Social social, @RequestParam String username, @RequestParam String password) {
    public void condivisioneSuSocial(int prodottoId, Social social, String username, String password) {
        handlerUtenti.condivisioneSuSocial(prodottoId, social, username, password);
    }

    // HandlerPagamenti
    public void effettuaPagamento(String username, MetodoPagamento metodoDiPagamento) {
        handlerPagamenti.effettuaPagamento(username, metodoDiPagamento);
    }


}
