package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.Ruolo;
import cs.unicam.filiera_agricola.Utenti.Utente;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        return carrello.getProdotti();
    }

    // aggiungere anche la quantità di quanti prodotti si aggiungono?
    public void aggiungiProdotto(int acquirenteId, int prodottoId) {
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
        carrello.getProdotti().add(prodotto);
        carrelloRepository.save(carrello);
    }


    public void rimuoviProdotto(int acquirenteId, int prodottoId) {
        UtenteRegistrato acquirente = utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (acquirente.getRuolo() != Ruolo.ACQUIRENTE)
            throw new RuntimeException("L'id fornito non corrisponde all'id di un acquirente");
        Carrello carrello = carrelloRepository.findByAcquirenteId(acquirenteId);
        carrello.getProdotti().removeIf(prodotto -> prodotto.getId() == prodottoId);
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
}
