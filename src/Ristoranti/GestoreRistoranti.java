package src.Ristoranti;

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

    public Ristorante getRistorante(String nome) {
        for (Ristorante r : listaRistoranti) {
            if (r.getNome().equals(nome)) {
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
        for (Ristorante r : listaRistoranti) {
            if (r.getCitta().toLowerCase().contains(citta.toLowerCase())) {
                filtrati.add(r);
            }
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
}