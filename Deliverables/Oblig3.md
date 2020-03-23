# Team Fiasko

## Deloppgave 1
### Rollefordeling
Rollefordelingen i teamet fungerer fint sånn som vi har det.

### Erfaringer
Siden vi har fra begynnelsen av prosjektet valgt å ha noen av møtene og parprogrammeringsøktene våre på Discord,
var vi bedre rustet til utfordringene som kom med SARS-CoV-2 pandemien og de inførte restriksjoner
det har medbragt.
    
### Retrospektiv
#### Plan
Det vi planlagte var å ha to fysiske møter og ett digitalt møte per korte sprint (en uke) 
for parprogrammering, planlegging og generell diskusjon rundt prosjektet.\
Vi planlagte også å lage ferdig alle brukerhistoriene, akseptansekravene og 
arbeidsoppgavene før vi begynte noe av kodearbeidet.\
Arbeidsoppgaver skulle settes opp som issues i prosjektet på GitHub med gode beskrivelser 
for så å legges til prosjekttavlen. Når noen i teamet skal begynne på en ny arbeidsoppgave
legger de seg til i oppgaven på prosjekttavlen for så å flytte oppgaven fra To do til In progress kolonnen.

#### Gjennomførelse
Vi har hatt noen fysiske møter før SARS-CoV-2 krisen startet. Disse gikk med til en del planlegging
og jobbing med brukerhistorier. Etter dette beholdt vi møtetids oppsettet men alle møter ble digitale.\
I denne sprinten har vi brukt en del digital parprogrammering med skjermdeling som har fungert bra for oss.\
Ellers har vi gjennomført planen som planlagt.

#### Forbedringspunkter
*   Forbedre bruk av tester. Bruke mer test driven development. Vi har til nå skrevet teser etter vi har skrevet kode
    noe som har ført til noen bugs som kanskje kunne vært unngått med tdd.
    
### Prioritering av oppgaver
Vi må bli ferdig med runder og kortvelging først. Etter dette prioriterer vi å få nettverksfunksjonaliteten på plass.
Til slutt vil vi prioritere det grafiske og eventulle nice to haves.

### Gruppedynamikk og kommunikasjon
Vi har noen uenigheter når det gjelder effektiv tidsbruk på møter og hvor mye tid hver enkelt skal bruke på
prosjektet ut over de fastsatte møtetidene som utgjør ca 8 timer i uken.

## Deloppgave 2
### Krav
Implementere fase og alt av funksjoner en fase kommer til trenge.

### Brukerhistorier og akseptansekrav

- Som spiller må jeg ha en kortstokk for å oppbevare kortene mine.
    - Spilleren har en funksjonell kortstokk.

- Som spiller trenger jeg programmeringskort for å programmere roboten min.
    - Programmeringskort har en handling og en prioritet.

- Som spiller trenger jeg et program for å fortelle roboten min hva den skal gjøre iløpet av en runde.
    - Spilleren har et funksjonellt program.
    - Skal kunne hente ut programmet fra en spiller.

- Som fase trenger jeg å kunne aktivere andre objekter for å progresere spillet.
    - En fase kan gjennomføres.
    - Aktiverte objekter gjør sin funksjon.

- Som transportbånd trenger jeg å kunne flytte roboter for å gjøre min funksjon.
    - Transportbånd flytter robot i gitt retning.
    - Transportbånd kan rotere roboter i spesefikke scenario.

- Som fase trenger jeg å kunne kjøre programmeringskort etter høyest verdi for å bestemme 
    rekkefølgen på trekkene til robotene.
    - Programmeringskort er sorterbare.
    - Programmeringskort kan hentes og gis videre.
    
- Som tannhjul trenger jeg å kunen snu roboter for gjennomføre min funksjon.
    - Tannhjul vrir roboter som står på de 90 grader i retningen tannhjulet har.

- Som laser trenger jeg å kunne avfyre laseren min for å gjøre skade på roboter.
    - Laser skyter i en rett linje i den rettningen laseren har.
    - Laseren blir stoppet av vegger og andre roboter.
    - Laseren gjør skade på roboten den treffer.

- Som spiller trenger jeg å kunne registrere flagg for å kunne vinne spillet.
    - Ved slutten av en fase vil roboter som står på et flagg få oppdatert currentflagg verdien sin,
     gitt at den har besøkt det forige flagget.
    - Spilleren vinner når alle flagg er besøkt i rett rekkefølge.

### Arbeidsoppgaver
#### Spiller
- Lag en klasse som representerer en kortstokk.
- Legg til en kortstokk i spillerklassen.
- Lag en representasjon av registere.
- Legg til en representasjon av programmeringskort.

#### Fase
- Legg til funksjon for å sortere programmeringskort utifra høyest verdi.
- Legg til funksjon for å aktivere tannhjul.
- Lage en funksjon for å fyre av en laser i en retning.
- Legg til en funksjon for å sjekke om en robot står på et flagg.
- Legg til en funksjon for å sjekke om roboten har besøkt forige flagg.
- Legg til en funksjon som sjekker flagstatus til en robot.
- Legge til funksjon for å aktivere transportbånd.
- Legg til en fase i robotrallygame som aktiverer alle objekter som skal bli aktivert i en fase.