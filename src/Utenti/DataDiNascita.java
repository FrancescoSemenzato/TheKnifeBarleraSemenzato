package src.Utenti;

public class DataDiNascita {
    private int Giorno, Mese, Anno;

    public DataDiNascita(int Giorno, int Mese, int Anno){
        /*Fare un inserimento:
         * Giorno-> input Giorno
         * Mese -> input Mese
         * Anno -> input Anno
         */
        this.Giorno = Giorno;
        this.Mese = Mese;
        this.Anno = Anno;
    }

    public DataDiNascita(String data){
        String[] campi;
        if(data.charAt(2) == '-')
            campi = data.split("-");
        else
            campi = data.split("/");
        
        this.Giorno = Integer.parseInt(campi[0]);
        this.Mese = Integer.parseInt(campi[1]);
        this.Anno = Integer.parseInt(campi[2]);
    }

    public String getDataDiNascita() {
        return String.format("%02d-%02d-%04d", Giorno, Mese, Anno);
    }
}
