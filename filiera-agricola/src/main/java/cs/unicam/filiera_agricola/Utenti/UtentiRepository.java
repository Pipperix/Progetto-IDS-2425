package cs.unicam.filiera_agricola.Utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UtentiRepository extends JpaRepository<UtenteRegistrato, Integer> {
    Optional<UtenteRegistrato> findByUsername(String username);
}
