package cs.unicam.filiera_agricola.Prodotti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdiniRepository extends JpaRepository<Ordine, Integer> {
    List<Ordine> findByAcquirenteId(int acquirenteId);
}
