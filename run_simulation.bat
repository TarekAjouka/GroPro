@echo off
echo Verkehrssimulation
echo ==================
echo.
echo 1) Alle IHK-Beispiele ausführen
echo 2) Eigene Eingabedatei verarbeiten
echo.
set /p wahl="Bitte waehlen Sie (1 oder 2): "

if "%wahl%"=="1" (
    jshell -q -s "var p = new Programm(); p.starteAlleBeispiele(); /exit"
) else if "%wahl%"=="2" (
    set /p pfad="Bitte geben Sie den Pfad zur Eingabedatei ein: "
    jshell -q -s "var p = new Programm(); p.start('%pfad%'); /exit"
) else (
    echo Ungueltige Eingabe!
)
pause