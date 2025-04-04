package cs.unicam.filiera_agricola.Prodotti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProdottiRepository extends JpaRepository<Prodotto, Integer> {

    // Trova tutti i prodotti con data di scadenza passata
    List<Prodotto> findByDataScadenzaBefore(LocalDate now);

    // Rimuove tutti i prodotti con data di scadenza passata
    //void deleteByDataScadenzaBefore(LocalDate now);

}