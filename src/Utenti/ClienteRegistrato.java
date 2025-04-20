package src.Utenti;

import java.util.ArrayList;

import src.Recensione;
import src.Ristoranti.Ristorante;

public class ClienteRegistrato extends Utente{
    private ArrayList <Recensione> ListaRecensioniUtente;
    private ArrayList <Ristorante> ListaPreferiti;
    
    public ClienteRegistrato(){}

    public ClienteRegistrato(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno){
        super(Nome, Cognome, Username, Password, Domicilio, "Cliente Registrato", Giorno, Mese, Anno);

        ListaRecensioniUtente = new ArrayList<Recensione>();
        ListaPreferiti = new ArrayList<Ristorante>();
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

    public void getRecensioni(){
        System.out.println("La lista delle tue recensioni:");
        for(int i=0; i<ListaRecensioniUtente.size(); i++)
            System.out.println(i + "- " + ListaRecensioniUtente.get(i));
    }

    public void RemoveRecensione(int index){
        ListaRecensioniUtente.remove(index);
    }

    public void ModificaRecensione(int index, String newCommento, int newVoto){
        ListaRecensioniUtente.get(index).setCommento(newCommento);
        ListaRecensioniUtente.get(index).setVoto(newVoto);
    }
}