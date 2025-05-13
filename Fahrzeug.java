import java.util.Map;
import java.util.Random;

public class Fahrzeug {
    private int x;
    private int y;
    private Verbindung actuell;
    private double speed;

    public Fahrzeug(Einfallpunkt ep) {
        this.x = ep.getX();
        this.y = ep.getY();
        this.actuell = new Verbindung(ep, ep.getZiel());

        Random random = new Random();
        this.speed = (random.nextGaussian() * 10.0 + 45.0) * 1000 / 3600;
    }

    public String getSpeed() {
        return speed + " m/s";
    }

    public boolean bewegen(int sec) {
        // Berechne die verbleibende Strecke zum Zielpunkt
        double dx = actuell.getNach().getX() - x;
        double dy = actuell.getNach().getY() - y;
        
        // Berechne die verbleibende Länge zum Ziel
        double verbleibendeStrecke = Math.sqrt(dx * dx + dy * dy);
        
        // Bewegungsdistanz ist die Geschwindigkeit mal Sekunden
        double bewegungsdistanz = speed * sec;
        
        if (bewegungsdistanz < verbleibendeStrecke) {
            // Fall 1: Auto fährt normal weiter auf der aktuellen Verbindung
            x += (dx / verbleibendeStrecke) * bewegungsdistanz;
            y += (dy / verbleibendeStrecke) * bewegungsdistanz;
            return false;
        } else {
            // Fahrzeug erreicht das Ziel
            x = actuell.getNach().getX();
            y = actuell.getNach().getY();
            
            // Prüfe ob das Ziel ein Einfallpunkt ist
            if (actuell.getNach().getTyp().equals("Einfallpunkt")) {
                return true; // Fahrzeug soll gelöscht werden
            }
            
            // Wenn es eine Kreuzung ist
            if (actuell.getNach().getTyp().equals("Kreuzung")) {
                Kreuzung kreuzung = (Kreuzung) actuell.getNach();
                
                // Hole Wahrscheinlichkeiten ohne den Herkunftspunkt
                Map<Punkt, Double> wahrscheinlichkeiten = kreuzung.getWahrscheinlichkeitenOhne(actuell.getVon());
                
                // Zufällige Zahl zwischen 0 und 100
                double zufallsZahl = new Random().nextDouble() * 100;
                double summe = 0.0;
                
                // Wähle Zielpunkt basierend auf Wahrscheinlichkeiten
                for (Map.Entry<Punkt, Double> entry : wahrscheinlichkeiten.entrySet()) {
                    summe += entry.getValue();
                    if (zufallsZahl <= summe) {
                        // Neue Verbindung erstellen: von der Kreuzung zum gewählten Ziel
                        actuell = new Verbindung(kreuzung, entry.getKey());
                        break;
                    }
                }
                
                // Restliche Bewegung auf der neuen Verbindung
                double restStrecke = bewegungsdistanz - verbleibendeStrecke;
                if (restStrecke > 0) {
                    dx = actuell.getNach().getX() - x;
                    dy = actuell.getNach().getY() - y;
                    double neueStrecke = Math.sqrt(dx * dx + dy * dy);
                    x += (dx / neueStrecke) * restStrecke;
                    y += (dy / neueStrecke) * restStrecke;
                }
            }
            return false;
        }
    }
}