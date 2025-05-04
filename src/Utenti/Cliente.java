package src.Utenti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.Recensione;
import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;

public class Cliente extends Utente{
    public static final String FilePathRecensioni="FilesCSV/ListaRecensioni.csv";
    public static final String FilePathUtenti="FilesCSV/ListaUtenti.csv";
    private ArrayList <Recensione> ListaRecensioniUtente;
    private ArrayList <Ristorante> ListaPreferiti;
    
    public Cliente(){}

    public Cliente(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno, boolean nuovo){
        super(Nome, Cognome, Username, Password, Domicilio, "Cliente", Giorno, Mese, Anno, nuovo);

        ListaRecensioniUtente = new ArrayList<Recensione>();
        ListaPreferiti = new ArrayList<Ristorante>();
    }

    public Cliente(String Nome, String Cognome, String Username, String Password, String Domicilio, String Data, boolean nuovo){
        super(Nome, Cognome, Username, Password, Domicilio, "Cliente", Data, nuovo);

        ListaRecensioniUtente = new ArrayList<Recensione>();
        CaricaListaRecensione(ListaRecensioniUtente);
        ListaPreferiti = new ArrayList<Ristorante>();
    }

    public ArrayList<Recensione> getListaRecensioni(){ return ListaRecensioniUtente; }
    public ArrayList<Ristorante> getPreferiti(){ return ListaPreferiti; }
    public String getPreferitiString() {
        if (ListaPreferiti.isEmpty()) {
            return "//";
        }
        StringBuilder preferiti = new StringBuilder();
        for (Ristorante r : ListaPreferiti) {
            if (r != null) {
                preferiti.append(r.getNome()).append("_");
            }
        }
        if (preferiti.length() > 0 && preferiti.charAt(preferiti.length() - 1) == '_') {
            preferiti.deleteCharAt(preferiti.length() - 1);
        }
        return preferiti.toString();
    }

    public void CaricaListaRecensione(ArrayList<Recensione> rec){
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathRecensioni))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi.length >= 5) {
                    if (campi[4].equals(getUsername()) && campi[3].equals("")) {
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

    /*
     * Da usare solo quando il cliente farà l'accesso
     */
    public void CaricaListaPreferiti(String username, GestoreRistoranti gest) {
        String line;
        ListaPreferiti.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathUtenti))) {
            br.readLine();  // Salta l'header
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
                                if (r != null)
                                    ListaPreferiti.add(r);
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

    public String AggiungiAiPreferiti(Ristorante ristorante){
        boolean trovato = false;
        for(Ristorante r : ListaPreferiti)
            if(r.getNome().equals(ristorante.getNome())){
                trovato = true;
                break;
            }
        return (trovato) ? "Già presente nella lista dei preferiti" : "Aggiunto alla lista dei preferiti";
    }

    public String VisualizzaPreferiti(){
        String ris="LISTA DEI PREFERITI:\n";
        for(Ristorante r : ListaPreferiti)
            ris += r.getNome() + "\n";
        return ris;
    }

    public Recensione AggiungiRecensione(int voto, String commento, Ristorante ristorante){
        Recensione rec = new Recensione(voto, commento, getUsername(), ristorante.getNome());
        ristorante.AggiungiRecensione(rec);
        ListaRecensioniUtente.add(rec);
        return rec;
    }

    public void VisualizzaRecensioni(){
        System.out.println("La lista delle tue recensioni:");
        for(int i=0; i<ListaRecensioniUtente.size(); i++)
            System.out.println(i + "- " + ListaRecensioniUtente.get(i));
    }

    public void RemoveRecensione(int index, Ristorante ristorante){
        ristorante.RimuoviRecensione(getUsername(), ListaRecensioniUtente.get(index).getCommento());
        ListaRecensioniUtente.remove(index);
    }

    public void ModificaRecensione(int index, String newCommento, int newVoto, Ristorante ristorante){
        ristorante.ModificaRecensione(getUsername(), newCommento, newVoto);
        ListaRecensioniUtente.get(index).setCommento(newCommento);
        ListaRecensioniUtente.get(index).setVoto(newVoto);
    }
}