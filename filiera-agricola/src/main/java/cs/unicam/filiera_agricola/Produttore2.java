package cs.unicam.filiera_agricola.Vendita;

import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Prodotti.Certificazione;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public Produttore extends Venditore(){
    @OneToMany(mappedBy = "certificazione_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Certificazione> certificazioniCreate = new HashSet<>();

    public Produttore() {}

    public Produttore(String partitaIva) {
        super(partitaIva);
    }

    public void aggiungiCertificazione(Prodotto prodotto, Certificazione certificazione){
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().aggiungiCertificazione(certificazione);
            certificazioniCreate.add(certificazione);
        }
    }

    public void modificaCertificazione(Prodotto prodotto, Certificazione certificazione){
        if (prodotto.getDescrizione() != null) {
            for (Certificazione cert : prodotto.getDescrizione().getCertificazioni()) {
                if (cert.getId() == nuovaCertificazione.getId()) {
                    cert.setNome(nuovaCertificazione.getNome());
                    break;
                }
            }
        }
    }

    public void rimuoviCertificazione(Prodotto prodotto, Certificazione certificazione){
        if (prodotto.getDescrizione() != null) {
            prodotto.getDescrizione().getCertificazioni().remove(certificazione);
            certificazioniCreate.remove(certificazione);
        }
    }
}