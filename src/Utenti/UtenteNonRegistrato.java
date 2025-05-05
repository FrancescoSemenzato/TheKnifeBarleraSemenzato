package src.Utenti;

import src.Recensione;
import java.util.ArrayList;

import src.Ristoranti.Ristorante;

public class UtenteNonRegistrato extends Utente{
    public UtenteNonRegistrato(){
        super(" ", " ", "Guest", " "," ", "Utente non registrato", 00,00, 0000, false);
    }

    public String getDettagli(Ristorante ristorante){
        return ristorante.visualizzaRistorante();
    }

    public ArrayList<Recensione> getRecensioni(Ristorante ristorante){
        return ristorante.getRecensioni();
    }

    public Cliente Registrare(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno){
        Cliente cl = new Cliente();
        return cl;
    }
}