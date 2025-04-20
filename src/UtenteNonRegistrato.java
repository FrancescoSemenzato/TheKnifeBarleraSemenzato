package src;

public class UtenteNonRegistrato extends Utente{
    public UtenteNonRegistrato(){
        super(" ", " ", "Guest", " "," ", "Utente non registrato", 00,00, 0000);
    }

    public String getDettagli(Ristorante ristorante){
        return ristorante.toString();
    }

    public void getRecensioni(Ristorante ristorante){
        ristorante.getRecensioni();
    }

    public ClienteRegistrato Registrare(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno){
        ClienteRegistrato cl = new ClienteRegistrato();
        return cl;
    }
}