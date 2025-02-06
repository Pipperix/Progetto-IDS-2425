import java.time.LocalDate;

public class Curatore extends UtenteRegistrato {

    public Curatore(int id, String username, String nome, String cognome, String email, Indirizzo indirizzo,
                    LocalDate dataDiNascita) {
        super(id, username, nome, cognome, email, indirizzo, dataDiNascita);
    }

    // aggiungere criteri secondo i quali un contenuto può essere approvato o meno ?
    // ogni Prodotto ha un boolean che dice se è stato approvato o meno dal curatore
    // ad ogni iterazione il Curatore prende in oggetto solo i Prodotti non ancora approvati
    public boolean approvaContenuto(Prodotto prodotto) {
        System.out.println("Contenuto approvato.");
        return true;
    }
}
