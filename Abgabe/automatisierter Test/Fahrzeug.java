import java.util.Map;
import java.util.Set;
import java.util.Random;

public class Fahrzeug {
    private double x;
    private double y;
    private Verbindung actuell;
    private double speed;

    public Fahrzeug(Einfallpunkt ep, Set<Verbindung> verbindungen) {
        this.x = ep.getX();
        this.y = ep.getY();
        
        // Temporäre Verbindung zum Vergleich erstellen
        Verbindung tempVerbindung = new Verbindung(ep, ep.getZiel());
        
        // Finde die passende Verbindung im Set
        this.actuell = null;
        for (Verbindung v : verbindungen) {
            if (v.equals(tempVerbindung)) {
                this.actuell = v;
                break;
            }
        }
        
        if (this.actuell == null) {
            throw new IllegalArgumentException("Keine Verbindung gefunden von " + 
                ep.getName() + " nach " + ep.getZiel().getName());
        }

        // Zähle das Fahrzeug sofort auf seiner ersten Verbindung
        this.actuell.fahrzeugZaehlen();

        Random random = new Random();
        this.speed = (random.nextGaussian() * 10.0 + 45.0) * 1000 / 3600;
    }

    public Verbindung getActuell() {
        return actuell;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public boolean bewegen(int sec, Set<Verbindung> verbindungen,boolean zeitSchritt) {
        double dx = actuell.getNach().getX() - x;
        double dy = actuell.getNach().getY() - y;
        
        double verbleibendeStrecke = Math.sqrt(dx * dx + dy * dy);
        double bewegungsdistanz = speed * sec /100;
        
        if (bewegungsdistanz < verbleibendeStrecke) {
            if(zeitSchritt){
                actuell.fahrzeugZaehlen();
            }
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
                        // Temporäre Verbindung zum Vergleich erstellen
                        Verbindung tempVerbindung = new Verbindung(kreuzung, entry.getKey());
                        
                        // Finde die neue Verbindung
                        for (Verbindung v : verbindungen) {
                            if (v.equals(tempVerbindung)) {
                                actuell = v;
                                actuell.fahrzeugZaehlen();
                                break;
                            }
                        }
                        
                        if (actuell == null) {
                            throw new IllegalStateException("Keine Verbindung gefunden von " + 
                                kreuzung.getName() + " nach " + entry.getKey().getName());
                        }
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

    public String getSpeed() {
        return speed + " m/s";
    }
}