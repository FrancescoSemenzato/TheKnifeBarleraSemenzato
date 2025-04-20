// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

import src.Ristoranti.GestoreRistoranti;
import src.Ristoranti.Ristorante;

public class Main {
    public static void main(String[] args) {
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        for (Ristorante r : gestoreRistoranti.filtraPerCitta("Vienna")) {
            System.out.println(r.toString());
        }
    }
}
