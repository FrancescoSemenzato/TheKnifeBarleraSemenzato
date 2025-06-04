package src.models;

/**
 * Nome: Francesco
 * Cognome: Semenzato
 * Matricola: 760120
 * Sede: Varese
 *
 * Nome: Marco
 * Cognome: Barlera
 * Matricola: 760000
 * Sede: Varese
 * 
 * Descrizione: Eccezione personalizzata che viene lanciata quando un utente annulla un'operazione di input.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
public class InputAnnullatoException extends RuntimeException {
    /**
     * Crea un'istanza dell'eccezione con il messaggio "Input annullato dall'utente".
     */
    public InputAnnullatoException() {
        super("Input annullato dall'utente.");
    }
}