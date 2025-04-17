// javac -d bin $(find src -name "*.java")
// java -cp bin src.Main

package src;

public class Main {
    public static void main(String[] args) {
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        System.out.println(gestoreRistoranti.filtraPerCitta("New York"));
    }
}
