package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @Autowired
    private UtentiRepository utentiRepository;

    public HandlerProdotti() {
    }

    @Autowired
    public HandlerProdotti(ProdottiRepository prodottiRepository, PacchettiDiProdottiRepository pacchettiDiProdottiRepository,
                           DescrizioniRepository descrizioniRepository, CertificazioniRepository certificazioniRepository,
                           ProcessiTrasformazioneRepository processiTrasformazioneRepository, UtentiRepository utentiRepository) {
        this.prodottiRepository = prodottiRepository;
        this.pacchettiDiProdottiRepository = pacchettiDiProdottiRepository;
        this.descrizioniRepository = descrizioniRepository;
        this.certificazioniRepository = certificazioniRepository;
        this.processiTrasformazioneRepository = processiTrasformazioneRepository;
        this.utentiRepository = utentiRepository;
    }

    private static HandlerProdotti instance = new HandlerProdotti();

    public static HandlerProdotti getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {

        //Venditore venditore1 = new Venditore("username", "nome", "cognome", "email", "password", null, "1234567890");
        //utenteRepository.save(venditore1);

        // Prodotto
        Prodotto prodotto = new Prodotto("Pomodoro", 1.5, LocalDate.now());
        Descrizione descrizione = new Descrizione("Pomodoro rosso", 10);
        Certificazione certificazione1 = new Certificazione("Biologico");
        Certificazione certificazione2 = new Certificazione("DOP");

        prodotto.setDescrizione(descrizione);
        descrizione.aggiungiCertificazione(certificazione1);
        descrizione.aggiungiCertificazione(certificazione2);

        prodottiRepository.save(prodotto);
//        certificazioniRepository.save(certificazione1);
//        certificazioniRepository.save(certificazione2);

        // Prodotto trasformato
        Prodotto prodottoTrasformato = new Prodotto("Passata di pomodoro", 2.5, LocalDate.now().minusDays(2));
        Descrizione descrizioneTrasformata = new Descrizione("Passata di pomodoro", 10);
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
                .setDataScadenza(LocalDate.now().minusYears(1))
                .setDescrizione(new Descrizione("Zucchina verde", 10))
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

    /*
    // Aggiunge un prodotto
    @PostMapping(value = "/prodotti/crea")
    public ResponseEntity<String> addProdotto(@RequestBody Prodotto prodotto) {
        if (!prodottiRepository.existsById(prodotto.getId())) {
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Prodotto già esistente");
    }

     */

    @PostMapping(value = "/prodotti/crea")
    public ResponseEntity<String> addProdotto(@RequestBody Prodotto prodotto) {
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
    }

    // Elimina un prodotto
    @DeleteMapping(value = "/prodotti/elimina/{id}")
    public ResponseEntity<String> deleteProdotto(@PathVariable int id) {
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

    /*
    // Aggiorna un prodotto
    @PutMapping(value = "/prodotti/modifica/{id}")
    public ResponseEntity<String> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodotto) {
        if (prodottiRepository.existsById(id)) {
            // prodottiListRepository.deleteById(id);
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiornato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

     */
    @PutMapping(value = "/prodotti/modifica/{id}")
    public ResponseEntity<String> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodottoModificato) {
    Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if(prodottoOpt.isPresent()) {
        Prodotto prodotto = prodottoOpt.get();

        if (prodottoModificato.getNome() != null) {
            prodotto.setNome(prodottoModificato.getNome());
        }
        if (prodottoModificato.getPrezzo() > 0) {
            prodotto.setPrezzo(prodottoModificato.getPrezzo());
        }
        if (prodottoModificato.getDataScadenza() != null) {
            prodotto.setDataScadenza(prodottoModificato.getDataScadenza());
        }
        if (prodottoModificato.getDescrizione() != null) {
            prodotto.setDescrizione(prodottoModificato.getDescrizione());
        }
        prodottiRepository.save(prodotto);
        return ResponseEntity.ok("Prodotto modificato con successo.");
    } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
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

    // Aggiunge un pacchetto di prodotti
    //@CrossOrigin
    @PostMapping(value = "/pacchetti/crea")
    public ResponseEntity<Object> addPacchetto(@RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        if (!pacchettiDiProdottiRepository.existsById(pacchettoDiProdotti.getId())) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
            return ResponseEntity.ok("Pacchetto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Pacchetto già esistente");
    }

    // Elimina un pacchetto di prodotti
    @DeleteMapping(value = "/pacchetti/elimina/{id}")
    public ResponseEntity<Object> deletePacchetto(@PathVariable int id) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.deleteById(id);
            return ResponseEntity.ok("Pacchetto eliminato");
        } else
            return ResponseEntity.badRequest().body("Pacchetto non esistente");
    }

    // Aggiorna un pacchetto di prodotti
    @PutMapping(value = "/pacchetti/modifica/{id}")
    public ResponseEntity<Object> updatePacchetto(@PathVariable int id, @RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
            return ResponseEntity.ok("Pacchetto aggiornato");
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
            if (!prodotto.getDescrizione().isApprovato()) {
                prodotto.getDescrizione().setApprovato(true);
                prodottiRepository.save(prodotto);
                return ResponseEntity.ok("Prodotto approvato con successo.");
            } else {
                return ResponseEntity.badRequest().body("Prodotto già approvato.");
            }
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

    // VENDITORE
    // @Transactional
    // Modifica quantità di un prodotto
    @PutMapping(value = "/prodotti/{id}/quantita")
    public ResponseEntity<String> modificaQuantita(@PathVariable int id, @RequestParam int quantita) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            prodotto.getDescrizione().setQuantita(quantita);
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Quantità modificata con successo.");
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

}
