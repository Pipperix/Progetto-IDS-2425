package cs.unicam.filiera_agricola.Vendita;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Azienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String partitaIva;

    @OneToMany(mappedBy = "azienda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venditore> venditori = new HashSet<>();

    @Embedded
    private Luogo luogo;

    public Azienda() {}

    public Azienda(String nome, String partitaIva, Luogo luogo) {
        this.nome = nome;
        this.partitaIva = partitaIva;
        this.luogo = luogo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Set<Venditore> getVenditori() {
        return venditori;
    }

    public void addVenditore(Venditore venditore) {
        venditori.add(venditore);
        venditore.setAzienda(this);
    }

    public void removeVenditore(Venditore venditore) {
        venditori.remove(venditore);
        venditore.setAzienda(null);
    }

    public Luogo getLuogo() {
        return luogo;
    }

    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }

}