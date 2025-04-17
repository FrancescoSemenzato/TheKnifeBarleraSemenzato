package src;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestoreRistoranti {
    private ArrayList<Ristorante> listaRistoranti;
    private String FilePath="FilesCSV/ListaRistoranti.csv";

    public GestoreRistoranti() {
        this.listaRistoranti = leggiDaFile(FilePath);
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
                    String nazione = campi[3];
                    String prezzo = campi[4];
                    int fasciaDiPrezzo = Integer.parseInt(campi[5]);
                    String tipoDiCucina = campi[6];
                    float latitudine = Float.parseFloat(campi[7].replace(".", "").replace(",", "."));
                    float longitudine = Float.parseFloat(campi[8].replace(".", "").replace(",", "."));
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

    public ArrayList<Ristorante> getLista() {
        return listaRistoranti;
    }

    public ArrayList<Ristorante> filtraPerCitta(String citta) {
        ArrayList<Ristorante> filtrati = new ArrayList<>();
        for (Ristorante r : listaRistoranti) {
            if (r.getCitta().equalsIgnoreCase(citta)) {
                filtrati.add(r);
            }
        }
        return filtrati;
    }
}