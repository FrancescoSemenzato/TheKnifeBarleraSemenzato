package src.Utenti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.Recensione;
import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;

/**
 * La classe Cliente rappresenta un utente registrato al sistema con ruolo "Cliente".
 * Ogni cliente può aggiungere/rimuovere ristoranti preferiti e lasciare/modificare recensioni.
 */
public class Cliente extends Utente {

    /** Percorso del file CSV contenente tutte le recensioni. */
    public static final String FilePathRecensioni = "FilesCSV/ListaRecensioni.csv";

    /** Percorso del file CSV contenente tutti gli utenti. */
    public static final String FilePathUtenti = "FilesCSV/ListaUtenti.csv";

    private ArrayList<Recensione> ListaRecensioniUtente;
    private ArrayList<Ristorante> ListaPreferiti;

    /** Costruttore vuoto. */
    public Cliente() {}

    /**
     * Costruttore per Cliente con data di nascita separata.
     * 
     * @param Nome Nome del cliente.
     * @param Cognome Cognome del cliente.
     * @param Username Username scelto.
     * @param Password Password scelta.
     * @param Domicilio Indirizzo di residenza.
     * @param Giorno Giorno di nascita.
     * @param Mese Mese di nascita.
     * @param Anno Anno di nascita.
     * @param nuovo Specifica se l'utente è nuovo.
     */
    public Cliente(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno, boolean nuovo) {
        super(Nome, Cognome, Username, Password, Domicilio, "Cliente", Giorno, Mese, Anno, nuovo);
        ListaRecensioniUtente = new ArrayList<>();
        ListaPreferiti = new ArrayList<>();
    }

    /**
     * Costruttore per Cliente con data di nascita come stringa.
     * 
     * @param Nome Nome del cliente.
     * @param Cognome Cognome del cliente.
     * @param Username Username scelto.
     * @param Password Password scelta.
     * @param Domicilio Indirizzo di residenza.
     * @param Data Data di nascita in formato stringa.
     * @param nuovo Specifica se l'utente è nuovo.
     */
    public Cliente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Data, boolean nuovo) {
        super(Nome, Cognome, Username, Password, Domicilio, "Cliente", Data, nuovo);
        ListaRecensioniUtente = new ArrayList<>();
        CaricaListaRecensione(ListaRecensioniUtente);
        ListaPreferiti = new ArrayList<>();
    }

    /** @return La lista delle recensioni lasciate dall'utente. */
    public ArrayList<Recensione> getListaRecensioni() {
        return ListaRecensioniUtente;
    }

    /** @return La lista dei ristoranti preferiti. */
    public ArrayList<Ristorante> getPreferiti() {
        return ListaPreferiti;
    }

    /**
     * @return Una stringa con i nomi dei ristoranti preferiti separati da underscore,
     * oppure "//" se la lista è vuota.
     */
    public String getPreferitiString() {
        if (ListaPreferiti.isEmpty()) return "//";
        StringBuilder preferiti = new StringBuilder();
        for (Ristorante r : ListaPreferiti) {
            if (r != null) preferiti.append(r.getNome()).append("_");
        }
        if (preferiti.length() > 0 && preferiti.charAt(preferiti.length() - 1) == '_') {
            preferiti.deleteCharAt(preferiti.length() - 1);
        }
        return preferiti.toString();
    }

    /**
     * Carica le recensioni scritte dall'utente corrente dal file CSV.
     * 
     * @param rec Lista da riempire con le recensioni dell'utente.
     */
    public void CaricaListaRecensione(ArrayList<Recensione> rec) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathRecensioni))) {
            br.readLine(); // Ignora intestazione
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi.length >= 5 && campi[4].equals(getUsername())) {
                    if (campi[3].equals("")) {
                        rec.add(new Recensione(Integer.parseInt(campi[1]), campi[2], campi[4], campi[0]));
                    } else {
                        rec.add(new Recensione(Integer.parseInt(campi[1]), campi[2], campi[4], campi[0], campi[3]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    /**
     * Carica la lista dei preferiti dell'utente a partire dal file CSV degli utenti.
     * Deve essere chiamato solo dopo il login.
     *
     * @param username Username del cliente.
     * @param gest Gestore dei ristoranti per ricavare gli oggetti Ristorante.
     */
    public void CaricaListaPreferiti(String username, GestoreRistoranti gest) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathUtenti))) {
            br.readLine(); // Salta header
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi[0].equals(username) && campi.length > 7) {
                    String preferitiStr = campi[7].trim();
                    if (!preferitiStr.equals("//") && !preferitiStr.isEmpty()) {
                        String[] preferiti = preferitiStr.split("_");
                        for (String nomeRistorante : preferiti) {
                            nomeRistorante = nomeRistorante.trim();
                            if (!nomeRistorante.isEmpty()) {
                                Ristorante r = gest.getRistorante(nomeRistorante);
                                if (r != null) ListaPreferiti.add(r);
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    /**
     * Aggiunge un ristorante alla lista dei preferiti, se non già presente.
     * 
     * @param ristorante Ristorante da aggiungere.
     * @return Messaggio di conferma o avviso.
     */
    public String AggiungiAiPreferiti(Ristorante ristorante) {
        for (Ristorante r : ListaPreferiti) {
            if (r.equals(ristorante)) {
                return "Già presente nella lista dei preferiti";
            }
        }
        ListaPreferiti.add(ristorante);
        return "Ristorante aggiunto alla lista dei preferiti";
    }

    /**
     * Rimuove un ristorante dalla lista dei preferiti.
     * 
     * @param ristorante Ristorante da rimuovere.
     */
    public void RimuoviPreferiti(Ristorante ristorante) {
        ListaPreferiti.removeIf(r -> r.equals(ristorante));
    }

    /**
     * Restituisce una stringa formattata con l'elenco dei ristoranti preferiti.
     * 
     * @return Lista dei preferiti in formato testuale.
     */
    public String VisualizzaPreferiti() {
        StringBuilder ris = new StringBuilder("LISTA DEI PREFERITI:\n");
        for (Ristorante r : ListaPreferiti) {
            ris.append(r.getNome()).append("\n");
        }
        return ris.toString();
    }

    /**
     * Crea una nuova recensione e la aggiunge alla lista dell'utente e del ristorante.
     * 
     * @param voto Voto assegnato.
     * @param commento Testo del commento.
     * @param ristorante Ristorante a cui si riferisce la recensione.
     * @return Oggetto Recensione creato.
     */
    public Recensione AggiungiRecensione(int voto, String commento, Ristorante ristorante) {
        Recensione rec = new Recensione(voto, commento, getUsername(), ristorante.getNome());
        ristorante.AggiungiRecensione(rec);
        ListaRecensioniUtente.add(rec);
        return rec;
    }

    /** Stampa a schermo tutte le recensioni scritte dall'utente. */
    public void VisualizzaRecensioni() {
        System.out.println("La lista delle tue recensioni:");
        for (int i = 0; i < ListaRecensioniUtente.size(); i++) {
            System.out.println(i + "- " + ListaRecensioniUtente.get(i));
        }
    }

    /**
     * Rimuove una recensione in base all'indice, sia dalla lista del ristorante sia da quella dell'utente.
     * 
     * @param index Indice della recensione.
     * @param ristorante Ristorante a cui appartiene la recensione.
     */
    public void RemoveRecensione(int index, Ristorante ristorante) {
        ristorante.RimuoviRecensione(getUsername(), ListaRecensioniUtente.get(index).getCommento());
        ListaRecensioniUtente.remove(index);
    }

    /**
     * Modifica una recensione esistente, aggiornando sia la lista utente che quella del ristorante.
     * 
     * @param index Indice della recensione da modificare.
     * @param newCommento Nuovo commento da inserire.
     * @param newVoto Nuovo voto da assegnare.
     * @param ristorante Ristorante associato alla recensione.
     */
    public void ModificaRecensione(int index, String newCommento, int newVoto, Ristorante ristorante) {
        ristorante.RimuoviRecensione(this.getUsername(), ListaRecensioniUtente.get(index).getCommento());
        RemoveRecensione(index, ristorante);
        ListaRecensioniUtente.add(AggiungiRecensione(newVoto, newCommento, ristorante));
    }
}
