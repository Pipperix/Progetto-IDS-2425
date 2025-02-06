import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Animatore extends UtenteRegistrato {

    private List<Evento> eventiCreati;

    public Animatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo, LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
        this.eventiCreati = new ArrayList<>();
    }

    public Evento creaEvento(String nome, String tipo, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                             Luogo luogo, int capienzaPersone) {
        Evento nuovoEvento = new Evento(nome, tipo, descrizione, dataInizio, dataFine, luogo, capienzaPersone);
        eventiCreati.add(nuovoEvento);
        return nuovoEvento;
    }

    public void modificaEvento(Evento evento, String nuovoNome, String nuovoTipo, String nuovaDescrizione,
                               LocalDateTime nuovaDataInizio, LocalDateTime nuovaDataFine, Luogo nuovoLuogo, int nuovaCapienza) {
        if (eventiCreati.contains(evento)) {
            evento.setNome(nuovoNome);
            evento.setTipo(nuovoTipo);
            evento.setDescrizione(nuovaDescrizione);
            evento.setDataInizio(nuovaDataInizio);
            evento.setDataFine(nuovaDataFine);
            evento.setLuogo(nuovoLuogo);
            evento.setCapienzaPersone(nuovaCapienza);
            System.out.println("Evento modificato con successo!");
        } else {
            System.out.println("Errore: L'evento non esiste o non è stato creato da questo animatore.");
        }
    }

    public void eliminaEvento(Evento evento) {
        if (eventiCreati.remove(evento)) {
            System.out.println("Evento eliminato con successo!");
        } else {
            System.out.println("Errore: L'evento non esiste o non è stato creato da questo animatore.");
        }
    }

    // Metodo per visualizzare tutti gli eventi creati da un animatore
    public void mostraEventiCreati() {
        if (eventiCreati.isEmpty()) {
            System.out.println("Nessun evento creato.");
        } else {
            for (Evento evento : eventiCreati) {
                System.out.println("- " + evento.getNome() + " (" + evento.getDataInizio() + " - " + evento.getDataFine() + ")");
            }
        }
    }
}
