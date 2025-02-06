public class DescrizioneProdotto {

    private String descrizione;
    private double prezzo;

    public DescrizioneProdotto(String descrizione, double prezzo) {
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    // Getters
    public String getDescrizione() {
        return descrizione;
    }
    public double getPrezzo() { return prezzo; }

    // Setters
    public void setDescrizione(String descrizione) {
        if (descrizione == null) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "DescrizioneProdotto{" +
                "descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }

}
