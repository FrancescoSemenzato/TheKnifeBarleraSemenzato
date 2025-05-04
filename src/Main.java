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
        //Caricamento delle liste per utenti
        ArrayList <Cliente> ListaClienti = new ArrayList<Cliente>();
        ArrayList <Ristoratore> ListaRistoratori = new ArrayList<Ristoratore>();
        CaricaListe(ListaClienti, ListaRistoratori);
        //Caricamento della lista Ristoranti
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

                case 1:{//CLIENTI O RISTORATORI
                    pulisciTerminale();
                    System.out.println("PER TORNARE INDIETRO INSERIRE '#'");
                    System.out.println("POSSIEDI GIA' UN ACCOUNT? [SI/NO]");
                    selezioneStringa = in.nextLine();
                    pulisciTerminale();
                    if(selezioneStringa.toLowerCase().charAt(0) == 's'){//Form ACCEDI
                        do{
                            System.out.println("INSERISCI USERNAME, PASSWORD E RUOLO PER ACCEDERE");
                            System.out.print("USERNAME ->\t");
                            username = in.nextLine();
                            System.out.print("PASSWORD ->\t");
                            password = in.nextLine();
                            System.out.print("RUOLO [Cliente/Ristoratore] ->\t ");
                            ruolo = in.nextLine();
            
                            if(ruolo.toLowerCase().charAt(0) == 'c'){
                                for(Cliente c : ListaClienti) {
                                    if(c.getUsername().equals(username) && c.getPasswordDecifrata(password, username).equals(password)){
                                        cl = c;
                                        datiCorretti = true;
                                        ruolo = "cliente";
                                        break;
                                    }
                                }
                                    
                            }
                            else if(ruolo.toLowerCase().charAt(0) == 'r'){
                                for(Ristoratore r : ListaRistoratori) {
                                    if(r.getUsername().equals(username) && r.getPasswordDecifrata(password, username).equals(password)){
                                        ris = r;
                                        datiCorretti = true;
                                        ruolo = "ristoratore";
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

                    else{//Registrati o entra come Guest o Esci
                        do{
                            pulisciTerminale();
                            System.out.println("SCEGLI TRA UNA DI QUESTE OPZIONI: \n");
                            System.out.println("1- REGISTRATI COME CLIENTE");
                            System.out.println("2- REGISTRATI COME RISTORATORE");
                            System.out.println("3- ENTRA COME GUEST");
                            System.out.println("4- TORNA AL MENU'");
                            do {
                                System.out.print("SELEZIONE ->\t");
                                try {
                                    selezioneInt = Integer.parseInt(in.nextLine());
                                } catch (NumberFormatException e) {
                                    selezioneInt = -1;
                                }
                            } while(selezioneInt < 1 || selezioneInt > 4);
                        }while(false); // esegui solo una volta, il do-while interno gestisce la validazione
                        
                        switch (selezioneInt) {
                            case 1:{
                                System.out.print("NOME ->\t");
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
                                System.out.print("NOME ->\t");
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
                                UtenteNonRegistrato guest = new UtenteNonRegistrato();
                                ruolo = "guest";
                                break;
                            }
            
                            case 4:{
                                ruolo = "esci";
                                break;
                            }
                        }
                    }
                
                    /*A QUESTO PUNTO, TEORICAMENTE, LA VARIABILE ruolo, DOVREBBE AVERE UN VALORE TRA ù
                    "cliente" "ristoratore" o "guest
                    SE ANDIAMO A FINIRE NEL DEFAULT SIAMO CUCINATI 💀*/
                    pulisciTerminale();
                    switch (ruolo) {
                        /*OPERAZIONI PER CLIENTE */
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
                                        int distanza = 50;
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
                                        System.out.println("\n\n");
                                        System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();

                                        break;
                                    }
                                    case 2:{
                                        //Visualizza i filtri di ricerca, con un altro SWITCH CASE
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
                                                    //Visualizza i ristoranti in base alla citta'
                                                        System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                        String citta = in.nextLine();
                                                        for(Ristorante r : gestoreRistoranti.filtraPerCitta(citta)){
                                                            System.out.println(r.visualizzaRistorante());
                                                        }

                                                        System.out.println("\n\n");
                                                        System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                        in.nextLine();
                                                        pulisciTerminale();
                                                        break;
                                                }
                                                case 2:{
                                                    //Visualizza i ristoranti in base al nome e città
                                                    System.out.print("INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE ->");
                                                    String nomeRistorante = in.nextLine();
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);

                                                    SelezioneRistorante(filtrati);

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                    }
                                                case 3:{
                                                    //Visualizza i ristoranti in base al tipo di cucina
                                                    System.out.print("INSERISCI IL TIPO DI CUCINA CHE DESIDERI CERCARE ->");
                                                    String tipoCucina = in.nextLine();
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine();
                                                    for(Ristorante r : gestoreRistoranti.filtraPerTipoDiCucina(tipoCucina, citta)){
                                                        System.out.println(r.visualizzaRistorante());
                                                    }

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 4:{
                                                    // Visualizza i ristoranti in base alla fascia di prezzo
                                                    boolean continuaPrezzo = true;
                                                    System.out.println("PUOI CERCARE INSERENDO UN RANGE DI PREZZO OPPURE UN VALORE E UN SEGNO DI COMPARAZIONE [<, >, =] ->");

                                                    while(continuaPrezzo){
                                                        try {
                                                            System.out.print("INSERISCI UNA FASCIA DI PREZZO CHE DESIDERI CERCARE -> ");
                                                            String inputPrezzo1 = in.nextLine().trim();
                                                            int prezzo1 = Integer.parseInt(inputPrezzo1);
    
                                                            System.out.print("INSERISCI UNA SECONDA FASCIA DI PREZZO O UN SEGNO DI COMPARAZIONE -> ");
                                                            String inputPrezzo2 = in.nextLine().trim();
    
                                                            System.out.print("INSERISCI LA CITTA' DI RICERCA -> ");
                                                            String citta = in.nextLine().trim().toLowerCase();
    
                                                            ArrayList<Ristorante> filtrati;
    
                                                            if (inputPrezzo2.equals("<") || inputPrezzo2.equals(">") || inputPrezzo2.equals("=")) {
                                                                filtrati = gestoreRistoranti.filtraPerFasciaDiPrezzo(prezzo1, inputPrezzo2.charAt(0), citta);
                                                                continuaPrezzo = false;
                                                            } else {
                                                                int prezzo2 = Integer.parseInt(inputPrezzo2);
                                                                int min = Math.min(prezzo1, prezzo2);
                                                                int max = Math.max(prezzo1, prezzo2);
                                                                filtrati = gestoreRistoranti.filtraPerFasciaDiPrezzo(min, max, citta);
                                                                continuaPrezzo = false;
                                                            }
    
                                                            if (filtrati.isEmpty()) {
                                                                System.out.println("NESSUN RISTORANTE TROVATO CON I CRITERI INDICATI.");
                                                            } else {
                                                                for (Ristorante r : filtrati) {
                                                                    System.out.println(r.visualizzaRistorante());
                                                                }
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("ERRORE: Inserire solo numeri validi per la fascia di prezzo.");
                                                        } catch (Exception e) {
                                                            System.out.println("Si è verificato un errore: " + e.getMessage());
                                                        }
                                                    }
                                                    System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 5:{
                                                    //Visualizza i ristoranti in base alla disponibilita' del servizio di delivery
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine();
                                                    SelezioneRistorante(gestoreRistoranti.filtraPerDelivery(citta));

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 6:{
                                                    //Visualizza i ristoranti in base alla disponibilita' di prenotazione online
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine();
                                                    SelezioneRistorante(gestoreRistoranti.filtraPerPrenotazioneOnline(citta));

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 7:{
                                                    //Visualizza i ristoranti in base alla media delle stelle
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine();
                                                    float stelle;
                                                    do {
                                                        System.out.print("INSERISCI LA MEDIA DI STELLE [0-5]->");
                                                        stelle = Float.parseFloat(in.nextLine());
                                                    }while(stelle<0 || stelle>5);
                                                    SelezioneRistorante(gestoreRistoranti.filtraPerMediaStelle(stelle,citta));
    
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 8: {
                                                    ArrayList<Ristorante> risultati = new ArrayList<>();
                                                    boolean continuaUnione = true;
                                                    String cittaGlobal = null;

                                                    while (continuaUnione) {
                                                        System.out.println("AGGIUNGI UN FILTRO:");
                                                        System.out.println("1- CITTA'");
                                                        System.out.println("2- NOME");
                                                        System.out.println("3- TIPO DI CUCINA");
                                                        System.out.println("4- FASCIA DI PREZZO");
                                                        System.out.println("5- DELIVERY");
                                                        System.out.println("6- PRENOTAZIONE ONLINE");
                                                        System.out.println("7- MEDIA STELLE");
                                                        System.out.println("8- VISUALIZZA RISULTATI E TERMINA");

                                                        int filtro = -1;
                                                        do {
                                                            System.out.print("SCELTA -> ");
                                                            try {
                                                                filtro = Integer.parseInt(in.nextLine());
                                                            } catch (NumberFormatException e) {
                                                                filtro = -1;
                                                            }
                                                        } while (filtro < 1 || filtro > 8);

                                                        switch (filtro) {
                                                            case 1: {
                                                                System.out.print("INSERISCI LA CITTA' -> ");
                                                                cittaGlobal = in.nextLine();
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerCitta(cittaGlobal));
                                                                break;
                                                            }
                                                            case 2: {
                                                                System.out.print("INSERISCI IL NOME DEL RISTORANTE -> ");
                                                                String nomeRistorante = in.nextLine();
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                                                break;
                                                            }
                                                            case 3: {
                                                                System.out.print("INSERISCI IL TIPO DI CUCINA -> ");
                                                                String tipo = in.nextLine();
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerTipoDiCucina(tipo, cittaGlobal));
                                                                break;
                                                            }
                                                            case 4: {
                                                                System.out.println("INSERISCI FASCIA DI PREZZO");
                                                                try {
                                                                    System.out.print("PRIMO VALORE -> ");
                                                                    int prezzo1 = Integer.parseInt(in.nextLine());
                                                                    System.out.print("SECONDO VALORE O OPERATORE [<, >, =] -> ");
                                                                    String input = in.nextLine();
                                                                    if (cittaGlobal == null) {
                                                                        System.out.print("INSERISCI LA CITTA' -> ");
                                                                        cittaGlobal = in.nextLine();
                                                                    }

                                                                    if (input.equals("<") || input.equals(">") || input.equals("=")) {
                                                                        risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerFasciaDiPrezzo(prezzo1, input.charAt(0), cittaGlobal));
                                                                    } else {
                                                                        int prezzo2 = Integer.parseInt(input);
                                                                        int min = Math.min(prezzo1, prezzo2);
                                                                        int max = Math.max(prezzo1, prezzo2);
                                                                        risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerFasciaDiPrezzo(min, max, cittaGlobal));
                                                                    }
                                                                } catch (Exception e) {
                                                                    System.out.println("ERRORE: Valori di prezzo non validi.");
                                                                }
                                                                break;
                                                            }
                                                            case 5: {
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerDelivery(cittaGlobal));
                                                                break;
                                                            }
                                                            case 6: {
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerPrenotazioneOnline(cittaGlobal));
                                                                break;
                                                            }
                                                            case 7: {
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine();
                                                                }
                                                                float stelle;
                                                                do {
                                                                    System.out.print("INSERISCI MEDIA STELLE [0-5] -> ");
                                                                    stelle = Float.parseFloat(in.nextLine());
                                                                } while (stelle < 0 || stelle > 5);
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerMediaStelle(stelle, cittaGlobal));
                                                                break;
                                                            }
                                                            case 8: {
                                                                continuaUnione = false;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    
                                                    if (risultati.isEmpty()) {
                                                        System.out.println("NESSUN RISTORANTE TROVATO CON I FILTRI INSERITI.");
                                                    } else {
                                                        SelezioneRistorante(risultati);
                                                    }
                                                
                                                    System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 9:{
                                                    //Torna al menu' cliente
                                                    continuaFiltro = false;
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    case 3:{
                                        //Visualizza la lista dei preferiti
                                        System.out.println("ECCO LA LISTA DEI TUOI RISTORANTI PREFERITI:");
                                        if (cl.getPreferiti().isEmpty()) {
                                            System.out.println("NESSUN RISTORANTE NELLA LISTA DEI TUOI PREFERITI.");
                                        } else {
                                            System.out.println("ECCO LA LISTA DEI TUOI RISTORANTI PREFERITI:");
                                            SelezioneRistorante(cl.getPreferiti());
                                        }

                                        System.out.println("\n\n");
                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 4:{
                                        //Aggiungi un ristorante alla lista dei preferiti
                                        System.out.print("INSERISCI IL NOME DEL RISTORANTE -> ");
                                        String nomeRistorante = in.nextLine();
                                        Ristorante r = GetSelezioneRistorante(gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                        if(r == null) {
                                            System.out.println("NESSUN RISTORANTE VERRA' AGGIUNTO AI PREFERITI.");
                                        } else {
                                            cl.AggiungiAiPreferiti(r);
                                            System.out.println("IL RISTORANTE " + r.getNome() + " E' STATO AGGIUNTO AI TUOI PREFERITI.");
                                        }

                                        System.out.println("\n\n");
                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 5:{
                                        // Scrivi una recensione
                                        System.out.print("INSERISCI IL NOME DEL RISTORANTE PER CUI VUOI SCRIVERE UNA RECENSIONE -> ");
                                        String nomeRistorante = in.nextLine().trim();

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        Ristorante r = GetSelezioneRistorante(risultati);

                                        if (r == null) {
                                            System.out.println("NESSUN RISTORANTE VERRÀ SELEZIONATO.");
                                        } else {
                                            System.out.print("INSERISCI IL TESTO DELLA RECENSIONE -> ");
                                            String testoRecensione = in.nextLine();

                                            int voto = -1;
                                            do {
                                                System.out.print("INSERISCI IL VOTO [0-5] -> ");
                                                try {
                                                    voto = Integer.parseInt(in.nextLine());
                                                } catch (NumberFormatException e) {
                                                    System.out.println("VALORE NON VALIDO. INSERIRE UN NUMERO TRA 0 E 5.");
                                                }
                                            } while (voto < 0 || voto > 5);

                                            cl.AggiungiRecensione(voto, testoRecensione, r);
                                            System.out.println("LA RECENSIONE È STATA AGGIUNTA CORRETTAMENTE.");
                                        }

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 6:{
                                        //Modifica recensione
                                        System.out.println("STAI PER VISUALIZZARE LE TUE RECENSIONI:");
                                        cl.VisualizzaRecensioni();
                                        System.out.print("INSERISCI IL NUMERO DELLA RECENSIONE DA MODIFICARE -> ");
                                        Recensione rec = GetSelezioneRecensione(cl.getListaRecensioni());                       

                                        System.out.print("INSERISCI IL TESTO DELLA RECENSIONE -> ");
                                        String testoRecensione = in.nextLine();

                                        int voto = -1;
                                        do {
                                            System.out.print("INSERISCI IL VOTO [0-5] -> ");
                                            try {
                                                voto = Integer.parseInt(in.nextLine());
                                            } catch (NumberFormatException e) {
                                                System.out.println("VALORE NON VALIDO. INSERIRE UN NUMERO TRA 0 E 5.");
                                            }
                                        } while (voto < 0 || voto > 5);

                                        cl.ModificaRecensione(cl.getListaRecensioni().indexOf(rec) , testoRecensione, voto, gestoreRistoranti.getRistorante(rec.getNomeRistorante()));

                                        System.out.println("LA RECENSIONE È STATA MODIFICATA CORRETTAMENTE.");

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 7:{
                                        //Rimouvi recensione
                                        System.out.println("STAI PER VISUALIZZARE LE TUE RECENSIONI:");
                                        cl.VisualizzaRecensioni();
                                        System.out.print("INSERISCI IL NUMERO DELLA RECENSIONE DA ELIMINARE -> ");
                                        Recensione rec = GetSelezioneRecensione(cl.getListaRecensioni());                       

                                        cl.RemoveRecensione(cl.getListaRecensioni().indexOf(rec), gestoreRistoranti.getRistorante(rec.getNomeRistorante()));

                                        System.out.println("LA RECENSIONE È STATA ELIMINATA CORRETTAMENTE.");

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 8:{
                                        //Torna al menu' principale
                                        continuaCliente = false;
                                        break;
                                    }
                                }   
                            }
                            break;
                        }
                        
                        case "ristoratore":{
                            //operazioni per ristoratore
                            break;
                        }
                        

                        //Cerca in base al nome e in base alla città
                        case "guest":{
                            pulisciTerminale();
                            boolean continuaGuest = true;
                            System.out.println("BENVENUTO IN GUEST MODE");
                            while(continuaGuest){
                                do{
                                    System.out.println("1- VISUALIZZA RISTORANTI IN BASE ALLA CITTA'");
                                    System.out.println("2- CERCA RISTORANTE INSERENDO IL NOME");
                                    System.out.println("3- TORNA AL MENU' PRINCIPALE");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 3);
                                }while(false); // esegui solo una volta, il do-while interno gestisce la validazione
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        String citta;
                                        do{
                                            System.out.print("INSERISCI LA CITTA' ->\t");
                                            citta = in.nextLine();
                                        }while(citta.length() < 3 || citta.length() > 20);
                                        for (Ristorante r : gestoreRistoranti.filtraPerCitta(citta)) {
                                            System.out.println(r.visualizzaRistorante());
                                        }
                                        System.out.println("\n\n");
                                        System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }
                
                                    case 2:{
                                        System.out.print("INSERISCI IL NOME PER CERCARE IL RISTORANTE ->\t");
                                        String nomeRistorante = in.nextLine();

                                        ArrayList<Ristorante> risultati = new ArrayList<>(gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));

                                        if (risultati.isEmpty()) {
                                            System.out.println("Nessun ristorante trovato con quel nome.");
                                        } else {
                                            SelezioneRistorante(risultati);
                                        }

                                        System.out.println("\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine();
                                        pulisciTerminale();
                                        break;
                                    }

                                    case 3:{
                                        continuaGuest = false;
                                        break;
                                    }
                                }
                            }
                            
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
                
                
                case 2:{//ESCI
                    pulisciTerminale();
                    continua = false;
                    break;
                }   
            }
        
        System.out.println("\n\n\n\n");
        }
        //la fuinzione esci la richiamiamo solo alla fine del while, perchè se esce dal while è perchè il programma si deve chiudere
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
            System.out.println("Nessun ristorante selezionato.");
        } else {
            System.out.println("HAI SELEZIONATO: ");
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