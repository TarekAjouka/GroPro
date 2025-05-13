import java.io.*;
import java.util.*;

public class Einlesen {
    private int endeZeit;
    private int zeitSchritt;
    private final Map<String, Punkt> punkte = new HashMap<>();
    private final List<Einfallpunkt> einfallpunkte = new ArrayList<>();
    private final List<Kreuzung> kreuzungen = new ArrayList<>();
    private final Set<Verbindung> verbindungen = new HashSet<>();

    public void ladeAusDatei(String dateipfad) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dateipfad));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            if (line.equalsIgnoreCase("Einfallspunkte:")) {
                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    String zielName = parts[3];
                    int takt = Integer.parseInt(parts[4]);

                    // Zielpunkt ist Platzhalter, wird später aufgelöst
                    Einfallpunkt ep = new Einfallpunkt(name, x, y, null, takt);
                    punkte.put(name, ep);
                    einfallpunkte.add(ep);
                }

            }else if(line.equalsIgnoreCase("Zeitraum:")){
                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    endeZeit = Integer.parseInt(parts[0]);
                    zeitSchritt = Integer.parseInt(parts[1]);

                }
            }
            else if (line.equalsIgnoreCase("Kreuzungen:")) {
                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);

                    Kreuzung kreuzung = new Kreuzung(name, x, y);
                    punkte.put(name, kreuzung);
                    kreuzungen.add(kreuzung);

                    // Verbindungen in Map speichern (erst als Platzhalter, Ziel wird später aufgelöst)
                    for (int i = 3; i < parts.length; i += 2) {
                        String zielName = parts[i];
                        int anteil = Integer.parseInt(parts[i + 1]);
                        // Zielpunkt-Referenz wird später ersetzt
                        kreuzung.addVerbindung(new DummyPunkt(zielName), anteil);
                    }
                }
            }
        }
        reader.close();

        // Verbindungen vervollständigen (Zielpunkte ersetzen, Verbindungsliste füllen)
        for (Einfallpunkt ep : einfallpunkte) {
            Punkt ziel = punkte.get(ep.getZielName()); // Zielname kommt aus Dummy
            ep.setZiel(ziel);

        }

        for (Kreuzung k : kreuzungen) {
            Map<Punkt, Integer> alte = k.getVerteilungsAnteile();
            Map<Punkt, Integer> ersetzt = new HashMap<>();

            for (Map.Entry<Punkt, Integer> entry : alte.entrySet()) {
                Punkt dummy = entry.getKey();
                Punkt echtesZiel = punkte.get(dummy.getName());
                ersetzt.put(echtesZiel, entry.getValue());

            }

            // Ersetzte Map setzen
            alte.clear();
            alte.putAll(ersetzt);
        }

        for (Kreuzung k : kreuzungen) {
            for (Map.Entry<Punkt, Integer> entry : k.getVerteilungsAnteile().entrySet()) {
                Punkt ziel = entry.getKey();
                int anteil = entry.getValue();

                // 1. Originalrichtung: von k nach Ziel
                Verbindung v1 = new Verbindung(k, ziel);


                // 2. Umgekehrte Richtung: von ziel nach k (aber ohne Anteil)
                Verbindung v2 = new Verbindung(ziel, k);
                verbindungen.add(v1);
                verbindungen.add(v2);
            }
        }

    }

    public int getEndeZeit() {
        return endeZeit;
    }
    public int getZeitSchritt() {
        return zeitSchritt;
    }

    public Map<String, Punkt> getPunkte() {
        return punkte;
    }

    public List<Einfallpunkt> getEinfallpunkte() {
        return einfallpunkte;
    }

    public List<Kreuzung> getKreuzungen() {
        return kreuzungen;
    }

    public Set<Verbindung> getVerbindungen() {
        return verbindungen;
    }

    // DummyPunkt nur zur Zwischenspeicherung der Namen, wird später ersetzt
    private static class DummyPunkt extends Punkt {
        public DummyPunkt(String name) {
            super(0, 0); // Koordinaten egal, da nur zur Identifikation
            this.name = name;
        }

        private final String name;

        @Override
        public String getTyp() {
            return "Dummy";
        }

        @Override
        public String getName() {
            return name;
        }
    }
}