package src.Ristoranti;

import java.util.ArrayList;

import src.Recensione;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ristorante {
    private String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Stelle;
    private int FasciaDiPrezzo;
    private float Latitudine, Longitudine;
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

        ListaRecensioni = new ArrayList<Recensione>();
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

    public String visualizzaRistorante() {
        return "Ristorante:" + Nome + ", " + Indirizzo + "\n" +
                "Tipo di cucina: " + TipoDiCucina + "\n" +
                "Servizi: " + Servizi + "\n" +
                "SitoWeb: " + URLWeb + "\n" +
                "Prezzo: " + Prezzo + "\n" +
                "delivery = " + (Delivery ? "Ha il servizio di Delivery" : "Non ha il servizio di delivery" )+ '\n' +
                "prenotazioneOnline = " + (PrenotazioneOnline ? "E' possibile prenotare online" : "Non è possibile prenotare online" )+ '\n';
    }

    public void getRecensioni(){
        for(Recensione r : ListaRecensioni)
            r.toString();
    }

    public void AggiungiRecensione(Recensione recensione){
        ListaRecensioni.add(recensione);
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
