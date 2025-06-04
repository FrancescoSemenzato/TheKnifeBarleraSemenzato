
package src.models;

import java.time.LocalDate;

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
 * Descrizione: Classe che rappresenta una data di nascita.
 * Verifica la validità della data e consente di ottenere età e formati leggibili.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
public class DataDiNascita {
    private int Giorno, Mese, Anno;
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.now();

    /**
     * Costruisce una data di nascita con giorno, mese e anno.
     * @param Giorno giorno del mese
     * @param Mese mese dell'anno
     * @param Anno anno di nascita
     * @throws IllegalArgumentException se la data non è valida
     */
    public DataDiNascita(int Giorno, int Mese, int Anno) throws IllegalArgumentException {
        if (!dataValida(Giorno, Mese, Anno)) {
            throw new IllegalArgumentException("Data di nascita non valida");
        }
        this.Giorno = Giorno;
        this.Mese = Mese;
        this.Anno = Anno;
    }

    /**
     * Costruisce una data di nascita a partire da una stringa nel formato "gg-mm-aaaa" o "gg/mm/aaaa".
     * @param data stringa contenente la data
     * @throws IllegalArgumentException se la stringa non rappresenta una data valida
     */
    public DataDiNascita(String data) {
        String[] parts = data.split("[-/]");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Formato data non valido: " + data);
        }
    
        try {
            Giorno = Integer.parseInt(parts[0]);
            Mese = Integer.parseInt(parts[1]);
            Anno = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numeri non validi nella data: " + data);
        }
    
        if (!dataValida(Giorno, Mese, Anno)) {
            throw new IllegalArgumentException("Data di nascita non valida: " + data);
        }
    }

    /**
     * @return il giorno della data di nascita
     */
    public int getGiorno() { return Giorno; }

    /**
     * @return il mese della data di nascita
     */
    public int getMese() { return Mese; }

    /**
     * @return l'anno della data di nascita
     */
    public int getAnno() { return Anno; }

    /**
     * Verifica se una data è valida e compresa tra il 01/01/1900 e oggi.
     * @param giorno giorno del mese
     * @param mese mese dell'anno
     * @param anno anno
     * @return true se la data è valida, false altrimenti
     */
    private boolean dataValida(int giorno, int mese, int anno) {
        try {
            LocalDate date = LocalDate.of(anno, mese, giorno);
            // Verifica che la data sia compresa tra MIN_DATE e MAX_DATE
            return !date.isBefore(MIN_DATE) && !date.isAfter(MAX_DATE);
        } catch (Exception e) {
            return false; // Se c'è un'eccezione, la data non è valida
        }
    }

    /**
     * Restituisce la data di nascita formattata come stringa "gg-mm-aaaa".
     * @return la data di nascita in formato stringa
     */
    public String getDataDiNascita() {
        return String.format("%02d-%02d-%04d", Giorno, Mese, Anno);
    }

    /**
     * Calcola l'età in anni della persona.
     * @return l'età corrente
     */
    public int getEta() {
        LocalDate oggi = LocalDate.now();
        LocalDate dataNascita = LocalDate.of(Anno, Mese, Giorno);
        return oggi.getYear() - dataNascita.getYear() - 
               (oggi.getDayOfYear() < dataNascita.getDayOfYear() ? 1 : 0);
    }

    /**
     * Verifica se una persona ha almeno 14 anni.
     * @param giorno giorno del mese
     * @param mese mese dell'anno
     * @param anno anno
     * @return true se la persona ha almeno 14 anni, false altrimenti
     */
    public static boolean etaValida(int giorno, int mese, int anno) {
        try {
            LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
            return LocalDate.now().minusYears(14).isAfter(dataNascita) || 
                   LocalDate.now().minusYears(14).isEqual(dataNascita);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se una persona è maggiorenne (almeno 18 anni).
     * @param giorno giorno del mese
     * @param mese mese dell'anno
     * @param anno anno
     * @return true se la persona è maggiorenne, false altrimenti
     */
    public static boolean maggiorenne(int giorno, int mese, int anno) {
        try {
            LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
            return LocalDate.now().minusYears(18).isAfter(dataNascita) || 
                   LocalDate.now().minusYears(18).isEqual(dataNascita);
        } catch (Exception e) {
            return false;
        }
    }
}