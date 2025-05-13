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

    }
}
