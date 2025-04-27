package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Prodotti.MetodoPagamento;
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


    @GetMapping("/{acquirenteId}")
    public ResponseEntity<Object> getCarrello(@PathVariable int acquirenteId) {
        try {
            List<Prodotto> carrello = carrelloService.getCarrello(acquirenteId);
            return ResponseEntity.ok(carrello);
        } catch (RuntimeException e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{acquirenteId}/aggiungi")
    public ResponseEntity<String> aggiungiProdotto(@PathVariable int acquirenteId,
                                                   @RequestParam int prodottoId,
                                                   @RequestParam(defaultValue = "1") int quantita) {
        try {
            carrelloService.aggiungiProdotto(acquirenteId, prodottoId, quantita);
            return ResponseEntity.ok("Aggiunti " + quantita + " pezzi del prodotto con id: " + prodottoId +
                    " al carrello! Può proseguire con gli acquisti.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{acquirenteId}/rimuovi")
    public ResponseEntity<String> rimuoviProdotto(@PathVariable int acquirenteId,
                                                  @RequestParam int prodottoId) {
        try {
            carrelloService.rimuoviProdotto(acquirenteId, prodottoId);
            return ResponseEntity.ok("Prodotto " + prodottoId + " rimosso con successo dal carrello!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{acquirenteId}/svuota")
    public ResponseEntity<String> svuotaCarrello(@PathVariable int acquirenteId) {
        try {
            carrelloService.svuotaCarrello(acquirenteId);
            return ResponseEntity.ok("Carrello svuotato con successo!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{acquirenteId}/totale")
    public ResponseEntity<String> calcolaTotale(@PathVariable int acquirenteId) {
        try {
            double totale = carrelloService.calcolaTotale(acquirenteId);
            return ResponseEntity.ok("Il totale della sua spesa sarà " + totale + " euro. Procedere con il pagamento?");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/effettuaPagamento")
    public ResponseEntity<Object> effettuaPagamento(@RequestParam String username, @RequestParam MetodoPagamento metodoDiPagamento) {
        try {
            carrelloService.effettuaPagamento(username, metodoDiPagamento);
            return ResponseEntity.ok("Pagamento effettuato con successo con " + metodoDiPagamento);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
