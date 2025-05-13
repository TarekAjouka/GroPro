import java.util.HashMap;
import java.util.Map;

public class Kreuzung extends Punkt {
    private String name;
    private Map<Punkt, Integer> verteilungsAnteile; // Zielpunkt → Verhältnisanteil

    public Kreuzung(String name, double x, double y) {
        super(x, y);
        this.name = name;
        this.verteilungsAnteile = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addVerbindung(Punkt ziel, int anteil) {
        verteilungsAnteile.put(ziel, anteil);
    }

    public Map<Punkt, Integer> getVerteilungsAnteile() {
        return verteilungsAnteile;
    }

    /**
     * Gibt berechnete Wahrscheinlichkeiten (in %) zurück, wenn ein Fahrzeug aus 'herkunft' kommt.
     * Der Herkunftspunkt wird ausgeschlossen, und die verbleibenden Anteile werden auf 100% normiert.
     */
    public Map<Punkt, Double> getWahrscheinlichkeitenOhne(Punkt herkunft) {
        Map<Punkt, Double> result = new HashMap<>();
        int summe = 0;

        // Summe der Anteile ohne die Herkunft
        for (Map.Entry<Punkt, Integer> entry : verteilungsAnteile.entrySet()) {
            if (!entry.getKey().equals(herkunft)) {
                summe += entry.getValue();
            }
        }

        // Prozentual normieren
        for (Map.Entry<Punkt, Integer> entry : verteilungsAnteile.entrySet()) {
            Punkt ziel = entry.getKey();
            if (!ziel.equals(herkunft)) {
                double prozent = (entry.getValue() * 100.0) / summe;
                result.put(ziel, prozent);
            }
        }

        return result;
    }

    @Override
    public String getTyp() {
        return "Kreuzung";
    }
}