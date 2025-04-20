package src;

public class Utente {
    private String Nome, Cognome, Username, Password, Domicilio, Ruolo;
    DataDiNascita Data;

    public Utente(){}
    
    public Utente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Ruolo, int Giorno, int Mese, int Anno){
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Username = Username;
        this.Password = Password;
        this.Domicilio = Domicilio;
        this.Ruolo = Ruolo;
        Data = new DataDiNascita(Giorno, Mese, Anno);
    }

    @Override
    public String toString(){
        return "Nome: " + Nome + "\nCognome: " + Cognome + "\nUsername: " + Username + "\nPassword" + Password + "\nDomicilio: "+ Domicilio + "\nData di Nascita: " + Data.getDataDiNascita();
    }
}
