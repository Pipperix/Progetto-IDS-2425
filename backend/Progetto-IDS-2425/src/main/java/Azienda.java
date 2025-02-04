public class Azienda {

    private String nome;
    private long partitaIva;
    private Luogo luogo;


    public Azienda(String nome, long partitaIva, Luogo luogo) {
        this.nome = nome;
        this.partitaIva = partitaIva;
        this.luogo = luogo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getPartitaIva() {
        return partitaIva;
    }
    public void setPartitaIva(long partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Luogo getLuogo() {
        return luogo;
    }
    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }
}