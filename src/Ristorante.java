package src;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ristorante {
    private String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Stelle;
    private int FasciaDiPrezzo;
    private float Latitudine, Longitudine;
    private boolean Delivery, PrenotazioneOnline;
    
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
    
    @Override
    public String toString() {
    return "Ristorante{" +
            "nome='" + Nome + '\'' +
            ", nazione='" + Nazione + '\'' +
            ", citta='" + Citta + '\'' +
            ", indirizzo='" + Indirizzo + '\'' +
            ", tipoDiCucina='" + TipoDiCucina + '\'' +
            ", servizi='" + Servizi + '\'' +
            ", urlWeb='" + URLWeb + '\'' +
            ", latitudine=" + Latitudine +
            ", longitudine=" + Longitudine +
            ", prezzo='" + Prezzo + '\'' +
            ", fasciaDiPrezzo=" + FasciaDiPrezzo +
            ", stelle=" + Stelle +
            ", delivery=" + Delivery +
            ", prenotazioneOnline=" + PrenotazioneOnline +
            '}';
}
}
