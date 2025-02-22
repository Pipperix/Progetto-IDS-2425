package cs.unicam.filiera_agricola.Prodotti;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificazioniListRepository extends CrudRepository<Certificazione, Integer> {
}