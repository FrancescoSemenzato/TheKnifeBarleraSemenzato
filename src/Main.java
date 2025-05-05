// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;
import src.Utenti.Cliente;
import src.Utenti.Ristoratore;
import src.Utenti.UtenteNonRegistrato;

public class Main {
    public static final String FilePathUtenti="FilesCSV/ListaUtenti.csv";
    public static final Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Caricamento delle liste per utenti
        ArrayList<Cliente> ListaClienti = new ArrayList<Cliente>();
        ArrayList<Ristoratore> ListaRistoratori = new ArrayList<Ristoratore>();
        CaricaListe(ListaClienti, ListaRistoratori);
        // Caricamento della lista Ristoranti
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        
        String selezioneStringa;
        int selezioneInt;
        String nome, cognome, username, password, domicilio, datadinascita, ruolo="";
        boolean datiCorretti = false, continua = true;

        Cliente cl = new Cliente();
        Ristoratore ris = new Ristoratore();

        while(continua){
            pulisciTerminale();
            System.out.println("BENVENUTO NEL PROGETTO THE KNIFE\n");
            System.out.println("SCEGLI CHE OPERAZIONE FARE:");
            System.out.println("1- REGISTRATI, ACCEDI O ENTRA COME GUEST");
            System.out.println("2- ESCI");
            do {
                System.out.print("SELEZIONE ->\t");
                try {
                    selezioneInt = Integer.parseInt(in.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            } while (true);

            switch (selezioneInt) {
                case 1:{ // CLIENTI O RISTORATORI
                    pulisciTerminale();
                    System.out.println("PER TORNARE INDIETRO INSERIRE '#'");
                    System.out.println("POSSIEDI GIA' UN ACCOUNT? [SI/NO]\n");
                    selezioneStringa = in.nextLine();
                    pulisciTerminale();
                    if(selezioneStringa.toLowerCase().charAt(0) == 's'){ // Form ACCEDI
                        do{
                            System.out.println("INSERISCI USERNAME, PASSWORD E RUOLO PER ACCEDERE\n");
                            System.out.print("USERNAME ->\t");
                            username = in.nextLine();
                            System.out.print("PASSWORD ->\t");
                            password = in.nextLine();
                            System.out.print("RUOLO [Cliente/Ristoratore] ->\t ");
                            ruolo = in.nextLine();
            
                            if(ruolo.toLowerCase().charAt(0) == 'c'){
                                for(Cliente c : ListaClienti) {
                                    String passwordDecifrata = c.getPasswordDecifrata(password, c.getUsername());
                                    if(c.getUsername().equals(username) && passwordDecifrata != null && passwordDecifrata.equals(password)){
                                        cl = c;
                                        datiCorretti = true;
                                        ruolo = "cliente";
                                        break;
                                    }
                                }    
                            }
                            if (ruolo.toLowerCase().charAt(0) == 'r') {
                                for (Ristoratore r : ListaRistoratori) {
                                    String passwordDecifrata = r.getPasswordDecifrata(password, r.getUsername());
                                    if (r.getUsername().equals(username) && passwordDecifrata != null && passwordDecifrata.equals(password)) {
                                        ris = r;
                                        datiCorretti = true;
                                        ruolo = "ristoratore";
                                        ris.CaricaListaRistoranti(ris.getUsername(), gestoreRistoranti);
                                        break;
                                    }
                                }
                            }
                        }while(!datiCorretti);
                        System.out.println("DATI INSERITI CORRETTAMENTE!");
                    }
                    else if(selezioneStringa.toLowerCase().charAt(0) == '#'){
                        break;
                    }
                    else{ // Registrati o entra come Guest o Esci
                        do{
                            pulisciTerminale();
                            System.out.println("SCEGLI TRA UNA DI QUESTE OPZIONI: \n");
                            System.out.println("1- REGISTRATI COME CLIENTE");
                            System.out.println("2- REGISTRATI COME RISTORATORE");
                            System.out.println("3- ENTRA COME GUEST");
                            System.out.println("4- TORNA AL MENU'\n");
                            do {
                                System.out.print("SELEZIONE ->\t");
                                try {
                                    selezioneInt = Integer.parseInt(in.nextLine());
                                } catch (NumberFormatException e) {
                                    selezioneInt = -1;
                                }
                            } while(selezioneInt < 1 || selezioneInt > 4);
                        }while(false);
                        
                        switch (selezioneInt) {
                            case 1:{
                                System.out.print("\nNOME ->\t\t");
                                nome = in.nextLine();
                                System.out.print("COGNOME ->\t");
                                cognome = in.nextLine();
                                System.out.print("USERNAME ->\t");
                                username = in.nextLine();
                                System.out.print("PASSWORD ->\t");
                                password = in.nextLine();
                                System.out.print("DOMICILIO [Città, Nazione] ->\t");
                                domicilio = in.nextLine();
                                System.out.print("DATA DI NASCITA [gg/mm/aaaa] ->\t");
                                datadinascita = in.nextLine();
                                cl = new Cliente(nome, cognome, username, password, domicilio, datadinascita, true);
                                cl.CaricaListaPreferiti(username, gestoreRistoranti);
                                ListaClienti.add(cl);
                                ruolo = "cliente";
                                break;
                            }
                            case 2:{
                                System.out.print("\nNOME ->\t\t");
                                nome = in.nextLine();
                                System.out.print("COGNOME ->\t");
                                cognome = in.nextLine();
                                System.out.print("USERNAME ->\t");
                                username = in.nextLine();
                                System.out.print("PASSWORD ->\t");
                                password = in.nextLine();
                                System.out.print("DOMICILIO [Città, Nazione] ->\t");
                                domicilio = in.nextLine();
                                System.out.print("DATA DI NASCITA [gg/mm/aaaa] ->\t");
                                datadinascita = in.nextLine();
                                ris = new Ristoratore(nome, cognome, username, password, domicilio, datadinascita, true);
                                ListaRistoratori.add(ris);
                                ruolo = "ristoratore";
                                break;
                            }
                            case 3:{
                                ruolo = "guest";
                                break;
                            }
                            case 4:{
                                ruolo = "esci";
                                break;
                            }
                        }
                    }
                    

                    //Funzionalità in base al ruolo
                    pulisciTerminale();
                    switch (ruolo) {
                        case "cliente":{
                            pulisciTerminale();
                            boolean continuaCliente = true;
                            System.out.println("BENVENUTO IN MODALITA' CLIENTE\n");
                            while(continuaCliente){
                                do{
                                    System.out.println("1- VISUALIZZA RISTORANTI VICINO A TE");
                                    System.out.println("2- VISUALIZZA FILTRI DI RICERCA");
                                    System.out.println("3- VISUALIZZA LISTA PREFERITI");
                                    System.out.println("4- AGGIUNGI RISTORANTE A LISTA PREFERITI");
                                    System.out.println("5- SCRIVI UNA RECENSIONE");
                                    System.out.println("6- MODIFICA UNA RECENSIONE");
                                    System.out.println("7- RIMUOVI UNA RECENSIONE");
                                    System.out.println("8- TORNA AL MENU' PRINCIPALE");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 8);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        int distanza = 20;
                                        ArrayList<Ristorante> vicini = gestoreRistoranti.filtraPerVicinoA(cl.getDomicilio(), distanza);

                                        pulisciTerminale();
                                        if(vicini.isEmpty()){
                                            System.out.println("Non hai ristoranti vicini nell'arco di " + distanza + "km");
                                        }
                                        else{
                                            System.out.println("STAI PER VEDERE I RISTORANTI VICINO A TE");
                                            System.out.println("IL TUO DOMICILIO E': " + cl.getDomicilio());
                                            System.out.println("Trovati " + vicini.size() + " ristoranti vicini.\n");
                                            System.out.println("Ristoranti trovati:"); 
                                            for (int i = 0; i < vicini.size(); i++) {
                                                System.out.printf("%d - %s\n", i + 1, vicini.get(i).getNome());
                                            }
                                            SelezioneRistorante(vicini);
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        boolean continuaFiltro = true;
                                        while(continuaFiltro){
                                            do{
                                                System.out.println("1- VISUALIZZA RISTORANTI IN BASE ALLA CITTA'");
                                                System.out.println("2- VISUALIZZA RISTORANTI IN BASE AL NOME");
                                                System.out.println("3- VISUALIZZA RISTORANTI IN BASE AL TIPO DI CUCINA");
                                                System.out.println("4- VISUALIZZA RISTORANTI IN BASE ALLA FASCIA DI PREZZO");
                                                System.out.println("5- VISUALIZZA RISTORANTI IN BASE ALLA DISPONIBILITA' DEL SERVIZIO DELIVERY");
                                                System.out.println("6- VISUALIZZA RISTORANTI IN BASE ALLA DISPONIBILITA' DI PRENOTAZIONE ONLINE");
                                                System.out.println("7- VISUALIZZA RISTORANTI IN BASE ALLA MEDIA DELLE STELLE");
                                                System.out.println("8- UNISCI PIU' FILTRI DI RICERCA");
                                                System.out.println("9- TORNA AL MENU' CLIENTE");
                                                do {
                                                    System.out.print("SELEZIONE ->\t");
                                                    try {
                                                        selezioneInt = Integer.parseInt(in.nextLine());
                                                    } catch (NumberFormatException e) {
                                                        selezioneInt = -1;
                                                    }
                                                } while(selezioneInt < 1 || selezioneInt > 9);
                                            }while(false); 
                                            pulisciTerminale();
                                            switch (selezioneInt) {
                                                case 1:{
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine();
                                                    for(Ristorante r : gestoreRistoranti.filtraPerCitta(citta)){
                                                        System.out.println(r.visualizzaRistorante());
                                                    }
                                                    System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 2:{
                                                    String nomeRistorante;
                                                    do {
                                                        System.out.print("INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE -> ");
                                                        nomeRistorante = in.nextLine();
                                                        if(nomeRistorante.length() < 2) {
                                                            System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                        }
                                                    } while(nomeRistorante.length() < 2);

                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                                    SelezioneRistorante(filtrati);
                                                    System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                // ... altri casi del filtro ...
                                            }
                                        }
                                        break;
                                    }
                                    // ... altri casi del menu cliente ...
                                }   
                            }
                            break;
                        }
                        
                        case "ristoratore":{
                            // operazioni per ristoratore
                            pulisciTerminale();
                            boolean continuaRistoratore = true;
                            System.out.println("BENVENUTO IN MODALITA' RISTORATORE\n");
                            while(continuaRistoratore){
                                do{
                                    System.out.println("1- VISUALIZZA I TUOI RISTORANTI");
                                    System.out.println("2- AGGIUNGI UN RISTORANTE");
                                    System.out.println("3- MODIFICA UN RISTORANTE");
                                    System.out.println("4- ELIMINA UN RISTORANTE");
                                    System.out.println("5- VISUALIZZA LE RECENSIONI DI UN RISTORANTE");
                                    System.out.println("6- RISPONDI A UNA RECENSIONE");
                                    System.out.println("7- MODIFICA UNA RISPOSTA");
                                    System.out.println("8- ELIMINA UNA RISPOSTA");
                                    System.out.println("9- TORNA AL MENU' PRINCIPALE");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 9);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        System.out.println("I TUOI RISTORANTI SONO:");
                                        if(ris.getListaRistoranti().isEmpty()) {
                                            System.out.println("Nessun ristorante associato al tuo account.");
                                        } else {
                                            for(Ristorante r : ris.getListaRistoranti()){
                                                System.out.println(r.visualizzaRistorante());
                                            }
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2: { // Aggiungi un ristorante
                                        String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo = "";
                                        double Latitudine = 0, Longitudine = 0;
                                        int Stelle = 0, FasciaDiPrezzo = 0;
                                        boolean Delivery = false, PrenotazioneOnline = false;
                                    
                                        // Input nome
                                        System.out.print("Nome del ristorante: ");
                                        Nome = in.nextLine().trim();
                                    
                                        // Input nazione
                                        System.out.print("Nazione: ");
                                        Nazione = in.nextLine().trim();
                                    
                                        // Input città
                                        System.out.print("Città: ");
                                        Citta = in.nextLine().trim();
                                    
                                        // Input indirizzo
                                        System.out.print("Indirizzo: ");
                                        Indirizzo = in.nextLine().trim();
                                    
                                        // Input tipo di cucina
                                        System.out.print("Tipo di cucina: ");
                                        TipoDiCucina = in.nextLine().trim();
                                    
                                        // Input servizi
                                        System.out.print("Servizi offerti (es. WiFi, Parcheggio): ");
                                        Servizi = in.nextLine().trim();
                                    
                                        // Input URL del sito web
                                        System.out.print("URL del sito web: ");
                                        URLWeb = in.nextLine().trim();
                                    
                                        // Input prezzo 
                                        while (true) {
                                            System.out.print("Prezzo (solo numero): ");
                                            try {
                                                int temp = Integer.parseInt(in.nextLine().trim());
                                                if (temp >= 0) {
                                                    // Calcolo della fascia di prezzo in base al valore inserito
                                                    if (temp > 100) {
                                                        Prezzo = "€€€€";
                                                    } else if (temp > 70) {
                                                        Prezzo = "€€€";
                                                    } else if (temp > 40) {
                                                        Prezzo = "€€";
                                                    } else {
                                                        Prezzo = "€";
                                                    }
                                                    break;
                                                } else {
                                                    System.out.println("Il prezzo non può essere negativo.");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci un numero valido.");
                                            }
                                        }
                                    
                                        // Input latitudine
                                        while (true) {
                                            System.out.print("Latitudine (-90 a 90): ");
                                            try {
                                                Latitudine = Double.parseDouble(in.nextLine().trim());
                                                if (Latitudine >= -90 && Latitudine <= 90) break;
                                                else System.out.println("Latitudine non valida.");
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci un numero valido.");
                                            }
                                        }
                                    
                                        // Input longitudine
                                        while (true) {
                                            System.out.print("Longitudine (-180 a 180): ");
                                            try {
                                                Longitudine = Double.parseDouble(in.nextLine().trim());
                                                if (Longitudine >= -180 && Longitudine <= 180) break;
                                                else System.out.println("Longitudine non valida.");
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci un numero valido.");
                                            }
                                        }
                                    
                                        // Input stelle
                                        String result = "";
                                        while (true) {
                                            System.out.print("Numero di stelle (0-5): ");
                                            try {
                                                int stelle = Integer.parseInt(in.nextLine().trim());
                                                if (stelle >= 0 && stelle <= 5) {
                                                    if (stelle == 0) {
                                                        result = "Selected Restaurants";
                                                    } else {
                                                        result = stelle + " Stars";
                                                    }
                                                    break;
                                                } else {
                                                    System.out.println("Inserisci un numero da 0 a 5.");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci un numero valido.");
                                            }
                                        }
                                    
                                        // Input delivery
                                        while (true) {
                                            System.out.print("Offre servizio di delivery? (s/n): ");
                                            String input = in.nextLine().trim().toLowerCase();
                                            if (input.equals("s")) {
                                                Delivery = true;
                                                break;
                                            } else if (input.equals("n")) {
                                                Delivery = false;
                                                break;
                                            } else {
                                                System.out.println("Risposta non valida. Inserisci 's' o 'n'.");
                                            }
                                        }
                                    
                                        // Input prenotazione online
                                        while (true) {
                                            System.out.print("Permette la prenotazione online? (s/n): ");
                                            String input = in.nextLine().trim().toLowerCase();
                                            if (input.equals("s")) {
                                                PrenotazioneOnline = true;
                                                break;
                                            } else if (input.equals("n")) {
                                                PrenotazioneOnline = false;
                                                break;
                                            } else {
                                                System.out.println("Risposta non valida. Inserisci 's' o 'n'.");
                                            }
                                        }

                                        // Input prezzo
                                        System.out.print("Fascia di prezzo (numero intero): ");
                                        FasciaDiPrezzo = Integer.parseInt(in.nextLine().trim());
                                    
                                        // Chiamata al metodo per aggiungere il ristorante
                                        ris.AggiungiRistorante(Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Latitudine, Longitudine, FasciaDiPrezzo, result, Delivery, PrenotazioneOnline);
                                        System.out.println("RISTORANTE AGGIUNTO CORRETTAMENTE");
                                    
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    
                                    case 3:{
                                        
                                    }
                                    case 4:{
                                        
                                    }
                                    case 5:{
                                        
                                    }
                                    case 6:{
                                        
                                    }
                                    case 7:{
                                        
                                    }
                                    case 8:{
                                        
                                    }
                                    case 9:{
                                        continuaRistoratore = false;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        
                        case "guest":{
                            pulisciTerminale();
                            boolean continuaGuest = true;
                            System.out.println("BENVENUTO IN GUEST MODE");
                            while(continuaGuest){
                                do{
                                    System.out.println("1- VISUALIZZA RISTORANTI IN BASE ALLA CITTA'");
                                    System.out.println("2- CERCA RISTORANTE INSERENDO IL NOME");
                                    System.out.println("3- VISUALIZZA LE RECENSIONI DI UN RISTORANTE");
                                    System.out.println("4- TORNA AL MENU' PRINCIPALE\n");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 4);
                                }while(false);
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        String citta;
                                        do{
                                            System.out.print("INSERISCI LA CITTA' ->\t");
                                            citta = in.nextLine();
                                        }while(citta.length() < 3);
                                        SelezioneRistorante(gestoreRistoranti.filtraPerCitta(citta));
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        String nomeRistorante;
                                        do {
                                            System.out.print("INSERISCI IL NOME PER CERCARE IL RISTORANTE ->\t");
                                            nomeRistorante = in.nextLine();
                                            if(nomeRistorante.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri");
                                            }
                                        } while(nomeRistorante.length() < 2);

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        if (risultati.isEmpty()) {
                                            System.out.println("Nessun ristorante trovato con quel nome.");
                                        } else {
                                            SelezioneRistorante(risultati);
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3:{
                                        UtenteNonRegistrato guest = new UtenteNonRegistrato();
                                        String nomeRistorante;
                                        do {
                                            System.out.print("INSERISCI IL NOME DEL RISTORANTE ->\t");
                                            nomeRistorante = in.nextLine();
                                            if(nomeRistorante.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri");
                                            }
                                        } while(nomeRistorante.length() < 2);

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        if (risultati.isEmpty()) {
                                            System.out.println("Nessun ristorante trovato con quel nome.");
                                        } else {
                                            Ristorante risulRistorante = GetSelezioneRistorante(risultati);
                                            ArrayList<Recensione> recensioni = guest.getRecensioni(risulRistorante);

                                            if (recensioni == null || recensioni.isEmpty()) {
                                                System.out.println("Nessuna recensione trovata.");
                                            } else {
                                                for (Recensione r : recensioni) {
                                                    System.out.println(r.visualizzaRecensione());
                                                }
                                            }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 4:{
                                        continuaGuest = false;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        case "esci":{
                            break;
                        }
                        default:{
                            System.out.println("ERRORE");
                            break;
                        }
                    }
                    break;
                }
                case 2:{ // ESCI
                    pulisciTerminale();
                    continua = false;
                    break;
                }   
            }
        }
        Esci(ListaClienti, ListaRistoratori, gestoreRistoranti);
    }

    public static void CaricaListe(ArrayList<Cliente> cl , ArrayList<Ristoratore> rs){
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathUtenti))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi[6].equals("Cliente")) {
                    cl.add(new Cliente(campi[1], campi[2], campi[0], campi[3], campi[5], campi[4], false));
                }
                else{
                    rs.add(new Ristoratore(campi[1], campi[2], campi[0], campi[3], campi[5], campi[4], false));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    public static void Esci(ArrayList<Cliente> cl, ArrayList<Ristoratore> ris, GestoreRistoranti risto){
        risto.scriviSuFile();

        for(Cliente c : cl){
            c.CaricaListaPreferiti(c.getUsername(), risto);
        }
        for(Ristoratore r : ris){
            r.CaricaListaRistoranti(r.getUsername(), risto);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePathUtenti))) {
            // intestazione
            bw.write("Username;Nome;Cognome;Password;DataDiNascita;Domicilio;Ruolo;Preferiti;Ristoranti");
            bw.newLine();

            for (Cliente c : cl) {
                String linea = String.join(";",
                    c.getUsername(),
                    c.getNome(),
                    c.getCognome(),
                    c.getPassword(),
                    c.getDataDiNascita(),
                    c.getDomicilio(),
                    "Cliente",
                    c.getPreferitiString(),
                    "//"
                );
                bw.write(linea);
                bw.newLine();
            }

            for (Ristoratore r : ris) {
                String linea = String.join(";",
                    r.getUsername(),
                    r.getNome(),
                    r.getCognome(),
                    r.getPassword(),
                    r.getDataDiNascita(),
                    r.getDomicilio(),
                    "Ristoratore",
                    "//",
                    r.getRistorantiString()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file degli utenti: " + e.getMessage());
        }
    }

    public static void pulisciTerminale() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void SelezioneRistorante(ArrayList<Ristorante> risultati) {
        pulisciTerminale();
        System.out.println("Ristoranti trovati:");
        for (int i = 0; i < risultati.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, risultati.get(i).getNome());
        }
        System.out.println("0 - Nessun ristorante");
    
        int scelta = -1;
        do {
            System.out.print("Seleziona il numero del ristorante da visualizzare (0 per annullare) ->\t");
            try {
                scelta = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                scelta = -1;
            }
        } while (scelta < 0 || scelta > risultati.size());
    
        if (scelta == 0) {
            System.out.println("\nNessun ristorante selezionato.");
        } else {
            System.out.println("\nHAI SELEZIONATO: \n");
            System.out.println(risultati.get(scelta - 1).visualizzaRistorante());
        }
    }

    public static Ristorante GetSelezioneRistorante(ArrayList<Ristorante> risultati) {
        pulisciTerminale();
        System.out.println("Ristoranti trovati:");
        for (int i = 0; i < risultati.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, risultati.get(i).getNome());
        }
        System.out.println("0 - Nessuno");
    
        int scelta = -1;
        do {
            System.out.print("Seleziona il numero del ristorante da visualizzare (0 per annullare) ->\t");
            try {
                scelta = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                scelta = -1;
            }
        } while (scelta < 0 || scelta > risultati.size());
    
        if (scelta == 0) {
            return null;
        }
    
        return risultati.get(scelta - 1);
    }

    public static Recensione GetSelezioneRecensione(ArrayList<Recensione> risultati) {
        pulisciTerminale();
        System.out.println("Recensioni trovate:");
        for (int i = 0; i < risultati.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, risultati.get(i).getNomeRistorante());
        }
        System.out.println("0 - Nessuno");
    
        int scelta = -1;
        do {
            System.out.print("Seleziona il numero della recensione da visualizzare (0 per annullare) ->\t");
            try {
                scelta = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                scelta = -1;
            }
        } while (scelta < 0 || scelta > risultati.size());
    
        if (scelta == 0) {
            return null;
        }
    
        return risultati.get(scelta - 1);
    }
}