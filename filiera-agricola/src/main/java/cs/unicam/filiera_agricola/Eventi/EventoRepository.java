package cs.unicam.filiera_agricola.Eventi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface EventoRepository extends JpaRepository<Evento, Integer> {
    }
