package src.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Nome: Francesco
 * Cognome: Semenzato
 * Matricola: 760120
 * Sede: Varese
 *
 * Nome: Marco
 * Cognome: Barlera
 * Matricola: 760000
 * Sede: Varese
 * 
 * Descrizione: Rappresenta un ristorante con tutte le sue informazioni e metodi
 * per gestire recensioni, visualizzazione e confronto tra ristoranti.
 * 
 * @author Semenzato Francesco
 * @author Barlera Marco
 */
public class Ristorante {
    private String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Stelle;
    private int FasciaDiPrezzo, ContaRecensioni = 0;
    private Double Latitudine, Longitudine, MediaStelle;
    private boolean Delivery, PrenotazioneOnline;
    private ArrayList<Recensione> ListaRecensioni;
    
    /**
     * Costruttore vuoto per creare un ristorante senza inizializzare gli attributi.
     */
    public Ristorante(){}
    
    /**
     * Costruttore con parametri per inizializzare tutti gli attributi del ristorante.
     *
     * @param Nome Nome del ristorante
     * @param Nazione Nazione in cui si trova
     * @param Citta Città
     * @param Indirizzo Indirizzo completo
     * @param TipoDiCucina Tipologia di cucina offerta
     * @param Servizi Servizi offerti
     * @param URLWeb Sito web
     * @param Prezzo Indicazione dei prezzi
     * @param Latitudine Coordinata latitudine
     * @param Logitudine Coordinata longitudine
     * @param FasciaDiPrezzo Fascia di prezzo (intero)
     * @param Stelle Valutazione in stelle
     * @param Delivery True se offre servizio delivery
     * @param PrenotazioneOnline True se accetta prenotazioni online
     */
    public Ristorante(String Nome, String Nazione, String Citta, String Indirizzo, String TipoDiCucina, String Servizi, String URLWeb, String Prezzo, Double Latitudine, Double Logitudine, int FasciaDiPrezzo, String Stelle, boolean Delivery, boolean PrenotazioneOnline){
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
        this.ListaRecensioni = new ArrayList<>();
        this.MediaStelle = MediaStelle();
    }

    /**
     * Restituisce il nome del ristorante.
     * @return Nome del ristorante
     */
    public String getNome() { return Nome; }
    /**
     * Restituisce la nazione in cui si trova il ristorante.
     * @return Nazione
     */
    public String getNazione() { return Nazione; }
    /**
     * Restituisce la città in cui si trova il ristorante.
     * @return Città
     */
    public String getCitta() { return Citta; }
    /**
     * Restituisce l'indirizzo del ristorante.
     * @return Indirizzo
     */
    public String getIndirizzo() { return Indirizzo; }
    /**
     * Restituisce la tipologia di cucina offerta.
     * @return Tipologia di cucina
     */
    public String getTipoDiCucina() { return TipoDiCucina; }
    /**
     * Restituisce i servizi offerti.
     * @return Servizi
     */
    public String getServizi() { return Servizi; }
    /**
     * Restituisce l'indirizzo web.
     * @return URLWeb
     */
    public String getURLWeb() { return URLWeb; }
    /**
     * Restituisce la coordinata latitudine.
     * @return Latitudine
     */
    public Double getLatitudine() { return Latitudine; }
    /**
     * Restituisce la coordinata longitudine.
     * @return Longitudine
     */
    public Double getLongitudine() { return Longitudine; }
    /**
     * Restituisce la fascia di prezzo.
     * @return Fascia di prezzo
     */
    public int getFasciaDiPrezzo() { return FasciaDiPrezzo; }
    /**
     * Restituisce la valutazione in stelle.
     * @return Stelle
     */
    public String getStelle() { return Stelle; }
    /**
     * Restituisce true se offre il servizio di delivery.
     * @return True se offre il servizio di delivery
     */
    public boolean getDelivery() { return Delivery; }
    /**
     * Restituisce true se accetta prenotazioni online.
     * @return True se accetta prenotazioni online
     */
    public boolean getPrenotazioneOnline() { return PrenotazioneOnline; }
    /**
     * Restituisce la stringa con l'indicazione dei prezzi.
     * @return Prezzo
     */
    public String getPrezzo() { return Prezzo; }
    /**
     * Restituisce la lista di recensioni.
     * @return Lista di recensioni
     */
    public ArrayList<Recensione> getRecensioni(){ return ListaRecensioni;}
    /**
     * Restituisce la recensione all'indice specificato.
     * @param index Indice della recensione
     * @return Recensione
     */
    public Recensione getRecensione(int index){return ListaRecensioni.get(index);}
    /**
     * Restituisce la media delle stelle.
     * @return Media delle stelle
     */
    public Double getMediaStelle(){return MediaStelle;}
    /**
     * Restituisce il numero di recensioni.
     * @return Numero di recensioni
     */
    public int getNumeroRecensioni(){return ContaRecensioni;}

    /**
     * Imposta il nome del ristorante.
     * @param Nome Nome del ristorante
     */
    public void setNome(String Nome) { this.Nome = Nome; }
    /**
     * Imposta la nazione in cui si trova il ristorante.
     * @param Nazione Nazione
     */
    public void setNazione(String Nazione) { this.Nazione = Nazione; }
    /**
     * Imposta la città in cui si trova il ristorante.
     * @param Citta Città
     */
    public void setCitta(String Citta) { this.Citta = Citta; }
    /**
     * Imposta l'indirizzo del ristorante.
     * @param Indirizzo Indirizzo
     */
    public void setIndirizzo(String Indirizzo) { this.Indirizzo = Indirizzo; }
    /**
     * Imposta la tipologia di cucina offerta.
     * @param TipoDiCucina Tipologia di cucina
     */
    public void setTipoDiCucina(String TipoDiCucina) { this.TipoDiCucina = TipoDiCucina; }
    /**
     * Imposta i servizi offerti.
     * @param Servizi Servizi
     */
    public void setServizi(String Servizi) { this.Servizi = Servizi; }
    /**
     * Imposta l'indirizzo web.
     * @param URLWeb URLWeb
     */
    public void setURLWeb(String URLWeb) { this.URLWeb = URLWeb; }
    /**
     * Imposta la coordinata latitudine.
     * @param Latitudine Latitudine
     */
    public void setLatitudine(Double Latitudine) { this.Latitudine = Latitudine; }
    /**
     * Imposta la coordinata longitudine.
     * @param Longitudine Longitudine
     */
    public void setLongitudine(Double Longitudine) { this.Longitudine = Longitudine; }
    /**
     * Imposta la fascia di prezzo.
     * @param FasciaDiPrezzo Fascia di prezzo
     */
    public void setFasciaDiPrezzo(int FasciaDiPrezzo) { this.FasciaDiPrezzo = FasciaDiPrezzo; }
    /**
     * Imposta la valutazione in stelle.
     * @param Stelle Stelle
     */
    public void setStelle(String Stelle) { this.Stelle = Stelle; }
    /**
     * Imposta se offre il servizio di delivery.
     * @param Delivery True se offre il servizio di delivery
     */
    public void setDelivery(boolean Delivery) { this.Delivery = Delivery; }
    /**
     * Imposta se accetta prenotazioni online.
     * @param PrenotazioneOnline True se accetta prenotazioni online
     */
    public void setPrenotazioneOnline(boolean PrenotazioneOnline) { this.PrenotazioneOnline = PrenotazioneOnline; }
    /**
     * Imposta la stringa con l'indicazione dei prezzi.
     * @param Prezzo Prezzo
     */
    public void setPrezzo(String Prezzo) { this.Prezzo = Prezzo; }

    /**
     * Carica le recensioni da file e aggiorna la media delle stelle.
     *
     * @param Path Percorso del file contenente le recensioni
     */
    public void caricaRecensioni(String Path) {
        this.ListaRecensioni = leggiDaFile(Path);
        this.MediaStelle = MediaStelle();
    }

    /**
     * Restituisce una stringa formattata con i dettagli del ristorante.
     *
     * @return Dettagli formattati del ristorante
     */
    public String visualizzaRistorante() {
        return "Ristorante: " + Nome + ", " + Indirizzo + "\n" +
                "Tipo di cucina: " + TipoDiCucina + "\n" +
                "Servizi: " + Servizi + "\n" +
                "SitoWeb: " + (URLWeb.equals("") ? "Nessun sito web" : URLWeb) + "\n" +
                "Prezzo: " + Prezzo + "\n" +
                "Stelle: " + (MediaStelle > 0 ? MediaStelle : "Nessuna recensione" ) + "\n" +
                "Delivery = " + (Delivery ? "Ha il servizio di Delivery" : "Non ha il servizio di delivery" )+ '\n' +
                "PrenotazioneOnline = " + (PrenotazioneOnline ? "E' possibile prenotare online" : "Non è possibile prenotare online" )+ '\n';
    }

    /**
     * Restituisce una stringa contenente tutte le recensioni del ristorante.
     *
     * @return Tutte le recensioni in formato stringa
     */
    public String getRecensioniString(){
        String s = "";
        for(Recensione r : ListaRecensioni)
            s += r.visualizzaRecensione() + "\n";
        return s;
    }

    /**
     * Rimuove una recensione in base all'username e commento corrispondente.
     *
     * @param username Username dell'autore
     * @param commento Testo della recensione
     */
    public void RimuoviRecensione(String username, String commento){
        for(int i = ListaRecensioni.size() - 1; i >= 0; i--) {
            Recensione r = ListaRecensioni.get(i);
            if(r.getUsername().equals(username) && r.getCommento().equals(commento)) {
                ListaRecensioni.remove(i);
                ContaRecensioni--;
            }
        }
    }

    /**
     * Modifica una recensione esistente con lo stesso username, commento e voto.
     *
     * @param username Username dell'autore
     * @param commento Nuovo commento
     * @param voto Nuovo voto
     */
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

    /**
     * Aggiunge una recensione alla lista del ristorante.
     *
     * @param recensione Recensione da aggiungere
     */
    public void AggiungiRecensione(Recensione recensione){
        ListaRecensioni.add(recensione);
        ContaRecensioni++;
    }

    /**
     * Calcola la media dei voti delle recensioni.
     *
     * @return Media dei voti oppure NaN se la lista è vuota
     */
    public Double MediaStelle(){
        Double somma = 0.0;
        for(Recensione r : ListaRecensioni)
            somma += r.getVoto();
        return somma / ListaRecensioni.size();
    }

    /**
     * Stampa su console tutte le recensioni del ristorante.
     */
    public void VisualizzaRecensioni(){
        System.out.println("La lista delle recensioni del ristorante " + this.Nome + ":\n");
        for(Recensione rec: ListaRecensioni)
            System.out.println(rec.visualizzaRecensione());
    }

    /**
     * Legge le recensioni da un file CSV e le aggiunge alla lista.
     *
     * @param FilePath Percorso del file
     * @return Lista delle recensioni caricate
     */
    private ArrayList<Recensione> leggiDaFile(String FilePath) {
        ArrayList<Recensione> lista = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi.length == 5 && campi[0].equals(this.Nome)) {
                    ContaRecensioni++;
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

    /**
     * Restituisce una rappresentazione testuale dettagliata del ristorante.
     *
     * @return Stringa con tutti gli attributi principali
     */
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

    /**
     * Confronta due ristoranti in base a tutti gli attributi.
     *
     * @param obj Oggetto da confrontare
     * @return true se uguali, false altrimenti
     */
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
                Double.compare(Latitudine, that.Latitudine) == 0 &&
                Double.compare(Longitudine, that.Longitudine) == 0 &&
                Delivery == that.Delivery &&
                PrenotazioneOnline == that.PrenotazioneOnline;
    }

}
