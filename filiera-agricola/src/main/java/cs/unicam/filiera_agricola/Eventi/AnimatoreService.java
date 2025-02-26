package cs.unicam.filiera_agricola.Eventi;

import cs.unicam.filiera_agricola.FilieraAgricolaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimatoreService {
    private final FilieraAgricolaFacade filieraAgricolaFacade;

    @Autowired
    public AnimatoreService(FilieraAgricolaFacade filieraAgricolaFacade) {
        this.filieraAgricolaFacade = filieraAgricolaFacade;
    }

    public void creaEvento(Evento evento) {
        // Usa il Facade per creare l'evento
        filieraAgricolaFacade.creaEvento(evento);
    }

    public void modificaEvento(Evento evento) {
        Evento nuovoEvento = new Evento();
        filieraAgricolaFacade.modificaEvento(evento.getId(), nuovoEvento);
    }

    public void eliminaEvento(Evento evento) {
        filieraAgricolaFacade.eliminaEvento(evento.getId());
    }
}
