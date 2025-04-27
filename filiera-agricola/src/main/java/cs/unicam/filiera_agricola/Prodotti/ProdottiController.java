package cs.unicam.filiera_agricola.Prodotti;

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
import java.util.List;
import java.util.Optional;

@RestController
public class ProdottiController {

    @Autowired
    private ProdottiRepository prodottiRepository;
    @Autowired
    private PacchettiDiProdottiRepository pacchettiDiProdottiRepository;
    @Autowired
    private ProdottiService prodottiService;

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
        return ResponseEntity.ok(prodottiService.getProdotti());
    }

    // Ritorna dettaglio prodotto
    @GetMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> getProdotto(@PathVariable int id) {
        try {
            Prodotto prodotto = prodottiService.getProdotto(id);
            return ResponseEntity.ok(prodotto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(value = "/prodotti/{venditoreId}/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProdotto(@PathVariable int venditoreId, @RequestBody Prodotto prodotto) {
        try {
            prodottiService.addProdotto(venditoreId, prodotto);
            return ResponseEntity.ok("Prodotto aggiunto con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Elimina un prodotto
    @DeleteMapping(value = "/prodotti/{id}/elimina")
    public ResponseEntity<String> deleteProdotto(@PathVariable int id) {
        try {
            prodottiService.deleteProdotto(id);
            return ResponseEntity.ok("Prodotto eliminato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping(value = "/prodotti/{id}/modifica", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodottoModificato) {
        try {
            prodottiService.updateProdotto(id, prodottoModificato);
            return ResponseEntity.ok("Prodotto aggiornato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Ritorna tutti i pacchetti di prodotti
    @GetMapping(value = "/pacchetti")
    public ResponseEntity<Object> getPacchetti() {
        return ResponseEntity.ok(prodottiService.getPacchetti());
    }

    // Dettaglio pacchetto di prodotti
    @GetMapping(value = "/pacchetti/{id}")
    public ResponseEntity<Object> getPacchetto(@PathVariable int id) {
        try {
            PacchettoDiProdotti pacchettoDiProdotti = prodottiService.getPacchetto(id);
            return ResponseEntity.ok(pacchettoDiProdotti);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Aggiunge un pacchetto di prodotti
    @PostMapping(value = "/pacchetti/{distributoreId}/crea", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPacchetto(@PathVariable int distributoreId, @RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        try {
            prodottiService.addPacchetto(distributoreId, pacchettoDiProdotti);
            return ResponseEntity.ok("Pacchetto aggiunto con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Elimina un pacchetto di prodotti
    @DeleteMapping(value = "/pacchetti/{id}/elimina")
    public ResponseEntity<Object> deletePacchetto(@PathVariable int id) {
        try {
            prodottiService.deletePacchetto(id);
            return ResponseEntity.ok("Pacchetto eliminato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Aggiorna un pacchetto di prodotti
    @PutMapping(value = "/pacchetti/{id}/modifica", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePacchetto(@PathVariable int id, @RequestBody PacchettoDiProdotti pacchettoModificato) {
        try {
            prodottiService.updatePacchetto(id, pacchettoModificato);
            return ResponseEntity.ok("Pacchetto aggiornato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // CURATORE
    //@Transactional// garantisce che l'approvazione sia salvata correttamente
    @PostMapping(value = "/prodotti/{id}/approva")
    public ResponseEntity<String> approvaContenuto(@PathVariable int id) {
        try {
            prodottiService.approvaContenuto(id);
            return ResponseEntity.ok("Prodotto approvato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // VENDITORE
    // @Transactional
    // Modifica quantità di un prodotto
    @PutMapping(value = "/prodotti/{id}/quantita")
    public ResponseEntity<String> modificaQuantita(@PathVariable int id, @RequestParam int quantita) {
        try {
            prodottiService.modificaQuantita(id, quantita);
            return ResponseEntity.ok("Quantità modificata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/prodotti/{id}/aggiungiCertificazione", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiungiCertificazione(@PathVariable int id, @RequestBody Certificazione certificazione) {
        try {
            prodottiService.aggiungiCertificazione(id, certificazione);
            return ResponseEntity.ok("Certificazione aggiunta con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/prodotti/{id}/eliminaCertificazione/{certificazioneId}")
    public ResponseEntity<String> eliminaCertificazione(@PathVariable int id, @PathVariable int certificazioneId) {
        try {
            prodottiService.eliminaCertificazione(id, certificazioneId);
            return ResponseEntity.ok("Certificazione rimossa con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/prodotti/{id}/aggiungiProcesso", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> aggiungiProcessoTrasformazione(@PathVariable int id, @RequestBody ProcessoTrasformazione processoTrasformazione) {
        try {
            prodottiService.aggiungiProcessoTrasformazione(id, processoTrasformazione);
            return ResponseEntity.ok("Processo di trasformazione aggiunto con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/prodotti/{id}/eliminaProcesso/{processoId}")
    public ResponseEntity<String> eliminaProcessoTrasformazione(@PathVariable int id, @PathVariable int processoId) {
        try {
            prodottiService.eliminaProcessoTrasformazione(id, processoId);
            return ResponseEntity.ok("Processo di trasformazione rimosso con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{venditoreId}")
    public ResponseEntity<Object> getProdottiVenditore(@PathVariable int venditoreId) {
        try {
            List<Prodotto> prodottiVenditore = prodottiService.getProdottiVenditore(venditoreId);
            return ResponseEntity.ok(prodottiVenditore);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
