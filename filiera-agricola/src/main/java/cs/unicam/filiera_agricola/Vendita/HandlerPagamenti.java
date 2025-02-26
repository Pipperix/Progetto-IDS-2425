package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.MetodoPagamento;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.Social;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pagamenti")
public class HandlerPagamenti {
    private static HandlerPagamenti instance = new HandlerPagamenti();
    @Autowired
    private UtentiRepository utentiRepository;

    public static HandlerPagamenti getInstance() {
        return instance;
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
