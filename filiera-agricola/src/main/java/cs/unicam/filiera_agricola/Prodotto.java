package cs.unicam.filiera_agricola;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Prodotto {
    @Id
    private int Id;
    private String Nome;
    private double Prezzo;

    public Prodotto() {}

    public Prodotto(int id, String nome, double prezzo) {
        Id = id;
        Nome = nome;
        Prezzo = prezzo;
    }

    public int getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public double getPrezzo() {
        return Prezzo;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setPrezzo(double prezzo) {
        Prezzo = prezzo;
    }
}
