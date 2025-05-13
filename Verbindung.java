import java.util.Objects;

public class Verbindung {
    private Punkt von;
    private Punkt nach;
    private double gesamt;  // Gesamtzahl aller Fahrzeuge
    private double max;        // Maximale Anzahl der Fahrzeuge
    private int aktuelleAnzahl;  // Aktuelle Anzahl im Zeitschritt

    public Verbindung(Punkt von, Punkt nach) {

        if (von == null || nach == null) {
            throw new IllegalArgumentException("Von und Nach Punkte dürfen nicht null sein");
        }
        this.gesamt = 0;
        this.max = 0;
        this.aktuelleAnzahl = 0;
        this.von = von;
        this.nach = nach;
    }

    // Methode zum Zurücksetzen der aktuelleAnzahl am Anfang jedes Zeitschritts
    public void neuerZeitschritt() {
        // Aktualisiere max falls nötig
        if (aktuelleAnzahl > max) {
            max = aktuelleAnzahl;
        }
        // Setze aktuelle Anzahl zurück
        aktuelleAnzahl = 0;
    }

    // Fahrzeug auf dieser Verbindung im aktuellen Zeitschritt
    public void fahrzeugZaehlen() {
        aktuelleAnzahl++;
        gesamt++;
    }

    public int getAktuelleAnzahl() {
        return aktuelleAnzahl;
    }

    public double getGesamt() {
        return gesamt/100;
    }

    public double getMax() {
        return max/100;
    }

    public Punkt getVon() {
        return von;
    }

    public Punkt getNach() {
        return nach;
    }

    public double getLaenge() {
        double dx = nach.getX() - von.getX();
        double dy = nach.getY() - von.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "Verbindung von " + von.getName() + " nach " + nach.getName()
                + " (Länge: " + String.format("%.2f", getLaenge()) 
                + ", Gesamt: " + gesamt 
                + ", Max: " + max + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Verbindung that = (Verbindung) o;
        return von.equals(that.von) && nach.equals(that.nach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(von, nach);
    }
}