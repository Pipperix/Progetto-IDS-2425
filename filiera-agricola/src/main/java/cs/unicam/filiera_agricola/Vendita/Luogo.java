package cs.unicam.filiera_agricola.Vendita;
import jakarta.persistence.*;

@Embeddable
public class Luogo{
    private String nome;
    double latitudine;
    double longitudine;
    @Embedded
    private Indirizzo indirizzo;

    public Luogo() {}

    public Luogo(String nome, Indirizzo indirizzo, double latitudine, double longitudine) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getLatitudine() {return latitudine;}

    public void setLatitudine(double latitudine) {this.latitudine = latitudine;}

    public double getLongitudine() {return longitudine;}

    public void setLongitudine(double longitudine) {this.longitudine = longitudine;}
}