package src;

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

    public String getDataDiNascita(){ return "" + Giorno + "-" + Mese + "-" + Anno; }
}
