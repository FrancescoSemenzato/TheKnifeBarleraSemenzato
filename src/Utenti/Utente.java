package src.Utenti;


/**
 * Classe astratta che rappresenta un utente generico.
 */
public abstract class Utente{
    private String Nome, Cognome, Username, Password, Domicilio, Ruolo;
    DataDiNascita Data;
    Boolean nuovo;

    /**
     * Costruttore vuoto classe utente
     */
    public Utente(){}

    /**
     * Costruttore parametrico classe utente
     * @param Nome Nome dell'utente
     * @param Cognome Cognome dell'utente
     * @param Username Username dell'utente
     * @param Password Password 
     * @param Domicilio Indirizzo dell'utente 
     * @param Ruolo Ruolo utente (Ristoratore, Cliente, Guest), in base alla classe che viene creata
     * @param Giorno Giorno di nascita
     * @param Mese Mese di nascita
     * @param Anno Anno di nascita
     * @param nuovo Per capire se l'utente è già registrato o no. true=Appena Registrato e false=già registrato in precedenza
     */
    public Utente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Ruolo, int Giorno, int Mese, int Anno, boolean nuovo) {
        this.Nome = Nome.substring(0, 1).toUpperCase() + Nome.substring(1).toLowerCase();
        this.Cognome = Cognome.substring(0, 1).toUpperCase() + Cognome.substring(1).toLowerCase();
        this.Username = Username;
        if (nuovo) {
            this.Password = CifraPassword(Password); // da cifrare
        } else {
            this.Password = Password; // già cifrata
        }
        this.Domicilio = Domicilio;
        this.Ruolo = Ruolo;
        Data = new DataDiNascita(Giorno, Mese, Anno);
    }

    /**
     * Costruttore parametrico classe utente
     * @param Nome Nome dell'utente
     * @param Cognome Cognome dell'utente
     * @param Username Username dell'utente
     * @param Password Password 
     * @param Domicilio Indirizzo dell'utente 
     * @param Ruolo Ruolo utente (Ristoratore, Cliente, Guest), in base alla classe che viene creata
     * @param Data Data di nascita 
     * @param nuovo Per capire se l'utente è già registrato o no. true=Appena Registrato e false=già registrato in precedenza
     */
    public Utente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Ruolo, String Data, boolean nuovo) {
        this.Nome = Nome.substring(0, 1).toUpperCase() + Nome.substring(1).toLowerCase();
        this.Cognome = Cognome.substring(0, 1).toUpperCase() + Cognome.substring(1).toLowerCase();
        this.Username = Username;
        if (nuovo) {
            this.Password = CifraPassword(Password); // da cifrare
        } else {
            this.Password = Password; // già cifrata
        }
        this.Domicilio = Domicilio;
        this.Ruolo = Ruolo;
        this.Data = new DataDiNascita(Data);
    }
    /**
     * Restituisce il Nome dell'utente
     * @return Nome dell'utente
     */
    public String getNome(){ return Nome;}
    /**
     * Restituisce il Cognome dell'utente
     * @return Cognome dell'utente
     */
    public String getCognome(){return Cognome;}
    /**
     * Restituisce lo Username dell'utente 
     * @return Username dell'utente
     */
    public String getUsername(){ return Username;}
    /**
     * Restituisce la Password dell'utente
     * @return Password dell'utente
     */
    public String getPassword(){return Password;}
    /**
     * Restituisce il Domicilio dell'utente
     * @return Domicilio dell'utente
     */
    public String getDomicilio(){return Domicilio;}
    /**
     * Restituisce il Ruolo dell'utente
     * @return Ruolo dell'utente
     */
    public String getRuolo(){return Ruolo;}
    /**
     * Restituisce la Data di nascita dell'utente nel formato "gg-mm-aaaa" o "gg/mm/aaaa"
     * @return La data di nascita dell'utente
     */
    public String getDataDiNascita(){return Data.getDataDiNascita();}
    /**
     * Restituisce una istanza di DataDiNascita dell'utente 
     * @return Istanza di DataDiNascita
     */
    public DataDiNascita geDataDiNascita(){return Data;}

    /**
     * Imposta il nome dell'utente, capitalizzando la prima lettera
     * @param Nome Nome da impostare
     */
    public void setNome(String Nome){this.Nome = Nome.substring(0, 1).toUpperCase() + Nome.substring(1).toLowerCase();}
    /**
     * Imposta il cognome dell'utente, capitalizzando la prima lettera
     * @param Cognome Cognome da impostare
     */
    public void setCognome(String Cognome){this.Cognome = Cognome.substring(0, 1).toUpperCase() + Cognome.substring(1).toLowerCase();}
    /**
     * Imposta lo username dell'utente
     * @param Username Username da impostare
     */
    public void setUsername(String Username){this.Username = Username;}
    /**
     * Imposta la password dell'utente e la cifra
     * @param Password Password in chiaro da cifrare e salvare
     */
    public void setPassword(String Password){this.Password = CifraPassword(Password);}
    /**
     * Imposta il domicilio dell'utente
     * @param Domicilio Domicilio da impostare
     */
    public void setDomicilio(String Domicilio){this.Domicilio = Domicilio;}
    /**
     * Imposta il ruolo dell'utente (Cliente, Ristoratore, Guest, ecc.)
     * @param Ruolo Ruolo da impostare
     */
    public void setRuolo(String Ruolo){this.Ruolo = Ruolo;}
    /**
     * Imposta la data di nascita dell'utente a partire da una stringa
     * @param Data Stringa nel formato "gg-mm-aaaa" o "gg/mm/aaaa"
     */
    public void setDataDiNascita(String Data){this.Data = new DataDiNascita(Data);}
    /**
     * Imposta la data di nascita dell'utente a partire da giorno, mese e anno
     * @param Giorno Giorno di nascita
     * @param Mese Mese di nascita
     * @param Anno Anno di nascita
     */
    public void setDataDiNascita(int Giorno, int Mese, int Anno){this.Data = new DataDiNascita(Giorno, Mese, Anno);} 


    /**
     * Cifra la password seguendo una logica definita:
     * - le vocali vengono spostate di 3 posizioni nel codice ASCII
     * - le consonanti di 7
     * - le cifre pari di -3 modulo 10, dispari di -7 modulo 10
     * - gli altri simboli di +1
     * @param psw Password da cifrare
     * @return Password cifrata
     */
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

    /**
     * Decifra la password cifrata seguendo la logica inversa del metodo CifraPassword
     * @param pswCifrata Password cifrata
     * @return Password decifrata
     */
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

    /**
     * Verifica se la password inserita corrisponde a quella cifrata e in caso positivo la restituisce in chiaro
     * @param passwordInserita Password inserita dall'utente (in chiaro)
     * @param Username Username associato (non usato nel metodo, ma previsto per eventuali controlli futuri)
     * @return Password in chiaro se corretta, altrimenti null
     */
    public String getPasswordDecifrata(String passwordInserita, String Username) {
        if (CifraPassword(passwordInserita).equals(Password)) {
            return passwordInserita; // Restituisce la password in chiaro se corrisponde
        } else {
            return null;
        }
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Utente
     * @return Stringa contenente le informazioni principali dell'utente
     */
    @Override
    public String toString(){
        return "Nome: " + Nome + "\nCognome: " + Cognome + "\nUsername: " + Username + "\nPassword: " + Password + "\nDomicilio: "+ Domicilio + "\nData di Nascita: " + Data.getDataDiNascita();
    }
}