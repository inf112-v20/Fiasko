# Robo Rally [![Build Status](https://travis-ci.com/inf112-v20/Fiasko.svg?branch=master)](https://travis-ci.com/inf112-v20/Fiasko) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/51d37dc99db44758944198a59f2d2a4b)](https://www.codacy.com/gh/inf112-v20/Fiasko?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/Fiasko&amp;utm_campaign=Badge_Grade)
Dette prosjektet har som mål å lage en digital kopi av brettspillet Robo Rally.  Foreløbig er målet å implementere all 
nødvendig funksjonalitet for å kunne spille et "spill" Robo Rally over lan med opp til 8 spillere. Om tiden strekker til,
vil vi gå over til en fase der målet blir å implementere ting som er gøy, men ikke nødvendig.

Alle regler er hentet fra 2005 utgaven av spillguiden fra Wizards of the Coast, Inc.

## Spillstatus
Ved kjøring av .jar filen blir det kjørt en demo, uten mulighet for bruker å bevege robot.

## Knapper og kontrollmekanismer
### Knapper
-   Q: Tilbakestiller kamera og kamerarotasjon
-   R: Roterer kameraet
-   HOME: Bytter til en debug instans av spillet som lar en sjekke at alle teksturer vises riktig

### Andre egenskaper ved brukergrensesnittet
-   Rullehjulet vil forstørre og forminske spillbrettet
-   Spillbrettet kan flyttes ved å holde inne venstre musetast og bevege musen i en retning

## Bygging og kompilering

### Forkrav for å kunne kompilere og kjøre koden
-   [java](https://www.java.com/en/download/)
-   [git](https://git-scm.com/)
-   [maven](https://maven.apache.org/)

### Byggeprosedyre
```shell script
git clone https://github.com/inf112-v20/Fiasko.git
cd Fiasko
mvn clean install
```
Dette vil også kjøre alle tester i koden

### Kjøreprosedyre
```shell script
cd target
java -jar roborally-0.2-alpha-jar-with-dependencies.jar
```
Den produserte .jar filen kan alternativt flyttes til en annen mappe og dobbeltklikkes for å kjøres.