package cs.unicam.filiera_agricola.Prodotti;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HandlerProdotti {

    @Autowired
    private ProdottiListRepository prodottiListRepository;
    @Autowired
    private DescrizioniListRepository descrizioniListRepository;
    @Autowired
    private CertificazioniListRepository certificazioniListRepository;

    @PostConstruct
    public void init() {
        Prodotto prodotto = new Prodotto("Pomodoro", 1.5, LocalDate.now());
        Descrizione descrizione = new Descrizione("Pomodoro rosso", 10, true);
        Certificazione certificazione1 = new Certificazione("Biologico");
        Certificazione certificazione2 = new Certificazione("DOP");

        prodotto.setDescrizione(descrizione);
        descrizione.aggiungiCertificazione(certificazione1);
        descrizione.aggiungiCertificazione(certificazione2);

        prodottiListRepository.save(prodotto);
        certificazioniListRepository.save(certificazione1);
        certificazioniListRepository.save(certificazione2);
    }

    // Ritorna tutti i prodotti
    @GetMapping(value = "/prodotti")
    public ResponseEntity<Object> getProdotti() {
        return ResponseEntity.ok(prodottiListRepository.findAll());
    }

    // Ritorna dettaglio prodotto
    @GetMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> getProdotto(@PathVariable int id) {
        if (prodottiListRepository.existsById(id))
            return ResponseEntity.ok(prodottiListRepository.findById(id).get());
        else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    // Aggiunge un prodotto
    @PostMapping(value = "/addProdotto")
    public ResponseEntity<Object> addProdotto(@RequestBody Prodotto prodotto) {
        if (!prodottiListRepository.existsById(prodotto.getId())) {
            prodottiListRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Prodotto già esistente");
    }

    // Elimina un prodotto
    @DeleteMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> deleteProdotto(@PathVariable int id) {
        if (prodottiListRepository.existsById(id)) {
            prodottiListRepository.deleteById(id);
            return ResponseEntity.ok("Prodotto eliminato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    // Aggiorna un prodotto
    @PutMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodotto) {
        if (prodottiListRepository.existsById(id)) {
            // prodottiListRepository.deleteById(id);
            prodottiListRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiornato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

}
