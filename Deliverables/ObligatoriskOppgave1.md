# Team Fiasko
## Organisering av teamet

### Kompetanse

#### Petter Tobias Madsen
Relevante fag: INF101. 
Kan grunnleggende ting i Java men har ikke utført krevende prosjekter. 
Nesten ingen GUI-erfaring.

#### Steinar Aalstad Lillesund
Føler seg komfortabel med Java men ikke noe veldig avansert. 
Ingen GUI-erfaring.

#### Kristian Knarvik
Relevante fag: INF100, INF101 og INF102. 
Ganske erfaren i Java og har gjort ting med GUI før.

#### Torbjørn lunde jensen
Relevante fag: Tar INF101 inneværende semester.
Litt erfaring, men ikke veldig mye. 
Ingen GUI-erfaring.

#### Gabriel Ingolf Olav Magnus
Relevante fag: INF101 og INF102. 
Litt komfortable med Java.

### Oppsummering

Overall kompetanse i java koding ligger på medium mens erfaring med git eller gui er relativt lav. 

### Roller

#### Titler

Kristian -> Team leder (Ansvarlig for å vite hva alle gjør og burde gjøre til enhver tid) 

Tobias -> Kommunikasjonsleder ( planlegger møter og parprogrammerings timer)

Steinar -> Møte organisere (Ansvarlig for å organisere hva som skal gjennomgås på de avtalte møtene.)

Torbjørn -> Dokumentasjons ansvarlig (dokumenter møtene og sørger for at innleveringene er riktig)

Gabriel -> Kundeansvarlig (Ansvarlig for å vite alle spilleregler)

#### Grunnlag

Tobias -> Vi trenger noen som kan ta ansvar for å planlegge tidspunkt for møter 
          og kontaktperson for om noen ikke kan stille på møter.

Kristian -> Han har mest erfaring med kode og var en naturlig leder. han har overblikk over hva som skal bli gjort

Steinar -> skal organisere møtene slik at vi har en plan før møte. slik at vi ikke møter uten å ha noen plan

Torbjørn -> skal sørge for at det er korrekt dokumentasjon og bra nok dokumentasjon.   

Gabriel -> leder for spilleregler og ansvar for riktig spillmekanikk 

## Produktutforming

### Overordnet mål

Vi vil lage en fungerende digital versjon av RoboRally som er kompatibel med MAC, Windows og Linux.
Applikasjonen må kunne kjøre og fungere uten å ha noen ødeleggende feil eller mangler.
Det skal kunne fungere over LAN med opptil 8 spillere.
Spillet skal implementere alle spillereglene fra RoboRally (om tiden strekker til).

### Systemkrav

-   En visuell representasjon av et spillbrett.

-   En spiller som kan bevege seg på brettet.

-   En spiller taper når de mister alle liv.

-   En spiller dør om de tar 10 skade.

-   En spiller trenger liv. 

-   Spilleren trenger å kunne ta skade.

-   En spiller må kunne vinne.

-   Kort for å programmere roboten. 

-   Forskjellige funksjonelle objekter/ruter på kartet (eks. vegg, laser).

-   Runder (Består av 5 faser. Du får nye kort, reparasjon, powerdown etc.).

-   Faser (Ett kort fra hver spiller blir brukt, og objekter på brettet interagerer. 
    Registrering av flagg skjer etter hver fase).

-   Kunne spille med andre spillere over lan.

-   Lasere skyter i slutten av hver fase.

-   Spillere beveger seg hver fase og kort bestemmer hvem som går først.

-   Velge kort i starteren av runden.

-   Kunne velge powerdown etter alle har låst kortene sine.

-   Slutten av runden får spillere reparasjoner hvis de står på en reparasjonsbrikke eller flagg.

-   Sjekke om noen spillere står på riktig flagg.

-   Respawn etter en robot er ødelagt ved slutten av runden/fasen gitt at de har flere liv.

-   Hvis en spiller er i powerdown blir spilleren spurt (i starten av nye runden) om han vil bli i powerdown eller ikke.

-   Roboter må kunne dytte hverandre.

-   Samle inn kort i slutten av en runde.

-   Ikke samle sammen kort som er låst.

-   Vise hendelser som skjer på skjermen med en delay slik at spillet ser riktig ut.

-   Roboter dør hvis de går utenfor spillbrettet eller faller i ett hull.

### Prioriterte krav

1.  Vi har et brett tegnet inn med libgdx.
2.  Ha en brikke tegen inn med libgdx. 

### Prosjektmetodikk

-   2 fysiske møter i uke. En på gruppetimen og en på torsdager. 

-   Møter på discord online med skjerm deling for parprogrammering.

-   Syklus oppsett mellom hver oblig. sette opp oppgaver over hva som skal gjøres over hver syklus som i scrum oppsettet.

-   Vi skal ha en liten sprint som varer en uke hvor vi prøver å blir ferdig med alle oppgavene som blir tildelt på tirsdagene som i scrum oppsettet.

-   Vi tenker å ta en blanding av scrum og parprogrammering under prosjektet.

-   Tirsdager brukes for å re cape hva som har blitt gjort uken før 
    og for å avklare om vi føler oss ferdig med oppgaver.

-   Deling av dokumenter skjer over git eller discord.

-   Bruke project board for at vi skal ha kontroll over alle oppgavene og hvem som skal gjør hva.

-   Arbeids fordelese skal bli gjordt med hver enkeltes kompetanse og evner tatt i betraktning slik at en ikke blir for overbelastet under prosjektet.

#### Prosessen

-   Planlegger møter god stund før de skal skje.

-   Diskuter ting vi må ta felles på møter og planlegge arbeidoppgaver som skal gjøre til neste møte.

-   Vi har planlagt å ha parprogramerings timer slik at alle føler seg konfortable med oppgavene og at alle får bidratt med prosjektet.

-   Diskutere spill designe sammen i gruppen slik at alle er på samme nivå.

-   Torsdag er satt av for parprogrammering kl 10:15-12:00.

-   I slutten av en lange sprint (slutten av hver oblig) skal vi se over designet og koden i tilfelle vi føler vi må refakturere eller endere noen andre designe aspekter.

-   kommunikasjon skjer over slack og discrod.

#### organisering så langt

-   planlegger å jobbe deloppgavene fra oblig 1.

-   Sette opp møte tider og møte plasser samt. hva som skal bli gjordt.

-   Planlegger parprogrammerings økter for den første kode delen.

### Bruker historier

#### Vise et brett

-   Som en spiller må jeg kunne se et brett får å kunne spille spillet.
-   Som spillbrett må jeg vise alle elementer som er på meg 
-   Som spillbrett må jeg vær delt inn ruter/tiles der brikkene på meg må være i bare en rute av gangen.

##### Akseptansekrav for brett

-   Vi kan se et brett der noe kan bli plassert på brette i en rute/tile
  
#### Plassere en brikke på brettet

-   Som brikke må jeg kunne plassere på spillbrettet 
-   Som brikke må jeg være synlig på brettet

##### Akseptansekrav for brikke på brettet

-   Brikken blir plassert synlig i en bestemt tile på brettet

### Oppsumering

-   Etter denne først perioden er gruppe enig har vært litt kaotisk. dette kommer av at gruppen har hatt en del arbeid med å få til det 
adminastrative slik at vi kunne jobbe med metodikkene vi hadde valgt. dette forventer vi forbedre seg inn i neste sprint.

-   Det har også vært en del forandringer på rollene til medlemmene i teamet etter som vi har blitt bedere kjent med hverandre og funnet ut hvilken roller som hver enkelt heller burde ha.

-   Vi føler par programmering fungere bra og planlegger å fortsette med dette i neste sprint.

-   Vi føler at vi vil lage bedre og mer presisse Issus på projketboardet. etter som at noe av Issusene på boardet var for store og ikke spesisert nok 

-   Vi bruker travisy fordi den sjekker at alt virker og at coden kompilere.

-   Codasy sjekker at koden godt skrevet og at foramteringen er bra.

-   Vi har ikke tatt referat fra de tideligere møtene denne perioden fordi vi var ikke klar over at det var et krav før 
etter vi hadde hatt de fleste av møtene våre. men vi planlegger å gjøre det nå fremover.