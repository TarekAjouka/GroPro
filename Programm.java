public class Programm {
    public void start(String eingabePfad) {
        String ausgabePfad = "./"; // Standardmäßig aktuelles Verzeichnis
        if (eingabePfad.contains("/")) {
            ausgabePfad = eingabePfad.substring(0, eingabePfad.lastIndexOf("/") + 1);
        }
        System.out.println("Verarbeite Eingabedatei: " + eingabePfad);
        Simulation.start(eingabePfad, ausgabePfad);
    }


    public void starteAlleBeispiele() {
        for (int i = 1; i <= 5; i++) {
            String nummer = String.format("%02d", i);
            String pfad = "Vorlage/IHK_" + nummer + "/Eingabe.txt";
            String ausgabePfad = "Vorlage/IHK_" + nummer + "/";
            System.out.println("Verarbeite IHK-Beispiel " + nummer + "...");
            Simulation.start(pfad, ausgabePfad);
        }
    }
}