package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Eventi.Animatore;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import cs.unicam.filiera_agricola.Vendita.Distributore;
import cs.unicam.filiera_agricola.Vendita.Produttore;
import cs.unicam.filiera_agricola.Vendita.Trasformatore;
import cs.unicam.filiera_agricola.Vendita.Venditore;
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
        Certificazione certificazione1 = new Certificazione("Biologico", "Certificazione biologica");
        Certificazione certificazione2 = new Certificazione("DOP", "Denominazione di Origine Protetta");

        prodotto.setDescrizione(descrizione);
        descrizione.aggiungiCertificazione(certificazione1);
        descrizione.aggiungiCertificazione(certificazione2);

        prodottiRepository.save(prodotto);
//        certificazioniRepository.save(certificazione1);
//        certificazioniRepository.save(certificazione2);

        // Prodotto trasformato
        Prodotto prodottoTrasformato = new Prodotto("Passata di pomodoro", 2.5, LocalDate.now().minusDays(2));
        Descrizione descrizioneTrasformata = new Descrizione("Passata di pomodoro", 10);
        Certificazione certificazioneTrasformata1 = new Certificazione("Biologico", "Certificazione biologica");
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
                .setCertificazione(new Certificazione("Biologico", "Certificazione biologica"))
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

    @PostMapping(value = "/prodotti/{venditoreId}/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProdotto(@PathVariable int venditoreId, @RequestBody Prodotto prodotto) {
        Optional<UtenteRegistrato> utenteOpt = utentiRepository.findById(venditoreId);
        if (utenteOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Utente non trovato.");
        }
        // Verifico se l'utente è effettivamente un Animatore
        UtenteRegistrato utente = utenteOpt.get();
        if (!(utente instanceof Venditore || utente instanceof Produttore || utente instanceof Trasformatore || utente instanceof Distributore)) {
            return ResponseEntity.badRequest().body("L'id fornito non corrisponde a un Venditore valido.");
        }
        // Se l'utente è un Venditore, lo castiamo e associamo all'evento
        Venditore venditore = (Venditore) utente;
        prodotto.setVenditore(venditore); // Imposto il venditore sul prodotto

        prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
    }

    // Elimina un prodotto
    @DeleteMapping(value = "/prodotti/{id}/elimina")
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
    @PutMapping(value = "/prodotti/{id}/modifica", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    // TODO: aggiungere controllo che ti dice se il prodotto che stai tentando di aggiungere non è
    //  disponibile nel marketplace?
    // Aggiunge un pacchetto di prodotti
    @PostMapping(value = "/pacchetti/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPacchetto(@RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        if (!pacchettiDiProdottiRepository.existsById(pacchettoDiProdotti.getId())) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
            return ResponseEntity.ok("Pacchetto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Pacchetto già esistente");
    }

    // Elimina un pacchetto di prodotti
    @DeleteMapping(value = "/pacchetti/{id}/elimina")
    public ResponseEntity<Object> deletePacchetto(@PathVariable int id) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.deleteById(id);
            return ResponseEntity.ok("Pacchetto eliminato");
        } else
            return ResponseEntity.badRequest().body("Pacchetto non esistente");
    }

    // Aggiorna un pacchetto di prodotti
    @PutMapping(value = "/pacchetti/{id}/modifica", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePacchetto(@PathVariable int id, @RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
            return ResponseEntity.ok("Pacchetto aggiornato");
        } else
            return ResponseEntity.badRequest().body("Pacchetto non esistente");
    }

    // CURATORE
    //@Transactional// garantisce che l'approvazione sia salvata correttamente
    @PostMapping(value = "/prodotti/{id}/approva")
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

    //TODO: verificare prima che il prodotto è approvato?
    @PostMapping(value = "/prodotti/{id}/aggiungiCertificazione", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiungiCertificazione(@PathVariable int id, @RequestBody Certificazione certificazione) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            prodotto.getDescrizione().aggiungiCertificazione(certificazione);
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Certificazione aggiunta con successo.");
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

    // TODO: mettere certificazioneId come @RequestParam invece che @PathVariable?
    @DeleteMapping(value = "/prodotti/{id}/eliminaCertificazione/{certificazioneId}")
    public ResponseEntity<String> eliminaCertificazione(@PathVariable int id, @PathVariable int certificazioneId) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            Certificazione certificazione = certificazioniRepository.findById(certificazioneId).orElse(null);
            if (certificazione != null) {
                prodotto.getDescrizione().rimuoviCertificazione(certificazione);
                prodottiRepository.save(prodotto);
                return ResponseEntity.ok("Certificazione rimossa con successo.");
            } else {
                return ResponseEntity.badRequest().body("Certificazione non trovata.");
            }
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

    //TODO: verificare prima che il prodotto è approvato?
    @PostMapping(value = "/prodotti/{id}/aggiungiProcesso", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiungiProcessoTrasformazione(@PathVariable int id, @RequestBody ProcessoTrasformazione processoTrasformazione) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            prodotto.getDescrizione().aggiungiProcessoTrasformazione(processoTrasformazione);
            prodottiRepository.save(prodotto);
            return ResponseEntity.ok("Processo di trasformazione aggiunto con successo.");
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

    // TODO: mettere il processoId come @RequestParam invece che @PathVariable?
    @DeleteMapping(value = "/prodotti/{id}/eliminaProcesso/{processoId}")
    public ResponseEntity<String> eliminaProcessoTrasformazione(@PathVariable int id, @PathVariable int processoId) {
        Optional<Prodotto> prodottoOpt = prodottiRepository.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            ProcessoTrasformazione processoTrasformazione = processiTrasformazioneRepository.findById(processoId).orElse(null);
            if (processoTrasformazione != null) {
                prodotto.getDescrizione().rimuoviProcessoTrasformazione(processoTrasformazione);
                prodottiRepository.save(prodotto);
                return ResponseEntity.ok("Processo di trasformazione rimosso con successo.");
            } else {
                return ResponseEntity.badRequest().body("Processo di trasformazione non trovato.");
            }
        } else {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
    }

}
