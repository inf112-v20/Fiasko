# Team Fiasko

## Deloppgave 1

*   Rollene har fungert greit fram til nå.

### Tobias 
#### Kommunikasjonsleder
##### Har ansvar for å organisere gruppe møte og par programering møter
*   Rollen innebærer å finn ut hvilken dager folk kan møte og om vi burde møte i person eller ha møte digitalt på discord.
*   Jeg får også beskjed om noen er syk slik at gruppen kan ta det i betraktning under møte.

### Kristian 
#### Teamleder
##### Ansvarlig for å vite hva alle gjør og burde gjøre til enhver tid
*   Har holdet stry på projektboardet.
*   Har hatt kontroll over hvem som skal gjøre hvilken av oppgavene på prosjekttavlen.
*   Har kontrolert kvaliteten på andres arbeid.

### Steinar 
#### Møteorganisator
##### Ansvarlig for å organisere hva som skal gjennomgås på de avtalte møtene
*   Kommer med en liten plan for hva vi skal snakke om på møtene.
*   Kontrolerer at møte holdes innenfor agendaen.

### Gabriel 
#### Kundekontakt
##### Ansvarlig for å vite alle spilleregler
*   Må svare på spårsmål fra de andre på gruppen angående spillereglene.
*   Må ha kontroll over de forskjellige mekanikkene i spillet.

### Torbjørn 
#### Dokumentasjonsansvarlig
##### Dokumenterer møtene og sørger for at innleveringene er riktig
*   Sørger for at referater blir skrevet i forbindelse med møter.
*   Har ansvar for at dokumentasjon i og utenfor kode er god nok.
*   Ser over rettskrivning og fikser markdown issues.

### Videre med deloppgave 1
*   Teamet fungerer fint og oppsettet vi har valgt fungerer fint for oss for tiden.

*   Alle blir hørt og det er åpent for å komme med tilbakemeldinger på ting som kan bli bedre.

*   Kommunikasjonen fungerer bra.

*   Kan bli bedre til å ta initiativet med å begynne på oppgaver.

*   Vi synes det var en god idea å planlegge klasse strukturne felles i et gruppe rom. Det gav alle en god oversikt over
hva de forskjellige klassen måtte inneholde.

*   Vi har brukt parprogramering som har gjordt at commitsene har blitt veldig forskjøvet.

*   En del har blitt diskuter i gruppe der bare en har gjort alle notatene.

### forbedringpunkter
*   Jevnere commits fra alle på laget
*   Bli bedre på brukerhistorier

## Deloppgave 2

### Krav
1.  En spiller som kan bevege seg på brettet.
2.  Forskjellige funksjonelle objekter/ruter på kartet (eks. vegg, laser).
3.  Roboter dør hvis de går utenfor spillbrettet eller faller i et hull.
4.  Roboter må kunne dytte hverandre.
5.  Roboten trenger å kunne ta skade.
6.  En robot trenger liv.
7.  En robot som er ødelagt vil komme tilbake i en backupposisjon med to i skade.
8.  Vise hendelser som skjer på skjermen med en forsinkelse slik at spilleren ser bevegelsene i spillet.

#### Brukerhistorier for krav 1
*Brukerhistorie* 
*   Som robot trenger jeg posisjon for å ha kontroll på hvor jeg er og hvor jeg skal.

*Akseptansekrav*
*   Vi har en klasse som lagrer en posisjon for alle roboter på brettet.
*   Posisjoner består av x og y koordinater.
*   Roboten lagrer en instanse av positions klassen.

*Brukerhistorie*
*   Som spiller trenger jeg en robot for å kunne spille spillet.

*Akseptansekrav*
*   Vi har en reprensentasjon av en robot. som kan bli plaseret på brettet og flyttet på.

*Arbeidsoppgaver*
*   Lag en klasse som representer en position.
*   Roboten må inneholde en position.
*   Lag en klasse som representerer en robot.

#### Brukerhistorier for krav 2

*Brukerhistorie*
*   Som brett må jeg kunne ha forskjellige tiles for å kunne vise alle aspektene av spillet.

*Akseptansekrav*
*   Å kunne plassere en type tile på brettet.
*   Brette må kunne vite hvilken tile som er på en bestemt position.

*Brukerhistorie*
*   Som vegg må jeg kunne stoppe en robot fra å gå gjennom meg for å hindre den i å gjøre et ugyldig trekk.

*   Som vegg må jeg kunne bli plassert i forskjellige retninger for 
å kunne bestemme den funksjonelle retningen til veggen.

*Akseptansekrav*
*   En robot som prøver å gå gjennom en vegg blir stoppet.
*   Kunne plassere vegger i forskjelige retninger.

*Arbeidsoppgaver*
*   Lag en representasjon av en vegg.
*   Endre bevegelsesmetoden til roboten slik at den følger funksjonaliteten til vegger.
*   Kunne plassere veggen i forskjellige retninger.
*   Legg til en representasjon av et hull.
*   Lag en representasjon av en tile.
*   Legg til en metode i brettet som forteller oss hvilken tile som er i en bestemt posisjon.
*   Legg til en måte for å kunne lage et brett med forskjellige tiles i spesifikke posisjoner.

#### Brukerhistorier for krav 3

*Brukerhistorie*
*   Som spiller må roboten min kunne dø, for å håntere om roboten tar for mye skade eller går i et hull/av kartet.

*Akseptansekrav*
*   Roboten mister et liv hvis den går i et hull eller av brettet.
*   Roboten blir fjernet fra brettet.

*Arbeidsoppgaver*
*   Legg til en metdoe som fjerner liv fra roboten.

*   Legg til en metode som fjerner en robot fra brettet.

*   Legg til en metode som sjekker positionen til roboten for å se om den er gått i et hull eller om den har gått 
utenfor brettet.

#### Brukerhistorier for krav 4
*Brukerhistorie*
*   Som robot må jeg kunne dytte en annen robot hvis det er lovlig å dytte roboten, for å kunne gjøre et gyldig trekk.

*Akseptansekrav*
*   Hvis en robot beveger seg til en position hvor det er en annen robot vil den andre roboten bli dyttet så lenge den 
kan bli dyttet.

*   Hvis den andre roboten blir blokkert av en vegg så kan den ikke bli dyttet og ingen av robotene flytter på seg.

*   Hvis det er flere roboter på rekke så gitt at det ikke er noen vegg som stopper de blir alle robotene flyttet.

*Arbeidsoppgaver*
*   Legg til funksjonalitet for å sjekke om det er en annen robot i veien.
*   Legg til funksjonalitet for å sjekke om den andre roboten kan bli dyttet i retningen den første roboten peker.
*   Legg til funksjonalitet for å dytte roboter.

#### Brukerhistorier for krav 5
*Brukerhistorie*
*   Som spiller må roboten min kunne ta skade, for å håntere påvirkning fra objekter som avgir skade til roboter.

*Akseptansekrav*
*   Roboten sin skadeverdi blir økt hvis den tar skade.

*Arbeidsoppgaver*
*   Legg til en skadeverdi i roboten som holder styr på mengden skade roboten har.
*   Legg til en metode for å sette skaden til roboten.
*   Legg til en metode for å hente ut skaden til roboten.

#### Brukerhistorier for krav 6
*Brukerhistorie*
*   Som spiller trenger roboten min liv for å kunne tape.

*Akseptansekrav*
*   Roboten har en verdi som reprensenterer mengden liv den har.

*Arbeidsoppgaver*
*   Legg til en livverdi i robot klassen.

#### Brukerhistorier for krav 7
*Brukerhistorie*
*   Som spiller må roboten min kunne gjennopstå om den har flere liv, for å kunne spille videre.

*Akseptansekrav*
*   En dø robot gjennoppstår i en backup posisjon om den har flere liv.
*   Roboten har 2 i skade etter den gjennoppstår.

*Arbeidsoppgaver*
*   Lag en metode som respawner en robot i en backup posisjon og som setter skaden til roboten til 2.

#### Brukerhistorier for krav 8
*Brukerhistorie*
*   Som spiller trenger jeg at det er litt forsinkelse i spiller slik at jeg kan se hva som faktisk skjer.

*Akseptansekrav*
*   Det er en forsinkelse mellom hver handlig som skjer i spillet slik at vi kan følge med på hva som skjer.

*Arbeidsoppgaver*
*   Legg til en forsinkelse mellom hver handling
*   Sørg for at forsinkelsen ikke kræsjer spillet.

### Vidre med del 2

*   Vi har prioritert å lage spillet fra bunnen av og opp siden noen deler av spillet er avhenger andre.

*Hovedkrav til MVP*

*   Alle de syv første kravene føler vi er en del av MVP, mens krav 8: Vise hendelser som skjer på skjermen med en
forsinkelse slik at spilleren ser bevegelsene i spillet, ikke er nødvendig for at spillet skal fungere.
 
*   Vi har fulgt prioritering av kravene i MVP, men også implementert noen "nice to have" som vi synes er viktig for
spilleropplevelsen.


*   Siden sist har vi jobbet mer på brett og spilleren og jobbet med krav 1-7.

*   For å se spesifike oppgaver som er gjort, sjekk prosjekttavlen på github.

*   Vi vet ikke om noen bugs i koden. Alle kjente bugs har blitt fikset underveis.

## UML
![UML](../Deliverables/Umls/UmlOblig2.png "UML Oblig 2")
![UML](../Deliverables/Umls/UmlOblig2WithoutDependencies.png "UML Oblig 2 uten avhengigheter")