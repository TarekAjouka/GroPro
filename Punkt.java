import java.util.Objects;

public abstract class Punkt {
    protected double x;
    protected double y;

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract String getTyp();     // z.B "Einfallpunkt" oder "Kreuzung"
    public abstract String getName();    // wird in den Unterklassen implementiert

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Punkt)) return false;
        Punkt punkt = (Punkt) o;
        return x == punkt.x &&
                y == punkt.y &&
                getName().equals(punkt.getName()) &&
                getTyp().equals(punkt.getTyp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, getName(), getTyp());
    }

    @Override
    public String toString() {
        return getTyp() + " " + getName() + " (" + x + ", " + y + ")";
    }
}