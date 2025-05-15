public class Einfallpunkt extends Punkt {
    private String name;
    private Punkt ziel; // Zielpunkt (z.b eine Kreuzung)
    private int takt;   // Takt in dem Fahrzeuge erzeugt werden

    public Einfallpunkt(String name, double x, double y, Punkt ziel, int takt) {
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
    public String getZielName() {
        return ziel == null ? null : ziel.getName();
    }

    public void setZiel(Punkt ziel) {
        this.ziel = ziel;
    }

    @Override
    public String getTyp() {
        return "Einfallpunkt";
    }
}