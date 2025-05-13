public class Main {

    public static void main(String[] args) {
        Einlesen first = new Einlesen();
        try {
            first.ladeAusDatei("Eingabe.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(first.getVerbindungen());
        System.out.println(first.getVerbindungen().size());
        System.out.println(first.getEinfallpunkte());
        System.out.println(first.getKreuzungen());
        System.out.println(first.getPunkte());
        System.out.println(first.getEndeZeit());
        System.out.println(first.getZeitSchritt());
        //Fahrzeug x = new Fahrzeug(1, 1);
        //System.out.println(x.getSpeed());

    }
}
