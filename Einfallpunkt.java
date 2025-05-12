public class Einfallpunkt extends Punkt {
    private String name;
    private Punkt ziel; // Zielpunkt (z.b eine Kreuzung)
    private int takt;   // Takt in dem Fahrzeuge erzeugt werden

    public Einfallpunkt(String name, int x, int y, Punkt ziel, int takt) {
        super(x, y);
        this.name = name;
        this.ziel = ziel;
        this.takt = takt;
    }

    public String getName() {
        return name;
    }

    public Punkt getZiel() {
        return ziel;
    }

    public int getTakt() {
        return takt;
    }

    @Override
    public String getTyp() {
        return "Einfallpunkt";
    }
}