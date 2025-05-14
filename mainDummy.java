import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class mainDummy {
    public static void main(String[] args) {
        Einlesen first = new Einlesen();
        try {
            first.ladeAusDatei("Eingabe.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Simulationsparameter
        int zeitEnde = first.getEndeZeit();
        int zeitschritt = first.getZeitSchritt();

        Set<Verbindung> verbindungen = first.getVerbindungen();
        List<Einfallpunkt> einfallpunkte = first.getEinfallpunkte();
        Map<String, Punkt> punkte = first.getPunkte();
        List<Kreuzung> kreuzungen = first.getKreuzungen();


        // Liste für aktive Fahrzeuge
        List<Fahrzeug> aktiveFahrzeuge = new ArrayList<>();

        //Fahzeuge.txt erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Fahrzeuge.txt"))) {
            // Hauptsimulationsschleife
            for (int i = 1; i <= zeitEnde; i++) {
                boolean ifZeitschritt = false;
                if (i % zeitschritt == 0) {
                    ifZeitschritt = true;
                }

                Iterator<Fahrzeug> iterator = aktiveFahrzeuge.iterator();
                while (iterator.hasNext()) {
                    Fahrzeug fahrzeug = iterator.next();
                    boolean istAmZiel = fahrzeug.bewegen(1, verbindungen, ifZeitschritt);
                    if (istAmZiel) {
                        iterator.remove();
                    }
                }

                for (Einfallpunkt ep : einfallpunkte) {
                    if (i % ep.getTakt() == 0) {
                        Fahrzeug neuesFahrzeug = new Fahrzeug(ep, verbindungen);
                        aktiveFahrzeuge.add(neuesFahrzeug);
                    }
                }

                if (i % zeitschritt == 0) {
                    for (Verbindung v : verbindungen) {
                        v.neuerZeitschritt();
                    }
                    // Schreibe in die Datei statt System.out.println
                    writer.write("*** t=" + i);
                    writer.newLine();
                    for (int j = 0; j < aktiveFahrzeuge.size(); j++) {
                        Fahrzeug f = aktiveFahrzeuge.get(j);
                        writer.write(f.getX() + " " + f.getY() + " " +
                                f.getActuell().getNach().x + " " +
                                f.getActuell().getNach().y + " " + j);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
        }

        //Plan.txt erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Plan.txt"))) {
            for (Verbindung v : verbindungen) {
                Punkt von = v.getVon();
                Punkt nach = v.getNach();
                writer.write(von.getX() + " " + von.getY() + " " + nach.getX() + " " + nach.getY());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Plan.txt: " + e.getMessage());
        }

        //Statistik.txt erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Statistik.txt"))) {
            // Erste Liste: Verbindungen mit Gesamtanzahl
            writer.write("Gesamtanzahl Fahrzeuge pro 100 m:");
            writer.newLine();
            for (Verbindung v : verbindungen) {
                writer.write(v.getVon().getName() + " -> " + v.getNach().getName() + ": " + v.getGesamt());
                writer.newLine();
            }


            // Zweite Liste: Verbindungen mit Maximalanzahl
            writer.write("Maximale Anzahl Fahrzeuge pro 100m:");
            writer.newLine();
            for (Verbindung v : verbindungen) {
                writer.write(v.getVon().getName() + " -> " + v.getNach().getName() + ": " + v.getMax());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Statistik.txt: " + e.getMessage());
        }

    }
}