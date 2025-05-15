import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simulation {

    public static void start(String pfad,String outPut){

        Einlesen first = new Einlesen();
        try {
            first.ladeAusDatei(pfad);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Simulationsparameter
        int zeitEnde = first.getEndeZeit();
        int zeitschritt = first.getZeitSchritt();

        Set<Verbindung> verbindungen = first.getVerbindungen();
        List<Einfallpunkt> einfallpunkte = first.getEinfallpunkte();

        // Liste für aktive Fahrzeuge
        // ID wird erstmal auf 0 gesetzt und erhöht sich jeweils um 1 bei jedem neuen Fahrzeug
        int nextId =0;
        Map<Integer, Fahrzeug> aktiveFahrzeuge = new HashMap<>();


        //Fahzeuge.txt erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPut+"Fahrzeuge.txt"))) {
            for (int i = 0; i <= zeitEnde; i++) {
                boolean ifZeitschritt = false;
                if (i % zeitschritt == 0) {
                    ifZeitschritt = true;
                }

                // Fahrzeuge bewegen
                Iterator<Map.Entry<Integer, Fahrzeug>> iterator = aktiveFahrzeuge.entrySet().iterator();
                while (iterator.hasNext()) {
                    Fahrzeug fahrzeug = iterator.next().getValue();
                    boolean istAmZiel = fahrzeug.bewegen(1, verbindungen, ifZeitschritt);
                    if (istAmZiel) {
                        iterator.remove();
                    }
                }

                // Neue Fahrzeuge erstellen
                for (Einfallpunkt ep : einfallpunkte) {
                    if (i!=0&&i % ep.getTakt() == 0) {
                        Fahrzeug neuesFahrzeug = new Fahrzeug(ep, verbindungen);
                        aktiveFahrzeuge.put(nextId++, neuesFahrzeug);
                    }
                }

                // Schreiben in die Datei
                if (i % zeitschritt == 0) {
                    writer.write("*** t = " + i);
                    writer.newLine();
                    for (Map.Entry<Integer, Fahrzeug> entry : aktiveFahrzeuge.entrySet()) {
                        Fahrzeug f = entry.getValue();
                        int id = entry.getKey();
                        writer.write(f.getX() + " " + f.getY() + " " +
                                f.getActuell().getNach().x + " " +
                                f.getActuell().getNach().y + " " + id);
                        writer.newLine();
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
        }

        //Plan.txt erstellen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPut+"Plan.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outPut+"Statistik.txt"))) {
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