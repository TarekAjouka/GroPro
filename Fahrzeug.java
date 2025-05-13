import java.util.Map;
import java.util.Random;

public class Fahrzeug {
    private double x;
    private double y;
    private Verbindung actuell;
    private double speed;

    public Fahrzeug(Einfallpunkt ep, Map<String, Verbindung> verbindungen) {
        this.x = ep.getX();
        this.y = ep.getY();
        
        // Suche die passende Verbindung aus der Map
        String verbindungKey = ep.getName() + "-" + ep.getZiel().getName();  // oder wie auch immer Ihr Key-Format ist
        this.actuell = verbindungen.get(verbindungKey);
        
        if (this.actuell == null) {
            throw new IllegalArgumentException("Keine Verbindung gefunden von " + ep.getName() + " nach " + ep.getZiel().getName());
        }

        // Zähle das Fahrzeug sofort auf seiner ersten Verbindung
        this.actuell.fahrzeugZaehlen();

        Random random = new Random();
        this.speed = (random.nextGaussian() * 10.0 + 45.0) * 1000 / 3600;
    }

    public String getSpeed() {
        return speed + " m/s";
    }

    public boolean bewegen(int sec, Map<String, Verbindung> verbindungen) {
        // Fahrzeug ist bereits gezählt worden, also hier KEIN actuell.fahrzeugZaehlen() mehr nötig
        
        // Berechne die verbleibende Strecke zum Zielpunkt
        double dx = actuell.getNach().getX() - x;
        double dy = actuell.getNach().getY() - y;
        
        // Rest des Codes wie vorher...
        double verbleibendeStrecke = Math.sqrt(dx * dx + dy * dy);
        double bewegungsdistanz = speed * sec /100;
        
        if (bewegungsdistanz < verbleibendeStrecke) {
            x += (dx / verbleibendeStrecke) * bewegungsdistanz;
            y += (dy / verbleibendeStrecke) * bewegungsdistanz;
        } else {
            x = actuell.getNach().getX();
            y = actuell.getNach().getY();
            
            if (actuell.getNach().getTyp().equals("Einfallpunkt")) {
                return true;
            }
            
            if (actuell.getNach().getTyp().equals("Kreuzung")) {
                Kreuzung kreuzung = (Kreuzung) actuell.getNach();
                Map<Punkt, Double> wahrscheinlichkeiten = kreuzung.getWahrscheinlichkeitenOhne(actuell.getVon());
                double zufallsZahl = new Random().nextDouble() * 100;
                double summe = 0.0;
                
                for (Map.Entry<Punkt, Double> entry : wahrscheinlichkeiten.entrySet()) {
                    summe += entry.getValue();
                    if (zufallsZahl <= summe) {
                        String verbindungKey = kreuzung.getName() + "-" + entry.getKey().getName();
                        actuell = verbindungen.get(verbindungKey);
                        if (actuell == null) {
                            throw new IllegalStateException("Keine Verbindung gefunden von " + 
                                kreuzung.getName() + " nach " + entry.getKey().getName());
                        }
                        // Zähle das Fahrzeug auf der neuen Verbindung
                        actuell.fahrzeugZaehlen();
                        break;
                    }
                }
                
                double restStrecke = bewegungsdistanz - verbleibendeStrecke;
                if (restStrecke > 0) {
                    dx = actuell.getNach().getX() - x;
                    dy = actuell.getNach().getY() - y;
                    double neueStrecke = Math.sqrt(dx * dx + dy * dy);
                    x += (dx / neueStrecke) * restStrecke;
                    y += (dy / neueStrecke) * restStrecke;
                }
            }
        }
        return false;
    }
}