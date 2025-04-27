package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.ProcessoTrasformazione;
import cs.unicam.filiera_agricola.Prodotti.ProdottiController;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;
import jakarta.persistence.*;

@Entity
public class Trasformatore extends Venditore{

    public Trasformatore() {}

    public Trasformatore(String username, String nomeUtente, String cognome, String email, String password, Luogo luogo, String partitaIva) {
        super(username, nomeUtente, cognome, email, password, luogo, partitaIva);
    }

    public void aggiungiProcessoTrasformazione(ProdottiController prodotti, int id, ProcessoTrasformazione processo) {
        prodotti.aggiungiProcessoTrasformazione(id, processo);
    }

    public void eliminaProcessoTrasformazione(ProdottiController prodotti, int id, int processoId) {
        prodotti.eliminaProcessoTrasformazione(id, processoId);
    }

    public void aggiungiCertificazione(ProdottiController prodotti, int id, Certificazione certificazione) {
        prodotti.aggiungiCertificazione(id, certificazione);
    }

    public void eliminaCertificazione(ProdottiController prodotti, int id, int certificazioneId) {
        prodotti.eliminaCertificazione(id, certificazioneId);
    }
}