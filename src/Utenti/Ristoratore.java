package src.Utenti;

import java.util.ArrayList;

import src.Recensione;
import src.Ristoranti.Ristorante;

public class Ristoratore extends Utente {
    private ArrayList <Ristorante> ListaRistoranti;

    public Ristoratore(String Nome, String Cognome, String Username, String Password, String Domicilio, int Giorno, int Mese, int Anno){
        super(Nome, Cognome, Username, Password, Domicilio, "Ristoratore", Giorno, Mese, Anno);

        ListaRistoranti = new ArrayList<Ristorante>();
    }

    public String getRistoranti(){
        int i=0;
        String ris = "Elenco dei ristoranti:\n";
        for(Ristorante r : ListaRistoranti)
            ris += ++i + "- "+ r.getNome() + "\n";
        
        return ris;
    }

    public Ristorante AggiungiRistorante(String Nome, String Nazione, String Citta, String Indirizzo, String TipoDiCucina, String Servizi, String URLWeb, String Prezzo, float Latitudine, float Logitudine, int FasciaDiPrezzo, String Stelle, boolean Delivery, boolean PrenotazioneOnline){
        Ristorante r = new Ristorante(Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Latitudine, Logitudine, FasciaDiPrezzo, Stelle, Delivery, PrenotazioneOnline);
        ListaRistoranti.add(r);
        return r;
    }

    public String getRecensioniRistoranti(){
        String ris = "Lista di tutte le recensioni dei tuoi ristoranti\n";
        for(Ristorante r : ListaRistoranti)
            for(int i=0; i<r.getRecensioni().size(); i++)
                ris += (i+1) + "- "+ r.getRecensione(i).toString();
        
        return ris;
    }

    public String getRecensioniRistoranteSingolo(int index){
        String ris = "Lista di tutte le recensioni del ristorante " + ListaRistoranti.get(index).getNome() + ":\n";
        
        for(int i=0; i<ListaRistoranti.get(index).getRecensioni().size(); i++){
            ris += (i+1) + "- " + ListaRistoranti.get(index).getRecensione(i).toString();
        }

        return ris;
    }

    public ArrayList<Recensione> getRecensioniRistoranteSingolo(String nomeRistorante){
        ArrayList<Recensione> listarecensioni = new ArrayList<Recensione>();
        
        for(Ristorante r : ListaRistoranti){
            if(r.getNome().toLowerCase().equals(nomeRistorante))
                for(int i=0; i<ListaRistoranti.size(); i++)
                        listarecensioni.add(r.getRecensione(i));

        }            
        return listarecensioni;
    }

    public String AggiungiRisposta(String risposta, int index, String nomeRistorante){
        ArrayList<Recensione> listaRecensioni = new ArrayList<Recensione>();
        listaRecensioni = getRecensioniRistoranteSingolo(nomeRistorante);

        listaRecensioni.get(index).setRisposta(risposta);

        return "Recensione aggiunta correttamente";
    }

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

    public String NumeroRecensioniRicevute(){
        String ris = "NUMERO RECENSUIONI DI TUTTI I TUOI RISTORANTI: \n";
        for(Ristorante r : ListaRistoranti)
            ris += r.getNome() + ": " + r.getNumeroRecensioni() + " RECENSIONI\n";
        
        return ris;
    }
}
