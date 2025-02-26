package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Prodotti.PacchettoDiProdotti;
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
public class HandlerPiattaforma {

    private static HandlerPiattaforma instance = new HandlerPiattaforma();
    //@Autowired
    private UtentiRepository utentiRepository;
    //@Autowired
    private ProdottiRepository prodottiRepository;

    public HandlerPiattaforma() {
    }

    @Autowired
    public HandlerPiattaforma(ProdottiRepository prodottiRepository, UtentiRepository utentiRepository) {
        this.prodottiRepository = prodottiRepository;
        this.utentiRepository = utentiRepository;
    }

    public static HandlerPiattaforma getInstance() {
        return instance;
    }

    // GESTORE
    @Transactional
    @DeleteMapping(value = "/rimuovi-prodotti-scaduti")
    public ResponseEntity<String> rimuoviProdottiScaduti() {
        List<Prodotto> prodottiScaduti = prodottiRepository.findByDataScadenzaBefore(LocalDate.now());
        if (!prodottiScaduti.isEmpty()) {
            prodottiRepository.deleteAll(prodottiScaduti);
            //prodottiRepository.deleteByDataScadenzaBefore(LocalDate.now());
            return ResponseEntity.ok(prodottiScaduti.size() + " prodotti scaduti rimossi.");
        } else
            return ResponseEntity.badRequest().body("Nessun prodotto scaduto da rimuovere.");
    }

    // GESTORE
    @Transactional
    @PutMapping(value = "/autorizza-account/{id}")
    public ResponseEntity<String> autorizzaAccount(@PathVariable int id) {
        Optional<UtenteRegistrato> utenteOpt = utentiRepository.findById(id);

        if (utenteOpt.isPresent()) {
            UtenteRegistrato utente = utenteOpt.get();
            if (!utente.isAutorizzato()) {
                utente.setAutorizzato(true); // Modifica lo stato dell'utente
                utentiRepository.save(utente); // Salva l'utente con lo stato aggiornato
                return ResponseEntity.ok("Account autorizzato con successo.");
            } else {
                return ResponseEntity.badRequest().body("L'account è già stato autorizzato.");
            }
        } else {
            return ResponseEntity.badRequest().body("Utente non trovato.");
        }
    }

}
