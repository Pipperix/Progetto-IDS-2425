package cs.unicam.filiera_agricola.Prodotti;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cs.unicam.filiera_agricola.Utenti.UtentiRepository;
import cs.unicam.filiera_agricola.Acquisto.Acquirente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class HandlerOrdine {

    @Autowired
    private OrdiniRepository ordiniRepository;

    private UtentiRepository utentiRepository;

//    @Autowired
//    private AcquirentiRepository acquirentiRepository;

    private static HandlerOrdine instance = new HandlerOrdine();

    public static HandlerOrdine getInstance() {
        return instance;
    }

    // Creazione di un ordine
    @Transactional
    @PostMapping("/creaOrdine")
    public void creaOrdine(int acquirenteId, Map<Prodotto, Integer> prodotti, MetodoPagamento metodoPagamento) {
        // Trova l'acquirente
        Acquirente acquirente = (Acquirente) utentiRepository.findById(acquirenteId)
                .orElseThrow(() -> new RuntimeException("Acquirente non trovato"));

        // Crea l'ordine
        Ordine ordine = new Ordine(LocalDateTime.now(), acquirente, metodoPagamento);
        ordine.setProdotti(prodotti);

        // Salva l'ordine
        ordiniRepository.save(ordine);
    }

    // Restituisce tutti gli ordini di un acquirente
    @GetMapping("/getOrdini")
    @JsonManagedReference
    public List<Ordine> getOrdini(int acquirenteId) {
        return ordiniRepository.findByAcquirenteId(acquirenteId);
    }

    // Notifica Ordine
    @GetMapping("/notificaOrdine")
    public String notificaOrdine(int ordineId) {

//        // Trova l'ordine
//        Ordine ordine = ordiniRepository.findById(ordineId)
//                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
//
//        // Trova l'acquirente
//        Acquirente acquirente = ordine.getAcquirente();
//
//        // Trova Venditori
//        List<Venditore> venditori = new ArrayList<>();
//
//        ordine.getProdotti().keySet().forEach(
//                prodotto -> {
//                    // Trova venditore
//                    Venditore venditore = venditoriRepository.findByProdottoId(prodotto.getId())
//                            .orElseThrow(() -> new RuntimeException("Venditore non trovato"));
//                    venditori.add(venditore);
//        });

        // Notifica l'ordine
        return "Ordine " + ordineId + " notificato";
    }

}
