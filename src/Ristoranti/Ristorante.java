package src.Ristoranti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import src.Recensione;

public class Ristorante {
    private String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Stelle;
    private int FasciaDiPrezzo;
    private float Latitudine, Longitudine, MediaStelle;
    private boolean Delivery, PrenotazioneOnline;
    private ArrayList<Recensione> ListaRecensioni;
    
    public Ristorante(String Nome, String Nazione, String Citta, String Indirizzo, String TipoDiCucina, String Servizi, String URLWeb, String Prezzo, float Latitudine, float Logitudine, int FasciaDiPrezzo, String Stelle, boolean Delivery, boolean PrenotazioneOnline){
        this.Nome = Nome;
        this.Nazione = Nazione;
        this.Citta = Citta;
        this.Indirizzo = Indirizzo;
        this.TipoDiCucina = TipoDiCucina;
        this.Servizi = Servizi;
        this.URLWeb = URLWeb;
        this.Prezzo = Prezzo;
        this.Latitudine = Latitudine;
        this.Longitudine = Logitudine;
        this.FasciaDiPrezzo = FasciaDiPrezzo;
        this.Stelle = Stelle;
        this.Delivery = Delivery;
        this.PrenotazioneOnline = PrenotazioneOnline;
        this.ListaRecensioni = leggiDaFile("FilesCSV/ListaRecensioni");
        this.MediaStelle = MediaStelle();
    }

    public String getNome() { return Nome; }
    public String getNazione() { return Nazione; }
    public String getCitta() { return Citta; }
    public String getIndirizzo() { return Indirizzo; }
    public String getTipoDiCucina() { return TipoDiCucina; }
    public String getServizi() { return Servizi; }
    public String getURLWeb() { return URLWeb; }
    public float getLatitudine() { return Latitudine; }
    public float getLongitudine() { return Longitudine; }
    public int getFasciaDiPrezzo() { return FasciaDiPrezzo; }
    public String getStelle() { return Stelle; }
    public boolean getDelivery() { return Delivery; }
    public boolean getPrenotazioneOnline() { return PrenotazioneOnline; }
    public String getPrezzo() { return Prezzo; }
    public ArrayList<Recensione> getRecensioni(){ return ListaRecensioni;}

    public String visualizzaRistorante() {
        return "Ristorante:" + Nome + ", " + Indirizzo + "\n" +
                "Tipo di cucina: " + TipoDiCucina + "\n" +
                "Servizi: " + Servizi + "\n" +
                "SitoWeb: " + URLWeb + "\n" +
                "Prezzo: " + Prezzo + "\n" +
                "Stelle: " + (MediaStelle > 0 ? MediaStelle : "Nessuna recensione" ) + "\n" +
                "Delivery = " + (Delivery ? "Ha il servizio di Delivery" : "Non ha il servizio di delivery" )+ '\n' +
                "PrenotazioneOnline = " + (PrenotazioneOnline ? "E' possibile prenotare online" : "Non è possibile prenotare online" )+ '\n';
    }

    public String visualizzaRecensioni(){
        String s = "";
        for(Recensione r : ListaRecensioni)
            s += r.visualizzaRecensione() + "\n";
        return s;
    }

    public void RimuoviRecensione(String username, String commento){
        for(Recensione r : ListaRecensioni)
            if(r.getUsername().equals(username) && r.getCommento().equals(commento))
                ListaRecensioni.remove(r);
    }

    public void ModificaRecensione(String username, String commento, int voto){
        int i=0;
        for(Recensione r : ListaRecensioni){
            if(r.getUsername().equals(username) && r.getCommento().equals(commento) && r.getVoto() == voto){
                ListaRecensioni.get(i).setCommento(commento);
                ListaRecensioni.get(i).setVoto(voto);
            }
            i++;
        }

    }

    public void AggiungiRecensione(Recensione recensione){
        ListaRecensioni.add(recensione);
    }

    public float MediaStelle(){
        float somma = 0;
        for(Recensione r : ListaRecensioni)
            somma += r.getVoto();
        return somma / ListaRecensioni.size();
    }

    private ArrayList<Recensione> leggiDaFile(String FilePath) {
        ArrayList<Recensione> lista = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi.length == 5 && campi[0].equals(this.Nome)) {
                    String nome = campi[0];
                    int voto = Integer.parseInt(campi[1]);
                    String recensione = campi[2];
                    String risposta = campi[3];
                    String username = campi[4];
                    Recensione r;
                    if(risposta.equals(""))
                        r = new Recensione(voto, recensione, username, nome);
                    else
                        r = new Recensione(voto, recensione, username, nome, risposta);
                    lista.add(r);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
        return lista;
    }


    @Override
    public String toString() {
    return "Ristorante {\n" +
            "nome = " + Nome + '\n' +
            "nazione = " + Nazione + '\n' +
            "citta = " + Citta + '\n' +
            "indirizzo = " + Indirizzo + '\n' +
            "tipoDiCucina = " + TipoDiCucina + '\n' +
            "servizi = " + Servizi + '\n' +
            "urlWeb = " + URLWeb + '\n' +
            "latitudine = " + Latitudine + '\n' +
            "longitudine = " + Longitudine + '\n' +
            "prezzo = " + Prezzo + '\n' +
            "fasciaDiPrezzo = " + FasciaDiPrezzo + '\n' +
            "stelle = " + Stelle + '\n' +
            "delivery = " + (Delivery ? "SI" : "NO" )+ '\n' +
            "prenotazioneOnline = " + (PrenotazioneOnline ? "SI" : "NO" )+ '\n' +
            "\n}\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Ristorante that = (Ristorante) obj;

        return Nome.equals(that.Nome) &&
                Nazione.equals(that.Nazione) &&
                Citta.equals(that.Citta) &&
                Indirizzo.equals(that.Indirizzo) &&
                TipoDiCucina.equals(that.TipoDiCucina) &&
                Servizi.equals(that.Servizi) &&
                URLWeb.equals(that.URLWeb) &&
                Prezzo.equals(that.Prezzo) &&
                Stelle.equals(that.Stelle) &&
                FasciaDiPrezzo == that.FasciaDiPrezzo &&
                Float.compare(Latitudine, that.Latitudine) == 0 &&
                Float.compare(Longitudine, that.Longitudine) == 0 &&
                Delivery == that.Delivery &&
                PrenotazioneOnline == that.PrenotazioneOnline;
    }

}
