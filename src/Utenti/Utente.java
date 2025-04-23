package src.Utenti;

public abstract class Utente {
    private String Nome, Cognome, Username, Password, Domicilio, Ruolo;
    DataDiNascita Data;

    public Utente(){}
    
    public Utente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Ruolo, int Giorno, int Mese, int Anno){
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Username = Username;
        this.Password = CifraPassword(Password);
        this.Domicilio = Domicilio;
        this.Ruolo = Ruolo;
        Data = new DataDiNascita(Giorno, Mese, Anno);
    }

    public Utente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Ruolo, String Data){
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Username = Username;
        this.Password = CifraPassword(Password);
        this.Domicilio = Domicilio;
        this.Ruolo = Ruolo;
        this.Data = new DataDiNascita(Data);
    }

    public String getNome(){ return Nome;}
    public String getCognome(){return Cognome;}
    public String getUsername(){ return Username;}
    public String getPassword(){return CifraPassword(Password);}
    public String getDomicilio(){return Domicilio;}
    public String getRuolo(){return Ruolo;}
    public String getDataDiNascita(){return Data.getDataDiNascita();}
    public DataDiNascita geDataDiNascita(){return Data;}

    private String CifraPassword(String psw) {
        String pswCifrata = "";
    
        for (int i = 0; i < psw.length(); i++) {
            char c = psw.charAt(i);
    
            if (Character.isAlphabetic(c)) {
                // Se vocale
                if ("AEIOUaeiou".indexOf(c) != -1)
                    pswCifrata += (char) (c + 3);
                else
                    pswCifrata += (char) (c + 7);
            } 
            else if (Character.isDigit(c)) {
                int valore = Character.getNumericValue(c);
                if (valore % 2 == 0) {
                    valore = (valore + 10 - 3) % 10;
                } else {
                    valore = (valore + 10 - 7) % 10;
                }
                pswCifrata += (char) ('0' + valore);
            } 
            else {
                pswCifrata += (char) (c + 1);
            }
        }
        return pswCifrata;
    }

    private String DecifraPassword(String pswCifrata) {
        String pswDecifrata = "";
    
        for (int i = 0; i < pswCifrata.length(); i++) {
            char c = pswCifrata.charAt(i);
    
            if (Character.isAlphabetic(c)) {
                if ("AEIOUaeiou".indexOf((char)(c - 3)) != -1)
                    pswDecifrata += (char) (c - 3);
                else
                    pswDecifrata += (char) (c - 7);
            } 
            else if (Character.isDigit(c)) {
                int valore = Character.getNumericValue(c);
                // Per decifrare devo invertire il modulo di prima
                if ((valore + 3) % 2 == 0) {
                    valore = (valore + 3) % 10;
                } else {
                    valore = (valore + 7) % 10;
                }
                pswDecifrata += (char) ('0' + valore);
            } 
            else {
                pswDecifrata += (char) (c - 1);
            }
        }
    
        return pswDecifrata;
    }

    public String getPasswordDecifrata(String ConfermaPassword, String Username){
        if(ConfermaPassword.equals(Password))
            return DecifraPassword(getPassword());
        else
            return "Sbgliato";
    }

    @Override
    public String toString(){
        return "Nome: " + Nome + "\nCognome: " + Cognome + "\nUsername: " + Username + "\nPassword" + Password + "\nDomicilio: "+ Domicilio + "\nData di Nascita: " + Data.getDataDiNascita();
    }
}