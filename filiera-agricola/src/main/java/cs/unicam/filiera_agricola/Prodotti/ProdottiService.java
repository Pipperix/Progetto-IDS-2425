package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import cs.unicam.filiera_agricola.Vendita.Venditore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @Service
    public class ProdottiService {

    @Autowired
    private ProdottiRepository prodottiRepository;
    @Autowired
    private PacchettiDiProdottiRepository pacchettiDiProdottiRepository;
    @Autowired
    private CertificazioniRepository certificazioniRepository;
    @Autowired
    private ProcessiTrasformazioneRepository processiTrasformazioneRepository;
    @Autowired
    private UtentiRepository utentiRepository;

    public ProdottiService() {
    }

    @Autowired
    public ProdottiService(ProdottiRepository prodottiRepository, PacchettiDiProdottiRepository pacchettiDiProdottiRepository,
                           CertificazioniRepository certificazioniRepository, ProcessiTrasformazioneRepository
                                       processiTrasformazioneRepository, UtentiRepository utentiRepository) {
        this.prodottiRepository = prodottiRepository;
        this.pacchettiDiProdottiRepository = pacchettiDiProdottiRepository;
        this.certificazioniRepository = certificazioniRepository;
        this.processiTrasformazioneRepository = processiTrasformazioneRepository;
        this.utentiRepository = utentiRepository;
    }

    // Ritorna tutti i prodotti
    public List<Prodotto> getProdotti() {
        return prodottiRepository.findAll();
                /*
                .stream()
                .filter(prodotto -> prodotto.getDescrizione().isApprovato())
                .toList();
                 */
    }

    public Prodotto getProdotto(int id) {
        if (prodottiRepository.existsById(id))
            return prodottiRepository.findById(id).get();
        else
            throw new RuntimeException("Prodotto non trovato");
    }

    public void addProdotto(int venditoreId, Prodotto prodotto) {
    UtenteRegistrato utente = utentiRepository.findById(venditoreId)
            .orElseThrow(() -> new RuntimeException("Utente non trovato"));

    // Verifico se l'utente è effettivamente un Animatore
    if (utente.getRuolo() != Ruolo.VENDITORE && utente.getRuolo() != Ruolo.PRODUTTORE &&
            utente.getRuolo() != Ruolo.TRASFORMATORE && utente.getRuolo() != Ruolo.DISTRIBUTORE) {
        throw new RuntimeException("L'id fornito non corrisponde all'id di un venditore");
    }
    // Se l'utente è un Venditore, lo castiamo e associamo all'evento
    Venditore venditore = (Venditore) utente;
    prodotto.setVenditore(venditore); // Imposto il venditore sul prodotto

    prodottiRepository.save(prodotto);
}

    // Elimina un prodotto
    public void deleteProdotto(int id) {
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
        } else
            throw new RuntimeException("Prodotto non trovato");
    }


    public void updateProdotto(int id, Prodotto prodottoModificato) {
    Prodotto prodotto = prodottiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
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
}

    // Ritorna tutti i pacchetti di prodotti
    public List<PacchettoDiProdotti> getPacchetti() {
        return pacchettiDiProdottiRepository.findAll();
    }

    // Dettaglio pacchetto di prodotti
    public PacchettoDiProdotti getPacchetto(int id) {
        if (pacchettiDiProdottiRepository.existsById(id))
            return pacchettiDiProdottiRepository.findById(id).get();
        else
            throw new RuntimeException("Pacchetto non trovato");
    }

    // TODO: aggiungere controllo che ti dice se il prodotto che stai tentando di aggiungere non è
    //  disponibile nel marketplace?
    // Aggiunge un pacchetto di prodotti
    public void addPacchetto(@RequestBody PacchettoDiProdotti pacchettoDiProdotti) {
        if (!pacchettiDiProdottiRepository.existsById(pacchettoDiProdotti.getId())) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
        } else
            throw new RuntimeException("Pacchetto già esistente");
    }

    // Elimina un pacchetto di prodotti
    public void deletePacchetto(int id) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.deleteById(id);
        } else
            throw new RuntimeException("Pacchetto non trovato");
    }

    // Aggiorna un pacchetto di prodotti
    public void updatePacchetto(int id, PacchettoDiProdotti pacchettoDiProdotti) {
        if (pacchettiDiProdottiRepository.existsById(id)) {
            pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
        } else
            throw new RuntimeException("Pacchetto non trovato");
    }

    // CURATORE
    public void approvaContenuto(int id) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        if (!prodotto.getDescrizione().isApprovato()) {
            prodotto.getDescrizione().setApprovato(true);
            prodottiRepository.save(prodotto);
        } else {
            throw new RuntimeException("Il prodotto è già approvato.");
            }
    }

    // VENDITORE
    // Modifica quantità di un prodotto
    public void modificaQuantita(int id, int quantita) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        prodotto.getDescrizione().setQuantita(quantita);
        prodottiRepository.save(prodotto);
    }

    //TODO: verificare prima che il prodotto è approvato?
    @PostMapping(value = "/prodotti/{id}/aggiungiCertificazione", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void aggiungiCertificazione(int id, Certificazione certificazione) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        prodotto.getDescrizione().aggiungiCertificazione(certificazione);
        prodottiRepository.save(prodotto);
    }

    public void eliminaCertificazione(int id, int certificazioneId) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        Certificazione certificazione = certificazioniRepository.findById(certificazioneId).
                orElseThrow(() -> new RuntimeException("Certificazione non trovata"));
        prodotto.getDescrizione().rimuoviCertificazione(certificazione);
        prodottiRepository.save(prodotto);
    }

    //TODO: verificare prima che il prodotto è approvato?
    public void aggiungiProcessoTrasformazione(int id, ProcessoTrasformazione processoTrasformazione) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        prodotto.getDescrizione().aggiungiProcessoTrasformazione(processoTrasformazione);
        prodottiRepository.save(prodotto);
    }

    public void eliminaProcessoTrasformazione(int id, int processoId) {
        Prodotto prodotto = prodottiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        ProcessoTrasformazione processoTrasformazione = processiTrasformazioneRepository.findById(processoId).
                orElseThrow(() -> new RuntimeException("Processo di trasformazione non trovato"));
        prodotto.getDescrizione().rimuoviProcessoTrasformazione(processoTrasformazione);
        prodottiRepository.save(prodotto);
    }

}
