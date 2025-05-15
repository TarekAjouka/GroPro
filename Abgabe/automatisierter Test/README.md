# GroPro
BENUTZERHANDBUCH: VERKEHRSSIMULATION
====================================

1. PROGRAMMSTART
---------------
- Doppelklicken Sie auf die Datei "run_simulation.bat"
- Das Programm kompiliert automatisch alle Java-Dateien

2. HAUPTMENÜ
------------
Nach dem Start erscheint folgendes Menü:

Waehlen Sie eine Option:
1 - Vordefinierte IHK-Beispiele ausfuehren
2 - Eigene Eingabedatei simulieren
3 - Beenden

3. OPTIONEN IM DETAIL
---------------------

3.1 IHK-BEISPIELE (Option 1)
- Führt automatisch alle vordefinierten IHK-Beispiele aus
- Verarbeitet Beispieldateien aus dem Vorlage-Ordner
- Ergebnisse werden in den jeweiligen IHK-Ordnern gespeichert

3.2 EIGENE SIMULATION (Option 2)
- Ermöglicht die Simulation mit eigener Eingabedatei
- Pfadeingabe-Möglichkeiten:
    * Datei im gleichen Ordner: Eingabe.txt
    * Datei in Unterordner: Ordnername/Eingabe.txt
    * Kompletter Pfad: C:/MeinOrdner/Eingabe.txt

3.3 BEENDEN (Option 3)
- Beendet das Programm

4. AUSGABEDATEIEN
-----------------
Das Programm erstellt drei Ausgabedateien:

4.1 Fahrzeuge.txt
- Enthält Positionsdaten aller Fahrzeuge
- Aktualisiert bei jedem Zeitschritt

4.2 Plan.txt
- Zeigt alle Straßenverbindungen
- Format: Start- und Endkoordinaten

4.3 Statistik.txt
- Gesamtanzahl Fahrzeuge pro 100m
- Maximale Anzahl Fahrzeuge pro 100m

5. FEHLERBEHEBUNG
----------------
5.1 Datei nicht gefunden
- Überprüfen Sie die Schreibweise des Pfads
- Stellen Sie sicher, dass die Datei existiert
- Prüfen Sie das aktuelle Verzeichnis

5.2 Kompilierungsfehler
- Überprüfen Sie die Java-Installation
- Stellen Sie sicher, dass alle Java-Dateien vorhanden sind

6. BEISPIEL-PFADEINGABEN
------------------------
Eingabe.txt                          (Gleicher Ordner)
./Eingabe.txt                        (Alternative für gleichen Ordner)
MeinOrdner/Eingabe.txt              (Unterordner)
C:/MeineProjekte/Test/Eingabe.txt   (Vollständiger Pfad)

7. WICHTIGE HINWEISE
-------------------
- Ausgabedateien werden im gleichen Ordner wie die Eingabedatei erstellt
- Nach jeder Simulation kehren Sie zum Hauptmenü zurück
- Fehlermeldungen werden im Konsolenfenster angezeigt

Bei weiteren Fragen wenden Sie sich bitte an den Support.

=====================================
Ende des Benutzerhandbuchs
