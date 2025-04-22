package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/piattaforma")
public class PiattaformaController {

    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private ProdottiRepository prodottiRepository;

    @Autowired
    private PiattaformaService piattaformaService;

    // GESTORE
    @Transactional
    @DeleteMapping(value = "/rimuovi-prodotti-scaduti")
    public ResponseEntity<String> rimuoviProdottiScaduti() {
        try {
            piattaformaService.rimuoviProdottiScaduti();
            return ResponseEntity.ok("Prodotti scaduti rimossi con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // GESTORE
    @Transactional
    @PutMapping(value = "/autorizza-account/{id}")
    public ResponseEntity<String> autorizzaAccount(@PathVariable int id) {
        try {
            piattaformaService.autorizzaAccount(id);
            return ResponseEntity.ok("Account autorizzato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
