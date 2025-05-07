package src;
public class InputAnnullatoException extends RuntimeException {
    public InputAnnullatoException() {
        super("Input annullato dall'utente.");
    }
}