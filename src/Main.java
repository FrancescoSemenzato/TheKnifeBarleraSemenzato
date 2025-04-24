// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;
import src.Utenti.Cliente;
import src.Utenti.DataDiNascita;
import src.Utenti.Ristoratore;
import src.Utenti.UtenteNonRegistrato;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        String nome, cognome, username, password, domicilio, datadinascita;
        String buffer;
        
        System.out.println("BENVENUTO NEL PROGETTO THE KNIFE");
        System.out.println("POSSIEDI GIA'UN ACCOUNT? [SI/NO]");
        selezioneStringa = in.nextLine();

        if(selezioneStringa.toLowerCase().charAt(0) == 's'){
            //Form ACCEDI
        }
        else{
            //Registrati o entra come Guest o Esci
            do{
                System.out.println("SCEGLI TRA UNA DI QUESTE OPZIONI: ");
                System.out.println("1- REGISTRATI COME CLIENTE");
                System.out.println("2- REGISTRATI COME RISTORATORE");
                System.out.println("3- ENTRA COME GUEST");
                System.out.println("4- ESCI");
                System.out.print("SELEZIONE ->\t");
                selezioneInt = in.nextInt();
                buffer = in.nextLine();
            }while(selezioneInt < 0 || selezioneInt > 5);
            
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
                    System.out.print("DOMICILIO ->\t");
                    domicilio = in.nextLine();
                    System.out.print("DATA DI NASCITA [gg/mm/aaaa] ->\t");
                    datadinascita = in.nextLine();
                    Cliente cl = new Cliente(nome, cognome, username, password, domicilio, datadinascita);
                    cl.CaricaListaPreferiti(username);
                    ListaClienti.add(cl);
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
                    System.out.print("DOMICILIO ->\t");
                    domicilio = in.nextLine();
                    System.out.print("DATA DI NASCITA [gg/mm/aaaa] ->\t");
                    datadinascita = in.nextLine();
                    Ristoratore ris = new Ristoratore(nome, cognome, username, password, domicilio, datadinascita);
                    ListaRistoratori.add(ris);
                    break;
                }

                case 3:{
                    UtenteNonRegistrato guest = new UtenteNonRegistrato();
                    break;
                }

                case 4:{
                    Esci(ListaClienti, ListaRistoratori, gestoreRistoranti);
                    break;
                }
            }
        }
    }

    public static void CaricaListe(ArrayList<Cliente> cl , ArrayList<Ristoratore> rs){
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FilePathUtenti))) {
            br.readLine();  // Questa riga legge la prima riga e la ignora
            while ((line = br.readLine()) != null) {
                String[] campi = line.split(";");
                if (campi[6].equals("Cliente")) {
                    cl.add(new Cliente(campi[1], campi[2], campi[0], campi[3], campi[5], campi[4]));
                }
                else{
                    rs.add(new Ristoratore(campi[1], campi[2], campi[0], campi[3], campi[5], campi[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }
    }

    public static void Esci(ArrayList<Cliente> cl, ArrayList<Ristoratore> ris, GestoreRistoranti risto){
        risto.scriviSuFile();

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
                    ""
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
                    "",
                    r.getRistorantiString()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file degli utenti: " + e.getMessage());
        }
    }
}