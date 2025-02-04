import java.util.HashSet;
import java.util.Set;

public class DescrizioneTrasformato extends DescrizioneProdotto {

    private Set<ProcessoTrasformazione> trasformazioni = new HashSet<>();

    public DescrizioneTrasformato(String descrizione, double prezzo, Set<ProcessoTrasformazione> trasformazioni) {
        super(descrizione, prezzo);
        this.trasformazioni = trasformazioni;
    }

    public DescrizioneTrasformato(String descrizione, double prezzo) {
        super(descrizione, prezzo);
        this.trasformazioni = new HashSet<>();
    }

    public Set<ProcessoTrasformazione> getTrasformazioni() {
        return new HashSet<>(trasformazioni);
    }

    public void aggiungiTrasformazione(ProcessoTrasformazione trasformazione) {
        if (trasformazione != null) {
            trasformazioni.add(trasformazione);
        } else {
            throw new IllegalArgumentException("La trasformazione non può essere null.");
        }
    }

    public void rimuoviTrasformazione(ProcessoTrasformazione trasformazione) {
        trasformazioni.remove(trasformazione);
    }

    @Override
    public String toString() {
        return "DescrizioneTrasformato{" +
                "descrizione='" + getDescrizione() + '\'' +
                ", prezzo=" + getPrezzo() +
                ", trasformazioni=" + trasformazioni +
                '}';
    }


}
