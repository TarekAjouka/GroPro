public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            // Standardbeispiele ausführen
            Simulation.start("Vorlage/IHK_01/Eingabe.txt","Vorlage/IHK_01/");
            Simulation.start("Vorlage/IHK_02/Eingabe.txt","Vorlage/IHK_02/");
            Simulation.start("Vorlage/IHK_03/Eingabe.txt","Vorlage/IHK_03/");
            Simulation.start("Vorlage/IHK_04/Eingabe.txt","Vorlage/IHK_04/");
            Simulation.start("Vorlage/IHK_05/Eingabe.txt","Vorlage/IHK_05/");
        } else if (args.length == 1) {
            // Extrahiere den Verzeichnispfad aus dem Eingabepfad
            String eingabePfad = args[0];
            String ausgabePfad = eingabePfad.substring(0, eingabePfad.lastIndexOf('/') + 1);
            Simulation.start(eingabePfad, ausgabePfad);
        } else {
            System.out.println("Verwendung:");
            System.out.println("java Main                     # Führt alle Beispiele aus");
            System.out.println("java Main eingabedatei.txt   # Führt Simulation mit angegebener Datei aus");
        }
    }
}

