package cs.unicam.filiera_agricola.Prodotti;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class HandlerProdotti {

    @Autowired
    private ProdottiRepository prodottiRepository;
    @Autowired
    private PacchettiDiProdottiRepository pacchettiDiProdottiRepository;
    @Autowired
    private DescrizioniRepository descrizioniRepository;
    @Autowired
    private CertificazioniRepository certificazioniRepository;
    @Autowired
    private ProcessiTrasformazioneRepository processiTrasformazioneRepository;

    private static HandlerProdotti instance = new HandlerProdotti();

    public static HandlerProdotti getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        // Prodotto
        Prodotto prodotto = new Prodotto("Pomodoro", 1.5, LocalDate.now());
        Descrizione descrizione = new Descrizione("Pomodoro rosso", 10, true);
        Certificazione certificazione1 = new Certificazione("Biologico");
        Certificazione certificazione2 = new Certificazione("DOP");

        prodotto.setDescrizione(descrizione);
        descrizione.aggiungiCertificazione(certificazione1);
        descrizione.aggiungiCertificazione(certificazione2);

        prodottiRepository.save(prodotto);
//        certificazioniRepository.save(certificazione1);
//        certificazioniRepository.save(certificazione2);

        // Prodotto trasformato
        Prodotto prodottoTrasformato = new Prodotto("Passata di pomodoro", 2.5, LocalDate.now());
        Descrizione descrizioneTrasformata = new Descrizione("Passata di pomodoro", 10, true);
        Certificazione certificazioneTrasformata1 = new Certificazione("Biologico");
        ProcessoTrasformazione processoTrasformazione = new ProcessoTrasformazione("Passata di pomodoro", "Pomodoro rosso");

        prodottoTrasformato.setDescrizione(descrizioneTrasformata);
        descrizioneTrasformata.aggiungiCertificazione(certificazioneTrasformata1);
        descrizioneTrasformata.aggiungiProcessoTrasformazione(processoTrasformazione);

        prodottiRepository.save(prodottoTrasformato);
//        certificazioniRepository.save(certificazioneTrasformata1);
//        processiTrasformazioneRepository.save(processoTrasformazione);

        // Prodotto costruito con il builder
        Prodotto prodottoBuilder = new ProdottoSingoloBuilder()
                .setNome("Zucchina")
                .setPrezzo(1.0)
                .setDataScadenza(LocalDate.now())
                .setDescrizione(new Descrizione("Zucchina verde", 10, true))
                .setCertificazione(new Certificazione("Biologico"))
                .build();

        prodottiRepository.save(prodottoBuilder);

        // Pacchetto di prodotti
        PacchettoDiProdotti pacchettoDiProdotti = new PacchettoDiProdotti("Pacchetto di pomodori");
        pacchettoDiProdotti.aggiungiProdotto(prodotto);
        pacchettoDiProdotti.aggiungiProdotto(prodottoTrasformato);

        pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
    }

    // Ritorna tutti i prodotti
    @GetMapping(value = "/prodotti")
    public ResponseEntity<Object> getProdotti() {
        return ResponseEntity.ok(prodottiRepository.findAll());
    }

    // Ritorna dettaglio prodotto
    @GetMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> getProdotto(@PathVariable int id) {
        if (prodottiRepository.existsById(id))
            return ResponseEntity.ok(prodottiRepository.findById(id).get());
        else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    // Aggiunge un prodotto
    @PostMapping(value = "/addProdotto")
    public ResponseEntity<Object> addProdotto(@RequestBody Prodotto prodotto) {
        if (!prodottiRepository.existsById(prodotto.getId())) {
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Prodotto già esistente");
    }

    // Elimina un prodotto
    @DeleteMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> deleteProdotto(@PathVariable int id) {
        if (prodottiRepository.existsById(id)) {
            Prodotto prodotto = prodottiRepository.findById(id).get();
            pacchettiDiProdottiRepository.findAll().forEach(
                    pacchetto -> {
                        if (pacchetto.getProdotti().contains(prodotto)) {
                            pacchetto.rimuoviProdotto(prodotto);
                            pacchettiDiProdottiRepository.save(pacchetto);
                        }
                        if (pacchetto.getProdotti().size() < 2) {
                            pacchettiDiProdottiRepository.delete(pacchetto);
                        }
                    }
            );
            prodottiRepository.deleteById(id);
            return ResponseEntity.ok("Prodotto eliminato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    // Aggiorna un prodotto
    @PutMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodotto) {
        if (prodottiRepository.existsById(id)) {
            // prodottiListRepository.deleteById(id);
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiornato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    // Ritorna tutti i pacchetti di prodotti
    @GetMapping(value = "/pacchetti")
    public ResponseEntity<Object> getPacchetti() {
        return ResponseEntity.ok(pacchettiDiProdottiRepository.findAll());
    }

    // Dettaglio pacchetto di prodotti
    @GetMapping(value = "/pacchetti/{id}")
    public ResponseEntity<Object> getPacchetto(@PathVariable int id) {
        if (pacchettiDiProdottiRepository.existsById(id))
            return ResponseEntity.ok(pacchettiDiProdottiRepository.findById(id).get());
        else
            return ResponseEntity.badRequest().body("Pacchetto non esistente");
    }

    // Elimina un pacchetto di prodotti
    @DeleteMapping(value = "/pacchetti/{id}")
    public ResponseEntity<Object> deletePacchetto(@PathVariable int id) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.deleteById(id);
            return ResponseEntity.ok("Pacchetto eliminato");
        } else
            return ResponseEntity.badRequest().body("Pacchetto non esistente");
    }

    // CURATORE
    //@Transactional// garantisce che l'approvazione sia salvata correttamente
    @PostMapping(value = "/prodotti/approva/{id}")
    public ResponseEntity<String> approvaContenuto(@PathVariable int id) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            if (!prodotto.isApprovato()) {
                prodotto.setApprovato(true);
                prodottiRepository.save(prodotto);
                return ResponseEntity.ok("Prodotto approvato con successo.");
            } else {
                return ResponseEntity.badRequest().body("Prodotto già approvato.");
            }
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

}
