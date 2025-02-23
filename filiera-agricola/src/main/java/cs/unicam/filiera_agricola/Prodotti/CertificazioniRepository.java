package cs.unicam.filiera_agricola.Prodotti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificazioniRepository extends JpaRepository<Certificazione, Integer> {
}