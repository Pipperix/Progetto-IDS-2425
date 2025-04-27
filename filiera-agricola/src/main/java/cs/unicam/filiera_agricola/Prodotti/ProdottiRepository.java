package cs.unicam.filiera_agricola.Prodotti;

import cs.unicam.filiera_agricola.Vendita.Venditore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProdottiRepository extends JpaRepository<Prodotto, Integer> {

    // Trova tutti i prodotti con data di scadenza passata
    List<Prodotto> findByDataScadenzaBefore(LocalDate now);

    // Trova tutti i prodotti di un determinato venditore
    List<Prodotto> findByVenditore(Venditore venditore);
}