package src.Utenti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataDiNascita {
    private int Giorno, Mese, Anno;
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.now();

    public DataDiNascita(int Giorno, int Mese, int Anno) throws IllegalArgumentException {
        if (!dataValida(Giorno, Mese, Anno)) {
            throw new IllegalArgumentException("Data di nascita non valida");
        }
        this.Giorno = Giorno;
        this.Mese = Mese;
        this.Anno = Anno;
    }

    public DataDiNascita(String data) throws IllegalArgumentException {
        String[] campi;
        try {
            if (data.contains("-")) {
                campi = data.split("-");
            } else if (data.contains("/")) {
                campi = data.split("/");
            } else {
                throw new IllegalArgumentException("Formato data non valido. Usare '-' o '/' come separatori");
            }

            if (campi.length != 3) {
                throw new IllegalArgumentException("Formato data non valido. Usare GG-MM-AAAA o GG/MM/AAAA");
            }

            this.Giorno = Integer.parseInt(campi[0]);
            this.Mese = Integer.parseInt(campi[1]);
            this.Anno = Integer.parseInt(campi[2]);

            if (!dataValida(Giorno, Mese, Anno)) {
                throw new IllegalArgumentException("Data di nascita non valida");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("I valori della data devono essere numerici");
        }
    }

    public int getGiorno() { return Giorno; }
    public int getMese() { return Mese; }
    public int getAnno() { return Anno; }

    private boolean dataValida(int giorno, int mese, int anno) {
        try {
            LocalDate date = LocalDate.of(anno, mese, giorno);
            // Verifica che la data sia compresa tra MIN_DATE e MAX_DATE
            return !date.isBefore(MIN_DATE) && !date.isAfter(MAX_DATE);
        } catch (Exception e) {
            return false; // Se c'è un'eccezione, la data non è valida
        }
    }

    public String getDataDiNascita() {
        return String.format("%02d-%02d-%04d", Giorno, Mese, Anno);
    }

    public int getEta() {
        LocalDate oggi = LocalDate.now();
        LocalDate dataNascita = LocalDate.of(Anno, Mese, Giorno);
        return oggi.getYear() - dataNascita.getYear() - 
               (oggi.getDayOfYear() < dataNascita.getDayOfYear() ? 1 : 0);
    }

    public static boolean etaValida(int giorno, int mese, int anno) {
        try {
            LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
            return LocalDate.now().minusYears(14).isAfter(dataNascita) || 
                   LocalDate.now().minusYears(14).isEqual(dataNascita);
        } catch (Exception e) {
            return false;
        }
    }

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