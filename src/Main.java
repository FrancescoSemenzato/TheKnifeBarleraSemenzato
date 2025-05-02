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
    public static void main(String[] args) {
        //Caricamento delle liste per utenti
        ArrayList <Cliente> ListaClienti = new ArrayList<Cliente>();
        ArrayList <Ristoratore> ListaRistoratori = new ArrayList<Ristoratore>();
        CaricaListe(ListaClienti, ListaRistoratori);
        //Caricamento della lista Ristoranti
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        
        Scanner in = new Scanner(System.in);
        String selezioneStringa;
        int selezioneInt;
        String nome, cognome, username, password, domicilio, datadinascita, ruolo="";
        String buffer;
        boolean datiCorretti = false, continua = true;

        Cliente cl = new Cliente();
        Ristoratore ris = new Ristoratore();
        
        while(continua){
            pulisciTerminale();
            System.out.println("BENVENUTO NEL PROGETTO THE KNIFE");
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
                            System.out.println("SCEGLI TRA UNA DI QUESTE OPZIONI: ");
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
                            System.out.println("BENVENUTO IN MODALITA' CLIENTE");
                            while(continuaCliente){
                                do{
                                    System.out.println("1- VISUALIZZA RISTORANTI VICINO A TE");
                                    System.out.println("2- VISUALIZZA FILTRI DI RICERCA");
                                    System.out.println("3- VISUALIZZA LISTA PREFERITI");
                                    System.out.println("4- AGGIUNGI RISTORANTE A LISTA PREFERITI");
                                    System.out.println("5- SCRIVI UNA RECENSIONE");
                                    System.out.println("6- TORNA AL MENU' PRINCIPALE");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 6);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        ArrayList<Ristorante> vicini = gestoreRistoranti.filtraPerVicinoA(cl.getDomicilio(), 50);

                                        pulisciTerminale();
                                        System.out.println("STAI PER VEDERE I RISTORANTI VICINO A TE");
                                        System.out.println("IL TUO DOMICILIO E': " + cl.getDomicilio());

                                        System.out.println("Trovati " + vicini.size() + " ristoranti vicini.\n");

                                        System.out.println("Ristoranti trovati:"); 
                                        for (int i = 0; i < vicini.size(); i++) {
                                            System.out.printf("%d - %s\n", i + 1, vicini.get(i).getNome());
                                        }

                                        int scelta = -1;
                                        do {
                                            System.out.print("Seleziona il numero del ristorante da visualizzare ->\t");
                                            try {
                                                scelta = Integer.parseInt(in.nextLine());
                                            } catch (NumberFormatException e) {
                                                scelta = -1;
                                            }
                                        } while (scelta < 1 || scelta > vicini.size());

                                        System.out.println("HAI SELEZIONATO: ");
                                        System.out.println(vicini.get(scelta - 1).visualizzaRistorante());

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
                                                System.out.println("8- TORNA AL MENU' PRINCIPALE");
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

                                                    System.out.println("Ristoranti trovati:"); 
                                                    for (int i = 0; i < filtrati.size(); i++) {
                                                        System.out.printf("%d - %s\n", i + 1, filtrati.get(i).getNome());
                                                    }
                                                    int scelta = -1;
                                                    do {
                                                        System.out.print("Seleziona il numero del ristorante da visualizzare ->\t");
                                                        try {
                                                            scelta = Integer.parseInt(in.nextLine());
                                                        } catch (NumberFormatException e) {
                                                            scelta = -1;
                                                        }
                                                    } while (scelta < 1 || scelta > filtrati.size());

                                                    System.out.println("HAI SELEZIONATO: ");
                                                    System.out.println(filtrati.get(scelta - 1).visualizzaRistorante());

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine();
                                                    pulisciTerminale();
                                                    break;
                                                    }
                                                case 3:{
                                                    //Visualizza i ristoranti in base al tipo di cucina
                                                    System.out.println("INSERISCI IL TIPO DI CUCINA CHE DESIDERI CERCARE ->");
                                                    String tipoCucina = in.nextLine();
                                                    System.out.println("INSERISCI LA CITTA' DI RICERCA ->");
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
                                                    //Visualizza i ristoranti in base alla fascia di prezzo
                                                    break;
                                                }
                                                case 5:{
                                                    //Visualizza i ristoranti in base alla disponibilita' del servizio di delivery
                                                    break;
                                                }
                                                case 6:{
                                                    //Visualizza i ristoranti in base alla disponibilita' di prenotazione online
                                                    break;
                                                }
                                                case 7:{
                                                    //Visualizza i ristoranti in base alla media delle stelle
                                                    break;
                                                }
                                                case 8:{
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
                                        break;
                                    }
                                    case 4:{
                                        //Aggiungi un ristorante alla lista dei preferiti
                                        break;
                                    }
                                    case 5:{
                                        //Scrivi una recensione
                                        break;
                                    }
                                    case 6:{
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
                                            System.out.println("Ristoranti trovati:");
                                            for (int i = 0; i < risultati.size(); i++) {
                                                System.out.printf("%d - %s\n", i + 1, risultati.get(i).getNome());
                                            }

                                            int scelta = -1;
                                            do {
                                                System.out.print("Seleziona il numero del ristorante da visualizzare ->\t");
                                                try {
                                                    scelta = Integer.parseInt(in.nextLine());
                                                } catch (NumberFormatException e) {
                                                    scelta = -1;
                                                }
                                            } while (scelta < 1 || scelta > risultati.size());

                                            pulisciTerminale();
                                            System.out.println("DETTAGLI DEL RISTORANTE SELEZIONATO:\n");
                                            System.out.println(risultati.get(scelta - 1).visualizzaRistorante());
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
}