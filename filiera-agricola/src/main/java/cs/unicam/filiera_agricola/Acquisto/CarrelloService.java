package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Prodotti.MetodoPagamento;
import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private ProdottiRepository prodottiRepository;

    @Autowired
    private UtentiRepository utentiRepository;

    public List<Prodotto> getCarrello(int acquirenteId) {
        UtenteRegistrato acquirente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (acquirente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        if (carrelloRepository.findByAcquirenteId(acquirenteId) == null)
            throw new RuntimeException("Il carrello è vuoto. Inizia gli acquisti");
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        return carrello.getProdotti();
    }

    public void aggiungiProdotto(int acquirenteId, int prodottoId, int quantita) {
        UtenteRegistrato utente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!(utente instanceof Acquirente)) {
            throw new RuntimeException("L'id fornito non corrisponde a un Acquirente valido");
        }

        Acquirente acquirente = (Acquirente) utente;

        // Recupero o creazione del carrello
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        if (carrello == null) {
            carrello = new Carrello();
            carrello.setAcquirente(acquirente);
            carrello.setProdotti(new ArrayList<>());
        }

        Prodotto prodotto = prodottiRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        if (!prodotto.getDescrizione().isApprovato())
            throw new RuntimeException("Il prodotto non è stato ancora approvato");

        for (int i = 0; i < quantita; i ++) {
            carrello.getProdotti().add(prodotto);
        }
        carrelloRepository.save(carrello);
    }


    public void rimuoviProdotto(int acquirenteId, int prodottoId) {
        UtenteRegistrato acquirente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (acquirente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        Prodotto prodottoDaRimuovere = null;
        for (Prodotto p : carrello.getProdotti()) {
            if (p.getId() == prodottoId) {
                prodottoDaRimuovere = p;
                break;
            }
        }
        if (prodottoDaRimuovere == null) {
            throw new RuntimeException("Il prodotto con ID " + prodottoId + " non è presente nel carrello");
        }
        carrello.getProdotti().remove(prodottoDaRimuovere);
        carrelloRepository.save(carrello);
    }

    public void svuotaCarrello(int acquirenteId) {
        UtenteRegistrato acquirente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (acquirente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        carrello.getProdotti().clear();
        carrelloRepository.save(carrello);
    }

    public double calcolaTotale(int acquirenteId) {
        UtenteRegistrato acquirente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (acquirente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        double totale = 0;
        for (Prodotto prodotto : carrello.getProdotti()) {
            totale += prodotto.getPrezzo();
        }
        return totale;
    }

    public void effettuaPagamento (String username, MetodoPagamento metodoDiPagamento) {
        UtenteRegistrato utente = utentiRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (utente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        Acquirente acquirente = (Acquirente) utente;
        acquirente.setMetodoPagamento(metodoDiPagamento);
        utentiRepository.save(acquirente);
        svuotaCarrello(acquirente.getId());
    }
}
