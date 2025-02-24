package cs.unicam.filiera_agricola.Prodotti;

import java.time.LocalDate;

public class PacchettoBuilder implements Builder<PacchettoDiProdotti>{
    private PacchettoDiProdotti pacchettoDiProdotti;

    public PacchettoBuilder() {
        pacchettoDiProdotti = new PacchettoDiProdotti();
    }

    @Override
    public Builder<PacchettoDiProdotti> setNome(String nome) {
        pacchettoDiProdotti.setNome(nome);
        return this;
    }

    @Override
    public Builder<PacchettoDiProdotti> setPrezzo(double prezzo) {
        return null;
    }

    @Override
    public Builder<PacchettoDiProdotti> setDataScadenza(LocalDate dataScadenza) {
        return null;
    }

    @Override
    public Builder<PacchettoDiProdotti> setDescrizione(Descrizione descrizione) {
        return null;
    }

    @Override
    public Builder<PacchettoDiProdotti> setProcessoTrasformazione(ProcessoTrasformazione processoTrasformazione) {
        return null;
    }

    @Override
    public Builder<PacchettoDiProdotti> setCertificazione(Certificazione certificazione) {
        return null;
    }

    @Override
    public PacchettoDiProdotti build() {
        // Validazione opzionale degli attributi
        if (pacchettoDiProdotti.getNome() == null || pacchettoDiProdotti.getNome().isEmpty()) {
            throw new IllegalStateException("Il nome del prodotto è obbligatorio");
        }
        if (pacchettoDiProdotti.getProdotti().size() < 2) {
            throw new IllegalStateException("Il pacchetto deve contenere almeno due prodotti");
        }
        return pacchettoDiProdotti;
    }
}
