package cs.unicam.filiera_agricola.Vendita;
import jakarta.persistence.Embeddable;

@Embeddable
public Indirizzo{
    private String via;
    private String civico;
    private String cap;
    private String città;

    public Indirizzo(String via, String civico, String cap, String città){
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.città = città;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }
}