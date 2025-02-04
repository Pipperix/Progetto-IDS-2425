public class ProdottoTrasformato extends Prodotto {

    private DescrizioneTrasformato processo;

    public ProdottoTrasformato(int id, String nome, DescrizioneProdotto descrizioneProdotto, int quantita, DescrizioneTrasformato processo) {
        super(id, nome, descrizioneProdotto, quantita);
        this.processo = processo;
    }

    public ProdottoTrasformato(int id, String nome, DescrizioneProdotto descrizioneProdotto, int quantita) {
        super(id, nome, descrizioneProdotto, quantita);
        this.processo = new DescrizioneTrasformato(descrizioneProdotto.getDescrizione(), descrizioneProdotto.getPrezzo());
    }

    // Getters
    public DescrizioneTrasformato getProcesso() { return processo; }

    // Setters
    public void setProcesso(DescrizioneTrasformato processo) { this.processo = processo; }

    @Override
    public String toString() {
        return "ProdottoTrasformato{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descrizioneProdotto=" + getDescrizioneProdotto().toString() +
                ", quantità=" + getQuantita() +
                ", descrizioneTrasformato=" + this.processo.toString() +
                '}';
    }
}
