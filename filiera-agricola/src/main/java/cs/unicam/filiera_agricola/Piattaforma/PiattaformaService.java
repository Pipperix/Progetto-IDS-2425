package cs.unicam.filiera_agricola.Piattaforma;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Utenti.UtenteRegistrato;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PiattaformaService {

    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private ProdottiRepository prodottiRepository;

    public PiattaformaService() {
    }

    @Autowired
    public PiattaformaService(ProdottiRepository prodottiRepository, UtentiRepository utentiRepository) {
        this.prodottiRepository = prodottiRepository;
        this.utentiRepository = utentiRepository;
    }

    // GESTORE
    public void rimuoviProdottiScaduti() {
        List<Prodotto> prodottiScaduti = prodottiRepository.findByDataScadenzaBefore(LocalDate.now());
        if (!prodottiScaduti.isEmpty()) {
            prodottiRepository.deleteAll(prodottiScaduti);
            //prodottiRepository.deleteByDataScadenzaBefore(LocalDate.now());
        } else
            throw new RuntimeException("Nessun prodotto scaduto da rimuovere.");
    }

    // GESTORE
    public void autorizzaAccount(@PathVariable int id) {
        UtenteRegistrato utente = utentiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!utente.isAutorizzato()) {
            utente.setAutorizzato(true); // Modifica lo stato dell'utente
            utentiRepository.save(utente); // Salva l'utente con lo stato aggiornato
        } else {
            throw new RuntimeException("L'utente è già autorizzato.");
        }
    }

}
