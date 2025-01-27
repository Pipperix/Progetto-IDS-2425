import java.time.LocalDateTime;

public class Ordine {

    private LocalDateTime istante;
    private int id;


    public Ordine (LocalDateTime istante, int id) {
        this.istante = istante;
        this.id = id;
    }
}
