import java.util.HashSet;
import java.util.Set;

public class DescrizioneTrasformato extends DescrizioneProdotto {


    private Set<ProcessoTrasformazione> trasformazioni = new HashSet<>();


    public DescrizioneTrasformato(String descrizione, double prezzo, Set<ProcessoTrasformazione> trasformazioni) {
        super(descrizione, prezzo);
        this.trasformazioni = trasformazioni;
    }


}
