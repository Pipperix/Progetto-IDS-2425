package cs.unicam.filiera_agricola.Eventi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface EventoRepository extends JpaRepository<Evento, Integer> {
        // Trova tutti gli eventi di un determinato animatore
        List<Evento> findByAnimatore(Animatore animatore);
    }
