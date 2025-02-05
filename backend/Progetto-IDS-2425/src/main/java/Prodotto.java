import java.util.HashSet;
import java.util.Set;

public class Prodotto {

    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;
    private boolean approvato = false; // Approvato dal Curatore
    private Certificazione certificazione = null; // Solo per Produttori e Trasformatori
    private Set<ProcessoTrasformazione> processiTrasformazione; // Solo per Trasformatori

    public Prodotto() {}

    public Prodotto(int id, String nome, String descrizione, double prezzo, int quantita) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public double getPrezzo() { return prezzo; }
    public int getQuantita() { return quantita; }
    public boolean isApprovato() { return approvato; }
    public Certificazione getCertificazione() { return certificazione; }
    public Set<ProcessoTrasformazione> getProcessiTrasformazione() { return processiTrasformazione; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public void setApprovato(boolean approvato) { this.approvato = approvato; }
    public void setCertificazione(Certificazione certificazione) { this.certificazione = certificazione; }
    public void setProcessiTrasformazione(Set<ProcessoTrasformazione> processiTrasformazione) { this.processiTrasformazione = processiTrasformazione; }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", prezzo=" + getPrezzo() +
                ", quantita=" + getQuantita() +
                ", approvato=" + isApprovato() +
                ", certificazione=" + getCertificazione().toString() +
                ", processiTrasformazione=" + getProcessiTrasformazione().toString() +
                '}';
    }

}
