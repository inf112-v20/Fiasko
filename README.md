# Robo Rally [![Build Status](https://travis-ci.com/inf112-v20/Fiasko.svg?branch=master)](https://travis-ci.com/inf112-v20/Fiasko) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/51d37dc99db44758944198a59f2d2a4b)](https://www.codacy.com/gh/inf112-v20/Fiasko?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/Fiasko&amp;utm_campaign=Badge_Grade)
Dette prosjektet har som mål å lage en digital kopi av brettspillet Robo Rally.  Foreløbig er målet å implementere all 
nødvendig funksjonalitet for å kunne spille et "spill" Robo Rally over lan med opp til 8 spillere. Om tiden strekker til,
vil vi gå over til en fase der målet blir å implementere ting som er gøy, men ikke nødvendig.

Alle regler er hentet fra 2005 utgaven av spillguiden fra Wizards of the Coast, Inc.

## Spillstatus
Ved kjøring av .jar filen blir det vist en "splash screen" og kjørt en demo når brukeren trykker venstre musetast, 
uten mulighet for bruker å bevege robot.

## Manuell testing
Demoen er definert i RoboRallyGame sin metode runGameLoop(). Metodene som kan brukes for testing er:
-   makeMove(Robot id, Korthandling) //Utfører en handling på en robot
-   fireAllLasers() //Fyrer av alle lasere inkludert robotlasere
-   moveAllConveyorBelts() //Flytter alle transportbånd
-   checkAllFlags() //Oppdaterer roboter som besøker flagg
-   rotateCogwheels() //Roterer tannhjul

Robot id blir representert ved enumen RobotID
Korthandling blir representert ved enumen Action

Ved å bruke metodene over kan alt i en eller flere faser testes og simuleres. Den store forskjellen fra MVP er at all
bruker-input blir hardkodet før programmet kjører.

Eksempel på makeMove: makeMove(RobotID.ROBOT_1, Action.MOVE_1); //Flytter robot 1 ett steg fremover

Linjene som inneholder runPhase(n) kjører spesifikke faser som bruker tilfeldig utdelte programmeringskort uten 
integritetsvalidering. Disse bør kommenteres ut under testing.

Robotene sin posisjon blir initialisert i RoboRallyGame sin metode initializeGame() og kan endres dersom det er 
fordelaktig å starte dem på andre posisjoner.

Brettet blir også lastet inn i RoboRallyGame sin metode initializeGame(). Det er 3 brett som er relevante å teste på:
-   Checkmate
-   Dizzy_Dash
-   Risky_Exchange

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