public class Verbindung {
    private Punkt von;
    private Punkt nach;

    public Verbindung(Punkt von, Punkt nach) {
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
}