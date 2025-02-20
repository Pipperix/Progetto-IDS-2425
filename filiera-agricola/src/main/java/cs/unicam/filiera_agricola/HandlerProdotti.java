package cs.unicam.filiera_agricola;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HandlerProdotti {

    @Autowired
    private ProdottiListRepository prodottiListRepository;

    @Autowired
    public HandlerProdotti(ProdottiListRepository prodottiListRepository) {
        Prodotto test = new Prodotto(1,"Prova", 1.0);
        prodottiListRepository.save(test);
    }

    @GetMapping(value = "/prodotti")
    public ResponseEntity<Object> getProdotti() {
        return ResponseEntity.ok(prodottiListRepository.findAll());
    }

    @GetMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> getProdotto(@PathVariable int id) {
        if (prodottiListRepository.existsById(id))
            return ResponseEntity.ok(prodottiListRepository.findById(id).get());
        else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    @PostMapping(value = "/addProdotto")
    public ResponseEntity<Object> addProdotto(@RequestBody Prodotto prodotto) {
        if (!prodottiListRepository.existsById(prodotto.getId())) {
            prodottiListRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiunto");
        } else
            return ResponseEntity.badRequest().body("Prodotto già esistente");
    }

    @DeleteMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> deleteProdotto(@PathVariable int id) {
        if (prodottiListRepository.existsById(id)) {
            prodottiListRepository.deleteById(id);
            return ResponseEntity.ok("Prodotto eliminato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

    @PutMapping(value = "/prodotti/{id}")
    public ResponseEntity<Object> updateProdotto(@PathVariable int id, @RequestBody Prodotto prodotto) {
        if (prodottiListRepository.existsById(id)) {
            // prodottiListRepository.deleteById(id);
            prodottiListRepository.save(prodotto);
            return ResponseEntity.ok("Prodotto aggiornato");
        } else
            return ResponseEntity.badRequest().body("Prodotto non esistente");
    }

//    @PutMapping(value = "/updateProdotto")
//    public ResponseEntity<Object> updateProdotto(@PathParam ("id") @RequestBody Prodotto prodotto) {
//        if (prodottiListRepository.existsById(prodotto.getId())) {
//            prodottiListRepository.save(prodotto);
//            return ResponseEntity.ok("Prodotto aggiornato");
//        } else
//            return ResponseEntity.badRequest().body("Prodotto non esistente");
//    }

}
