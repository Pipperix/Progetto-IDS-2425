package cs.unicam.filiera_agricola;

import cs.unicam.filiera_agricola.Eventi.Evento;
import cs.unicam.filiera_agricola.Eventi.HandlerEventi;
import cs.unicam.filiera_agricola.Prodotti.*;
import cs.unicam.filiera_agricola.Piattaforma.HandlerPiattaforma;
import cs.unicam.filiera_agricola.Utenti.HandlerUtenti;
import cs.unicam.filiera_agricola.Utenti.Social;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Vendita.Luogo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@RestController
public class FilieraAgricolaFacade {

    private final HandlerEventi handlerEventi;
    private final HandlerOrdine handlerOrdine;
    private final HandlerProdotti handlerProdotti;
    private final HandlerPiattaforma handlerPiattaforma;
    //private final HandlerPagamenti handlerPagamenti;
    private final HandlerUtenti handlerUtenti;

    public FilieraAgricolaFacade (HandlerEventi handlerEventi, HandlerOrdine handlerOrdine,
                                  HandlerProdotti handlerProdotti, HandlerPiattaforma handlerPiattaforma,
                                  HandlerUtenti handlerUtenti) {
        this.handlerEventi = handlerEventi;
        this.handlerOrdine = handlerOrdine;
        this.handlerProdotti = handlerProdotti;
        this.handlerPiattaforma = handlerPiattaforma;
        // this.handlerPagamenti = handlerPagamenti;
        this.handlerUtenti = handlerUtenti;
    }

    // HandlerEventi
    public ResponseEntity<String> creaEvento(Evento evento) {
        return handlerEventi.creaEvento(evento);
    }
    public ResponseEntity<List<Evento>> getTuttiEventi() {
        return handlerEventi.getTuttiEventi();
    }
    public ResponseEntity<Object> getEventoById(int id) {
        return handlerEventi.getEventoById(id);
    }
    public ResponseEntity<String> modificaEvento(int id, Evento eventoModificato) {
        return handlerEventi.modificaEvento(id, eventoModificato);
    }
    public ResponseEntity<String> eliminaEvento(int id) {
        return handlerEventi.eliminaEvento(id);
    }

    // HandlerOrdine
    public void creaOrdine(int acquirenteId, Map<Prodotto, Integer> prodotti, MetodoPagamento metodoPagamento) {
        handlerOrdine.creaOrdine(acquirenteId, prodotti, metodoPagamento);
    }
    public List<Ordine> getOrdini(int acquirenteId) {
        return handlerOrdine.getOrdini(acquirenteId);
    }
    public String notificaOrdine(int ordineId) {
        return handlerOrdine.notificaOrdine(ordineId);
    }

    // HandlerProdotti
    public ResponseEntity<Object> getProdotti() {
        return handlerProdotti.getProdotti();
    }
    public ResponseEntity<Object> getProdotto(int id) {
        return handlerProdotti.getProdotto(id);
    }
    public ResponseEntity<Object> addProdotto(Prodotto prodotto) {
        return handlerProdotti.addProdotto(prodotto);
    }
    public ResponseEntity<Object> deleteProdotto(int id) {
        return handlerProdotti.deleteProdotto(id);
    }
    public ResponseEntity<Object> updateProdotto(int id, Prodotto prodotto) {
        return handlerProdotti.updateProdotto(id, prodotto);
    }
    public ResponseEntity<Object> getPacchetti() {
        return handlerProdotti.getPacchetti();
    }
    public ResponseEntity<Object> getPacchetto(int id) {
        return handlerProdotti.getPacchetto(id);
    }
    public ResponseEntity<Object> addPacchetto(PacchettoDiProdotti pacchettoDiProdotti) {
        return handlerProdotti.addPacchetto(pacchettoDiProdotti);
    }
    public ResponseEntity<Object> deletePacchetto(int id) {
        return handlerProdotti.deletePacchetto(id);
    }
    public ResponseEntity<Object> updatePacchetto(int id, PacchettoDiProdotti pacchettoDiProdotti) {
        return handlerProdotti.updatePacchetto(id, pacchettoDiProdotti);
    }
    public ResponseEntity<String> approvaContenuto(int id) {
        return handlerProdotti.approvaContenuto(id);
    }
    public ResponseEntity<String> modificaQuantita(int id, int quantita) {
        return handlerProdotti.modificaQuantita(id, quantita);
    }

    // HandlerPiattaforma
    public ResponseEntity<String> rimuoviProdottiScaduti() {
        return handlerPiattaforma.rimuoviProdottiScaduti();
    }
    public ResponseEntity<String> autorizzaAccount(int id) {
        return handlerPiattaforma.autorizzaAccount(id);
    }

    // HandlerUtenti
    //@GetMapping(value = "/utenti/tutti")
    public ResponseEntity<List<UtenteRegistrato>> getTuttiUtenti() {
        return handlerUtenti.getTuttiUtenti();
    }
    //@PostMapping(value = "/utenti/registrazione")
    public ResponseEntity<Object> registrazione(UtenteRegistrato nuovoUtente) {
        return handlerUtenti.registrazione(nuovoUtente);
    }
    //@PostMapping("/utenti/login")
    //public ResponseEntity<Object> autenticazione(@RequestParam String username, @RequestParam String password) {
    public ResponseEntity<Object> autenticazione(String username, String password) {
            return handlerUtenti.autenticazione(username, password);
    }
    //@PostMapping("/utenti/logout")
    //public ResponseEntity<Object> disconnessione(@RequestParam String username) {
    public ResponseEntity<Object> disconnessione(String username) {
        return handlerUtenti.disconnessione(username);
    }
    //@PutMapping("/utenti/modifica-dati/{id}")
    //public ResponseEntity<String> modificaDatiUtente(@PathVariable int id, @RequestBody UtenteRegistrato nuovoUtente) {
    public ResponseEntity<String> modificaDatiUtente(int id, UtenteRegistrato nuovoUtente) {
        return handlerUtenti.modificaDatiUtente(id, nuovoUtente);
    }
    //@GetMapping("/utenti/mappa")
    public ResponseEntity<List<Luogo>> visualizzaMappa() {
        return handlerUtenti.visualizzaMappa();
    }
    //@PostMapping("/utenti/condividi")
    //public ResponseEntity<String> condivisioneSuSocial(@RequestParam int prodottoId, @RequestParam Social social, @RequestParam String username, @RequestParam String password) {
    public ResponseEntity<String> condivisioneSuSocial(int prodottoId, Social social, String username, String password) {
        return handlerUtenti.condivisioneSuSocial(prodottoId, social, username, password);
    }


}
