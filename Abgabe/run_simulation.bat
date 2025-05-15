@echo off
setlocal EnableDelayedExpansion

echo Verkehrssimulation - Flexible Ausfuehrung
echo =========================================

:: Java-Kompilierung
echo Kompiliere Java-Dateien...
javac *.java

if errorlevel 1 (
    echo Fehler bei der Kompilierung!
    pause
    exit /b 1
)

:menu
echo.
echo Waehlen Sie eine Option:
echo 1 - Vordefinierte IHK-Beispiele ausfuehren
echo 2 - Eigene Eingabedatei simulieren
echo 3 - Beenden
echo.

set /p auswahl="Ihre Wahl (1-3): "

if "%auswahl%"=="1" goto ihk_beispiele
if "%auswahl%"=="2" goto eigene_datei
if "%auswahl%"=="3" goto ende

echo Ungueltige Eingabe! Bitte erneut versuchen.
goto menu

:ihk_beispiele
echo.
echo Starte IHK-Beispiele...
java Main
echo.
echo IHK-Beispiele wurden ausgefuehrt.
goto menu

:eigene_datei
echo.
set /p eingabepfad="Geben Sie den Pfad zur Eingabedatei ein (z.B. mein/pfad/Eingabe.txt): "
set /p ausgabepfad="Geben Sie den Ausgabepfad ein (z.B. mein/pfad/): "

if not exist "%eingabepfad%" (
    echo Fehler: Eingabedatei nicht gefunden!
    goto menu
)

echo.
echo Starte Simulation...
java -cp . Simulation "%eingabepfad%" "%ausgabepfad%"
echo.
echo Simulation wurde ausgefuehrt.
goto menu

:ende
echo.
echo Programm wird beendet.
pause
exit /b 0