package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Eventi.Evento;
import cs.unicam.filiera_agricola.Prodotti.HandlerProdotti;
import cs.unicam.filiera_agricola.Prodotti.MetodoPagamento;
import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private ProdottiRepository prodottiRepository;
    @Autowired
    private HandlerProdotti handlerProdotti;

    @GetMapping("/{acquirenteId}")
    public ResponseEntity<List<Prodotto>> getCarrello(@PathVariable int acquirenteId) {
        List<Prodotto> carrello = carrelloService.getCarrello(acquirenteId);
        return ResponseEntity.ok(carrello);
    }

    @PostMapping("/{acquirenteId}/aggiungi")
    public ResponseEntity<String> aggiungiProdotto(@PathVariable int acquirenteId,
                                                   @RequestParam int prodottoId) {
        /*
        Prodotto prodotto = handlerProdotti.getProdotto(prodottoId);
        if (!prodotto.getDescrizione().isApprovato())
            return ResponseEntity.badRequest().body("Il prodotto non è stato ancora approvato. " +
                    "Attendi la sua approvazione prima di poterlo aggiungere al carrello.");
         */
        carrelloService.aggiungiProdotto(acquirenteId, prodottoId);
        return ResponseEntity.ok("Prodotto " + prodottoId + " aggiunto con successo al carrello! " +
                "Può proseguire con gli acquisti.");
    }

    @DeleteMapping("/{acquirenteId}/rimuovi")
    public ResponseEntity<String> rimuoviProdotto(@PathVariable int acquirenteId,
                                                  @RequestParam int prodottoId) {
        carrelloService.rimuoviProdotto(acquirenteId, prodottoId);
        return ResponseEntity.ok("Prodotto " + prodottoId + " rimosso con successo dal carrello!");
    }

    @DeleteMapping("/{acquirenteId}/svuota")
    public ResponseEntity<String> svuotaCarrello(@PathVariable int acquirenteId) {
        carrelloService.svuotaCarrello(acquirenteId);
        return ResponseEntity.ok("Carrello svuotato con successo!");
    }

    //TODO: implementare la modifica del carrello??
    // @PutMapping("/{acquirenteId}/modifica")


    @GetMapping("/{acquirenteId}/totale")
    public ResponseEntity<String> calcolaTotale(@PathVariable int acquirenteId) {
        double totale = carrelloService.calcolaTotale(acquirenteId);
        return ResponseEntity.ok("Il totale della sua spesa sarà " + totale + " euro. Procedere con il pagamento?");
    }

    @PostMapping("/effettuaPagamento")
    public ResponseEntity<Object> effettuaPagamento(@RequestParam String username, @RequestParam MetodoPagamento metodoDiPagamento) {
        // Trova l'acquirente
        Optional<UtenteRegistrato> acquirente = utentiRepository.findByUsername(username);
        if (acquirente.isEmpty() || acquirente.get().getRuolo() != Ruolo.ACQUIRENTE) {
            return ResponseEntity.badRequest().body("Acquirente non trovato o lo username non corrisponde ad un acquirente.");
        }
        // Ritorna una risposta positiva
        return ResponseEntity.ok("Pagamento effettuato con successo con " + metodoDiPagamento);
    }

}
