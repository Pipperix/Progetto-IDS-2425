package cs.unicam.filiera_agricola.Acquisto;

import cs.unicam.filiera_agricola.Eventi.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
    Carrello findByAcquirenteId(int acquirenteId);
}
