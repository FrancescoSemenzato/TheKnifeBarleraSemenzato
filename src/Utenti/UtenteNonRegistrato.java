package src.Utenti;

import src.Recensione;
import java.util.ArrayList;

import src.Ristoranti.Ristorante;

/**
 * Classe che rappresenta un utente non registrato nel sistema.
 * Fornisce metodi per visualizzare i dettagli di un ristorante e le recensioni associate,
 * oltre a un metodo per registrare un nuovo cliente.
 */
public class UtenteNonRegistrato extends Utente{
    /**
     * Costruttore della classe UtenteNonRegistrato.
     * Inizializza un utente con dati di default indicanti un utente non registrato.
     */
    public UtenteNonRegistrato(){
        super(" ", " ", "Guest", " "," ", "Utente non registrato", 01,01, 1900, false);
    }

    /**
     * Restituisce i dettagli di un ristorante specificato.
     * @param ristorante Il ristorante di cui visualizzare i dettagli.
     * @return Una stringa contenente le informazioni del ristorante.
     */
    public String getDettagli(Ristorante ristorante){
        return ristorante.visualizzaRistorante();
    }

    /**
     * Restituisce la lista di recensioni associate a un ristorante specificato.
     * @param ristorante Il ristorante di cui ottenere le recensioni.
     * @return Una lista di oggetti Recensione relativi al ristorante.
     */
    public ArrayList<Recensione> getRecensioni(Ristorante ristorante){
        return ristorante.getRecensioni();
    }

    /**
     * Registra un nuovo cliente con i dati forniti.
     * @param Nome Il nome del cliente.
     * @param Cognome Il cognome del cliente.
     * @param Username L'username scelto per il cliente.
     * @param Password La password scelta per il cliente.
     * @param Domicilio L'indirizzo di domicilio del cliente.
     * @param Giorno Il giorno di nascita del cliente.
     * @param Mese Il mese di nascita del cliente.
     * @param Anno L'anno di nascita del cliente.
     * @return Un oggetto Cliente creato con i dati forniti.
     */
    public Cliente Registrare(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno){
        Cliente cl = new Cliente();
        return cl;
    }
}