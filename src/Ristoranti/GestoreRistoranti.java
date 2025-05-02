package src.Ristoranti;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import src.Recensione;

public class GestoreRistoranti {
    private ArrayList<Ristorante> listaRistoranti;
    private String FilePath="FilesCSV/ListaRistoranti.csv";
    private String filePathRecensioni="FilesCSV/ListaRecensioni.csv";

    public GestoreRistoranti() {
        this.listaRistoranti = leggiDaFile(FilePath);
        for (Ristorante r : listaRistoranti) {
            r.caricaRecensioni(filePathRecensioni);
        }
    }

    public Ristorante getRistorante(String nome) {
        for (Ristorante r : listaRistoranti) {
            if (r.getNome().trim().equals(nome.trim())) {
                return r;
            }
        }
        return null;
    }

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
                    Double latitudine = Double.parseDouble(campi[8]);
                    Double longitudine = Double.parseDouble(campi[7]);
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

    public void scriviSuFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath));
             BufferedWriter ba = new BufferedWriter(new FileWriter(filePathRecensioni))) {
    
            // intestazione ristoranti
            bw.write("Nome;Indirizzo;Citta;Nazione;Prezzo;FasciaDiPrezzo;TipoDiCucina;Longitudine;Latitudine;PrenotazioneOnline;Delivery;UrlWeb;Stelle;Servizi");
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

    public ArrayList<Ristorante> getListaRistoranti() {
        return listaRistoranti;
    }

    //Aggiungi un ristorante, solo il ristoratore
    public void AggiungiRistorante(Ristorante r) {
        this.listaRistoranti.add(r);
    }

    //Rimuovi un ristorante, solo il ristoratore
    public void RimuoviRistorante(Ristorante r) {
        this.listaRistoranti.remove(r);
    }

    //Una lista perchè restituisce i ristoranti con il nome simile a quello cercato
    public ArrayList<Ristorante> filtraPerNomeRistorante(String nome) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : listaRistoranti) {
            if (r.getNome().toLowerCase().contains(nome.toLowerCase())) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    //Filtra in base alla città, filtra anche in condizioni parziali
    public ArrayList<Ristorante> filtraPerCitta(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        try {
            JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e");
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(citta);
            request.setLimit(1);
            JOpenCageResponse response = geocoder.forward(request);
    
            if (!response.getResults().isEmpty()) {
                String cittaNormalizzata = response.getResults().get(0).getComponents().getCity();
                if (cittaNormalizzata == null) {
                    cittaNormalizzata = response.getResults().get(0).getComponents().getTown(); // fallback
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

    //Filtra in base al tipo di cucina, controlla se la stringa tipo cercata è presente nel tipo di cucina del ristorante, ignorando maiuscole/minuscole
    public ArrayList<Ristorante> filtraPerTipoDiCucina(String tipo, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getTipoDiCucina().toLowerCase().contains(tipo.toLowerCase())) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    //Filtra in base prezzo maggiore, minore o uguale
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
    //Filtra in base alla fascia di prezzo, prima piccolo poi grande
    public ArrayList<Ristorante> filtraPerFasciaDiPrezzo(int fascia1, int fascia2, String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getFasciaDiPrezzo() >= fascia1 && r.getFasciaDiPrezzo() <= fascia2) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    //Filtra in base al delivery
    public ArrayList<Ristorante> filtraPerDelivery(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getDelivery()) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    //Filtra in base al servizio di prenotazioneOnline
    public ArrayList<Ristorante> filtraPerPrenotazioneOnline(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : filtraPerCitta(citta)) {
            if (r.getPrenotazioneOnline()) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }

    //Filtra in base alla media delle stelle
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

    //Per quando hai due criteri di ricerche
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2) {
        ArrayList<Ristorante> unita = new ArrayList<>(lista1);
        for (Ristorante r : lista2) {
            if (!unita.contains(r)) {
                unita.add(r);
            }
        }
        return unita;
    }
    //Per quando hai tre criteri di ricerca
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2, ArrayList<Ristorante> lista3) {
        return unisciListe(lista1, unisciListe(lista2, lista3));
    }
    //Per quando hai quattro criteri di ricerca
    public ArrayList<Ristorante> unisciListe(ArrayList<Ristorante> lista1, ArrayList<Ristorante> lista2, ArrayList<Ristorante> lista3,  ArrayList<Ristorante> lista4) {
        return unisciListe(unisciListe(lista1, lista2), unisciListe(lista3, lista4));
    }


    public ArrayList<Ristorante> filtraPerVicinoA(String indirizzoUtente, double distanzaMassimaKm) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        
        try {
            
            JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e");
            JOpenCageForwardRequest request = new JOpenCageForwardRequest(indirizzoUtente);
            request.setLimit(1);
            JOpenCageResponse response = geocoder.forward(request);
    
            if (!response.getResults().isEmpty()) {
                JOpenCageResult result = response.getResults().get(0);
                double latUtente = result.getGeometry().getLat();
                double lonUtente = result.getGeometry().getLng();
                
    
                for (Ristorante r : listaRistoranti) {
                    
                    double distanza = calcolaDistanza(latUtente, lonUtente, 
                                                  r.getLatitudine(), r.getLongitudine());
                    
                    
                    if (distanza <= distanzaMassimaKm) {
                        filtrati.add(r);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
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

    