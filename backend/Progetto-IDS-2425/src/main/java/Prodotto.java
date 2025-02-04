public class Prodotto {

    private int id;
    private String nome;
    private DescrizioneProdotto descrizioneProdotto;
    private int quantita;

    public Prodotto (int id, String nome, DescrizioneProdotto descrizioneProdotto, int quantita) {
        this.id = id;
        this.nome = nome;
        this.descrizioneProdotto = descrizioneProdotto;
        this.quantita = quantita;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public DescrizioneProdotto getDescrizioneProdotto() { return descrizioneProdotto; }
    public int getQuantita() { return quantita; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescrizioneProdotto(DescrizioneProdotto descrizioneProdotto) { this.descrizioneProdotto = descrizioneProdotto; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizioneProdotto=" + descrizioneProdotto.toString() +
                ", quantita=" + quantita +
                '}';
    }

}
