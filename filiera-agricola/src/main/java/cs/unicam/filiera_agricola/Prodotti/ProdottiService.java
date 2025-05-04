package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import cs.unicam.filiera_agricola.Vendita.Distributore;
import cs.unicam.filiera_agricola.Vendita.Venditore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    }

    public Prodotto getProdotto(int id) {
        if (prodottiRepository.existsById(id))
            return prodottiRepository.findById(id).get();
        else
            throw new RuntimeException("Prodotto non trovato");
    }

    public void addProdotto(int venditoreId, Prodotto prodotto) {
        Venditore venditore = findVenditore(venditoreId);
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

   // Aggiunge un pacchetto di prodotti
    public void addPacchetto(int distributoreId, PacchettoDiProdotti pacchettoDiProdotti) {
        UtenteRegistrato utente = utentiRepository.findById(distributoreId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Verifico se l'utente è effettivamente un Distributore
        if (utente.getRuolo() != Ruolo.DISTRIBUTORE) {
            throw new RuntimeException("L'id fornito non corrisponde all'id di un distributore");
        }
        // Se l'utente è un Distributore, lo castiamo e associamo all'evento
        Distributore distributore = (Distributore) utente;

        if (pacchettiDiProdottiRepository.existsById(pacchettoDiProdotti.getId())) {
            throw new RuntimeException("Pacchetto già esistente");
        }

        // Controlla che ogni prodotto esista nel marketplace
        for (Prodotto prodotto : pacchettoDiProdotti.getProdotti()) {
            if (!prodottiRepository.existsById(prodotto.getId())) {
                throw new RuntimeException("Prodotto con ID " + prodotto.getId() + " non esiste nel marketplace");
            }
        }
        pacchettiDiProdottiRepository.save(pacchettoDiProdotti);
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

    public List<Prodotto> getProdottiVenditore (int venditoreId) {
        Venditore venditore = findVenditore(venditoreId);
        return prodottiRepository.findByVenditore(venditore);
    }

    private Venditore findVenditore(int venditoreId) {
        UtenteRegistrato utente = utentiRepository.findById(venditoreId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (utente.getRuolo() != Ruolo.VENDITORE && utente.getRuolo() != Ruolo.PRODUTTORE &&
                utente.getRuolo() != Ruolo.TRASFORMATORE && utente.getRuolo() != Ruolo.DISTRIBUTORE) {
            throw new RuntimeException("L'id fornito non corrisponde all'id di un venditore");
        }
        Venditore venditore = (Venditore) utente;
        if (!venditore.isAutorizzato()) {
            throw new RuntimeException("L'account del venditore non è stato ancora autorizzato. " +
                    "Attendi l'autorizzazione del gestore.");
        }
        return venditore;
    }

    public List<Prodotto> getProdottiDaVerificare() {
        List<Prodotto> prodotti = prodottiRepository.findAll();
        List<Prodotto> prodottiNonVerificati = new ArrayList<>();

        for (Prodotto prodotto : prodotti) {
            if (!prodotto.getDescrizione().isApprovato()) {
                prodottiNonVerificati.add(prodotto);
            }
        }
        if (prodottiNonVerificati.isEmpty()) {
            throw new RuntimeException("Nessun prodotto non approvato trovato");
        }
        return prodottiNonVerificati;
    }
}
