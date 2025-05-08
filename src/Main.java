// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;
import src.Utenti.Cliente;
import src.Utenti.DataDiNascita;
import src.Utenti.Ristoratore;
import src.Utenti.UtenteNonRegistrato;
import src.Utenti.Utente;
import src.Utenti.Indirizzo;

import java.net.InetAddress;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;

public class Main {
    public static final String FilePathUtenti="FilesCSV/ListaUtenti.csv";
    public static final Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        //Permette di nascondere i messaggi di log
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
        // Caricamento delle liste per utenti
        ArrayList<Cliente> ListaClienti = new ArrayList<Cliente>();
        ArrayList<Ristoratore> ListaRistoratori = new ArrayList<Ristoratore>();
        CaricaListe(ListaClienti, ListaRistoratori);
        // Caricamento della lista Ristoranti
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        
        String selezioneStringa;
        int selezioneInt;
        String nome, cognome, username, password, domicilio, datadinascita="", ruolo="";
        boolean datiCorretti = false, continua = true;

        Cliente cl = new Cliente();
        Ristoratore ris = new Ristoratore();

        while(continua){
            pulisciTerminale();
            System.out.println("BENVENUTO NEL PROGETTO THE KNIFE\n");
            System.out.println("SCEGLI CHE OPERAZIONE FARE:\n");
            System.out.println("1- REGISTRATI, ACCEDI O ENTRA COME GUEST");
            System.out.println("2- ESCI\n");
            do {
                System.out.print("SELEZIONE ->\t");
                try {
                    selezioneInt = Integer.parseInt(in.nextLine().trim());
                    if (selezioneInt == 1 || selezioneInt == 2) {
                        break; 
                    } else {
                        System.out.println("Inserisci un numero valido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            } while (true);

            switch (selezioneInt) {
                case 1:{ // CLIENTI O RISTORATORI
                    pulisciTerminale();
                    System.out.println("PER TORNARE INDIETRO INSERIRE '#'");
                    System.out.println("POSSIEDI GIA' UN ACCOUNT? [SI/NO]\n");
                    System.out.print("RISPOSTA ->\t");
                    selezioneStringa = in.nextLine().trim();
                    pulisciTerminale();
                    if (selezioneStringa.toLowerCase().charAt(0) == 's') { // Form ACCEDI
                        boolean uscitaRichiesta = false;
                        do {
                            System.out.println("INSERISCI USERNAME E PASSWORD PER ACCEDERE (digita '#' per tornare indietro)\n");
                            System.out.print("USERNAME ->\t");
                            username = in.nextLine().trim();
                            if (username.equals("#")) {
                                uscitaRichiesta = true;
                                break;
                            }
                            System.out.print("PASSWORD ->\t");
                            password = in.nextLine().trim();
                            if (password.equals("#")) {
                                uscitaRichiesta = true;
                                break;
                            }
                            // Prova come Cliente
                            for (Cliente c : ListaClienti) {
                                String passwordDecifrata = c.getPasswordDecifrata(password, c.getUsername());
                                if (c.getUsername().equals(username) && passwordDecifrata != null && passwordDecifrata.equals(password)) {
                                    cl = c;
                                    ruolo = "cliente";
                                    datiCorretti = true;
                                    break;
                                }
                            }
                            // Se non trovato come cliente, prova come ristoratore
                            if (!datiCorretti) {
                                for (Ristoratore r : ListaRistoratori) {
                                    String passwordDecifrata = r.getPasswordDecifrata(password, r.getUsername());
                                    if (r.getUsername().equals(username) && passwordDecifrata != null && passwordDecifrata.equals(password)) {
                                        ris = r;
                                        ruolo = "ristoratore";
                                        datiCorretti = true;
                                        ris.CaricaListaRistoranti(ris.getUsername(), gestoreRistoranti);
                                        break;
                                    }
                                }
                            }
                            if (!datiCorretti && !uscitaRichiesta) {
                                System.out.println("Credenziali errate. Riprova oppure digita '#' per uscire.\n");
                            }
                    
                        } while (!datiCorretti && !uscitaRichiesta);
                        if (uscitaRichiesta) {
                            System.out.println("Uscita dal form di accesso.");
                        } else {
                            System.out.println("DATI INSERITI CORRETTAMENTE! Ruolo: " + ruolo);
                        }
                    
                    } else if (selezioneStringa.toLowerCase().charAt(0) == '#') {
                        break;
                    }
                    else {
                        boolean continuaRegistrazione = true;
                        // Registrati o entra come Guest o Esci
                        while (continuaRegistrazione) {
                            
                            do {
                                pulisciTerminale();
                                System.out.println("SCEGLI TRA UNA DI QUESTE OPZIONI: \n");
                                System.out.println("1- REGISTRATI COME CLIENTE");
                                System.out.println("2- REGISTRATI COME RISTORATORE");
                                System.out.println("3- ENTRA COME GUEST");
                                System.out.println("4- TORNA AL MENU'\n");
                                System.out.println("Inserisci '#' in qualsiasi momento per annullare\n");
                                
                                do {
                                    System.out.print("SELEZIONE ->\t");
                                    try {
                                        selezioneInt = Integer.parseInt(leggiInputConAnnullamento(in, ""));
                                    } catch (NumberFormatException e) {
                                        selezioneInt = -1;
                                    } catch (InputAnnullatoException e) {
                                        ruolo = "esci";
                                    }
                                } while(selezioneInt < 1 || selezioneInt > 4);
                            } while(false);
                            
                            switch (selezioneInt) {
                                case 1: {
                                    try {
                                        // Nome
                                        do {
                                            nome = leggiInputConAnnullamento(in, "\nNOME ->\t\t");
                                            if (!nome.matches("^[a-zA-Z\\s]+$")) {
                                                System.out.println("Il nome può contenere solo lettere e spazi.");
                                            } else if (nome.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri.");
                                            }
                                        } while (!nome.matches("^[a-zA-Z\\s]+$") || nome.length() < 2);
                                        
                                        // Cognome
                                        do {
                                            cognome = leggiInputConAnnullamento(in, "\nCOGNOME ->\t");
                                            if (!cognome.matches("^[a-zA-Z\\s]+$")) {
                                                System.out.println("Il cognome può contenere solo lettere e spazi.");
                                            } else if (cognome.length() < 2) {
                                                System.out.println("Il cognome deve contenere almeno 2 caratteri.");
                                            }
                                        } while (!cognome.matches("^[a-zA-Z\\s]+$") || cognome.length() < 2);
                                        
                                        // Username
                                        do {
                                            username = leggiInputConAnnullamento(in, "\nUSERNAME ->\t");
                                            if (usernameEsiste(username, ListaClienti, ListaRistoratori)) {
                                                System.out.println("Username già esistente. Inseriscine un altro.");
                                            }
                                        } while (usernameEsiste(username, ListaClienti, ListaRistoratori));
                                        
                                        // Password
                                        do {
                                            System.out.println("\nRequisiti password:");
                                            System.out.println("- Almeno 6 caratteri");
                                            System.out.println("- Almeno 1 lettera maiuscola");
                                            System.out.println("- Almeno 1 numero");
                                            System.out.println("- Almeno 1 carattere speciale (!@#$%^&*?)");
                                            password = leggiInputConAnnullamento(in, "PASSWORD ->\t");
                                            
                                            if (password.length() < 6) {
                                                System.out.println("La password deve contenere almeno 6 caratteri.");
                                            } else if (!password.matches(".*[A-Z].*")) {
                                                System.out.println("La password deve contenere almeno 1 lettera maiuscola.");
                                            } else if (!password.matches(".*\\d.*")) {
                                                System.out.println("La password deve contenere almeno 1 numero.");
                                            } else if (!password.matches(".*[!@#$%^&*?].*")) {
                                                System.out.println("La password deve contenere almeno 1 carattere speciale (!@#$%^&*?).");
                                            }
                                        } while (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?]).{6,}$"));
                                        
                                        // Indirizzo
                                        String indirizzoInput = leggiInputConAnnullamento(in, "\nINSERISCI INDIRIZZO [Via, Città] ->\t");
                                        domicilio = Indirizzo.getSelezionaIndirizzo(indirizzoInput);
                                        
                                        // Data di nascita
                                        boolean dataValida = false;
                                        do {
                                            try {
                                                datadinascita = leggiInputConAnnullamento(in, "\nDATA DI NASCITA [gg/mm/aaaa] ->\t");
                                                
                                                if (!datadinascita.matches("^\\d{2}[/-]\\d{2}[/-]\\d{4}$")) {
                                                    throw new IllegalArgumentException("Formato data non valido. Usare gg/mm/aaaa o gg-mm-aaaa");
                                                }
                                                
                                                DataDiNascita dataNascita = new DataDiNascita(datadinascita);
                                                
                                                if (!DataDiNascita.etaValida(dataNascita.getGiorno(), dataNascita.getMese(), dataNascita.getAnno())) {
                                                    System.out.println("Devi avere almeno 14 anni per registrarti!");
                                                    continue;
                                                }
                                                
                                                dataValida = true;
                                            } catch (IllegalArgumentException e) {
                                                System.out.println("Errore: " + e.getMessage());
                                                System.out.println("Inserisci una data valida nel formato gg/mm/aaaa");
                                            }
                                        } while (!dataValida);
                                        
                                        cl = new Cliente(nome, cognome, username, password, domicilio, datadinascita, true);
                                        cl.CaricaListaPreferiti(username, gestoreRistoranti);
                                        ListaClienti.add(cl);
                                        ruolo = "cliente";
                                        continuaRegistrazione = false;
                                        System.out.println("\nRegistrazione completata con successo! Benvenuto, " + username + "!");
                                        System.out.println("Premi INVIO per continuare.");
                                        in.nextLine();
                                        
                                    } catch (InputAnnullatoException e) {
                                        System.out.println("\nRegistrazione annullata. Torno al menu precedente...");
                                    }
                                    break;
                                }
                                case 2: {
                                    try {
                                        // Nome
                                        do {
                                            nome = leggiInputConAnnullamento(in, "\nNOME ->\t\t");
                                            if (!nome.matches("^[a-zA-Z\\s]+$")) {
                                                System.out.println("Il nome può contenere solo lettere e spazi.");
                                            } else if (nome.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri.");
                                            }
                                        } while (!nome.matches("^[a-zA-Z\\s]+$") || nome.length() < 2);
                                        
                                        // Cognome
                                        do {
                                            cognome = leggiInputConAnnullamento(in, "\nCOGNOME ->\t");
                                            if (!cognome.matches("^[a-zA-Z\\s]+$")) {
                                                System.out.println("Il cognome può contenere solo lettere e spazi.");
                                            } else if (cognome.length() < 2) {
                                                System.out.println("Il cognome deve contenere almeno 2 caratteri.");
                                            }
                                        } while (!cognome.matches("^[a-zA-Z\\s]+$") || cognome.length() < 2);
                                        
                                        // Username
                                        do {
                                            username = leggiInputConAnnullamento(in, "\nUSERNAME ->\t");
                                            if (usernameEsiste(username, ListaClienti, ListaRistoratori)) {
                                                System.out.println("Username già esistente. Inseriscine un altro.");
                                            }
                                        } while (usernameEsiste(username, ListaClienti, ListaRistoratori));
                                        
                                        // Password
                                        do {
                                            System.out.println("\nRequisiti password:");
                                            System.out.println("- Almeno 6 caratteri");
                                            System.out.println("- Almeno 1 lettera maiuscola");
                                            System.out.println("- Almeno 1 numero");
                                            System.out.println("- Almeno 1 carattere speciale (!@#$%^&*?)");
                                            password = leggiInputConAnnullamento(in, "PASSWORD ->\t");
                                            
                                            if (password.length() < 6) {
                                                System.out.println("La password deve contenere almeno 6 caratteri.");
                                            } else if (!password.matches(".*[A-Z].*")) {
                                                System.out.println("La password deve contenere almeno 1 lettera maiuscola.");
                                            } else if (!password.matches(".*\\d.*")) {
                                                System.out.println("La password deve contenere almeno 1 numero.");
                                            } else if (!password.matches(".*[!@#$%^&*?].*")) {
                                                System.out.println("La password deve contenere almeno 1 carattere speciale (!@#$%^&*?).");
                                            }
                                        } while (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?]).{6,}$"));
                                        
                                        // Indirizzo
                                        String indirizzoInput = leggiInputConAnnullamento(in, "\nINSERISCI INDIRIZZO [Via, Città] ->\t");
                                        domicilio = Indirizzo.getSelezionaIndirizzo(indirizzoInput);
                                        
                                        // Data di nascita
                                        boolean dataValida = false;
                                        do {
                                            try {
                                                datadinascita = leggiInputConAnnullamento(in, "\nDATA DI NASCITA [gg/mm/aaaa] ->\t");
                                                
                                                if (!datadinascita.matches("^\\d{2}[/-]\\d{2}[/-]\\d{4}$")) {
                                                    throw new IllegalArgumentException("Formato data non valido. Usare gg/mm/aaaa o gg-mm-aaaa");
                                                }
                                                
                                                DataDiNascita dataNascita = new DataDiNascita(datadinascita);
                                                
                                                if (!DataDiNascita.maggiorenne(dataNascita.getGiorno(), dataNascita.getMese(), dataNascita.getAnno())) {
                                                    System.out.println("Devi essere maggiorenne per registrarti!");
                                                    continue;
                                                }
                                                
                                                dataValida = true;
                                            } catch (IllegalArgumentException e) {
                                                System.out.println("Errore: " + e.getMessage());
                                                System.out.println("Inserisci una data valida nel formato gg/mm/aaaa");
                                            }
                                        } while (!dataValida);
                                        
                                        ris = new Ristoratore(nome, cognome, username, password, domicilio, datadinascita, true);
                                        ListaRistoratori.add(ris);
                                        ruolo = "ristoratore";
                                        continuaRegistrazione = false;
                                        System.out.println("\nRegistrazione completata con successo! Benvenuto, " + username + "!");
                                        System.out.println("Premi INVIO per continuare.");
                                        in.nextLine();
                                        
                                    } catch (InputAnnullatoException e) {
                                        System.out.println("\nRegistrazione annullata. Torno al menu precedente...");
                                    }
                                    break;
                                }
                                case 3: {
                                    ruolo = "guest";
                                    continuaRegistrazione = false;
                                    break;
                                }
                                case 4: {
                                    ruolo = "esci";
                                    continuaRegistrazione = false;
                                    break;
                                }
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
                                    System.out.println("1- MODIFICA ACCOUNT \n");
                                    System.out.println("2- VISUALIZZA RISTORANTI VICINO A TE");
                                    System.out.println("3- VISUALIZZA FILTRI DI RICERCA");
                                    System.out.println("4- VISUALIZZA LISTA DEI PREFERITI");
                                    System.out.println("5- MODIFICA LA LISTA DEI PREFERITI");
                                    System.out.println("6- SCRIVI UNA RECENSIONE");
                                    System.out.println("7- MODIFICA UNA RECENSIONE");
                                    System.out.println("8- RIMUOVI UNA RECENSIONE");
                                    System.out.println("9- TORNA AL MENU' PRINCIPALE\n");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine().trim());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 9);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1: {
                                        boolean modificaUtente = true;
                                        while (modificaUtente) {
                                            modificaUtente = modificaUtente(cl, ListaClienti, ListaRistoratori);
                                        }

                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        int distanza = 20;
                                        ArrayList<Ristorante> vicini = gestoreRistoranti.filtraPerVicinoA(cl.getDomicilio(), distanza);

                                        //pulisciTerminale();
                                        if(vicini.isEmpty()){
                                            System.out.println(cl.getDomicilio());
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
                                            Ristorante ristoranteSelezionato = GetSelezioneRistorante(vicini);
                                            if(ristoranteSelezionato != null){
                                                System.out.println("\n" + ristoranteSelezionato.visualizzaRistorante());
                                            }
                                            else{
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3:{
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
                                                System.out.println("9- TORNA AL MENU' CLIENTE\n");
                                                do {
                                                    System.out.print("SELEZIONE ->\t");
                                                    try {
                                                        selezioneInt = Integer.parseInt(in.nextLine().trim());
                                                    } catch (NumberFormatException e) {
                                                        selezioneInt = -1;
                                                    }
                                                } while(selezioneInt < 1 || selezioneInt > 9);
                                            }while(false); 
                                            pulisciTerminale();
                                            switch (selezioneInt) {
                                                case 1:{
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA -> ");
                                                    String citta = in.nextLine().trim();
                                                    Ristorante ristoranteSelezionato = GetSelezioneRistorante(gestoreRistoranti.filtraPerCitta(citta));
                                                    if(ristoranteSelezionato != null){
                                                        System.out.println(ristoranteSelezionato.visualizzaRistorante());
                                                    }
                                                    else{
                                                        System.out.println("\nNessun ristorante selezionato.");
                                                    }
                                                    System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 2:{
                                                    String nomeRistorante;
                                                    do {
                                                        System.out.print("INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE -> ");
                                                        nomeRistorante = in.nextLine().trim();
                                                        if(nomeRistorante.length() < 2) {
                                                            System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                        }
                                                    } while(nomeRistorante.length() < 2);

                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                                    if(GetSelezioneRistorante(filtrati) != null){
                                                        System.out.println(GetSelezioneRistorante(filtrati).visualizzaRistorante());
                                                    }
                                                    else{
                                                        System.out.println("\nNessun ristorante selezionato.");
                                                    }
                                                    System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 3:{
                                                    //Visualizza i ristoranti in base al tipo di cucina
                                                    System.out.print("INSERISCI IL TIPO DI CUCINA CHE DESIDERI CERCARE ->");
                                                    String tipoCucina = in.nextLine().trim();
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine().trim();
                                                    for(Ristorante r : gestoreRistoranti.filtraPerTipoDiCucina(tipoCucina, citta)){
                                                        System.out.println(r.visualizzaRistorante());
                                                    }

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
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
                                                            String inputPrezzo1 = in.nextLine().trim().trim();
                                                            int prezzo1 = Integer.parseInt(inputPrezzo1);
    
                                                            System.out.print("INSERISCI UNA SECONDA FASCIA DI PREZZO O UN SEGNO DI COMPARAZIONE -> ");
                                                            String inputPrezzo2 = in.nextLine().trim().trim();
    
                                                            System.out.print("INSERISCI LA CITTA' DI RICERCA -> ");
                                                            String citta = in.nextLine().trim().trim().toLowerCase();
    
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
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 5:{
                                                    //Visualizza i ristoranti in base alla disponibilita' del servizio di delivery
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine().trim();
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerDelivery(citta);
                                                    if(GetSelezioneRistorante(filtrati) != null){
                                                        System.out.println(GetSelezioneRistorante(filtrati).visualizzaRistorante());
                                                    }
                                                    else{
                                                        System.out.println("\nNessun ristorante selezionato.");
                                                    }

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 6:{
                                                    //Visualizza i ristoranti in base alla disponibilita' di prenotazione online
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine().trim();
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerPrenotazioneOnline(citta);
                                                    if(GetSelezioneRistorante(filtrati) != null){
                                                        System.out.println(GetSelezioneRistorante(filtrati).visualizzaRistorante());
                                                    }
                                                    else{
                                                        System.out.println("\nNessun ristorante selezionato.");
                                                    }
                                                    

                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 7:{
                                                    //Visualizza i ristoranti in base alla media delle stelle
                                                    System.out.print("INSERISCI LA CITTA' DI RICERCA ->");
                                                    String citta = in.nextLine().trim();
                                                    float stelle;
                                                    do {
                                                        System.out.print("INSERISCI LA MEDIA DI STELLE [0-5]->");
                                                        stelle = Float.parseFloat(in.nextLine().trim());
                                                    }while(stelle<0 || stelle>5);
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerMediaStelle(stelle,citta); 
                                                    if(GetSelezioneRistorante(filtrati) != null){
                                                        System.out.println(GetSelezioneRistorante(filtrati).visualizzaRistorante());
                                                    }
                                                    else{
                                                        System.out.println("\nNessun ristorante selezionato.");
                                                    }
    
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE UN TASTO PER CONTINUARE");
                                                    in.nextLine().trim();
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
                                                                filtro = Integer.parseInt(in.nextLine().trim());
                                                            } catch (NumberFormatException e) {
                                                                filtro = -1;
                                                            }
                                                        } while (filtro < 1 || filtro > 8);

                                                        switch (filtro) {
                                                            case 1: {
                                                                System.out.print("INSERISCI LA CITTA' -> ");
                                                                cittaGlobal = in.nextLine().trim();
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerCitta(cittaGlobal));
                                                                break;
                                                            }
                                                            case 2: {
                                                                String nomeRistorante;
                                                                do {
                                                                    System.out.print("INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE -> ");
                                                                    nomeRistorante = in.nextLine().trim();
                                                                    if(nomeRistorante.length() < 2) {
                                                                        System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                                    }
                                                                } while(nomeRistorante.length() < 2);
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                                                break;
                                                            }
                                                            case 3: {
                                                                System.out.print("INSERISCI IL TIPO DI CUCINA -> ");
                                                                String tipo = in.nextLine().trim();
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine().trim();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerTipoDiCucina(tipo, cittaGlobal));
                                                                break;
                                                            }
                                                            case 4: {
                                                                System.out.println("INSERISCI FASCIA DI PREZZO");
                                                                try {
                                                                    System.out.print("PRIMO VALORE -> ");
                                                                    int prezzo1 = Integer.parseInt(in.nextLine().trim());
                                                                    System.out.print("SECONDO VALORE O OPERATORE [<, >, =] -> ");
                                                                    String input = in.nextLine().trim();
                                                                    if (cittaGlobal == null) {
                                                                        System.out.print("INSERISCI LA CITTA' -> ");
                                                                        cittaGlobal = in.nextLine().trim();
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
                                                                    cittaGlobal = in.nextLine().trim();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerDelivery(cittaGlobal));
                                                                break;
                                                            }
                                                            case 6: {
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine().trim();
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerPrenotazioneOnline(cittaGlobal));
                                                                break;
                                                            }
                                                            case 7: {
                                                                if (cittaGlobal == null) {
                                                                    System.out.print("INSERISCI LA CITTA' -> ");
                                                                    cittaGlobal = in.nextLine().trim();
                                                                }
                                                                float stelle;
                                                                do {
                                                                    System.out.print("INSERISCI MEDIA STELLE [0-5] -> ");
                                                                    stelle = Float.parseFloat(in.nextLine().trim());
                                                                } while (stelle < 0 || stelle > 5);
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerMediaStelle(stelle, cittaGlobal));
                                                                break;
                                                            }
                                                            case 8: {
                                                                continuaUnione = false;
                                                                break;
                                                            }
                                                        }
                                                        if(risultati.isEmpty()) 
                                                                System.out.println("NESSUN RISTORANTE TROVATO CON I FILTRI INSERITI.");
                                                            else {
                                                                if(GetSelezioneRistorante(risultati) != null){
                                                                    System.out.println(GetSelezioneRistorante(risultati).visualizzaRistorante());
                                                                }
                                                                else{
                                                                    System.out.println("\nNessun ristorante selezionato.");
                                                                }
                                                            }
                                                
                                                        
                                                            System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                                            in.nextLine().trim();
                                                            break;
                                                    }
                                            }
                                                case 9:{
                                                    //Torna al menu' cliente
                                                    continuaFiltro = false;
                                                    break;
                                                }
                                        }
                                    }
                                    break;
                                    }   
                                    case 4:{
                                        //Visualizza la lista dei preferiti
                                        System.out.println("ECCO LA LISTA DEI TUOI RISTORANTI PREFERITI:");
                                        if (cl.getPreferiti().isEmpty()) {
                                            System.out.println("NESSUN RISTORANTE NELLA LISTA DEI TUOI PREFERITI.");
                                        } else {
                                            System.out.println("ECCO LA LISTA DEI TUOI RISTORANTI PREFERITI:");
                                            Ristorante preferito = GetSelezioneRistorante(cl.getPreferiti());
                                            if(preferito != null){
                                                System.out.println(preferito.visualizzaRistorante());
                                            }
                                            else{
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }
                                        System.out.println("\n\n");
                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 5:{
                                        boolean continuaFiltro = true;
                                        while (continuaFiltro) {
                                        //Aggiungi un ristorante alla lista dei preferiti ed elimina un ristorante dalla lista dei preferiti
                                        System.out.println("SCEGLI CHE OPERAZIONE EFFETTUARE\n");
                                        System.out.println("1- AGGIUNGI UN RISTORANTE ALLA LISTA PREFERITI");
                                        System.out.println("2- ELIMINA UN RISTORANTE ALLA LISTA PREFERITI");
                                        System.out.println("3- Torna Indietro\n");
                                        do {
                                            System.out.print("SELEZIONE ->\t");
                                            try {
                                                selezioneInt = Integer.parseInt(in.nextLine().trim());
                                            } catch (NumberFormatException e) {
                                                selezioneInt = -1;
                                            }
                                        } while(selezioneInt < 1 || selezioneInt > 2);
                                        
                                        switch(selezioneInt){
                                            case 1:{
                                                //Aggiungi un ristorante alla lista dei preferiti
                                                System.out.print("INSERISCI IL NOME DEL RISTORANTE -> ");
                                                String nomeRistorante = in.nextLine().trim();
                                                Ristorante r = GetSelezioneRistorante(gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                                if(r == null) {
                                                    System.out.println("NESSUN RISTORANTE VERRA' AGGIUNTO AI PREFERITI.");
                                                } else {
                                                    System.out.println(cl.AggiungiAiPreferiti(r));
                                                }

                                                System.out.println("\n\n");
                                                System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                                in.nextLine().trim();
                                                pulisciTerminale();
                                                break;
                                            }
                                            case 2:{
                                                //Elimina un ristorante alla lista dei preferiti
                                                System.out.print("INSERISCI IL NOME DEL RISTORANTE -> ");
                                                String nomeRistorante = in.nextLine().trim();
                                                Ristorante r = GetSelezioneRistorante(gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                                if(r == null) {
                                                    System.out.println("NESSUN RISTORANTE VERRA' ELIMINATO DAI PREFERITI.");
                                                } else {
                                                    cl.RimuoviPreferiti(r);
                                                    System.out.println("RISTORANTE ELIMINATO DAI PREFERITI.");
                                                }

                                                System.out.println("\n\n");
                                                System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                                in.nextLine().trim();
                                                pulisciTerminale();
                                                break;
                                            }
                                            case 3:{
                                                //Torna al menu' cliente
                                                continuaFiltro = false;
                                                break;
                                            }
                                        }
                                        }
                                    }
                                    case 6:{
                                        // Scrivi una recensione
                                        System.out.print("INSERISCI IL NOME DEL RISTORANTE PER CUI VUOI SCRIVERE UNA RECENSIONE -> ");
                                        String nomeRistorante = in.nextLine().trim().trim();

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        Ristorante r = GetSelezioneRistorante(risultati);

                                        if (r == null) {
                                            System.out.println("NESSUN RISTORANTE VERRÀ SELEZIONATO.");
                                        } else {
                                            System.out.print("INSERISCI IL TESTO DELLA RECENSIONE -> ");
                                            String testoRecensione = in.nextLine().trim();

                                            int voto = -1;
                                            do {
                                                System.out.print("INSERISCI IL VOTO [0-5] -> ");
                                                try {
                                                    voto = Integer.parseInt(in.nextLine().trim());
                                                } catch (NumberFormatException e) {
                                                    System.out.println("VALORE NON VALIDO. INSERIRE UN NUMERO TRA 0 E 5.");
                                                }
                                            } while (voto < 0 || voto > 5);

                                            cl.AggiungiRecensione(voto, testoRecensione, r);
                                            System.out.println("LA RECENSIONE È STATA AGGIUNTA CORRETTAMENTE.");
                                        }

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 7:{
                                        //Modifica recensione
                                        System.out.println("STAI PER VISUALIZZARE LE TUE RECENSIONI:");
                                        cl.VisualizzaRecensioni();
                                        System.out.print("INSERISCI IL NUMERO DELLA RECENSIONE DA MODIFICARE -> ");
                                        Recensione rec = GetSelezioneRecensione(cl.getListaRecensioni());                       

                                        if (rec == null) {
                                            System.out.println("NESSUNA RECENSIONE VERRA' MODIFICATA.");
                                        }
                                        else{
                                            System.out.print("INSERISCI IL TESTO DELLA RECENSIONE -> ");
                                            String testoRecensione = in.nextLine().trim();

                                            int voto = -1;
                                            do {
                                                System.out.print("INSERISCI IL VOTO [0-5] -> ");
                                                try {
                                                    voto = Integer.parseInt(in.nextLine().trim());
                                                } catch (NumberFormatException e) {
                                                    System.out.println("VALORE NON VALIDO. INSERIRE UN NUMERO TRA 0 E 5.");
                                                }
                                            } while (voto < 0 || voto > 5);

                                            cl.ModificaRecensione(cl.getListaRecensioni().indexOf(rec) , testoRecensione, voto, gestoreRistoranti.getRistorante(rec.getNomeRistorante()));

                                            System.out.println("LA RECENSIONE È STATA MODIFICATA CORRETTAMENTE.");
                                        }
                                        

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 8:{
                                        //Rimouvi recensione
                                        System.out.println("STAI PER VISUALIZZARE LE TUE RECENSIONI:");
                                        cl.VisualizzaRecensioni();
                                        System.out.print("INSERISCI IL NUMERO DELLA RECENSIONE DA ELIMINARE -> ");
                                        Recensione rec = GetSelezioneRecensione(cl.getListaRecensioni());                       

                                        cl.RemoveRecensione(cl.getListaRecensioni().indexOf(rec), gestoreRistoranti.getRistorante(rec.getNomeRistorante()));

                                        System.out.println("LA RECENSIONE È STATA ELIMINATA CORRETTAMENTE.");

                                        System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 9:{
                                        //Torna al menu' principale
                                        continuaCliente = false;
                                        break;
                                    }
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
                                    System.out.println("1- MODIFICA IL TUO ACCOUNT\n");
                                    System.out.println("2- VISUALIZZA I TUOI RISTORANTI");
                                    System.out.println("3- AGGIUNGI UN RISTORANTE");
                                    System.out.println("4- MODIFICA UN RISTORANTE");
                                    System.out.println("5- ELIMINA UN RISTORANTE");
                                    System.out.println("6- VISUALIZZA LE RECENSIONI DI UN RISTORANTE");
                                    System.out.println("7- RISPONDI AD UNA RECENSIONE");
                                    System.out.println("8- MODIFICA UNA RISPOSTA");
                                    System.out.println("9- ELIMINA UNA RISPOSTA");
                                    System.out.println("10- TORNA AL MENU' PRINCIPALE");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine().trim());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 10);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1: {
                                        boolean modificaUtente = true;
                                        while (modificaUtente) {
                                            modificaUtente = modificaUtente(ris, ListaClienti, ListaRistoratori);
                                        }
                                        System.out.println("L'ACCOUNT È STATO MODIFICATO CORRETTAMENTE.");
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        System.out.println("I TUOI RISTORANTI SONO:");
                                        if(ris.getListaRistoranti().isEmpty()) {
                                            System.out.println("Nessun ristorante associato al tuo account.");
                                        } else {
                                            for(Ristorante r : ris.getListaRistoranti()){
                                                System.out.println(r.visualizzaRistorante());
                                            }
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3: { // Aggiungi un ristorante
                                        String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo = "";
                                        double Latitudine = 0, Longitudine = 0;
                                        int FasciaDiPrezzo = 0;
                                        boolean Delivery = false, PrenotazioneOnline = false;
                                    
                                        // Input nome
                                        System.out.print("Nome del ristorante: ");
                                        Nome = in.nextLine().trim().trim();
                                    
                                        // Input nazione
                                        System.out.print("Nazione: ");
                                        Nazione = in.nextLine().trim().trim();
                                    
                                        // Input città
                                        System.out.print("Città: ");
                                        Citta = in.nextLine().trim().trim();
                                    
                                        // Input indirizzo
                                        System.out.print("Indirizzo: ");
                                        Indirizzo = in.nextLine().trim().trim();
                                    
                                        // Input tipo di cucina
                                        System.out.print("Tipo di cucina: ");
                                        TipoDiCucina = in.nextLine().trim().trim();
                                    
                                        // Input servizi
                                        System.out.print("Servizi offerti (es. WiFi, Parcheggio): ");
                                        Servizi = in.nextLine().trim().trim();
                                    
                                        // Input URL del sito web
                                        System.out.print("URL del sito web: ");
                                        URLWeb = in.nextLine().trim().trim();
                                    
                                        // Input prezzo 
                                        while (true) {
                                            System.out.print("Prezzo (solo numero): ");
                                            try {
                                                int temp = Integer.parseInt(in.nextLine().trim().trim());
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
                                    
                                        // Ottieni latitudine e longitudine dall'indirizzo usando OpenCage
                                        String fullAddress = Indirizzo + ", " + Citta + ", " + Nazione;
                                        JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e"); 
                                        JOpenCageForwardRequest request = new JOpenCageForwardRequest(fullAddress);
                                        request.setLanguage("it"); 

                                        JOpenCageResponse response = geocoder.forward(request);
                                        if (!response.getResults().isEmpty()) {
                                            JOpenCageResult location = response.getResults().get(0);
                                            Latitudine = location.getGeometry().getLat();
                                            Longitudine = location.getGeometry().getLng();
                                            System.out.println("Coordinate trovate: Latitudine = " + Latitudine + ", Longitudine = " + Longitudine);
                                        } else {
                                            System.out.println("Coordinate non trovate. Inserisci manualmente:");
                                            System.out.print("Latitudine: ");
                                            Latitudine = Double.parseDouble(in.nextLine().trim().trim());
                                            System.out.print("Longitudine: ");
                                            Longitudine = Double.parseDouble(in.nextLine().trim().trim());
                                        }
                                    
                                        // Input stelle
                                        String result = "";
                                        while (true) {
                                            System.out.print("Numero di stelle (0-5): ");
                                            try {
                                                int stelle = Integer.parseInt(in.nextLine().trim().trim());
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
                                            String input = in.nextLine().trim().trim().toLowerCase();
                                            if (input.toLowerCase().startsWith("s")) {
                                                Delivery = true;
                                                break;
                                            } else if (input.toLowerCase().startsWith("n")) {
                                                Delivery = false;
                                                break;
                                            } else {
                                                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                                            }
                                        }
                                    
                                        // Input prenotazione online
                                        while (true) {
                                            System.out.print("Permette la prenotazione online? (s/n): ");
                                            String input = in.nextLine().trim().trim().toLowerCase();
                                            if (input.toLowerCase().startsWith("s")) {
                                                PrenotazioneOnline = true;
                                                break;
                                            } else if (input.toLowerCase().startsWith("s")) {
                                                PrenotazioneOnline = false;
                                                break;
                                            } else {
                                                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                                            }
                                        }

                                        // Input prezzo
                                        System.out.print("Fascia di prezzo (numero intero): ");
                                        FasciaDiPrezzo = Integer.parseInt(in.nextLine().trim().trim());
                                    
                                        // Chiamata al metodo per aggiungere il ristorante
                                        gestoreRistoranti.AggiungiRistorante(ris.AggiungiRistorante(Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Latitudine, Longitudine, FasciaDiPrezzo, result, Delivery, PrenotazioneOnline));
                                        System.out.println("RISTORANTE AGGIUNTO CORRETTAMENTE");


                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    
                                    case 4:{ // MODIFICA RISTORANTE
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        if (r == null) {
                                            System.out.println("Nessun ristorante selezionato.");
                                        }
                                        else {
                                            modificaRistorante(r);
                                            System.out.println("RISTORANTE MODIFICATO CORRETTAMENTE");
                                        }

                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 5:{ // ELIMINA RISTORANTE
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        ris.RimuoviRistorante(r);
                                        gestoreRistoranti.RimuoviRistorante(r);
                                        System.out.println("RISTORANTE RIMOSSO CORRETTAMENTE");
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 6:{ // Visualizza Recensioni ristorante
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        r.VisualizzaRecensioni();
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 7:{ // Aggiungi Risposta
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        Recensione rec = GetSelezioneRecensione(r.getRecensioni());
                                        if(rec != null && rec.getRisposta().equals("")) {
                                            System.out.println("INSERISCI LA RISPOSTA ALLA RECNESIONE ->\t");
                                            rec.setRisposta(in.nextLine().trim());
                                        }
                                        else
                                            System.out.println("LA RECENSIONE HA GIA' UNA RISPOSTA");
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 8:{ // Modifica Una risposta
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        Recensione rec = GetSelezioneRecensione(r.getRecensioni());
                                        if(rec != null && !rec.getRisposta().equals("")) {
                                            System.out.println("INSERISCI LA NUOVA RISPOSTA ALLA RECENSIONE ->\t");
                                            rec.setRisposta(in.nextLine().trim());
                                        }
                                        else
                                            System.out.println("LA RECENSIONE NON HA UNA RISPOSTA DA MODIFICARE");
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                        
                                    }
                                    case 9:{ // Rimuovi Una risposta
                                        Ristorante r = GetSelezioneRistorante(ris.getListaRistoranti());
                                        Recensione rec = GetSelezioneRecensione(r.getRecensioni());
                                        if(rec != null && !rec.getRisposta().equals("")) {
                                            rec.EliminaRisposta();;
                                        }
                                        else
                                            System.out.println("LA RECENSIONE NON HA UNA RISPOSTA DA ELIMINARE");
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                        
                                    }
                                    case 10:{
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
                            System.out.println("BENVENUTO IN MODALITA' GUEST\n");
                            while(continuaGuest){
                                do{
                                    System.out.println("1- VISUALIZZA RISTORANTI IN BASE ALLA CITTA'");
                                    System.out.println("2- CERCA RISTORANTE INSERENDO IL NOME");
                                    System.out.println("3- VISUALIZZA LE RECENSIONI DI UN RISTORANTE");
                                    System.out.println("4- TORNA AL MENU' PRINCIPALE\n");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine().trim());
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
                                            citta = in.nextLine().trim();
                                        }while(citta.length() < 3);
                                        ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerCitta(citta);
                                        if(GetSelezioneRistorante(filtrati) != null){
                                            System.out.println("\n" + GetSelezioneRistorante(filtrati).visualizzaRistorante());
                                        }
                                        else{
                                            System.out.println("\nNessun ristorante selezionato.");
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        String nomeRistorante;
                                        do {
                                            System.out.print("INSERISCI IL NOME PER CERCARE IL RISTORANTE ->\t");
                                            nomeRistorante = in.nextLine().trim();
                                            if(nomeRistorante.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri");
                                            }
                                        } while(nomeRistorante.length() < 2);

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        if (risultati.isEmpty()) {
                                            System.out.println("Nessun ristorante trovato con quel nome.");
                                        } else {
                                            if(GetSelezioneRistorante(risultati) != null){
                                                System.out.println(GetSelezioneRistorante(risultati).visualizzaRistorante());
                                            }
                                            else{
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }
                                        System.out.println("\n\nPREMERE UN TASTO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3:{
                                        UtenteNonRegistrato guest = new UtenteNonRegistrato();
                                        String nomeRistorante;
                                        do {
                                            System.out.print("INSERISCI IL NOME DEL RISTORANTE ->\t");
                                            nomeRistorante = in.nextLine().trim();
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
                                        in.nextLine().trim();
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

    private static String leggiInputConAnnullamento(Scanner in, String prompt) {
        System.out.print(prompt);
        String input = in.nextLine().trim();
        if (input.equals("#")) {
            throw new InputAnnullatoException();
        }
        return input;
    }

    private static boolean isInternetReachable() {
        try {
            InetAddress.getByName("google.com").isReachable(3000); // Timeout 3 secondi
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private static void modificaRistorante(Ristorante ristorante){
        try {
            System.out.println("Cosa vuoi modificare?\n");
            System.out.println("1- Nome");
            System.out.println("2- Indirizzo, Citta' e Nazione");
            System.out.println("3- Tipo di cucina");
            System.out.println("4- Servizi");
            System.out.println("5- URL web");
            System.out.println("6- Fascia Di prezzo");
            System.out.println("7- Disponibilita' servizio delivery");
            System.out.println("8- Disponibilità prenotazione online");
            System.out.println("9- Prezzo");
            System.out.println("Inserisci '#' in qualsiasi momento per annullare\n");
            int selezione;
            do {
                selezione = -1;
                try {
                    selezione = Integer.parseInt(leggiInputConAnnullamento(in, "SELEZIONE ->\t").trim());
                } catch (NumberFormatException e) {
                    selezione = -1;
                }
            } while(selezione < 1 || selezione > 11);
            
            switch (selezione) {
                case 1:
                    ristorante.setNome(leggiInputConAnnullamento(in, "INSERISCI IL NUOVO NOME ->\t").trim());
                    break;
                case 2: {
                    String indirizzo = leggiInputConAnnullamento(in, "INSERISCI L'INDIRIZZO ->\t").trim();
                    String citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' ->\t").trim();
                    String nazione = leggiInputConAnnullamento(in, "INSERISCI LA NAZIONE ->\t").trim();
                
                    ristorante.setIndirizzo(indirizzo);
                    ristorante.setCitta(citta);
                    ristorante.setNazione(nazione);
                
                    if (isInternetReachable()) {
                        String indirizzoCompleto = indirizzo + ", " + citta + ", " + nazione;
                        JOpenCageGeocoder geocoder = new JOpenCageGeocoder("650d3794aa3a411d9184bd19486bdb3e");
                        JOpenCageForwardRequest request = new JOpenCageForwardRequest(indirizzoCompleto);
                        request.setLanguage("it");
                        request.setLimit(1);
                
                        try {
                            JOpenCageResponse response = geocoder.forward(request);
                            if (!response.getResults().isEmpty()) {
                                JOpenCageResult result = response.getResults().get(0);
                                double lat = result.getGeometry().getLat();
                                double lng = result.getGeometry().getLng();
                                ristorante.setLatitudine(lat);
                                ristorante.setLongitudine(lng);
                                System.out.println("Coordinate aggiornate: LAT=" + lat + " LNG=" + lng);
                            } else {
                                System.out.println("Impossibile trovare le coordinate per l'indirizzo inserito.");
                                System.out.println("Inserisci manualmente le coordinate:");
                                double lat = Double.parseDouble(leggiInputConAnnullamento(in, "Latitudine: ").trim());
                                double lng = Double.parseDouble(leggiInputConAnnullamento(in, "Longitudine: ").trim());
                                ristorante.setLatitudine(lat);
                                ristorante.setLongitudine(lng);
                            }
                        } catch (Exception e) {
                            System.out.println("Errore durante il geocoding: " + e.getMessage());
                            System.out.println("Inserisci manualmente le coordinate:");
                            double lat = Double.parseDouble(leggiInputConAnnullamento(in, "Latitudine: ").trim());
                            double lng = Double.parseDouble(leggiInputConAnnullamento(in, "Longitudine: ").trim());
                            ristorante.setLatitudine(lat);
                            ristorante.setLongitudine(lng);
                        }
                    } else {
                        System.out.println("Connessione Internet assente. Inserisci manualmente le coordinate:");
                        double lat = Double.parseDouble(leggiInputConAnnullamento(in, "Latitudine: ").trim());
                        double lng = Double.parseDouble(leggiInputConAnnullamento(in, "Longitudine: ").trim());
                        ristorante.setLatitudine(lat);
                        ristorante.setLongitudine(lng);
                    }
                    break;
                }
                case 3:
                    ristorante.setTipoDiCucina(leggiInputConAnnullamento(in, "INSERISCI IL TIPO DI CUCINA ->\t").trim());
                    break;
                case 4:
                    ristorante.setServizi(leggiInputConAnnullamento(in, "INSERISCI I SERVIZI ->\t").trim());
                    break;
                case 5:
                    ristorante.setURLWeb(leggiInputConAnnullamento(in, "INSERISCI L'URL WEB ->\t").trim());
                    break;
                case 6:
                    Integer FasciaDiPrezzo = Integer.parseInt(leggiInputConAnnullamento(in, "INSERISCI LA FASCIA DI PREZZO (numero intero)->\t").trim());
                    ristorante.setFasciaDiPrezzo(FasciaDiPrezzo);
                    break;
                case 7:
                    boolean Delivery;
                    while (true) {
                        String input = leggiInputConAnnullamento(in, "Permette il servizio di delivery? (s/n): ").trim().toLowerCase();
                        if (input.startsWith("s")) {
                            Delivery = true;
                            break;
                        } else if (input.startsWith("n")) {
                            Delivery = false;
                            break;
                        } else {
                            System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                        }
                    }
                    ristorante.setDelivery(Delivery);
                    break;
                case 8:
                    boolean PrenotazioneOnline;
                    while (true) {
                        String input = leggiInputConAnnullamento(in, "Permette la prenotazione online? (s/n): ").trim().toLowerCase();
                        if (input.startsWith("s")) {
                            PrenotazioneOnline = true;
                            break;
                        } else if (input.startsWith("n")) {
                            PrenotazioneOnline = false;
                            break;
                        } else {
                            System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                        }
                    }
                    ristorante.setPrenotazioneOnline(PrenotazioneOnline);
                    break;
                case 9:
                    String Prezzo;
                    while (true) {
                        try {
                            int temp = Integer.parseInt(leggiInputConAnnullamento(in, "Prezzo (solo numero): ").trim());
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
                    ristorante.setPrezzo(Prezzo);
                    break;
            }
        } catch (InputAnnullatoException e) {
            System.out.println("Operazione annullata.");
        }
    }
    
    private static boolean modificaUtente(Utente utente, ArrayList<Cliente> cl , ArrayList<Ristoratore> rs) {
        try {
            System.out.println("Cosa vuoi modificare?\n");
            System.out.println("1- Nome");
            System.out.println("2- Cognome");
            System.out.println("3- Username");
            System.out.println("4- Password");
            System.out.println("5- Data di nascita");
            System.out.println("6- Domicilio");
            System.out.println("7- Ruolo");
            System.out.println("8- Torna al menu precedente\n");
            System.out.println("Inserisci '#' in qualsiasi momento per annullare\n");
            int selezione;
            do {
                System.out.print("\nSELEZIONE ->\t");
                try {
                    selezione = Integer.parseInt(leggiInputConAnnullamento(in, "\nSELEZIONE ->\t").trim());
                } catch (NumberFormatException e) {
                    selezione = -1;
                }
            } while(selezione < 1 || selezione > 8);
            switch (selezione) {
                case 1:{
                    String nome;
                    do { //Nome
                        nome = leggiInputConAnnullamento(in, "\nNUOVONOME ->\t\t").trim();
                        if (!nome.matches("^[a-zA-Z\\s]+$")) {
                            System.out.println("Il nome può contenere solo lettere e spazi.");
                        } else if (nome.length() < 2) {
                            System.out.println("Il nome deve contenere almeno 2 caratteri.");
                        }
                    } while (!nome.matches("^[a-zA-Z\\s]+$") || nome.length() < 2);
                    utente.setNome(nome);
                    System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                    return true;
                }
                case 2:{
                    String cognome;
                    do { //Cognome
                        cognome = leggiInputConAnnullamento(in, "\nCOGNOME ->\t\t").trim();
                        if (!cognome.matches("^[a-zA-Z\\s]+$")) {
                            System.out.println("Il Conome può contenere solo lettere e spazi.");
                        } else if (cognome.length() < 2) {
                            System.out.println("Il Cognome deve contenere almeno 2 caratteri.");
                        }
                    } while (!cognome.matches("^[a-zA-Z\\s]+$") || cognome.length() < 2);
                    utente.setCognome(cognome);
                    return true;
                }
                case 3:{
                    String username;
                    do { //USERNAME, univoco
                        username = leggiInputConAnnullamento(in, "\nUSERNAME ->\t");
                        if (usernameEsiste(username, cl, rs)) {
                            System.out.println("Username già esistente. Inseriscine un altro.");
                        }
                    } while (usernameEsiste(username, cl, rs));
                    utente.setUsername(username);
                    System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                    return true;
                }
                case 4:{
                    String vecchiaPassword = leggiInputConAnnullamento(in, "\nINSERISCI LA VECCHIA PASSWORD ->\t").trim();
    
                    // Verifica della vecchia password usando il metodo getPasswordDecifrata
                    String passwordDecifrata = utente.getPasswordDecifrata(vecchiaPassword, utente.getUsername());
    
                    if (passwordDecifrata == null || !passwordDecifrata.equals(vecchiaPassword)) {
                        System.out.println("Password errata. Cambio password annullato.");
                        return true;
                    }
    
                    // Se la vecchia password è corretta, chiedo la nuova
                    String nuovaPassword;
                    do {
                        nuovaPassword = leggiInputConAnnullamento(in, "\nINSERISCI LA NUOVA PASSWORD ->\t").trim();
                        if (nuovaPassword.length() < 2) {
                            System.out.println("La password deve contenere almeno 2 caratteri.");
                        } else if (nuovaPassword.equals(vecchiaPassword)) {
                            System.out.println("La nuova password non può essere uguale a quella vecchia.");
                            nuovaPassword = ""; // forza il ripetere del ciclo
                        }
                    } while (nuovaPassword.length() < 2);
    
                    utente.setPassword(nuovaPassword); // presumo che questo la cifri di nuovo
                    System.out.println("Password cambiata con successo.");
                    return true;
                }
                case 5:{
                    int giorno, mese, anno;
                    boolean dataValida = false;
                    do {
                        try {
                            giorno = Integer.parseInt(leggiInputConAnnullamento(in, "\nINSERISCI IL NUOVO GIORNO DI NASCITA ->\t"));
                            mese = Integer.parseInt(leggiInputConAnnullamento(in, "INSERISCI IL NUOVO MESE DI NASCITA ->\t"));
                            anno = Integer.parseInt(leggiInputConAnnullamento(in, "INSERISCI IL NUOVO ANNO DI NASCITA ->\t"));
                            
                            // Verifica se l'utente è maggiorenne
                            if (!DataDiNascita.etaValida(giorno, mese, anno) && utente instanceof Cliente ) {
                                System.out.println("Devi avere almeno 14 anni per registrarti!");
                                continue;
                            }
    
                            if (!DataDiNascita.maggiorenne(giorno, mese, anno) && utente instanceof Ristoratore ) {
                                System.out.println("Devi avere almeno 18 anni per registrarti!");
                                continue;
                            }
                            
                            utente.setDataDiNascita(giorno, mese, anno);
                            dataValida = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci un numero valido!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Data non valida: " + e.getMessage());
                        }
                    } while(!dataValida);
                    System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                    return true;
                }
                case 6:{
                    System.out.print("\nINSERISCI IL NUOVO INDIRIZZO [Via, Città] ->\t");
                    String indirizzoInput = leggiInputConAnnullamento(in, "\nINSERISCI IL NUOVO INDIRIZZO [Via, Città] ->\t");
                    String domicilio = Indirizzo.getSelezionaIndirizzo(indirizzoInput);
                    utente.setDomicilio(domicilio);
                    System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                    return true;
                }
                case 7:{
                    String ruolo;
                    do {
                        ruolo = leggiInputConAnnullamento(in, "\nINSERISCI IL NUOVO RUOLO [Cliente/Ristoratore] ->\t").trim();
                        if(ruolo.length() < 2) {
                            System.out.println("Il ruolo deve contenere almeno 2 caratteri");
                        }
                    } while(ruolo.length() < 2);
                    if (ruolo.startsWith("c")) {
                            utente.setRuolo("Cliente");
                    }
                    if (ruolo.toLowerCase().startsWith("r")) {
                        utente.setRuolo("Ristoratore");
                }
                    System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                    return true;
                }
                case 8:{
                    return false;
                }
            }
            return false;
        } catch (InputAnnullatoException e) {
            System.out.println("Operazione annullata.");
            return false;
        }
    }

    private static void CaricaListe(ArrayList<Cliente> cl , ArrayList<Ristoratore> rs){
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

    private static void SalvaPreferitiClienti(ArrayList<Cliente> cl, BufferedWriter bw) throws IOException {
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
    }

    private static void SalvaRistorantiRistoratori(ArrayList<Ristoratore> ris, BufferedWriter bw) throws IOException {
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
    }
    
    private static void Esci(ArrayList<Cliente> cl, ArrayList<Ristoratore> ris, GestoreRistoranti risto) {
        risto.scriviSuFile();  // Salva i ristoranti su file (non utente)
    
        for (Cliente c : cl) {
            if(c.getPreferiti().size() == 0)
                c.CaricaListaPreferiti(c.getUsername(), risto);
        }
        for (Ristoratore r : ris) {
            if(r.getListaRistoranti().size() == 0)
                r.CaricaListaRistoranti(r.getUsername(), risto);
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePathUtenti))) {
            // intestazione
            bw.write("Username;Nome;Cognome;Password;DataDiNascita;Domicilio;Ruolo;Preferiti;Ristoranti");
            bw.newLine();
    
            // Scrivi separatamente preferiti e ristoranti
            SalvaPreferitiClienti(cl, bw);
            SalvaRistorantiRistoratori(ris, bw);
    
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file degli utenti: " + e.getMessage());
        }
    }

    private static void pulisciTerminale() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static boolean usernameEsiste(String username, List<Cliente> clienti, List<Ristoratore> ristoratori) {
        for (Cliente c : clienti) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        for (Ristoratore r : ristoratori) {
            if (r.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private static Ristorante GetSelezioneRistorante(ArrayList<Ristorante> risultati) {
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

    private static Recensione GetSelezioneRecensione(ArrayList<Recensione> risultati) {
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
                scelta = Integer.parseInt(in.nextLine().trim());
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