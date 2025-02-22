package cs.unicam.filiera_agricola.Prodotti;

public class ProcessoTrasformazione {
    private String nome;
    private String descrizione;

    public ProcessoTrasformazione(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
