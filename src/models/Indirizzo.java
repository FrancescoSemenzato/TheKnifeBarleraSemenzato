package src.models;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResult;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
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
 * Descrizione: Classe di utilità per la gestione degli indirizzi utilizzando il servizio JOpenCage.
 * Permette di selezionare un indirizzo corretto a partire da un input dell'utente.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
public class Indirizzo {
    private static final String API_KEY = "650d3794aa3a411d9184bd19486bdb3e"; 
    public static final Scanner in = new Scanner(System.in);
    
    /**
     * Tenta di trovare e far selezionare all'utente un indirizzo corretto e preciso a partire da una stringa di input.
     * Usa il servizio JOpenCage per ottenere possibili corrispondenze e filtra i risultati poco significativi o duplicati.
     *
     * @param indirizzoInput l'indirizzo inserito dall'utente
     * @return l'indirizzo formattato scelto tra le opzioni proposte, oppure l'input originale se nessun risultato è stato selezionato
     */
    public static String getSelezionaIndirizzo(String indirizzoInput) {
        JOpenCageGeocoder geocoder = new JOpenCageGeocoder(API_KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(indirizzoInput);
        // Aggiunge una versione semplificata per una ricerca più permissiva
        String relaxedInput = indirizzoInput.toLowerCase().replaceAll("\\d+", "").trim();
        JOpenCageForwardRequest relaxedRequest = new JOpenCageForwardRequest(relaxedInput);
        relaxedRequest.setLimit(5);
        relaxedRequest.setNoAnnotations(true);
        relaxedRequest.setLanguage("it");
        request.setLimit(5); 
        request.setNoAnnotations(true);
        request.setLanguage("it"); 

        List<JOpenCageResult> results = new ArrayList<>();
        results.addAll(geocoder.forward(request).getResults());
        results.addAll(geocoder.forward(relaxedRequest).getResults());

        // Rimuove duplicati e risultati poco significativi
        List<JOpenCageResult> risultatiFiltrati = new ArrayList<>();
        Set<String> seenFormatted = new HashSet<>(results.size());

        for (JOpenCageResult result : results) {
            String formatted = result.getFormatted();
            
            // Filtra i risultati che contengono solo la città senza via
            if (!formatted.matches(".*\\d+.*") && formatted.split(",").length <= 3) {
                continue; // Salta i risultati che sono solo città/regioni, Nazione
            }

            boolean isSubset = false;
            for (String existing : seenFormatted) {
                if (existing.contains(formatted) || formatted.contains(existing)) {
                    isSubset = true;
                    break;
                }
            }

            if (!isSubset) {
                seenFormatted.add(formatted);
                risultatiFiltrati.add(result);
            }
        }

        // Ordina i risultati filtrati per lunghezza crescente (più specifici prima)
        risultatiFiltrati.sort((a, b) -> Integer.compare(a.getFormatted().length(), b.getFormatted().length()));

        results = risultatiFiltrati;

        if (results.isEmpty()) {
            System.out.println("Nessun risultato trovato per: " + indirizzoInput);
            return indirizzoInput; 
        }

        System.out.println("\nSeleziona l'indirizzo corretto:");
        for (int i = 0; i < results.size(); i++) {
            JOpenCageResult result = results.get(i);
            System.out.printf("%d. %s%n", i + 1, result.getFormatted());
        }
        System.out.println("0. Usa l'indirizzo inserito: " + indirizzoInput);

        int scelta = -1;
        do {
            System.out.print("Scelta -> ");
            try {
                scelta = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        } while (scelta < 0 || scelta > results.size());

        if (scelta == 0) {
            return indirizzoInput;
        }

        JOpenCageResult selected = results.get(scelta - 1);
        return selected.getFormatted();
    }
}