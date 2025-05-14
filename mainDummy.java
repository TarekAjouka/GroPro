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

        // Hauptsimulationsschleife

        for (int zeit = 0; zeit <= zeitEnde; zeit+= zeitschritt) {
            if(zeit == 0)continue;
            System.out.println("\nZeitschritt " + zeit + " Sekunden:");

            // Zurücksetzen der Fahrzeugzählung für alle Verbindungen
            for (Verbindung v : verbindungen) {
                v.neuerZeitschritt();
            }

            // Neue Fahrzeuge an Einfallpunkten erzeugen
            for (Einfallpunkt ep : einfallpunkte) {
                if (zeit % ep.getTakt() == 0) {
                    Fahrzeug neuesFahrzeug = new Fahrzeug(ep, verbindungen);
                    aktiveFahrzeuge.add(neuesFahrzeug);
                    System.out.println("Neues Fahrzeug an Einfallpunkt " + ep.getName() +
                            " mit Ziel " + ep.getZiel().getName());
                }
            }

            // Alle aktiven Fahrzeuge bewegen
            Iterator<Fahrzeug> iterator = aktiveFahrzeuge.iterator();
            while (iterator.hasNext()) {
                Fahrzeug fahrzeug = iterator.next();
                boolean istAmZiel = fahrzeug.bewegen(zeitschritt, verbindungen);

                if (istAmZiel) {
                    iterator.remove();
                    System.out.println("Fahrzeug hat sein Ziel erreicht und wurde entfernt");
                }
            }

            // Ausgabe der aktuellen Situation
            System.out.println("Aktive Fahrzeuge: " + aktiveFahrzeuge.size());
            for (Verbindung v : verbindungen) {
                if (v.getAktuelleAnzahl() > 0) {
                    System.out.println("Verbindung von " + v.getVon().getName() +
                            " nach " + v.getNach().getName() +
                            ": " + v.getAktuelleAnzahl() + " Fahrzeuge");
                }
            }
        }

        // Abschließende Statistik
        System.out.println("\nSimulation beendet!");
        System.out.println("Statistik der Verbindungen:");
        for (Verbindung v : verbindungen) {
            System.out.println(v.toString());
        }
    }
}