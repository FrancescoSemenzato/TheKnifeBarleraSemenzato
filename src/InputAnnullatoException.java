package src;

/**
 * Eccezione personalizzata che viene lanciata quando un utente annulla un'operazione di input.
 */
public class InputAnnullatoException extends RuntimeException {
    /**
     * Crea un'istanza dell'eccezione con il messaggio "Input annullato dall'utente".
     */
    public InputAnnullatoException() {
        super("Input annullato dall'utente.");
    }
}