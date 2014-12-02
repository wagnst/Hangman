package HangMan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameClass {

    /* BUCHSTABE */
    private String eingabeBuchstabe = "";
    /* WORT */
    private String eingabeWort = "";
    /* FEHLVERSUCHE */
    private int AnzahlFehlVersuche = 0;
    /* MATCHED STRING */
    private StringBuilder matchString;
    /* HAS WON? */
    private boolean hasWon = false;

    protected boolean isHasWon() {
        return this.hasWon;
    }

    private void setHasWon() {
        this.hasWon = true;
    }

    private String getEingabeBuchstabe() {
        return this.eingabeBuchstabe;
    }

    private void setEingabeBuchstabe(char eingabeBuchstabe) {
        this.eingabeBuchstabe += eingabeBuchstabe;
    }

    protected String getEingabeWort() {
        return this.eingabeWort;
    }

    private void setEingabeWort(String eingabeWort) {
        this.eingabeWort = eingabeWort;
    }

    protected int getAnzahlFehlVersuche() {
        return this.AnzahlFehlVersuche;
    }

    private void setAnzahlFehlVersuche() {
        this.AnzahlFehlVersuche += 1;
    }

    private void initMatchString() {
        matchString = new StringBuilder(this.eingabeWort.length());
        for (int i = 0; i < this.eingabeWort.length(); i++) {
            this.matchString.replace(i, i, "_");
        }
    }

    private StringBuilder getMatchString() {
        return this.matchString;
    }

    private void setMatchString(char buchStabe, int stelle) {
        // setze an Stelle stelle von MatchString buchStabe
        this.matchString.setCharAt(stelle, buchStabe);
    }

    /*
    zeichnet den Galgen, wobei n die Anzahl der Körperteile angibt, die bereits am Galgen hängen
     */
    private void zeichneGalgen(int n) throws IOException, InterruptedException {
        // Match Sting mit Leerzeichen zwischen Buchstaben
        StringBuilder matchStringBlanks = new StringBuilder(getMatchString().length() * 2);

        // Ausgabefenster leeren
        cls();

        System.out.println(" ------");
        System.out.println(" |     |");
        if (n == 0) {
            System.out.println("       |");
            System.out.println("       |");
            System.out.println("       |");
            System.out.println("       |");
        } else if (n == 1) {
            System.out.println(" O     |");
            System.out.println("       |");
            System.out.println("       |");
            System.out.println("       |");
        } else if (n == 2) {
            System.out.println(" O     |");
            System.out.println("\\      |");
            System.out.println("       |");
            System.out.println("       |");
            System.out.println("       |");
            System.out.println("       |");
        } else if (n == 3) {
            System.out.println(" O     |");
            System.out.println("\\ /    |");
            System.out.println("       |");
            System.out.println("       |");
        } else if (n == 4) {
            System.out.println(" O     |");
            System.out.println("\\ /    |");
            System.out.println(" |     |");
            System.out.println("       |");
        } else if (n == 5) {
            System.out.println(" O     |");
            System.out.println("\\ /    |");
            System.out.println(" |     |");
            System.out.println("/      |");
        } else if (n == 6) {
            System.out.println(" O     |");
            System.out.println("\\ /    |");
            System.out.println(" |     |");
            System.out.println("/ \\    |");
            System.out.println("YOU DIED :(");
        } else {
            //hier kommen wir nie hin, wenn nur 6 Versuche erlaubt sind...
            System.out.println("Maximal 6 Versuche erlaubt, bitte ändern!");
        }

        //status output
        System.out.print("== Gesuchtes Wort:     ");
        for (int i = 0; i < getMatchString().length(); i++) {
            System.out.print(this.matchString.charAt(i));
            System.out.print(" ");
        }
        System.out.println("\n== Fehlversuche:       " + getAnzahlFehlVersuche());
        System.out.println("== Buchstabenversuche: " + getEingabeBuchstabe().toString());
    }

    /*
    ermöglicht es Spieler 1, das zu erratende Wort einzugeben. Nach der Eingabe soll das Wort
    durch 40 Leerzeilen aus der Konsole gescrollt werden, damit Spieler 2 es nicht sehen kann.
    Tipps:
        - Wandeln Sie das Wort vor der Rückgabe mit der Stringmethode toUpper() in Großbuchstaben
            um, damit Sie später nicht mehr zwischen Groß- und Kleinschreibung unterscheiden
            müssen.
        - Lesen Sie das Wort mit scanner.nextLine() ein, damit der Rest der Zeile nicht bei der
            Eingabe des ersten Buchstabens durch Spieler 2 verwertet wird.
    */
    protected void wortEingeben(Scanner scanner) throws IOException, InterruptedException {
        String eingabeWort = "", eingabeText = "Bitte ein Wort eingeben: \n";

        //Eingabetext ausgeben
        System.out.println(eingabeText);

        //Wenn es weitere Buchstaben gibt, einlesen
        if (scanner.hasNextLine()) {
            eingabeWort = scanner.nextLine();
            eingabeWort.split(" "); //löscht Leerzeichen
        }

        //Leerzeilen einfügen
        cls();

        //Wort in Instanz setzen (Großbuchstaben)
        this.setEingabeWort(eingabeWort.toUpperCase());

        //Matchstring initialisieren und mit "_" füllen
        initMatchString();

        //leeren Galgen ausgeben
        zeichneGalgen(0);
    }

    protected void startSingleplayer() throws IOException, InterruptedException {

        //Leerzeilen einfügen
        cls();

        //Wort in Instanz setzen (Großbuchstaben)
        this.setEingabeWort(getRandomWordFromDIC().toUpperCase());

        //Matchstring initialisieren und mit "_" füllen
        initMatchString();

        //leeren Galgen ausgeben
        zeichneGalgen(0);
    }

    /*
    ermöglicht es Spieler 2, einen zu erratenden Buchstaben einzugeben.
            Tipps:
            - Lesen Sie die nächste Zeile mit scanner.nextLine() von der Konsole ein.
            - Prüfen Sie die Länge des Strings auf genau einen Buchstaben.
            - Geben Sie den eingegebenen Buchstaben zurück, nachdem Sie zuvor den String mit der
    Stringfunktion toUpper() in Großbuchstaben umgewandelt haben.
    */
    protected void buchstabenEingeben(Scanner scanner) {
        char eingabeBuchstabe = ' ';
        String eingabeText = "Bitte einen Buchstabe eingeben: \n";

        // Start Eingabe
        System.out.println(eingabeText);

        //Convert to UpperCase
        eingabeBuchstabe = Character.toUpperCase(scanner.next().charAt(0));

        // add input char to input string
        setEingabeBuchstabe(eingabeBuchstabe);

        //prüfe zwischenstand
        zwischenstandBerechnen();
    }

    /*
    berechnet aus allen bisher bereits von Spieler 2 eingegebenen Buchstaben (Übergabe als String)
    und dem gesuchten Wort einen String, der
        - alle bereits erratenen Buchstaben darstellt
        - für alle noch nicht erratenen Buchstaben einen _ (Underscore) darstellt
    Beispiel:
        - Gesuchtes Wort: Hochschule
        - bereits eingegebene Buchstaben: estah
        - Rückgabe: H__hs_h__e
    */
    private void zwischenstandBerechnen() {
        boolean _BOOL = false;

        //Buchstabenstring und eingabewortstring durchloopen und nach übereinstimmenden Buchstaben suchen
        //durchsucht immer alles, da so von Prof. gefordert.. (TODO: normalerweise nur aktuell eingegebenen...)
        for (int i = 0; i < getEingabeBuchstabe().length(); i++) {
            _BOOL = false;
            for (int j = 0; j < getEingabeWort().length(); j++) {
                // wenn Buchstabe gleich, setze Matchstring
                if (this.eingabeWort.charAt(j) == this.eingabeBuchstabe.charAt(i)) {
                    setMatchString(this.eingabeWort.charAt(j), j);
                    _BOOL = true;
                }
            }
        }
        if (_BOOL == false) {
            // wenn kein treffer, Fehlversuche erhöhen
            setAnzahlFehlVersuche();
        }
        //check game status (fail tries, picture,...)
        spielstatusKalkulieren();
    }

    /*
    zeichnet Galgen und setzt gewonnen, wenn Strings gleich
     */
    private void spielstatusKalkulieren() {
        if (getAnzahlFehlVersuche() <= 6) {
            //versuche Galgen zu zeichnen
            try {
                zeichneGalgen(getAnzahlFehlVersuche());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //wenn matchstring gleich eingabewort, gewonnen!
        if (this.matchString.toString().equals(getEingabeWort())) {
            setHasWon();
        }
    }

    /*
    Clears console
     */
    private static void cls() throws InterruptedException {
        Thread.sleep(300);
        for (int i = 0; i < 100; i++) {
            System.out.print("\n");
        }
    }

    /*
    Finalizer
     */
    protected void finalize() throws Throwable {
        // Invoke the finalizer of our superclass
        // We haven't discussed superclasses or this syntax yet
        super.finalize();
    }

    /*
    get a random word from german dictionary
    int numberOfLetters: length of word (2-37)
     */
    private String getRandomWordFromDIC() throws IOException {
        String[] dicWords;
        int randomNumber;

        //read from deWords.dic
        dicWords = readLines("./src/HangMan/deWords.dic");
        randomNumber = randInt(0, dicWords.length);

        return dicWords[randomNumber];
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    private static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Lesen einer UTF8 codierten Textdatei
     *
     * @return Stringarray (each line is an array entry)
     */
    public String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

}