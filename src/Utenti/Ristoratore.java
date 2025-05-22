package src.Utenti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.Recensione;
import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;

/**
 * Classe che rappresenta un utente con ruolo di Ristoratore.
 * Estende la classe astratta Utente e gestisce una lista di ristoranti associati al ristoratore.
 */
public class Ristoratore extends Utente {
    private static final String FilePathUtenti="FilesCSV/ListaUtenti.csv";
    private ArrayList <Ristorante> ListaRistoranti;

    /**
     * Costruttore vuoto della classe Ristoratore.
     */
    public Ristoratore(){}

    /**
     * Costruttore parametrico della classe Ristoratore, richiede la data di nascita con i numeri interi.
     * @param Nome Nome del ristoratore
     * @param Cognome Cognome del ristoratore
     * @param Username Username
     * @param Password Password
     * @param Domicilio Domicilio
     * @param Giorno Giorno di nascita
     * @param Mese Mese di nascita
     * @param Anno Anno di nascita
     * @param nuovo true se l'utente è appena registrato, false altrimenti
     */
    public Ristoratore(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno, boolean nuovo){
        super(Nome, Cognome, Username, Password, Domicilio, "Ristoratore", Giorno, Mese, Anno, nuovo);

        ListaRistoranti = new ArrayList<Ristorante>();
    }

     /**
     * Costruttore parametrico della classe Ristoratore., richiede la data di nascita formato stringa "gg-mm-aaaa" o "gg/mm/aaaa"
     * @param Nome Nome del ristoratore
     * @param Cognome Cognome del ristoratore
     * @param Username Username
     * @param Password Password
     * @param Domicilio Domicilio
     * @param Data Data di nascita in formato Stringa
     * @param nuovo true se l'utente è appena registrato, false altrimenti
     */
    public Ristoratore(String Nome, String Cognome, String Username, String Password, String Domicilio, String Data, boolean nuovo){
        super(Nome, Cognome, Username, Password, Domicilio, "Ristoratore", Data, nuovo);

        ListaRistoranti = new ArrayList<Ristorante>();
    }


    /**
     * Restituisce la lista dei ristoranti gestiti dal ristoratore.
     * @return Lista di oggetti Ristorante
     */
    public ArrayList<Ristorante> getListaRistoranti(){
        return ListaRistoranti;
    }

    /**
     * Carica i ristoranti associati al ristoratore leggendo dal file CSV degli utenti.
     * @param username Username del ristoratore
     * @param gest GestoreRistoranti per accedere agli oggetti Ristorante
     */
    public void CaricaListaRistoranti(String username, GestoreRistoranti gest) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathUtenti))) {
            br.readLine();  // Salta l'header
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi[0].equals(username) && campi.length > 8) {
                    String[] risto = campi[8].split("_");
                    for (String nomeRistorante : risto) {
                        if (!nomeRistorante.equals("//")) {
                            Ristorante r = gest.getRistorante(nomeRistorante);
                            if (r != null) {
                                ListaRistoranti.add(r);
                            }
                        }
                    }
                    break;  // Esci dal while dopo aver trovato l'utente
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    /**
     * Restituisce una stringa con i nomi dei ristoranti separati da underscore.
     * Se la lista è vuota, restituisce "//".
     * @return Stringa con i nomi dei ristoranti
     */
    public String getRistorantiString() {
        if (ListaRistoranti == null || ListaRistoranti.isEmpty()) {
            return "//";
        }
        
        StringBuilder ristoranti = new StringBuilder();
        for (Ristorante r : ListaRistoranti) {
            if (r != null && r.getNome() != null) {
                ristoranti.append(r.getNome()).append("_");
            }
        }
        
        if (ristoranti.length() > 0 && ristoranti.charAt(ristoranti.length() - 1) == '_') {
            ristoranti.deleteCharAt(ristoranti.length() - 1);
        }
        
        return ristoranti.toString();
    }

    /**
     * Restituisce una descrizione testuale del ristorante, con media stelle e numero recensioni.
     * @param r Ristorante da visualizzare
     * @return Stringa descrittiva del ristorante
     */
    public String VisualizzaRistorante(Ristorante r){
        String ris="";
        return (ris + r.getNome() + ", ha una media di stelle di: " + r.getMediaStelle() + ", ha " + r.getNumeroRecensioni() + " recensioni");
    }

    /**
     * Crea e aggiunge un nuovo ristorante alla lista del ristoratore.
     * @param Nome Nome del ristorante
     * @param Nazione Nazione del ristorante
     * @param Citta Città del ristorante
     * @param Indirizzo Indirizzo del ristorante
     * @param TipoDiCucina Tipo di cucina offerta
     * @param Servizi Servizi disponibili
     * @param URLWeb Sito web del ristorante
     * @param Prezzo Prezzo medio
     * @param Latitudine Latitudine geografica
     * @param Logitudine Longitudine geografica
     * @param FasciaDiPrezzo Fascia di prezzo (1-3)
     * @param Stelle Stelle assegnate
     * @param Delivery true se offre consegna a domicilio
     * @param PrenotazioneOnline true se supporta prenotazione online
     * @return Oggetto Ristorante appena creato
     */
    public Ristorante AggiungiRistorante(String Nome, String Nazione, String Citta, String Indirizzo, String TipoDiCucina, String Servizi, String URLWeb, String Prezzo, Double Latitudine, Double Logitudine, int FasciaDiPrezzo, String Stelle, boolean Delivery, boolean PrenotazioneOnline){
        Ristorante r = new Ristorante(Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Latitudine, Logitudine, FasciaDiPrezzo, Stelle, Delivery, PrenotazioneOnline);
        ListaRistoranti.add(r);
        return r;
    }

    /**
     * Rimuove un ristorante dalla lista del ristoratore.
     * @param r Ristorante da rimuovere
     */
    public void RimuoviRistorante(Ristorante r){
        ListaRistoranti.remove(r);
    }
    
    /**
     * Restituisce una stringa contenente tutte le recensioni dei ristoranti gestiti.
     * @return Stringa con tutte le recensioni
     */
    public String getRecensioniRistoranti(){
        String ris = "Lista di tutte le recensioni dei tuoi ristoranti\n";
        for(Ristorante r : ListaRistoranti)
            for(int i=0; i<r.getRecensioni().size(); i++)
                ris += (i+1) + "- "+ r.getRecensione(i).toString();
        
        return ris;
    }

    /**
     * Restituisce tutte le recensioni di un singolo ristorante identificato tramite indice nella lista.
     * @param index Indice del ristorante
     * @return Stringa con le recensioni del ristorante
     */
    public String getRecensioniRistoranteSingolo(int index){
        String ris = "Lista di tutte le recensioni del ristorante " + ListaRistoranti.get(index).getNome() + ":\n";
        
        for(int i=0; i<ListaRistoranti.get(index).getRecensioni().size(); i++){
            ris += "\n"+ (i+1) + "- " + ListaRistoranti.get(index).getRecensione(i).toString();
        }

        return ris;
    }

    /**
     * Restituisce una lista di recensioni di un singolo ristorante identificato per nome.
     * @param nomeRistorante Nome del ristorante
     * @return Lista di oggetti Recensione
     */
    public ArrayList<Recensione> getRecensioniRistoranteSingolo(String nomeRistorante){
        ArrayList<Recensione> listarecensioni = new ArrayList<Recensione>();
        
        for(Ristorante r : ListaRistoranti){
            if(r.getNome().toLowerCase().equals(nomeRistorante))
                for(int i=0; i<ListaRistoranti.size(); i++)
                        listarecensioni.add(r.getRecensione(i));

        }            
        return listarecensioni;
    }

    /**
     * Aggiunge una risposta a una recensione di un ristorante.
     * @param risposta Testo della risposta
     * @param index Indice della recensione nella lista
     * @param nomeRistorante Nome del ristorante
     * @return Messaggio di conferma
     */
    public String AggiungiRisposta(String risposta, int index, String nomeRistorante){
        ArrayList<Recensione> listaRecensioni = new ArrayList<Recensione>();
        listaRecensioni = getRecensioniRistoranteSingolo(nomeRistorante);

        listaRecensioni.get(index).setRisposta(risposta);

        return "Recensione aggiunta correttamente";
    }

    /**
     * Restituisce una stringa con la valutazione media dei ristoranti del ristoratore e la media generale.
     * @return Stringa con le medie delle valutazioni
     */
    public String RecensioneMediaRistoranti(){
        String ris = "VALUTAZIONE MEDIA DI TUTTI I TUOI RISTORANTI: \n";
        float media=0;
        for(Ristorante r : ListaRistoranti){
            ris += r.getNome() + ": " + r.getMediaStelle() + "\n";
            media += r.getMediaStelle();
        }
        ris += "MEDIA GENERALE: " + (media/ListaRistoranti.size());
        return ris;
    }

    /**
     * Restituisce una stringa con il numero di recensioni ricevute per ciascun ristorante.
     * @return Stringa con conteggio recensioni per ristorante
     */
    public String NumeroRecensioniRicevute(){
        String ris = "NUMERO RECENSIONI DI TUTTI I TUOI RISTORANTI: \n";
        for(Ristorante r : ListaRistoranti)
            ris += r.getNome() + ": " + r.getNumeroRecensioni() + " RECENSIONI\n";
        
        return ris;
    }
}
