public class Azienda {

    private String nome;
    private String partitaIva; // è una String (e non long) perché se inizia con degli zero questi vengono considerati
    private Luogo luogo;


    public Azienda(String nome, String partitaIva, Luogo luogo) {
        this.nome = nome;
        this.partitaIva = partitaIva;
        this.luogo = luogo;
    }

    // Getters
    public String getNome() {
        return nome;
    }
    public String getPartitaIva() {
        return partitaIva;
    }
    public Luogo getLuogo() {
        return luogo;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }
}