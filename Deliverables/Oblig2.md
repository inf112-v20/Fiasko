# Team Fiasko

## Deloppgave 1

*   Rollene har fungert greit fram til nå
### Tobias Kommunikasjonsleder
#####   Har ansvar for å organisere gruppe møte og par programering møter.
*   rollen innebærer å finn ut hvilken dagen folk kan møte og om vi burde møte i person eller har møte digitalt på discord.
*   jeg får også beskjed om noen er syk slik at gruppen kan ta det i betraktning under møte.
### Kristian Teamleder
#####   Ansvarlig for å vite hva alle gjør og burde gjøre til enhver tid
*   har holdet stry på projektboardet.
*   har hatt kontroll over hvem som skal gjør hva av oppgavene på projektboardet.
*   har kontroler kvaliteten på andre sitt arbeid
### Steinar Møteorganisator
#####   Ansvarlig for å organisere hva som skal gjennomgås på de avtalte møtene.
*   kommer med en liten plan for hva vi skal snakk om på møtene.
*   kontrolere at møte holder se ginnenfor agendaen.
### Gabriel Kundekontakt
#####  Ansvarlig for å vite alle spilleregler
*   må svare på spårsmål fra de andre på gruppen angående spill reglene.
*   må ha kontroll over de forskjelige mekanikkene i spillet.
### Torbjør Dokumentasjonsansvarlig
#####  Dokumenterer møtene og sørger for at innleveringene er riktig


*   Teamet fungerer fint og oppsettet vi har valgt fungerer fint for oss for tiden.
*   Alle blir hørt og det er åpent for å komme med tilbakemeldinger på ting som kan bli bedre
*   Kommunikasjonen fungerer bra
*   kan bli bedre til å ta insiativet med å begynne på oppgaver
*   Vi synes det var en god idea å planlegge klasse strukturne felles i et gruppe rom. det ga alle en god oversikt over
hva de forskjellige klassen måtte inneholde.

*   Vi har brukt parprogramering som som har gjordt at commitsene har blitt veldig forskjøve
*   En del har blitt diskuter i gruppe der bare en har gjort alle notatene.

### forbedringpunkter
*   Jevnere commits fra alle på laget
*   Bli bedre på brukerhistorier
## Deloppgave 2

#### Krav
1.   En spiller som kan bevege seg på brettet.
2.   Forskjellige funksjonelle objekter/ruter på kartet (eks. vegg, laser).
3.   Roboter dør hvis de går utenfor spillbrettet eller faller i ett hull.
4.   Roboter må kunne dytte hverandre.
5.   Roboten trenger å kunne ta skade
6.   En robot trenger liv
7.   En robot som er ødelagt vil komme tilbake i en backup position med to i skade.
8.   Vise hendelser som skjer på skjermen med en forsinkelse slik at spilleren ser bevegelsene i spillet
#### Bruker historier for krav 1
*Brukerhistorie* 
*   Som robot trenger jeg posisjon for å ha kontroll på hvor jeg er og hvor jeg skal.

*Akseptansekrav*
*   Vi har en klasse som lagrer en posisjon for alle roboter på brettet.
*   Posisjoner består av x og y koordinater.
*   Roboten lagrer en instanse av positions classen.

*Brukerhistorie*
*   Som spiller trenger jeg en robot for å kunne spille spillet

*Akseptansekrav*
*   Vi har en reprensentasjon av en robot. som kan bli plaseret på brettet og flyttet på.


*Arbeidsoppgaver*
*   Lag en klasse som representer en position.
*   Roboten må inneholde en position
*   Lag en klasse som representerer en robot.

#### Bruker historier for krav 2

*Brukerhistorie*
*   Som brett må jeg kunne ha forskjellige tiles for å kunne vise alle aspektene av spillet

*Akseptansekrav*
*   Å kunne plassere en type tile på brettet
*   Brette må kunne vite hvilken tile som er på en bestemt position

*Brukerhistorie*
*   Som vegg må jeg stoppe en robot fra å gå gjennom meg for hindre dem å gjøre et ugyldig trekk
*   Som vegg må jeg kunne bli plassert i forskjellige retninger for å kunne bestemme den funksjonelle retningen til veggen

*Akseptansekrav*
*   En robot som prøver å gå gjennom en vegg blir stoppet.
*   kunne plasere vegger i forskjelige retninger

*Arbeidsoppgaver*
*   Lag en representasjon av en vegg
*   Endre bevegelses metoden til roboten slik at den følger funksjonaliteten til vegger 
*   Kunne plasere veggen i forskjellige retninger
*   Leg til en represntation av et hull.
*   Lag en representasjon av en tile
*   Legg til en metode i brettet som forteler oss hvilken tile som er i en bestemt position
*   Legg til en måte får å kunne lage et brett med forskjelige tiles i spesifikke positioner

#### Bruker historier for krav 3

*Brukerhistorie*
*   Som spiller må roboten min kunne dø, for å håntere om roboten tar for mye skade eller går i et hull/av kartet

*Akseptansekrav*
*   Roboten mister et liv hvis den går i et hull eller av brettet
*   Roboten blir fjernet fra brettet

*Arbeidsoppgaver*
*   legg til en metdoe som fjerner liv fra roboten
*   legg til en metode som fjerner en robot fra brettet
*   legg til en metode som sjekker positionen til roboten for å se om den er gått i et hull eller om den har gått 
utenfor brettet

#### Bruker historier for krav 4
*Brukerhistorie*
*   Som robot må jeg kunne dytte en annen robot hvis det er lovlig å dytte roboten, for å kunne gjøre et gyldig trekk

*Akseptansekrav*
*   Hvis en robot beveger seg til en position hvor det er en annen robot blir den andre roboten bli dytte så lenge den 
kan bli dyttet.

*   hvis den andre roboten blir blokkert av en vegg så kan den ikke bli dyttet og ingen av robotene flytter på seg.
*   hvis det er flere roboter på rekke så gitt at det ikke er noen vegg som stopper de blir alle robotene flyttet.

*Arbeidsoppgaver*
*   legg til funksjonalitet for å sjekke om det er en annen robot i veien.
*   legg til funksjonalitet for å sjekke om den andre roboten kan bli dyttet i retningen den første roboten peker.
*   legg til funksjonalitet for å dytte roboter

#### Bruker historier for krav 5
*Brukerhistorie*
*   Som spiller må roboten min kunne ta skade, for å håntere påvirkning fra objekter som avgir skade til roboter

*Akseptansekrav*
*   roboten sin skade verdi blir økt hvis den tar skade.

*Arbeidsoppgaver*
*   legg til en skade verdi i roboten som holder styr på mengden skade roboten har.
*   legg til en metode for å sette skaden til roboten.
*   legg til en metode for å hente ut skaden til roboten.

#### Bruker historier for krav 6
*Brukerhistorie*
*   Som spiller trenger roboten min liv for å kunne tape

*Akseptansekrav*
*   Roboten har en verdi som reprensenterer mengden liv den har

*Arbeidsoppgaver*
* Legg til en liv verdi i robot klassen.


#### Bruker historier for krav 7
*Brukerhistorie*
*   Som spiller må roboten min kunne gjennopstå om den har flere liv, for å kunne spille videre

*Akseptansekrav*
*   En dø robot respawner i en backup position om den har flere liv.
*   Roboten har 2 i skade etter den respawner

*Arbeidsoppgaver*
*   Lag en metode som respawner en robot i en backup position og som setter skaden til roboten til 2

#### Bruker historier for krav 8
*Brukerhistorie*
*   Som spiller trenger jeg at det er litt forsinkelse i spiller slik at jeg kan se hva som faktisk skjer.

*Akseptansekrav*
*   det er en forsinkelse mellom hver handlig som skjer i spillet slik at vi kan følge med på hva som skjer.

*Arbeidsoppgaver*
*   legg til en forsinkelse mellom hver handling
*   sørg for at forsinkelsen ikke kresjer spillet.

### vidre med del 2

*   vi har prioriter å lage spillet fra bunnen av og opp siden noen deler av spillet er avhenger andre.
*   
*   













