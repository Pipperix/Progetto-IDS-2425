import java.time.LocalDateTime;

public class Evento {

    private String nome;
    private String tipo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Luogo luogo;
    private String descrizione;
    private int capienzaPersone;


    public Evento(String nome, String tipo, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                  Luogo luogo, int capienzaPersone) {
        this.nome = nome;
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.luogo = luogo;
        this.capienzaPersone = capienzaPersone;
    }
}
