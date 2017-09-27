#k-Anonymity

##Build und Unit-Tests ausführen

Unix/Linux/Mac OS X: 

./gradlew test -i

Windows: 

gradlew.bat test -i

## Zusammenfassung
Der hier implementierte Algorithmus (KAnonAlgorithm.java) berechnet die k-Anonymity wie folgt:
1. Ermittlung der Daten-Klassen der Quasi-Identifier-Spalten
2. In einer Schleife wird anschließend je nach Gewichtung (Steuerung über Konstanten) die Reduzierung auf Spaltenbasis durchgeführt, bis der k-Wert erreicht wird

## Nachteile

Die Anonymisierung erfolgt auf Basis einer konstanten Gewichtung nach Datenklasse. Daher werden zuerst Spalten einer Datenklasse reduziert bis auf die Reduzierung in der nächsten Datenklasse erfolgt.

