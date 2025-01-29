import java.time.LocalDate;

public class Trasformatore extends Venditore {


    public Trasformatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                         LocalDate dataDiNascita, long partitaIva, Azienda azienda) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita, partitaIva, azienda);
    }


    public ProdottoTrasformato creaContenutoTrasformato(int id, String nome, DescrizioneProdotto descrizioneProdotto) {
        return new ProdottoTrasformato(id, nome, descrizioneProdotto);
    }


    public ProcessoTrasformazione aggiungiProcessoTrasformazione(String nome, String descrizione) {
        return new ProcessoTrasformazione(nome, descrizione);
    }
}
