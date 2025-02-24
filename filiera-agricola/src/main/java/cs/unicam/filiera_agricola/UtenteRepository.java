package cs.unicam.filiera_agricola;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UtenteRepository extends JpaRepository<UtenteRegistrato, Integer> {
    Optional<UtenteRegistrato> findByUsername(String username);
}
