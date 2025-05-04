package cs.unicam.filiera_agricola.Utenti;

import cs.unicam.filiera_agricola.Prodotti.ProdottiRepository;
import cs.unicam.filiera_agricola.Prodotti.Prodotto;
import cs.unicam.filiera_agricola.Vendita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @Service
    public class UtentiService {

        @Autowired
        private UtentiRepository utentiRepository;
        @Autowired
        private ProdottiRepository prodottiRepository;

        public UtentiService() {}

        @Autowired
        public UtentiService(UtentiRepository utentiRepository, ProdottiRepository prodottiRepository) {
            this.utentiRepository = utentiRepository;
            this.prodottiRepository = prodottiRepository;
        }


        public List<UtenteRegistrato> getTuttiUtenti() {
            List<UtenteRegistrato> utenti = utentiRepository.findAll();
            return utenti;
        }

        public boolean registrazione(UtenteRegistrato nuovoUtente) {
            // Controllo se username è già in uso
            if (utentiRepository.findByUsername(nuovoUtente.getUsername()).isPresent()) {
                throw new RuntimeException("Utente già registrato. Esegui l'autenticazione");
            }
            // Impostare autorizzato a false automaticamente
            nuovoUtente.setAutorizzato(false);

            if (nuovoUtente.getRuolo() == Ruolo.PRODUTTORE || nuovoUtente.getRuolo() == Ruolo.TRASFORMATORE ||
                    nuovoUtente.getRuolo() == Ruolo.DISTRIBUTORE) {

                VenditoreFactory venditoreFactory = new VenditoreFactory(nuovoUtente.getRuolo());
                UtenteRegistrato utenteSpecifico = venditoreFactory.creaUtente(
                        nuovoUtente.getUsername(),
                        nuovoUtente.getNomeUtente(),
                        nuovoUtente.getCognome(),
                        nuovoUtente.getEmail(),
                        nuovoUtente.getPassword(),
                        nuovoUtente.getLuogo(),
                        ((Venditore) nuovoUtente).getPartitaIva() // Cast per accedere alla partitaIva
                );
            }

            UtenteFactory utenteFactory = new UtenteBaseFactory(nuovoUtente.getRuolo());
            // Creazione dell'utente specifico basato sul ruolo
            UtenteRegistrato utenteSpecifico = utenteFactory.creaUtente(
                    nuovoUtente.getUsername(),
                    nuovoUtente.getNomeUtente(),
                    nuovoUtente.getCognome(),
                    nuovoUtente.getEmail(),
                    nuovoUtente.getPassword(),
                    nuovoUtente.getLuogo()
            );

            // Salvataggio dell'utente nel database
            utentiRepository.save(utenteSpecifico);
            return true;
        }

        public boolean autenticazione(String username, String password) {
                Optional<UtenteRegistrato> user = utentiRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                // Autenticazione riuscita
                return true;
            }
            throw new RuntimeException("Credenziali errate o utente non registrato");
        }

        public boolean disconnessione(String username) {
            UtenteRegistrato user = utentiRepository.findByUsername(username).
                    orElseThrow(() -> new RuntimeException("Utente non trovato"));
            return true;
        }

        public void modificaDatiUtente(int id, UtenteRegistrato nuovoUtente) {
            // Recupera l'utente dal database
            UtenteRegistrato utente = utentiRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));

            // Aggiorna i dati dell'utente
            if (nuovoUtente.getUsername() != null) {
                utente.setUsername(nuovoUtente.getUsername());  // Modifica lo userrname
            }
            if (nuovoUtente.getNomeUtente() != null) {
                utente.setNomeUtente(nuovoUtente.getNomeUtente());  // Modifica il nome
            }
            if (nuovoUtente.getCognome() != null) {
                utente.setCognome(nuovoUtente.getCognome());  // Modifica il cognome
            }
            if (nuovoUtente.getEmail() != null) {
                utente.setEmail(nuovoUtente.getEmail());  // Modifica l'email
            }
            if (nuovoUtente.getPassword() != null) {
                utente.setPassword(nuovoUtente.getPassword());  // Modifica la password
            }
            if (nuovoUtente.getLuogo() != null) {
                utente.setLuogo(nuovoUtente.getLuogo());  // Modifica l'indirizzo
            }
            // Salva l'utente aggiornato
            utentiRepository.save(utente);
        }

        public List<Luogo> visualizzaMappa() {
            List<Luogo> mappa = new ArrayList<>();
            // Recupera gli utenti che sono venditori
            List<UtenteRegistrato> utenti = utentiRepository.findAll();
            for (UtenteRegistrato utente : utenti) {
                //if (utente instanceof Venditore venditore) {
                if (utente.getRuolo() == Ruolo.PRODUTTORE || utente.getRuolo() == Ruolo.TRASFORMATORE ||
                        utente.getRuolo() == Ruolo.DISTRIBUTORE || utente.getRuolo() == Ruolo.VENDITORE) {
                    // Aggiungi il luogo del venditore
                    mappa.add(utente.getLuogo());
                }
            }
            return mappa;
        }

        public void condivisioneSuSocial(int prodottoId, Social social,
                                                           String username, String password) {

            // Recupera il prodotto dal database
            Prodotto prodotto = prodottiRepository.findById(prodottoId)
                    .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

            // Recupera l'utente registrato dal database
            UtenteRegistrato utente = utentiRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        }

        public List<UtenteRegistrato> getUtentiDaAutorizzare() {
            List<UtenteRegistrato> utenti = utentiRepository.findAll();
            List<UtenteRegistrato> utentiNonAutorizzati = new ArrayList<>();

            for (UtenteRegistrato utente : utenti) {
                if (!utente.isAutorizzato()) {
                    utentiNonAutorizzati.add(utente);
                }
            }
            if (utentiNonAutorizzati.isEmpty()) {
                throw new RuntimeException("Nessun utente non autorizzato trovato");
            }
            return utentiNonAutorizzati;
        }

}
