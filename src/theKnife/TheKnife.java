/*
javac -cp "lib/*" -d bin $(find src -name "*.java")
java -cp "bin:lib/*" src.Main 
 */

package src.theKnife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.Cliente;
import src.models.DataDiNascita;
import src.models.GestoreRistoranti;
import src.models.Indirizzo;
import src.models.InputAnnullatoException;
import src.models.Recensione;
import src.models.Ristorante;
import src.models.Ristoratore;
import src.models.Utente;
import src.models.UtenteNonRegistrato;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;


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
 * Descrizione: Classe principale dell'applicazione "TheKnife". Contiene il metodo main e i percorsi ai file CSV.
 * 
 * @author Semenzato Francesco 
 * @author Barlera Marco
 */
/**
 * Classe principale dell'applicazione TheKnife.
 */
public class TheKnife {
    /**
     * Percorso al file CSV degli utenti.
     */
    public static final String FilePathUtenti="data/ListaUtenti.csv";
    /**
     * Scanner per leggere input da tastiera.
     */
    public static final Scanner in = new Scanner(System.in);
    /**
     * Metodo principale che avvia l'applicazione.
     * @param args Argomenti da linea di comando.
     */
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
                        System.out.println("Inserisci un numero valido.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.\n");
                }
            } while (true);
            switch (selezioneInt) {
                case 1:{ // CLIENTI O RISTORATORI
                    pulisciTerminale();
                    System.out.println("PER TORNARE INDIETRO INSERIRE '#'");
                    System.out.println("POSSIEDI GIA' UN ACCOUNT? [SI/NO]");
                    do {
                        System.out.print("\nRISPOSTA ->\t");
                        selezioneStringa = in.nextLine().trim();
                        if (!selezioneStringa.toLowerCase().equals("si") && !selezioneStringa.toLowerCase().equals("no")) {
                            System.out.println("Inserisci una risposta valida.");
                        }
                    } while (!selezioneStringa.toLowerCase().equals("si") && !selezioneStringa.toLowerCase().equals("no"));
                    pulisciTerminale();
                    if (selezioneStringa.toLowerCase().charAt(0) == 's' && selezioneStringa.length() == 2) { // Form ACCEDI
                        cl = new Cliente();
                        ris = new Ristoratore();
                        ruolo = "";
                        datiCorretti = false;
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
                    
                    }
                    if (selezioneStringa.toLowerCase().charAt(0) == '#') {
                        break;
                    }
                    if(selezioneStringa.toLowerCase().charAt(0) == 'n' && selezioneStringa.length() == 2) {
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
                                        continuaRegistrazione = false;
                                        break;
                                    }
                                } while(selezioneInt < 1 || selezioneInt > 4);
                            } while(false);
                            if(!continuaRegistrazione) {
                                break;
                            }
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
                                            if (username.length() < 3) {
                                                System.out.println("L'username deve contenere almeno 3 caratteri.");
                                            }
                                        } while (usernameEsiste(username, ListaClienti, ListaRistoratori) || username.length() < 3);
                                        
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

                                                if (!datadinascita.matches("^\\d{1,2}[/-]\\d{1,2}[/-]\\d{4}$")) {
                                                    throw new IllegalArgumentException("Formato data non valido. Usare gg/mm/aaaa o gg-mm-aaaa");
                                                }

                                                // Normalizza la data rimuovendo zeri iniziali da giorno e mese
                                                String[] parts = datadinascita.split("[/-]");
                                                int giorno = Integer.parseInt(parts[0]);
                                                int mese = Integer.parseInt(parts[1]);
                                                int anno = Integer.parseInt(parts[2]);
                                                datadinascita = String.format("%d-%d-%04d", giorno, mese, anno);

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
                                            else if (username.length() < 3) {
                                                System.out.println("L'username deve contenere almeno 3 caratteri.");
                                            }
                                        } while (usernameEsiste(username, ListaClienti, ListaRistoratori) || username.length() < 3);
                                        
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
                                                
                                                if (!datadinascita.matches("^\\d{1,2}[/-]\\d{1,2}[/-]\\d{4}$")) {
                                                    throw new IllegalArgumentException("Formato data non valido. Usare gg/mm/aaaa o gg-mm-aaaa");
                                                }

                                                // Normalizza la data rimuovendo zeri iniziali da giorno e mese
                                                String[] parts = datadinascita.split("[/-]");
                                                int giorno = Integer.parseInt(parts[0]);
                                                int mese = Integer.parseInt(parts[1]);
                                                int anno = Integer.parseInt(parts[2]);
                                                datadinascita = String.format("%d-%d-%04d", giorno, mese, anno);
                                                
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
                                    case 1:{
                                        boolean modificaUtente = true;
                                        while (modificaUtente) {
                                            modificaUtente = modificaUtente(cl, ListaClienti, ListaRistoratori);
                                        }
                                        if(cl.getRuolo().equals("Ristoratore")){
                                            continuaCliente = false;
                                        }
                                        System.out.println("\nL'ACCOUNT È STATO MODIFICATO CORRETTAMENTE.");
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        int distanza = 15;
                                        ArrayList<Ristorante> vicini = gestoreRistoranti.filtraPerVicinoA(cl.getDomicilio(), distanza);

                                        //pulisciTerminale();
                                        if(vicini.isEmpty()){
                                            System.out.println(cl.getDomicilio());
                                            System.out.println("Non hai ristoranti vicini nell'arco di " + distanza + "km");
                                        }
                                        else{
                                            Ristorante ristoranteSelezionato = GetSelezioneRistorante(vicini);
                                            if(ristoranteSelezionato != null){
                                                System.out.println("\n" + ristoranteSelezionato.visualizzaRistorante());
                                            }
                                            else{
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
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
                                                System.out.println("Inserisci '#' in qualsiasi momento per annullare\n");
                                                do {
                                                    System.out.print("SELEZIONE ->\t");
                                                    try {
                                                        selezioneInt = Integer.parseInt(leggiInputConAnnullamento(in, ""));
                                                    } catch (NumberFormatException e) {
                                                        selezioneInt = -1;
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        continuaFiltro = false;
                                                        break;
                                                    }
                                                } while(selezioneInt < 1 || selezioneInt > 9);
                                            }while(false); 
                                            pulisciTerminale();
                                            switch (selezioneInt) {
                                                case 1:{
                                                    String citta;
                                                    try {
                                                        citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerCitta(citta);
                                                    if (filtrati.isEmpty()) {
                                                        System.out.println("\nNessun ristorante trovato."); 
                                                    }
                                                    else {
                                                        Ristorante ristoranteSelezionato = GetSelezioneRistorante(filtrati);
                                                        if(ristoranteSelezionato != null){
                                                            System.out.println(ristoranteSelezionato.visualizzaRistorante());
                                                        }
                                                        else{
                                                            System.out.println("\nNessun ristorante selezionato.");
                                                        }
                                                    }
                                                    System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 2:{
                                                    String nomeRistorante="";
                                                    do {
                                                        try {
                                                            nomeRistorante = leggiInputConAnnullamento(in, "INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE -> ");
                                                        } catch (InputAnnullatoException e) {
                                                            System.out.println("OPERAZIONE ANNULLATA.");
                                                            break;
                                                        }
                                                        if(nomeRistorante.length() < 2) {
                                                            System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                        }
                                                    } while(nomeRistorante.length() < 2);
                                    
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                                    if (filtrati.isEmpty()) {
                                                        System.out.println("\nNessun ristorante trovato."); 
                                                    }
                                                    else {
                                                        Ristorante ristoranteSelezionato = GetSelezioneRistorante(filtrati);
                                                        if(ristoranteSelezionato != null){
                                                            System.out.println(ristoranteSelezionato.visualizzaRistorante());
                                                        }
                                                        else{
                                                            System.out.println("\nNessun ristorante selezionato.");
                                                        }
                                                    }
                                                    System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 3:{
                                                    //Visualizza i ristoranti in base al tipo di cucina
                                                    String tipoCucina;
                                                    try {
                                                        tipoCucina = leggiInputConAnnullamento(in, "INSERISCI IL TIPO DI CUCINA CHE DESIDERI CERCARE ->");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                    
                                                    String citta;
                                                    try {
                                                        citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA ->");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                    for(Ristorante r : gestoreRistoranti.filtraPerTipoDiCucina(tipoCucina, citta)){
                                                        System.out.println(r.visualizzaRistorante());
                                                    }
                                    
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE INVIO PER CONTINUARE");
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
                                                            String inputPrezzo1 = leggiInputConAnnullamento(in, "INSERISCI UNA FASCIA DI PREZZO CHE DESIDERI CERCARE -> ");
                                                            int prezzo1 = Integer.parseInt(inputPrezzo1);
                                    
                                                            String inputPrezzo2 = leggiInputConAnnullamento(in, "INSERISCI UNA SECONDA FASCIA DI PREZZO O UN SEGNO DI COMPARAZIONE -> ");
                                    
                                                            String citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ").toLowerCase();
                                    
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
                                                        } catch (InputAnnullatoException e) {
                                                            System.out.println("OPERAZIONE ANNULLATA.");
                                                            break;
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
                                                    String citta;
                                                    try {
                                                        citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA ->");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerDelivery(citta);
                                                    if (filtrati.isEmpty()) {
                                                        System.out.println("\nNessun ristorante trovato."); 
                                                    }
                                                    else {
                                                        Ristorante r = GetSelezioneRistorante(filtrati);
                                                        if(r != null){
                                                            System.out.println(r.visualizzaRistorante());
                                                        }
                                                        else{
                                                            System.out.println("\nNessun ristorante selezionato."); 
                                                        }
                                                    }
                                    
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE INVIO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 6:{
                                                    //Visualizza i ristoranti in base alla disponibilita' di prenotazione online
                                                    String citta;
                                                    try {
                                                        citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA ->");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerPrenotazioneOnline(citta);
                                                    if (filtrati.isEmpty()) {
                                                        System.out.println("\nNessun ristorante trovato.");
                                                    }
                                                    else {
                                                        Ristorante r = GetSelezioneRistorante(filtrati);
                                                        if(r != null){
                                                            System.out.println(r.visualizzaRistorante());
                                                        }
                                                        else{
                                                            System.out.println("\nNessun ristorante selezionato."); 
                                                        }
                                                    }
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE INVIO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 7:{
                                                    //Visualizza i ristoranti in base alla media delle stelle
                                                    String citta;
                                                    try {
                                                        citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA ->");
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                    float stelle = 0;
                                                    do {
                                                        try {
                                                            stelle = Float.parseFloat(leggiInputConAnnullamento(in, "INSERISCI LA MEDIA DI STELLE [0-5]->"));
                                                        } catch (InputAnnullatoException e) {
                                                            System.out.println("OPERAZIONE ANNULLATA.");
                                                            break;
                                                        }
                                                    }while(stelle<0 || stelle>5);
                                                    ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerMediaStelle(stelle,citta); 
                                                    if (filtrati.isEmpty()) {
                                                        System.out.println("\nNessun ristorante trovato.");
                                                    }
                                                    else {
                                                        Ristorante r = GetSelezioneRistorante(filtrati);
                                                        if(r != null){
                                                            System.out.println(r.visualizzaRistorante());
                                                        }
                                                        else{
                                                            System.out.println("\nNessun ristorante selezionato."); 
                                                        }
                                                    }
                                                    System.out.println("\n\n");
                                                    System.out.println("PREMERE INVIO PER CONTINUARE");
                                                    in.nextLine().trim();
                                                    pulisciTerminale();
                                                    break;
                                                }
                                                case 8: {
                                                    ArrayList<Ristorante> risultati = new ArrayList<>();
                                                    boolean continuaUnione = true;
                                                    String cittaGlobal = null;
                                                
                                                    while (continuaUnione) {
                                                        System.out.println("AGGIUNGI UN FILTRO:\n");
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
                                                            System.out.print("\nSELEZIONE ->\t");
                                                            try {
                                                                filtro = Integer.parseInt(leggiInputConAnnullamento(in, ""));
                                                            } catch (NumberFormatException e) {
                                                                filtro = -1;
                                                            } catch (InputAnnullatoException e) {
                                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                                continuaUnione = false;
                                                                break;
                                                            }
                                                        } while(filtro < 1 || filtro > 8);
                                                
                                                        switch (filtro) {
                                                            case 1: {
                                                                try {
                                                                    cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                } catch (InputAnnullatoException e) {
                                                                    System.out.println("OPERAZIONE ANNULLATA.");
                                                                    break;
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerCitta(cittaGlobal));
                                                                break;
                                                            }
                                                            case 2: {
                                                                String nomeRistorante="";
                                                                do {
                                                                    try {
                                                                        nomeRistorante = leggiInputConAnnullamento(in, "INSERISCI IL NOME DEL RISTORANTE CHE DESIDERI CERCARE -> ");
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
                                                                    if(nomeRistorante.length() < 2) {
                                                                        System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                                    }
                                                                } while(nomeRistorante.length() < 2);
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante));
                                                                break;
                                                            }
                                                            case 3: {
                                                                String tipoCucina;
                                                                try {
                                                                    tipoCucina = leggiInputConAnnullamento(in, "INSERISCI IL TIPO DI CUCINA CHE DESIDERI CERCARE ->");
                                                                } catch (InputAnnullatoException e) {
                                                                    System.out.println("OPERAZIONE ANNULLATA.");
                                                                    break;
                                                                }
                                                                if (cittaGlobal == null) {
                                                                    try {
                                                                        cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerTipoDiCucina(tipoCucina, cittaGlobal));
                                                                break;
                                                            }
                                                            case 4: {
                                                                // Visualizza i ristoranti in base alla fascia di prezzo
                                                                boolean continuaPrezzo = true;
                                                                System.out.println("INSERISCI FASCIA DI PREZZO");
                                                                System.out.println("PUOI CERCARE INSERENDO UN RANGE DI PREZZO OPPURE UN VALORE E UN SEGNO DI COMPARAZIONE [<, >, =] ->");
                                                            
                                                                while(continuaPrezzo){
                                                                    try {
                                                                        // Leggi il primo valore di prezzo
                                                                        System.out.print("PRIMO VALORE -> ");
                                                                        int prezzo1 = Integer.parseInt(in.nextLine().trim());
                                                            
                                                                        // Leggi il secondo valore o l'operatore di comparazione
                                                                        System.out.print("SECONDO VALORE O OPERATORE [<, >, =] -> ");
                                                                        String input = in.nextLine().trim();
                                                            
                                                                        // Leggi la città se non è già stata impostata
                                                                        if (cittaGlobal == null) {
                                                                            try {
                                                                                cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                            } catch (InputAnnullatoException e) {
                                                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                                                break;
                                                                            }
                                                                        }
                                                            
                                                                        // Controlla l'operatore e applica i filtri
                                                                        if (input.equals("<") || input.equals(">") || input.equals("=")) {
                                                                            // Filtra per fascia di prezzo con operatore
                                                                            risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerFasciaDiPrezzo(prezzo1, input.charAt(0), cittaGlobal));
                                                                            continuaPrezzo = false;
                                                                        } else {
                                                                            // Filtra per un range di prezzo
                                                                            int prezzo2 = Integer.parseInt(input);
                                                                            int min = Math.min(prezzo1, prezzo2);
                                                                            int max = Math.max(prezzo1, prezzo2);
                                                                            risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerFasciaDiPrezzo(min, max, cittaGlobal));
                                                                            continuaPrezzo = false;
                                                                        }
                                                            
                                                                    } catch (Exception e) {
                                                                        System.out.println("ERRORE: Valori di prezzo non validi.");
                                                                    }
                                                                }
                                                            
                                                                // Mostra i risultati o un messaggio di errore
                                                                if(risultati.isEmpty()) {
                                                                    System.out.println("NESSUN RISTORANTE TROVATO CON I FILTRI INSERITI.");
                                                                } 
                                                                else {
                                                                    Ristorante r = GetSelezioneRistorante(risultati);
                                                                    if(r != null){
                                                                        System.out.println(r.visualizzaRistorante());
                                                                    } else {
                                                                        System.out.println("\nNessun ristorante selezionato.");
                                                                    }
                                                                }
                                                            
                                                                // Attendi che l'utente prema INVIO per continuare
                                                                System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                                                in.nextLine().trim();
                                                                break;
                                                            }
                                                            case 5: {
                                                                if (cittaGlobal == null) {
                                                                    try {
                                                                        cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerDelivery(cittaGlobal));
                                                                break;
                                                            }
                                                            case 6: {
                                                                if (cittaGlobal == null) {
                                                                    try {
                                                                        cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
                                                                }
                                                                risultati = gestoreRistoranti.unisciListe(risultati, gestoreRistoranti.filtraPerPrenotazioneOnline(cittaGlobal));
                                                                break;
                                                            }
                                                            case 7: {
                                                                if (cittaGlobal == null) {
                                                                    try {
                                                                        cittaGlobal = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
                                                                }
                                                                float stelle = 0;
                                                                do {
                                                                    try {
                                                                        stelle = Float.parseFloat(leggiInputConAnnullamento(in, "INSERISCI MEDIA STELLE [0-5] -> "));
                                                                    } catch (InputAnnullatoException e) {
                                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                                        break;
                                                                    }
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
                                                                Ristorante r = GetSelezioneRistorante(risultati);
                                                                if(r != null){
                                                                    System.out.println(r.visualizzaRistorante());
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
                                        System.out.println("3- TORNA INDIETRO\n");
                                        do {
                                            System.out.print("SELEZIONE ->\t");
                                            try {
                                                selezioneInt = Integer.parseInt(in.nextLine().trim());
                                            } catch (NumberFormatException e) {
                                                selezioneInt = -1;
                                            }
                                        } while(selezioneInt < 1 || selezioneInt > 3);
                                        pulisciTerminale();
                                        switch(selezioneInt){
                                            case 1:{
                                                //Aggiungi un ristorante alla lista dei preferiti
                                                System.out.print("INSERISCI IL NOME DEL RISTORANTE -> ");
                                                String nomeRistorante = in.nextLine().trim();
                                                ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                                if (risultati.isEmpty()) {
                                                    System.out.println("NESSUN RISTORANTE TROVATO CON IL NOME INSERITO.");
                                                }
                                                else {
                                                    Ristorante r = GetSelezioneRistorante(risultati);
                                                    if(r == null) {
                                                        System.out.println("NESSUN RISTORANTE VERRA' AGGIUNTO AI PREFERITI.");
                                                    } else {
                                                        System.out.println(cl.AggiungiAiPreferiti(r));
                                                    }
                                                }

                                                System.out.println("\n\n");
                                                System.out.println("\nPREMERE INVIO PER CONTINUARE");
                                                in.nextLine().trim();
                                                pulisciTerminale();
                                                break;
                                            }
                                            case 2:{
                                                //Elimina un ristorante alla lista dei preferiti
                                                if (!cl.getPreferiti().isEmpty()) {
                                                    Ristorante r = GetSelezioneRistorante(cl.getPreferiti());
                                                    if(r == null) {
                                                        System.out.println("NESSUN RISTORANTE VERRA' ELIMINATO DAI PREFERITI.");
                                                    } else {
                                                        cl.RimuoviPreferiti(r);
                                                        System.out.println("RISTORANTE ELIMINATO DAI PREFERITI.");
                                                    }   
                                                }
                                                else{
                                                    System.out.println("NESSUN RISTORANTE NELLA LISTA DEI TUOI PREFERITI.");
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
                                        break;
                                    }
                                    case 6:{
                                        // Scrivi una recensione
                                        String nomeRistorante;
                                        try {
                                            nomeRistorante = leggiInputConAnnullamento(in, "INSERISCI IL NOME DEL RISTORANTE PER CUI VUOI SCRIVERE UNA RECENSIONE -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        if (risultati.isEmpty()) {
                                            System.out.println("NESSUN RISTORANTE TROVATO CON IL NOME INSERITO.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(risultati);
                                    
                                            if (r == null) {
                                                System.out.println("NESSUN RISTORANTE VERRÀ SELEZIONATO.");
                                            } else {
                                                String testoRecensione;
                                                try {
                                                    testoRecensione = leggiInputConAnnullamento(in, "\nINSERISCI IL TESTO DELLA RECENSIONE -> ");
                                                } catch (InputAnnullatoException e) {
                                                    System.out.println("OPERAZIONE ANNULLATA.");
                                                    break;
                                                }
                                        
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
                                            String testoRecensione;
                                            try {
                                                testoRecensione = leggiInputConAnnullamento(in, "INSERISCI IL TESTO DELLA RECENSIONE -> ");
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            }
                                    
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

                                       if (rec == null) {
                                           System.out.println("NESSUNA RECENSIONE VERRA' ELIMINATA.");
                                       }
                                       else {
                                            cl.RemoveRecensione(cl.getListaRecensioni().indexOf(rec), gestoreRistoranti.getRistorante(rec.getNomeRistorante()));
                                            System.out.println("LA RECENSIONE È STATA ELIMINATA CORRETTAMENTE.");
                                       }

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
                                        System.out.print("\nSELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(in.nextLine().trim());
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 10);
                                }while(false); 
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{ //Modifica Acount
                                        boolean modificaUtente = true;
                                        while (modificaUtente) {
                                            modificaUtente = modificaUtente(ris, ListaClienti, ListaRistoratori);
                                        }
                                        if(ris.getRuolo().equals("Cliente")){
                                            continuaRistoratore = false;
                                        }

                                        System.out.println("\nL'ACCOUNT È STATO MODIFICATO CORRETTAMENTE.");
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{ //Visualizza i tuoi ristoranti
                                        if(ris.getListaRistoranti().isEmpty()) {
                                            System.out.println("NESSUN RISTORANTE ASSOCIATO AL TUO ACCOUNT.");
                                        } else {
                                            System.out.println("I TUOI RISTORANTI SONO:\n");
                                            for(Ristorante r : ris.getListaRistoranti()){
                                                System.out.println(r.visualizzaRistorante());
                                            }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3:{ // Aggiungi un ristorante
                                        String Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo = "";
                                        double Latitudine = 0, Longitudine = 0;
                                        int FasciaDiPrezzo = 0;
                                        boolean Delivery = false, PrenotazioneOnline = false;
                                    
                                        // Input nome
                                        try {
                                            Nome = leggiInputConAnnullamento(in, "INSERISCI IL NOME DEL RISTORANTE -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input nazione
                                        try {
                                            Nazione = leggiInputConAnnullamento(in, "INSERISCI LA NAZIONE -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input città
                                        try {
                                            Citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input indirizzo
                                        try {
                                            Indirizzo = leggiInputConAnnullamento(in, "INSERISCI L'INDIRIZZO -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input tipo di cucina
                                        try {
                                            TipoDiCucina = leggiInputConAnnullamento(in, "INSERISCI IL TIPO DI CUCINA -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input servizi
                                        try {
                                            Servizi = leggiInputConAnnullamento(in, "INSERISCI I SERVIZI OFFERTI -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input URL del sito web
                                        try {
                                            URLWeb = leggiInputConAnnullamento(in, "INSERISCI L'URL DEL SITO WEB -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Input prezzo 
                                        while (true) {
                                            try {
                                                String input = leggiInputConAnnullamento(in, "PREZZO (solo numero): ");
                                                int temp = Integer.parseInt(input.trim());
                                                if (temp >= 0) {
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
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
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
                                            try {
                                                String latInput = leggiInputConAnnullamento(in, "Latitudine: ");
                                                Latitudine = Double.parseDouble(latInput.trim());
                                                String lonInput = leggiInputConAnnullamento(in, "Longitudine: ");
                                                Longitudine = Double.parseDouble(lonInput.trim());
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            }
                                        }
                                    
                                        // Input stelle
                                        String result = "";
                                        while (true) {
                                            try {
                                                String stelleInput = leggiInputConAnnullamento(in, "Numero di stelle (0-5): ");
                                                int stelle = Integer.parseInt(stelleInput.trim());
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
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci un numero valido.");
                                            }
                                        }
                                    
                                        // Input delivery
                                        while (true) {
                                            try {
                                                String input = leggiInputConAnnullamento(in, "Offre servizio di delivery? (s/n): ").toLowerCase();
                                                if (input.startsWith("s")) {
                                                    Delivery = true;
                                                    break;
                                                } else if (input.startsWith("n")) {
                                                    Delivery = false;
                                                    break;
                                                } else {
                                                    System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                                                }
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            }
                                        }
                                    
                                        // Input prenotazione online
                                        while (true) {
                                            try {
                                                String input = leggiInputConAnnullamento(in, "Permette la prenotazione online? (s/n): ").toLowerCase();
                                                if (input.startsWith("s")) {
                                                    PrenotazioneOnline = true;
                                                    break;
                                                } else if (input.startsWith("n")) {
                                                    PrenotazioneOnline = false;
                                                    break;
                                                } else {
                                                    System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                                                }
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            }
                                        }

                                        // Input prezzo
                                        try {
                                            String fasciaInput = leggiInputConAnnullamento(in, "Fascia di prezzo (numero intero): ");
                                            FasciaDiPrezzo = Integer.parseInt(fasciaInput.trim());
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }
                                    
                                        // Chiamata al metodo per aggiungere il ristorante
                                        gestoreRistoranti.AggiungiRistorante(ris.AggiungiRistorante(Nome, Nazione, Citta, Indirizzo, TipoDiCucina, Servizi, URLWeb, Prezzo, Latitudine, Longitudine, FasciaDiPrezzo, result, Delivery, PrenotazioneOnline));
                                        System.out.println("RISTORANTE AGGIUNTO CORRETTAMENTE");


                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    
                                    case 4:{ // MODIFICA RISTORANTE
                                        ArrayList<Ristorante> list = ris.getListaRistoranti();
                                        if (list.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da modificare.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(list);
                                            if (r == null) {
                                                System.out.println("Nessun ristorante selezionato.");
                                            }
                                            else {
                                                boolean modificaRistorante = true;
                                                while (modificaRistorante) {
                                                    modificaRistorante = modificaRistorante(r);
                                                    System.out.println("RISTORANTE MODIFICATO CORRETTAMENTE");
                                                }
                                            }
                                        }

                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 5:{ // ELIMINA RISTORANTE
                                        ArrayList<Ristorante> list = ris.getListaRistoranti();
                                        if (list.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da eliminare.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(list);
                                            if (r == null) {
                                                System.out.println("Nessun ristorante selezionato.");
                                            }
                                            else {
                                                ris.RimuoviRistorante(r);
                                                gestoreRistoranti.RimuoviRistorante(r);
                                                System.out.println("RISTORANTE RIMOSSO CORRETTAMENTE");
                                            }
                                        }
                                        System.out.println("RISTORANTE RIMOSSO CORRETTAMENTE");
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 6:{ // Visualizza Recensioni ristorante
                                        ArrayList<Ristorante> listaRistoranti = ris.getListaRistoranti();
                                        if (listaRistoranti.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da visualizzare.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(listaRistoranti);
                                            r.VisualizzaRecensioni();
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 7:{ // Aggiungi Risposta
                                        ArrayList<Ristorante> listaRistoranti = ris.getListaRistoranti();
                                        if (listaRistoranti.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da visualizzare.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(listaRistoranti);
                                            ArrayList<Recensione> recensioni = r.getRecensioni();
                                            if (recensioni.isEmpty()) {
                                                System.out.println("Non ci sono recensioni da visualizzare.");
                                            }
                                            else {
                                                Recensione rec = GetSelezioneRecensione(recensioni);
                                                if(rec != null && rec.getRisposta().equals("")) {
                                                    try {
                                                        String risposta = leggiInputConAnnullamento(in, "\nINSERISCI LA RISPOSTA ALLA RECENSIONE ->\t");
                                                        rec.setRisposta(risposta.trim());
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                }
                                                else
                                                    System.out.println("LA RECENSIONE HA GIA' UNA RISPOSTA");
                                            }
                                        }

                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 8:{ // Modifica Una risposta
                                        ArrayList<Ristorante> listaRistoranti = ris.getListaRistoranti();
                                        if (listaRistoranti.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da visualizzare.");
                                        }
                                        else {
                                            Ristorante r = GetSelezioneRistorante(listaRistoranti);
                                            ArrayList<Recensione> recensioni = r.getRecensioni();
                                            if (recensioni.isEmpty()) {
                                                System.out.println("Non ci sono recensioni da visualizzare.");
                                            }
                                            else {
                                                Recensione rec = GetSelezioneRecensione(recensioni);
                                                if(rec != null && !rec.getRisposta().equals("")) {
                                                    try {
                                                        String nuovaRisposta = leggiInputConAnnullamento(in, "INSERISCI LA NUOVA RISPOSTA ALLA RECENSIONE ->\t");
                                                        rec.setRisposta(nuovaRisposta.trim());
                                                    } catch (InputAnnullatoException e) {
                                                        System.out.println("OPERAZIONE ANNULLATA.");
                                                        break;
                                                    }
                                                }
                                                else
                                                    System.out.println("LA RECENSIONE NON HA UNA RISPOSTA DA MODIFICARE");
                                                    }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 9:{ // Rimuovi Una risposta
                                        ArrayList<Ristorante> listaRistoranti = ris.getListaRistoranti();
                                        if (listaRistoranti.isEmpty()) {
                                            System.out.println("Non ci sono ristoranti da visualizzare.");
                                        }
                                        else {
                                            Ristorante r;
                                            try {
                                                r = GetSelezioneRistorante(listaRistoranti);
                                            } catch (InputAnnullatoException e) {
                                                System.out.println("OPERAZIONE ANNULLATA.");
                                                break;
                                            }
                                            ArrayList<Recensione> recensioni = r.getRecensioni();
                                            if (recensioni.isEmpty()) {
                                                System.out.println("Non ci sono recensioni da visualizzare.");
                                            }
                                            else {
                                                Recensione rec;
                                                try {
                                                    rec = GetSelezioneRecensione(recensioni);
                                                } catch (InputAnnullatoException e) {
                                                    System.out.println("OPERAZIONE ANNULLATA.");
                                                    break;
                                                }
                                                if(rec != null && !rec.getRisposta().equals("")) {
                                                    rec.EliminaRisposta();;
                                                }
                                                else
                                                    System.out.println("LA RECENSIONE NON HA UNA RISPOSTA DA ELIMINARE");
                                            }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
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
                                    System.out.println("Inserisci '#' in qualsiasi momento per annullare\n");
                                    do {
                                        System.out.print("SELEZIONE ->\t");
                                        try {
                                            selezioneInt = Integer.parseInt(leggiInputConAnnullamento(in, ""));
                                        } catch (NumberFormatException e) {
                                            selezioneInt = -1;
                                        } catch (InputAnnullatoException e) {
                                            continuaGuest = false;
                                            break;
                                        }
                                    } while(selezioneInt < 1 || selezioneInt > 4);
                                }while(false);
                                if (continuaGuest == false) {
                                    break;
                                }
                                pulisciTerminale();
                                switch (selezioneInt) {
                                    case 1:{
                                        String citta;
                                        try {
                                            citta = leggiInputConAnnullamento(in, "INSERISCI LA CITTA' DI RICERCA -> ");
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }

                                        ArrayList<Ristorante> filtrati = gestoreRistoranti.filtraPerCitta(citta);
                                        if (filtrati.isEmpty()) {
                                            System.out.println("\nNessun ristorante trovato."); 
                                        } else {
                                            Ristorante ristoranteSelezionato = GetSelezioneRistorante(filtrati);
                                            if(ristoranteSelezionato != null){
                                                System.out.println(ristoranteSelezionato.visualizzaRistorante());
                                            } else {
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }

                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 2:{
                                        String nomeRistorante;
                                        try {
                                            nomeRistorante = leggiInputConAnnullamento(in, "INSERISCI IL NOME PER CERCARE IL RISTORANTE -> ");
                                            if(nomeRistorante.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                break;
                                            }
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }

                                        ArrayList<Ristorante> risultati = gestoreRistoranti.filtraPerNomeRistorante(nomeRistorante);
                                        if (risultati.isEmpty()) {
                                            System.out.println("Nessun ristorante trovato con quel nome.");
                                        } else {
                                            Ristorante r = GetSelezioneRistorante(risultati);
                                            if(r != null){
                                                System.out.println(r.visualizzaRistorante());
                                            }
                                            else{
                                                System.out.println("\nNessun ristorante selezionato.");
                                            }
                                        }
                                        System.out.println("\n\nPREMERE INVIO PER CONTINUARE");
                                        in.nextLine().trim();
                                        pulisciTerminale();
                                        break;
                                    }
                                    case 3:{
                                        UtenteNonRegistrato guest = new UtenteNonRegistrato();
                                        String nomeRistorante;
                                        try {
                                            nomeRistorante = leggiInputConAnnullamento(in, "INSERISCI IL NOME DEL RISTORANTE -> ");
                                            if(nomeRistorante.length() < 2) {
                                                System.out.println("Il nome deve contenere almeno 2 caratteri");
                                                break;
                                            }
                                        } catch (InputAnnullatoException e) {
                                            System.out.println("OPERAZIONE ANNULLATA.");
                                            break;
                                        }

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
                                                    System.out.println("\n" + r.visualizzaRecensione());
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
    
    private static boolean modificaRistorante(Ristorante ristorante){
        pulisciTerminale();
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
            System.out.println("10- Torna al menu precedente\n");
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
                    try {
                        String nuovoNome = leggiInputConAnnullamento(in, "INSERISCI IL NUOVO NOME ->\t").trim();
                        if (nuovoNome.length() < 2) {
                            System.out.println("Il nome deve contenere almeno 2 caratteri.");
                            break;
                        }
                        ristorante.setNome(nuovoNome);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 2: {
                    try {
                        String nuovoIndirizzo = leggiInputConAnnullamento(in, "\nINSERISCI IL NUOVO INDIRIZZO [Via, Città, Nazione] ->\t").trim();
                        if (nuovoIndirizzo.length() < 5) {
                            System.out.println("L'indirizzo inserito è troppo corto.");
                            break;
                        }
                        String indirizzoCompleto = Indirizzo.getSelezionaIndirizzo(nuovoIndirizzo);
                        ristorante.setIndirizzo(indirizzoCompleto);

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
                                System.out.println("\nCoordinate aggiornate: LAT=" + lat + " LNG=" + lng + "\n");
                            } else {
                                System.out.println("Impossibile trovare le coordinate per l'indirizzo inserito.");
                                double lat = Double.parseDouble(leggiInputConAnnullamento(in, "Latitudine: ").trim());
                                double lng = Double.parseDouble(leggiInputConAnnullamento(in, "Longitudine: ").trim());
                                ristorante.setLatitudine(lat);
                                ristorante.setLongitudine(lng);
                            }
                        } catch (Exception e) {
                            System.out.println("Errore durante il geocoding: " + e.getMessage());
                            double lat = Double.parseDouble(leggiInputConAnnullamento(in, "Latitudine: ").trim());
                            double lng = Double.parseDouble(leggiInputConAnnullamento(in, "Longitudine: ").trim());
                            ristorante.setLatitudine(lat);
                            ristorante.setLongitudine(lng);
                        }

                        System.out.println("ACCOUNT MODIFICATO CORRETTAMENTE");
                            return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                }
                case 3:
                    try {
                        String nuovoTipoDiCucina = leggiInputConAnnullamento(in, "INSERISCI IL TIPO DI CUCINA ->\t").trim();
                        if (nuovoTipoDiCucina.length() < 2) {
                            System.out.println("Il tipo di cucina deve contenere almeno 2 caratteri.");
                            break;
                        }
                        ristorante.setTipoDiCucina(nuovoTipoDiCucina);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 4:
                    try {
                        String nuovoServizi = leggiInputConAnnullamento(in, "INSERISCI I SERVIZI ->\t").trim();
                        if (nuovoServizi.length() < 2) {
                            System.out.println("I servizi devono contenere almeno 2 caratteri.");
                            break;
                        }
                        ristorante.setServizi(nuovoServizi);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 5:
                    try {
                        String nuovoURLWeb = leggiInputConAnnullamento(in, "INSERISCI L'URL WEB ->\t").trim();
                        if (nuovoURLWeb.length() < 5) {
                            System.out.println("L'URL inserito è troppo corto.");
                            break;
                        }
                        ristorante.setURLWeb(nuovoURLWeb);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 6:
                    try {
                        String inputFasciaDiPrezzo = leggiInputConAnnullamento(in, "INSERISCI LA FASCIA DI PREZZO (numero intero)->\t").trim();
                        Integer nuovoFasciaDiPrezzo = Integer.parseInt(inputFasciaDiPrezzo);
                        if (nuovoFasciaDiPrezzo < 0) {
                            System.out.println("La fascia di prezzo deve essere un numero intero positivo.");
                            break;
                        }
                        ristorante.setFasciaDiPrezzo(nuovoFasciaDiPrezzo);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Inserisci un numero valido per la fascia di prezzo.");
                        break;
                    }
                case 7:
                    try {
                        boolean nuovoDelivery;
                        while (true) {
                            String inputDelivery = leggiInputConAnnullamento(in, "Permette il servizio di delivery? (s/n): ").trim().toLowerCase();
                            if (inputDelivery.startsWith("s")) {
                                nuovoDelivery = true;
                                break;
                            } else if (inputDelivery.startsWith("n")) {
                                nuovoDelivery = false;
                                break;
                            } else {
                                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                            }
                        }
                        ristorante.setDelivery(nuovoDelivery);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 8:
                    try {
                        boolean nuovoPrenotazioneOnline;
                        while (true) {
                            String inputPrenotazioneOnline = leggiInputConAnnullamento(in, "Permette la prenotazione online? (s/n): ").trim().toLowerCase();
                            if (inputPrenotazioneOnline.startsWith("s")) {
                                nuovoPrenotazioneOnline = true;
                                break;
                            } else if (inputPrenotazioneOnline.startsWith("n")) {
                                nuovoPrenotazioneOnline = false;
                                break;
                            } else {
                                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
                            }
                        }
                        ristorante.setPrenotazioneOnline(nuovoPrenotazioneOnline);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 9:
                    try {
                        String nuovoPrezzo;
                        while (true) {
                            try {
                                String inputPrezzo = leggiInputConAnnullamento(in, "Prezzo (solo numero): ").trim();
                                int tempPrezzo = Integer.parseInt(inputPrezzo);
                                if (tempPrezzo >= 0) {
                                    // Calcolo della fascia di prezzo in base al valore inserito
                                    if (tempPrezzo > 100) {
                                        nuovoPrezzo = "€€€€";
                                    } else if (tempPrezzo > 70) {
                                        nuovoPrezzo = "€€€";
                                    } else if (tempPrezzo > 40) {
                                        nuovoPrezzo = "€€";
                                    } else {
                                        nuovoPrezzo = "€";
                                    }
                                    break;
                                } else {
                                    System.out.println("Il prezzo non può essere negativo.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Inserisci un numero valido.");
                            }
                        }
                        ristorante.setPrezzo(nuovoPrezzo);
                        return true;
                    } catch (InputAnnullatoException e) {
                        break;
                    }
                case 10: return false;
            }
        } catch (InputAnnullatoException e) {
            System.out.println("Operazione annullata.");
            return false;
        }
        return false;
    }

    private static boolean modificaUtente(Utente utente, ArrayList<Cliente> cl , ArrayList<Ristoratore> rs) {
        pulisciTerminale();
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
                try {
                    selezione = Integer.parseInt(leggiInputConAnnullamento(in, "\nSELEZIONE ->\t").trim());
                } catch (NumberFormatException e) {
                    selezione = -1;
                } catch (InputAnnullatoException e) {
                    return false;
                }

            } while(selezione < 1 || selezione > 8);
            switch (selezione) {
                case 1:{
                    String nome;
                    do { //Nome
                        nome = leggiInputConAnnullamento(in, "\nNUOVO NOME ->\t\t").trim();
                        if (!nome.matches("^[a-zA-Z\\s]+$")) {
                            System.out.println("Il nome può contenere solo lettere e spazi.\n");
                        } else if (nome.length() < 2) {
                            System.out.println("Il nome deve contenere almeno 2 caratteri.\n");
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
                            System.out.println("Il Conome può contenere solo lettere e spazi.\n");
                        } else if (cognome.length() < 2) {
                            System.out.println("Il Cognome deve contenere almeno 2 caratteri.\n");
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
                            System.out.println("Username già esistente. Inseriscine un altro.\n");
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
                                System.out.println("Devi avere almeno 14 anni per registrarti!\n");
                                continue;
                            }
    
                            if (!DataDiNascita.maggiorenne(giorno, mese, anno) && utente instanceof Ristoratore ) {
                                System.out.println("Devi avere almeno 18 anni per registrarti!\n");
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
                    String ruoloNuovo;
                    do {
                        ruoloNuovo = leggiInputConAnnullamento(in, "\nINSERISCI IL NUOVO RUOLO [Cliente/Ristoratore] ->\t").trim();
                        ruoloNuovo = ruoloNuovo.toLowerCase();
                        if(ruoloNuovo.length() < 2) {
                            System.out.println("Il ruolo deve contenere almeno 2 caratteri\n");
                        }
                        if(!ruoloNuovo.startsWith("c") && !ruoloNuovo.startsWith("r")) {
                            System.out.println(ruoloNuovo + " non corrisponde a nessun ruolo.\n");
                        }
                        if (ruoloNuovo.startsWith("c") && utente instanceof Cliente) {
                            System.out.println("L'utente è gia' associato al ruolo Cliente.\n");
                        }
                        if (ruoloNuovo.startsWith("r") && utente instanceof Ristoratore) {
                            System.out.println("L'utente è gia' associato al ruolo Ristoratore.\n");
                        }
                    } while(ruoloNuovo.length() < 2 
                    || (!ruoloNuovo.startsWith("c") && !ruoloNuovo.startsWith("r"))
                    || (ruoloNuovo.startsWith("c") && utente instanceof Cliente)
                    || (ruoloNuovo.startsWith("r") && utente instanceof Ristoratore));
                    
                    if (ruoloNuovo.startsWith("c")) {
                        utente.setRuolo("Cliente");
                        rs.remove(utente);
                        Cliente modifica = new Cliente(utente.getNome(), utente.getCognome(), utente.getUsername(), utente.getPassword(), utente.getDomicilio(), utente.getDataDiNascita(), false);
                        cl.add(modifica);
                    }
                    if (ruoloNuovo.toLowerCase().startsWith("r")) {
                        utente.setRuolo("Ristoratore");
                        cl.remove(utente);
                        Ristoratore modifica = new Ristoratore(utente.getNome(), utente.getCognome(), utente.getUsername(), utente.getPassword(), utente.getDomicilio(), utente.getDataDiNascita(), false);
                        rs.add(modifica);
                }
                    return false;
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
        System.out.println("Ristoranti trovati:\n");
        for (int i = 0; i < risultati.size(); i++) {
            //System.out.printf("%d - %s\n", i + 1, risultati.get(i).getNome());
            System.out.printf("%d - %s, %s\n", i + 1, risultati.get(i).getNome(), risultati.get(i).getIndirizzo());  //  mette anche l'indirizzo
        }
        System.out.println("0 - Nessuno");
    
        int scelta = -1;
        do {
            System.out.print("\nSeleziona il numero del ristorante da visualizzare (0 per annullare) ->\t");
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
            System.out.printf("%d - %s, %s, %s\n", i + 1, risultati.get(i).getNomeRistorante(),risultati.get(i).getCommento(), risultati.get(i).getVoto());
        }
        System.out.println("0 - Nessuno");
    
        int scelta = -1;
        do {
            System.out.print("\nSeleziona il numero della recensione che vuoi selezionare (0 per annullare) ->\t");
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