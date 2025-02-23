package cs.unicam.filiera_agricola.Prodotti;

import java.time.LocalDate;

public interface Builder<T> {
    Builder<T> setNome(String nome);
    Builder<T> setPrezzo(double prezzo);
    Builder<T> setDataScadenza(LocalDate dataScadenza);
    Builder<T> setDescrizione(Descrizione descrizione);
    Builder<T> setProcessoTrasformazione(ProcessoTrasformazione processoTrasformazione);
    Builder<T> setCertificazione(Certificazione certificazione);
    T build();
}
