public class ProcessoTrasformazione {

    private String nome;
    private String descrizione;

    public ProcessoTrasformazione(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "ProcessoTrasformazione{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

}