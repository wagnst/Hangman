package HangMan;
import java.util.Scanner;

public class HangMan {

    /*
    enthält das Hauptprogramm. Es öffnet zunächst die Konsole mit new Scanner(System.in), fordert
    dann den Spieler 1 zur Eingabe des gesuchten Wortes auf. Anschließend darf Spieler 2 solange
    neue Buchstaben eingeben, bis er entweder 6 Fehlversuche hinter sich hat oder das gesuchte
    Wort erraten hat.
     */
    public static void main(String ags[]) throws Throwable {
        //Scanner für Worteingabe
        Scanner wortScanner = new Scanner(System.in);

        // Spiel erzeugen
        GameClass SpielInstanz = new GameClass();

        // Spieler 1: Wort eingeben!
        SpielInstanz.wortEingeben(wortScanner);

        // Spiel beginnen (wenn Versuche kleiner 6 or no winner)
        while(SpielInstanz.getAnzahlFehlVersuche() < 6 && !SpielInstanz.isHasWon()){
            SpielInstanz.buchstabenEingeben(wortScanner);
        }

        // Prüfe auf Gewonnen oder Verloren
        if(SpielInstanz.isHasWon()) {
            System.out.println("Sehr gut! GEWONNEN mit: " + SpielInstanz.getAnzahlFehlVersuche() + " Fehlversuchen!");
        } else {
            System.out.println("Leider VERLOREN mit: " + SpielInstanz.getAnzahlFehlVersuche() + " Fehlversuchen!");
        }

        // Output input word
        System.out.println("Gesuchtes Wort war: " + SpielInstanz.getEingabeWort());

        // Finalize instance
        SpielInstanz.finalize();

    }

}
