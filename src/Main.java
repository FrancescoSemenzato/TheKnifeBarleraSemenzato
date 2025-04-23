// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;
import src.Utenti.Cliente;
import src.Utenti.Ristoratore;

import java.io.BufferedReader;
import java.io.FileReader;
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

        //caricamento della lista Ristoranti
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();

        System.out.println(ListaClienti.get(0).getUsername());
        System.out.println(ListaClienti.get(0).VisualizzaPreferiti());


        /* 
        Scanner in = new Scanner(System.in);
        String selezione;
        
        System.out.println("BENVENUTO NEL PROGETTO THE KNIFE");
        System.err.println("POSSIEDI GIA'UN ACCOUNT? [SI/NO]");
        selezione = in.nextLine();

        if(selezione.toLowerCase().charAt(0) == 'S'){
            //Form ACCEDI
        }
        else{
            //Registrati o entra come Guest o Esci
        }
        */
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
}
