## Brukerhistorier og akseptansekrav

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

- Som fase trenger jeg å kunne kjøre programmeringskort etter høyest verdi for å bestemme rekkefølgen på trekkene til robotene.
    - Programmeringskort er sorterbare.
    - Programmeringskort kan hentes og gis videre.
    
- Som tannhjul trenger jeg å kunen snu roboter for gjennomføre min funksjon.
    - Tannhjul vrir roboter som står på de 90 grader i retningen tannhjulet har.

- Som laser trenger jeg å kunne avfyre laseren min for å gjøre skade på roboter.
    - Laser skyter i en rett linje i den rettningen laseren har.
    - Laseren blir stoppet av vegger og andre roboter.
    - Laseren gjør skade på roboten den treffer.

- Som spiller trenger jeg å kunne registrere flagg for å kunne vinne spillet.
    - Ved slutten av en fase vil roboter som står på et flagg få oppdatert currentflagg verdien sin, gitt at den har besøkt det forige flagget.
    - Spilleren vinner når alle flagg er besøkt i rett rekkefølge.

- Som runde trenger jeg å kunne kjøre faser for å progresere spillet.
    - Runden kjører fem faser.

- Som spiller trenger jeg å få tildelt programmeringskort for å kunne lage et program.
    - Del ut programmeringskort fra spillet sitt dekk til hver spiller.
    - Spillere får anntall programmeringskort utifra hvor mye skade de har blitt påført.

- Som spiller trenger jeg å få mulighet til å annonsere powerdown for at roboen min vil kunne ta en powerdown.
    - Spiller må få spørsmål i starten av en runde om de vil gå i powerdown neste runde.
    - Roboten til spilleren må gå i powerdown om spilleren annonserte det forige runde.

- Som robot trenger jeg å kunne gå i powerdown for å kunne kurere all skade.
    - I starten av runden settes skaden til roboter som skal i powerdown til null.

- Som reperasjonstile trenger jeg å kunne fikse skade på roboter for å gjennomføre min funksjon.
    - Roboter som står på en reperasjonstile i slutten av en runde, får fjernet en skade.
    - Reperasjonstiles er: Flagg, skiftesnøkkel og skiftesnøkkel + hammer.

- Som spiller må jeg kunne levere inn mine gamle ulåste kort for å få nye kort.
    - Ulåste kort blir lagt tilbake i spillkortstokken etter runden er ferdig.

- Som spiller må jeg få mulighet til å fortsette min powerdown for å kunne fikse eventuell ny skade.
    - Spillere som er i powerdown får mulighet i starten av runden om å fortsette powerdown eller ikke.

- Som spiller må roboten min kunne bli respawnet for å kunne ta del i spillet videre, gitt jeg har flere liv.
    - En robot som dør får ett mindre liv.
    - Hvis roboten er tom for liv, taper spilleren dens.
    - Når en robot dør, gjennopliver den på sin backup position.

- Som spiller må registerene mine bli låst utifra hvor mye skade jeg har, for å representere en skadet robot.
    - Roboter med mer enn fire skade, får låst anntall register over fire skade. (robot damage - 4)
    - Kort som står i låste register blir ikke samlet inn og kan ikke endres.
    
## Arbeidsoppgaver

#### Spiller

- Lag en klasse som representerer en kortstokk.
- Legg til en kortstokk i spillerklassen.
- Lag en representasjon av registere.
- Legg til en representasjon av programmeringskort.

#### Runde

- Legg til en funksjon for å kjøre fem faser.
- Legg til funksjon for å tildele programmeringskort til alle spillere.
- Legg til en funksjon for å gi spiller en mulighet for å gå i powerdown.
- Legg til funksjon for å gå i powerdown.
- Legg til en funksjon som aktiverer alle reperasjonstiles og reparere roboter som står der.
- Legg til funksjon for å gi spillere som er i powerdown mulighet for å fortsette i powerdown.
- Legg til funksjon for å samle inn kort som ikke er låst.
- Legg til en funksjon som gjennopliver roboter som har flere liv.
- Legg til en funksjon for å låse register.

#### Fase

- Legg til funksjon for å sortere programmeringskort utifra høyest verdi.
- Legg til funksjon for å aktivere tannhjul.
- Lage en funksjon for å fyre av en laser i en retning.
- Legg til en funksjon for å sjekke om en robot står på et flagg.
- Legg til en funksjon for å sjekke om roboten har besøkt forige flagg.
- Legg til en funksjon som sjekker flagstatus til en robot.
- Legge til funksjon for å aktivere transportbånd.
- Legg til en fase i robotrallygame som aktiverer alle objekter som skal bli aktivert i en fase.