# 'Tripple-S' - SuperStorageSell



Allgemeine Hinweise:

    Zu Implementieren ist die Programmieraufgabe in Java (Lauffähig in Java SE 14)
    Zu den Aufgaben ist eine entsprechende grafische Benutzeroberfläche zu entwickeln
    Arbeiten Sie mit den Prinzipien der objektorientierten Programmierung wie in der Vorlesung behandelt
    Abzugeben sind alle Quelldateien (*.java) in einem Zip gepackt über Moodle
    Es muss eine Klasse "Start.java" mit einer main-Methode geben - hier wird das Programm gestartet
    Start ist der 05.06.2020
    Abgabeschluss ist der 24.07.2020, 23:59 Uhr
    In die Bewertung fließen funktionale Anforderungen (wie unten beschrieben), aber auch subjektive Faktoren wie die Strukturierung des Codes oder die Gestaltung der GUI ein.
    Die Arbeit ist keine Gruppenarbeit. Jeder muss einen individuellen Programmentwurf anfertigen

 

Allgemeines:

Entwickeln Sie ein Spiel, in der Sie in die Rolle eines Lageristen schlüpfen. Sie haben ein Lager und bekommen Aufträge, Waren einzulagern oder auszulagern. Für erledigte Aufträge bekommen Sie Geld. Ziel des Spiels ist es, so viel wie möglich einzunehmen.

 

    Das Lager:

Spiel startet, das Lager wird angelegt. Sie besitzen ein Hochregallager mit drei Lagerplätzen Breite und drei Lagerplätzen Höhe. Auf einem Lagerplatz finden drei Paletten hintereinander Platz. Sie haben also insgesamt für 27 Paletten Platz. Ihr Lager ist aktuell leer.

    Der Auftrag:

Über den Button "Neuer Auftrag" kann ein neuer Auftrag abgerufen werden. Es kann sich um einen Einlagerungsauftrag oder einen Auslagerungsauftrag handeln. Der Auftrag beinhaltet folgende Informationen: Produkt (siehe 3.), Belohnung (Wert in €). Es gibt keine Menge, es handelt sich immer um eine Einheit. Für die Erzeugung der Aufträge wird eine CSV-Datei bereitgestellt. Wenn das Ende der Datei erreicht ist, kann wieder mit Auftrag 1 begonnen werden. Falls Änderungen / Ergänzungen des CSV Datei notwendig werden, dokumentieren Sie dies in einer separaten Datei und geben Sie beide Dateien mit ab.

    Die Produkte:

Es gibt verschiedene Produkte mit jeweils verschiedenen Eigenschaften.

    Papier:

Eigenschaften: Farbe (Weiß, Grün, Blau), Größe (A3, A4, A5)

Besonderheiten: Keine

    Holz:

Eigenschaften: Art (Kiefer, Buche, Eiche), Form (Bretter, Balken, Scheit)

Besonderheiten: Holzbalken sind lang und werden daher auf drei Paletten verteilt. Ein gesamter Lagerplatz wird notwendig

    Stein:

Eigenschaften: Art (Marmor, Granit, Sandstein), Gewicht (Leicht, Mittel, Schwer)

Besonderheiten: Schwere Steine sind zu Schwer für das Regal und können nur unten eingelagert werden.

    Die Abwicklung:

Einlagerungsauftrag: Zu einem Auftrag werden die möglichen Lagerplätze dargestellt, also freie Lagerplätze. Nach Wahl eines Lagerplatzes wird die Palette eingelagert und die Belohnung gutgeschrieben.

Auslagerungsauftrag: Zu einem Auftrag werden die möglichen Lagerplätze dargestellt, also Lagerplätze, an dem genau dieses Produkt eingelagert ist. Nach Wahl eines Lagerplatzes wird die Palette ausgelagert und die Belohnung wird gutgeschrieben

    Die Lagerverwaltung:

Es ist möglich, dass ein Produkt zwar im Lager vorhanden ist, aber es wird durch ein anderes Produkt weiter vorne blockiert. Um dennoch an dieses Produkt heranzukommen, muss umgelagert werden. Dazu wird die gewünschte Palette ausgewählt. Mögliche Ziel-Lagerplätze werden dargestellt. Nach Wahl eines Ziel-Lagerplatzes wird die Umlagerung durchgeführt. Umlagerungen kosten 100 €. Des weiteren kann ein Produkt auch verschrottet werden, das kostet 500€.

    Das Auftragsmanagement:

Es können maximal drei Aufträge gleichzeitig bearbeitet werden (weitere Klicks auf "Neuer Auftrag"). So können Sie einen Auftrag auch zunächst einmal zurückstellen. Ein Auftrag kann auch abgelehnt werden, allerdings wird die Belohnung als Vertragsstrafe vom Kontostand abgezogen.

    Die Bilanz:

Natürlich muss immer der Kontostand sichtbar sein. Zusätzlich gibt es einen Button "Bilanz", der folgende Informationen anzeigt: Umsätze (Summe), Kosten (Summe) und Einzelbuchungen, also eine Tabelle mit jeder Veränderung des Kontostands. Jede Buchung ist mit einem Text (Einlagerungsauftrag, Auslagerungsauftrag, Umlagerung usw.) und einem Betrag versehen.
