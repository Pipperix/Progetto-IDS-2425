public class Certificazione {

    private int id;
    private String nome;
    private String descrizione;


    public Certificazione(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescrizione() {
        return descrizione;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        }
        this.nome = nome;
    }
    public void setDescrizione(String descrizione) {
        if (descrizione == null) {
            throw new IllegalArgumentException("La descrizione non può essere vuota.");
        }
        this.descrizione = descrizione;
    }
}
