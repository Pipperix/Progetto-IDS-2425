import java.util.Objects;

public class Indirizzo {

    private String citta;
    private int cap;
    private String via;
    private int civico;

    public Indirizzo(String citta, int cap, String via, int civico) {
        this.citta = Objects.requireNonNull(citta, "La città non può essere nulla.");
        if (cap <= 0) throw new IllegalArgumentException("Il CAP deve essere maggiore di 0.");
        this.cap = cap;
        this.via = Objects.requireNonNull(via, "La via non può essere nulla.");
        if (civico <= 0) throw new IllegalArgumentException("Il civico deve essere maggiore di 0.");
        this.civico = cap;
    }

    // Getter
    public String getCitta() {
        return citta;
    }

    public int getCap() {
        return cap;
    }

    public String getVia() {
        return via;
    }

    public int getCivico() {
        return civico;
    }

    // Setter
    public void setCitta(String citta) {
        this.citta = Objects.requireNonNull(citta, "La città non può essere nulla.");
    }

    public void setCap(int cap) {
        if (cap <= 0) throw new IllegalArgumentException("Il CAP deve essere maggiore di 0.");
        this.cap = cap;
    }

    public void setVia(String via) {
        this.via = Objects.requireNonNull(via, "La via non può essere nulla.");
    }

    public void setCivico(int civico) {
        if (civico <= 0) throw new IllegalArgumentException("Il civico deve essere maggiore di 0.");
        this.civico = cap;
    }

}
