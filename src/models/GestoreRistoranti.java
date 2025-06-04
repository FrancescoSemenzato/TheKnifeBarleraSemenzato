package src.models;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * Descrizione: GestoreRistoranti è una classe che gestisce una lista di ristoranti, 
 * permettendo operazioni come caricamento da file, salvataggio su file, 
 * filtri su vari criteri (nome, città, tipo di cucina, fascia di prezzo, servizi) 
 * e ricerche basate sulla posizione geografica.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
public class GestoreRistoranti {
    private ArrayList<Ristorante> listaRistoranti;
    private String FilePath="data/ListaRistoranti.csv";
    private String filePathRecensioni="data/ListaRecensioni.csv";

    /**
     * Costruttore della classe GestoreRistoranti.
     * Inizializza la lista dei ristoranti leggendo da file e carica le recensioni associate.
     */
    public GestoreRistoranti() {
        this.listaRistoranti = leggiDaFile(FilePath);
        for (Ristorante r : listaRistoranti) {
            r.caricaRecensioni(filePathRecensioni);
        }
    }

    /**
     * Restituisce un ristorante dato il suo nome.
     * @param nome Il nome del ristorante da cercare.
     * @return Il ristorante con il nome specificato, o null se non trovato.
     */
    public Ristorante getRistorante(String nome) {
        for (Ristorante r : listaRistoranti) {
            if (r.getNome().trim().equals(nome.trim())) {
                return r;
            }
        }
        return null;
    }

    /**
     * Legge le informazioni dei ristoranti da un file CSV e le aggiunge alla lista.
     * @param FilePath Percorso del file
     * @return Lista dei ristoranti caricate
     */
    private ArrayList<Ristorante> leggiDaFile(String FilePath) {
        ArrayList<Ristorante> lista = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi.length == 14) {
                    String nome = campi[0];
                    String indirizzo = campi[1];
                    String citta = campi[2];
                    String nazione = campi[3].equals("") ? campi[2] : campi[3];
                    String prezzo = campi[4];
                    int fasciaDiPrezzo = Integer.parseInt(campi[5]);
                    String tipoDiCucina = campi[6];
                    Double latitudine = Double.parseDouble(campi[7].replace(",", "."));
                    Double longitudine = Double.parseDouble(campi[8].replace(",", "."));
                    boolean prenotazioneOnline = campi[9].equalsIgnoreCase("SI");
                    boolean delivery = campi[10].equalsIgnoreCase("SI");
                    String urlWeb = campi[11];
                    String stelle = campi[12];
                    String servizi = campi[13];
                    
                    Ristorante r = new Ristorante(nome, nazione, citta, indirizzo, tipoDiCucina, servizi, urlWeb, prezzo, latitudine, longitudine, fasciaDiPrezzo, stelle, delivery, prenotazioneOnline);
                    lista.add(r);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Scrive la lista dei ristoranti e le loro recensioni su file.
     */
    public void scriviSuFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath));
             BufferedWriter ba = new BufferedWriter(new FileWriter(filePathRecensioni))) {
    
            // intestazione ristoranti
            bw.write("Nome;Indirizzo;Citta;Nazione;Prezzo;FasciaDiPrezzo;TipoDiCucina;Latitudine;Longitudine;PrenotazioneOnline;Delivery;UrlWeb;Stelle;Servizi");
            bw.newLine();
    
            // intestazione recensioni
            ba.write("NomeRistorante;Voto;Recensione;RispostaAllaRecensione;Username");
            ba.newLine();
    
            for (Ristorante r : listaRistoranti) {
                ArrayList<Recensione> recensioni = r.getRecensioni();
                if (recensioni != null && !recensioni.isEmpty()) {
                    for (Recensione rec : recensioni) {
                        String lineaRec = String.join(";",
                            rec.getNomeRistorante(),
                            String.valueOf(rec.getVoto()),
                            rec.getCommento(),
                            rec.getRisposta(),
                            rec.getUsername()
                        );
                        ba.write(lineaRec);
                        ba.newLine();
                    }
                }
    
                // scrittura del ristorante
                String linea = String.join(";",
                    r.getNome(),
                    r.getIndirizzo(),
                    r.getCitta(),
                    r.getNazione(),
                    r.getPrezzo(),
                    String.valueOf(r.getFasciaDiPrezzo()),
                    r.getTipoDiCucina(),
                    r.getLatitudine().toString(),
                    r.getLongitudine().toString(),
                    r.getPrenotazioneOnline() ? "SI" : "NO",
                    r.getDelivery() ? "SI" : "NO",
                    r.getURLWeb(),
                    r.getStelle(),
                    r.getServizi()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }

    /**
     * Restituisce la lista completa dei ristoranti.
     * @return Lista di ristoranti.
     */
    public ArrayList<Ristorante> getListaRistoranti() {
        return listaRistoranti;
    }

    /**
     * Aggiunge un ristorante alla lista.
     * @param r Il ristorante da aggiungere.
     */
    public void AggiungiRistorante(Ristorante r) {
        this.listaRistoranti.add(r);
    }

    /**
     * Rimuove un ristorante dalla lista.
     * @param r Il ristorante da rimuovere.
     */
    public void RimuoviRistorante(Ristorante r) {
        this.listaRistoranti.remove(r);
    }

    /**
     * Filtra la lista dei ristoranti in base al nome, cercando corrispondenze parziali o iniziali.
     * @param nome Il nome o parte del nome da cercare.
     * @return Lista di ristoranti che corrispondono al nome cercato.
     */
    public ArrayList<Ristorante> filtraPerNomeRistorante(String nome) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        if (nome == null || nome.trim().length() < 2) return filtrati;
    
        //Prova prima a cercare il nome che inizia con lo stesso nome
        String nomeLower = nome.toLowerCase().trim();
        for (Ristorante r : listaRistoranti) {
            String nomeRistorante = r.getNome().toLowerCase();
            if (nomeRistorante.startsWith(nomeLower)) {
                filtrati.add(r);
            }
        }

        //Se non ci sono risultati, cerca anche in condizioni parziali
        if (filtrati.isEmpty()) {
            for (Ristorante r : listaRistoranti) {
                if (r.getNome().toLowerCase().contains(nomeLower)) {
                    filtrati.add(r);
                }
            }
        }
    
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti in base alla città, anche con condizioni parziali e normalizzazione tramite geocoding.
     * @param citta La città da cercare.
     * @return Lista di ristoranti che si trovano nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerCitta(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        
        // Prima cerca direttamente nei dati locali
        String cittaLower = citta.toLowerCase().trim();
        for (Ristorante r : listaRistoranti) {
            if (r.getCitta().toLowerCase().contains(cittaLower)) {
                filtrati.add(r);
            }
        }
        
        try {
            JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e");
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(citta);
            request.setLimit(1);
            JOpenCageResponse response = geocoder.forward(request);
    
            if (!response.getResults().isEmpty()) {
                String cittaNormalizzata = response.getResults().get(0).getComponents().getCity();
                if (cittaNormalizzata == null) {
                    cittaNormalizzata = response.getResults().get(0).getComponents().getTown();
                }
    
                if (cittaNormalizzata != null) {
                    String finale = cittaNormalizzata.toLowerCase();
                    for (Ristorante r : listaRistoranti) {
                        if (r.getCitta().toLowerCase().contains(finale)) {
                            filtrati.add(r);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore nel geocoding della città: " + e.getMessage());
        }
        
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti in base al tipo di cucina e alla città.
     * @param tipo Il tipo di cucina da cercare.
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti che corrispondono al tipo di cucina e si trovano nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerTipoDiCucina(String tipo, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getTipoDiCucina().toLowerCase().contains(tipo.toLowerCase())) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti in base alla fascia di prezzo con un segno di confronto.
     * @param fascia La fascia di prezzo di riferimento.
     * @param segno Il segno di confronto ('>', '<', '=').
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti che soddisfano il criterio di fascia di prezzo nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerFasciaDiPrezzo(int fascia, char segno, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        switch (segno) {
            case '>': for (Ristorante r : filtraPerCitta(citta)) {
                        if (r.getFasciaDiPrezzo() > fascia) {
                            filtrati.add(r);
                        }
                    }
                break;
            case '<': for (Ristorante r : filtraPerCitta(citta)) {
                        if (r.getFasciaDiPrezzo() < fascia) {
                            filtrati.add(r);
                        }
                    }
                break;
            case '=': for (Ristorante r : filtraPerCitta(citta)) {
                        if (r.getFasciaDiPrezzo() == fascia) {
                            filtrati.add(r);
                        }
                    }
                break;
            default:
                break;
        }
        return filtrati;
    }
    /**
     * Filtra la lista dei ristoranti in base a un intervallo di fasce di prezzo.
     * @param fascia1 La fascia di prezzo minima.
     * @param fascia2 La fascia di prezzo massima.
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti che hanno una fascia di prezzo compresa tra fascia1 e fascia2 nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerFasciaDiPrezzo(int fascia1, int fascia2, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getFasciaDiPrezzo() >= fascia1 && r.getFasciaDiPrezzo() <= fascia2) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti che offrono il servizio di delivery nella città specificata.
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti con servizio delivery nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerDelivery(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getDelivery()) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti che offrono il servizio di prenotazione online nella città specificata.
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti con servizio di prenotazione online nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerPrenotazioneOnline(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getPrenotazioneOnline()) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    /**
     * Filtra la lista dei ristoranti in base alla media delle stelle, nella città specificata.
     * @param numero La media stelle minima richiesta.
     * @param citta La città in cui cercare.
     * @return Lista di ristoranti con media stelle maggiore o uguale a numero nella città specificata.
     */
    public ArrayList<Ristorante> filtraPerMediaStelle(float numero, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if(Character.isDigit(r.getStelle().charAt(0))) {
                float stelle = Float.parseFloat(String.valueOf(r.getStelle().charAt(0)));
                if (stelle >= numero) {
                    filtrati.add(r);
                }
            }
        }
        return filtrati;
    }

    /**
     * Unisce due liste di ristoranti eliminando i duplicati.
     * @param lista1 La prima lista di ristoranti.
     * @param lista2 La seconda lista di ristoranti.
     * @return Una lista unita senza duplicati.
     */
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2) {
        Set<Ristorante> set = new LinkedHashSet<>(lista1);
        set.addAll(lista2);
        return new ArrayList<>(set);
    }
    /**
     * Unisce tre liste di ristoranti eliminando i duplicati.
     * @param lista1 La prima lista di ristoranti.
     * @param lista2 La seconda lista di ristoranti.
     * @param lista3 La terza lista di ristoranti.
     * @return Una lista unita senza duplicati.
     */
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2, ArrayList<Ristorante> lista3) {
        return unisciListe(lista1, unisciListe(lista2, lista3));
    }
    /**
     * Unisce quattro liste di ristoranti eliminando i duplicati.
     * @param lista1 La prima lista di ristoranti.
     * @param lista2 La seconda lista di ristoranti.
     * @param lista3 La terza lista di ristoranti.
     * @param lista4 La quarta lista di ristoranti.
     * @return Una lista unita senza duplicati.
     */
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2, ArrayList<Ristorante> lista3,  ArrayList<Ristorante> lista4) {
        return unisciListe(unisciListe(lista1, lista2), unisciListe(lista3, lista4));
    }


    /**
     * Filtra la lista dei ristoranti che si trovano entro una certa distanza da un indirizzo utente.
     * @param indirizzoUtente L'indirizzo dell'utente da cui calcolare la distanza.
     * @param distanzaMassimaKm La distanza massima in chilometri.
     * @return Lista di ristoranti entro la distanza specificata dall'indirizzo utente.
     */
    public ArrayList<Ristorante> filtraPerVicinoA(String indirizzoUtente, double distanzaMassimaKm) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        
    
        try {
            JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e");
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(indirizzoUtente);
            request.setLimit(1);
            JOpenCageResponse response = geocoder.forward(request);
    
            if (response.getResults().isEmpty()) {
                System.err.println("[DEBUG] Nessun risultato per il geocoding");
            } else {
                JOpenCageResult result = response.getResults().get(0);
                double latUtente = result.getGeometry().getLat();
                double lonUtente = result.getGeometry().getLng();
    
                for (Ristorante r : listaRistoranti) {
                    double distanza = calcolaDistanza(latUtente, lonUtente, r.getLatitudine(), r.getLongitudine());
                    if (distanza <= distanzaMassimaKm) {
                        filtrati.add(r);
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return filtrati;
    }

    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della Terra in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distanza in km
    }

}
