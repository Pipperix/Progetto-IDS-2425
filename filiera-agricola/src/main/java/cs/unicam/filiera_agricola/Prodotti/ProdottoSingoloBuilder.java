package cs.unicam.filiera_agricola.Prodotti;
import java.time.LocalDate;

public class ProdottoSingoloBuilder implements Builder<Prodotto> {
    private Prodotto prodotto;

    public ProdottoSingoloBuilder() {
        prodotto = new Prodotto();
    }

    @Override
    public ProdottoSingoloBuilder setNome(String nome) {
        prodotto.setNome(nome);
        return this;
    }

    @Override
    public ProdottoSingoloBuilder setPrezzo(double prezzo) {
        prodotto.setPrezzo(prezzo);
        return this;
    }

    @Override
    public ProdottoSingoloBuilder setDataScadenza(LocalDate dataScadenza) {
        prodotto.setDataScadenza(dataScadenza);
        return this;
    }

    @Override
    public ProdottoSingoloBuilder setDescrizione(Descrizione descrizione) {
        prodotto.setDescrizione(descrizione);
        return this;
    }

    @Override
    public ProdottoSingoloBuilder setProcessoTrasformazione(ProcessoTrasformazione processoTrasformazione) {
        prodotto.setProcessoTrasformazione(processoTrasformazione);
        return this;
    }

    @Override
    public ProdottoSingoloBuilder setCertificazione(Certificazione certificazione) {
        prodotto.setCertificazione(certificazione);
        return this;
    }

    @Override
    public Prodotto build() {
        // Validazione opzionale degli attributi
        if (prodotto.getNome() == null || prodotto.getNome().isEmpty()) {
            throw new IllegalStateException("Il nome del prodotto è obbligatorio");
        }
        if (prodotto.getPrezzo() <= 0) {
            throw new IllegalStateException("Il prezzo deve essere maggiore di 0");
        }
        return prodotto;
    }
}