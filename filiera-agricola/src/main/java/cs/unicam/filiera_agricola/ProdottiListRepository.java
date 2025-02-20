package cs.unicam.filiera_agricola;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ProdottiListRepository extends CrudRepository<Prodotto, Integer> {
}
