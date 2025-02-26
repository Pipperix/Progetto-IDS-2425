package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.MetodoPagamento;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pagamenti")
public class HandlerPagamenti {
    private static HandlerPagamenti instance = new HandlerPagamenti();
    @Autowired
    private UtentiRepository utentiRepository;

    public static HandlerPagamenti getInstance() {
        return instance;
    }

    @GetMapping("/effettuaPagamento")
    public ResponseEntity<Object> effettuaPagamento(@RequestBody UtenteRegistrato utente, MetodoPagamento metodoDiPagamento) {
        // Trova l'acquirente
        Acquirente acquirente = (Acquirente) utentiRepository.findById(utente.getId())
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        // Ritorna una risposta positiva
        return ResponseEntity.ok("Pagamento effettuato con successo.");
    }
}
