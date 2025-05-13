import java.util.Objects;

public class Verbindung {
    private Punkt von;
    private Punkt nach;

    public Verbindung(Punkt von, Punkt nach) {
        if (von == null || nach == null) {
            throw new IllegalArgumentException("Von und Nach Punkte dürfen nicht null sein");
        }

        this.von = von;
        this.nach = nach;
    }

    public Punkt getVon() {
        return von;
    }

    public Punkt getNach() {
        return nach;
    }

    public double getLaenge() {
        int dx = nach.getX() - von.getX();
        int dy = nach.getY() - von.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "Verbindung von " + von.getName() + " nach " + nach.getName()
                + " (Länge: " + String.format("%.2f", getLaenge()) + ")";
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