public class Posizione {

    private double latitudine;
    private double longitudine;

    public Posizione(double latitudine, double longitudine) {
        if (latitudine < -90 || latitudine > 90) throw new IllegalArgumentException("La latitudine deve essere compresa tra -90 e 90.");
        if (longitudine < -180 || longitudine > 180) throw new IllegalArgumentException("La longitudine deve essere compresa tra -180 e 180.");
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    // Getter
    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    // Setter
    public void setLatitudine(double latitudine) {
        if (latitudine < -90 || latitudine > 90) throw new IllegalArgumentException("La latitudine deve essere compresa tra -90 e 90.");
        this.latitudine = latitudine;
    }

    public void setLongitudine(double longitudine) {
        if (longitudine < -180 || longitudine > 180) throw new IllegalArgumentException("La longitudine deve essere compresa tra -180 e 180.");
        this.longitudine = longitudine;
    }
}

