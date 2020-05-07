# Team Fiasko

## Deloppgave 1
### Rollefordeling
Rollefordelingen i teamet fungerer fint slik som vi har det.

### Erfaringer
*   Vi burde kommunisert med gruppeleder på et tidligere tidspunkt for å klarere hvilket krav som 
    faktisk skal møtes til innlevering.

*   Vi skulle passet mer på i starten å ikke lage isfjell ved oppdeling av arbeidsoppgaver.

*   Det er viktig å alltid lage brukerhistorier før implementering.

### Retrospektiv
#### Plan
Det vi planlaa var å ha tre digitale møter per korte sprint (en uke) for parprogrammering, 
planlegging og generell diskusjon rundt prosjektet. Vi bruker oppsettet til scrum med parprogrammering lagt til.
Dette var metodikken vi planlagte å bruke fra starten av prosjektet og det har fortsatt å være dette oppsettet vi 
har valgt bruke gjennom hele prosjektet.
Vi valgte å bruke scrum fordi oppsettet passer fint for tidsperiodene mellom obligene på prosjektet, der vi da kan ha 
korte sprinter på en uke hver og lange sprinter i tidsrommet fra oblig til oblig. 
Vi planla også å lage ferdig alle brukerhistoriene, akseptansekravene og 
arbeidsoppgavene før vi begynte noe av kodearbeidet.
Arbeidsoppgaver skulle settes opp som issues i prosjektet på GitHub med gode beskrivelser 
for så å legges til prosjekttavlen. Når noen i teamet skal begynne på en ny arbeidsoppgave skulle de
legge seg til i oppgaven på prosjekttavlen for så å flytte oppgaven fra "To do" kolonnen til "In progress" kolonnen.

#### Gjennomførelse
Vi har gjennomført planen vår som planlagt.
Det vi syntes at vi har gjort bra er at (nesten) alle har vært tilstede på alle møter vi har hatt.

#### Forbedringspunkter
Vi ville kommunisert med gruppeleder på et tidligere tidspunkt for å klarere hvilke krav som faktisk skal møtes 
til innlevering, i tillegg til å sjekke retteskjemet. Vi har lært det er viktig å dele opp arbeidsoppgaver i passelige 
biter, for å unngå isfjell.
Alltid lag brukerhistorie før implementering, et problem vi hadde i starten. 
Ha tester som automatisk sjekker at alt funker som det skal, og lag manuelle tester for det som ikke 
kan testes automatisk. Tenk på funksjonalitet i forhold til lettvinthet ved implementasjon av ting som multiplayer. 
Selv når vi kodet med multiplayer in mind, måtte vi endre en del for å støtte klient og server funksjonalitet.

Prosjektmetodikken vi har valgt ville vi også brukt hvis vi måtte begynt på nytt.

### Gruppedynamikk og kommunikasjon
Kommunikasjonen innad i teamet har i all hovedsak fungert fint.
Vi brukte allerede online møter før nedstenging, så da gikk vi fra to timer til 8 timer online møte i uken. 
Vi jobber typisk først sammen for så å dele oss opp i flere samtaler ut ifra hvem som jobber sammen.
Visualisering i form av tavle ble noe vanskeligere når vi måtte bruke skjermdeling av paint i stedet for 
en fysisk tavle. Når vi ikke hadde fysisk møter, så vi heller ikke gruppeleder, noe som førte til mindre kommunikasjon.
Når vi sitter i forskjellige samtaler er det også vanskeligere for teamleder å ha oversikt over hele gruppen. 
Det tar lenger tid å få hjelp enn om vi satt i samme rom.

## Deloppgave 2
### Brukerhistorier for en runde
#### Brukerhistorie 1
*   Som runde trenger jeg å kunne kjøre faser for å progresere spillet.

*Akseptansekrav*
*   Runden kjører fem faser.

*Arbeidsoppgaver*
*   Legg til en funksjon for å kjøre fem faser.

#### Brukerhistorie 2
*   Som spiller trenger jeg å få tildelt programmeringskort for å kunne lage et program.

*Akseptansekrav*
*   Del ut programmeringskort fra spillet sin kortstokk til hver spiller.
*   Spillere får antall programmeringskort ut ifra hvor mye skade de har blitt påført.

*Arbeidsoppgaver*
*   Legg til funksjon for å tildele programmeringskort til alle spillere.

#### Brukerhistorie 3
*   Som spiller trenger jeg å få mulighet til å annonsere powerdown for at roboten min vil kunne ta en powerdown.

*Akseptansekrav*
*   Spiller må få spørsmål i starten av en runde om de vil gå i powerdown neste runde.
*   Roboten til spilleren må gå i powerdown om spilleren annonserte det forrige runde.

*Arbeidsoppgaver*
*   Serveren må kunne sende programmeringskortene, spiller listen og brett navnet til alle klientene og 
alle klientene må forvente å mota dette objektet.

*   Legg til en funksjon for å gi spiller en mulighet for å gå i powerdown.
 
#### Brukerhistorie 4
*   Som robot trenger jeg å kunne gå i powerdown for å kunne kurere all skade.

*Akseptansekrav*
*   Starten av runden settes skaden til roboter som skal i powerdown til null.

*Arbeidsoppgaver*
*   Legg til funksjon for å gå i powerdown.

#### Brukerhistorie 5
*   Som reperasjonstile trenger jeg å kunne fikse skade på roboter for å gjennomføre min funksjon.

*Akseptansekrav*
*   Roboter som står på en reperasjonstile i slutten av en runde, får fjernet en skade.
*   Reperasjonstiles er: Flagg, skiftenøkkel og skiftenøkkel + hammer.

*Arbeidsoppgaver*
*   Legg til en funksjon som aktiverer alle reperasjonstiles og reparere roboter som står der.

#### Brukerhistorie 6
*   Som spiller må jeg kunne levere inn mine gamle ulåste kort for å få nye kort.

*Akseptansekrav*
*   Ulåste kort blir lagt tilbake i spillkortstokken etter runden er ferdig.

*Arbeidsoppgaver*
*   Legg til funksjon for å samle inn kort som ikke er låst.

#### Brukerhistorie 7
*   Som spiller må jeg få mulighet til å fortsette min powerdown for å kunne fikse eventuell ny skade.

*Akseptansekrav*
*   Spillere som er i powerdown får mulighet i starten av runden om å fortsette powerdown eller ikke.

*Arbeidsoppgaver*
*   Legg til funksjon for å gi spillere som er i powerdown mulighet for å fortsette i powerdown.

#### Brukerhistorie 8
*   Som spiller må roboten min kunne bli respawnet for å kunne ta del i spillet videre, gitt at den har flere liv.

*Akseptansekrav*
*   En robot som dør får ett mindre liv.
*   Hvis roboten er tom for liv, taper spilleren dens.
*   Når en robot dør, gjennoppliver den på sin backup posisjon.

*Arbeidsoppgaver*
*   Legg til en funksjon som gjennoppliver roboter som har flere liv.

#### Brukerhistorie 9
*   Som spiller må registrene mine bli låst ut ifra hvor mye skade jeg har, for å representere en skadet robot.

*Akseptansekrav*
*   Roboter med mer enn fire skade, får låst antall register over fire skade.
*   Kort som står i låste registre blir ikke samlet inn og kan ikke endres.

*Arbeidsoppgaver*
*   Legg til en funksjon for å låse registre.

### Brukerhistorier for GUI/Nettverk
#### Brukerhistorie 10
*   Som spiller må jeg kunne velge kort for å programmere roboten min.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt for å velge kort.

*Arbeidsoppgaver*
*   Lage et grafisk grensesnitt som lar en bruker velge kort.

#### Brukerhistorie 11
*   Som spiller, når jeg treffer det siste flagget mitt skal/må spillet stoppe for at jeg skal kunne vinne.

*Akseptansekrav*
*   Det blir vist en skjerm som sier hvem som vinner.

*Arbeidsoppgaver*
*   Lage en libgdx skjerm som sier hvilken spiller som har vunnet.
*   Få spillet til å ikke fortsette til neste runde, men bytte til vinneskjermen i stedet

#### Brukerhistorie 12
*   Som spiller må en person også være en server som kan dele ut informasjon til alle klienter for å ha et 
sentralt distribusjons-senter for informasjon.

*Akseptansekrav*
*   Det finnes en funksjon som lar deg velge å bli en server.

*Arbeidsoppgaver*
*   Legge til en grafisk knapp som lar spilleren starte en serever
*   Legge til funksjonalitet som starter en server dersom brukeren velger å starte som server.

#### Brukerhistorie 13
*   Som klient må jeg kunne sende inn spillernavnet mitt til serveren for å bli identifisert i spillet.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt som lar klienten skrive inn navnet sitt og sende det til serveren.
*   Serveren må forvente å motta navnet fra spilleren.

*Arbeidsoppgaver*
*   Serveren må forvente å motta navn fra spillere og må bruke det navnet til å lage en spiller.
*   Lage et grafisk grensesnitt som lar en klient koble seg til en server.

#### Brukerhistorie 14
*   Som spiller må jeg kunne se hvilken robot som er min for at jeg skal kunne identifisere roboten min.

*Akseptansekrav*
*   Brettet må kunne vise hvilken robot som hører til hvilken spiller.

*Arbeidsoppgaver*
*   Lage et grafisk grensesnitt som lar deg se navn på alle spillere og hvilken robot som tilhører hver spiller.

#### Brukerhistorie 15
*   Som server må jeg kunne sende brettnavn og en spillerliste med alle spillerne for at spillet skal bli synkronisert.

*Akseptansekrav*
*   Serveren må sende brettnavnet og en liste med alle spillerne til alle klientene.
*   Klientene må forvente å mota brettnavnet og en liste med alle spillerne.

*Arbeidsoppgaver*
*   Serveren må kunne sende spillerlisten og brettnavnet til alle klientene, og 
alle klientene må forvente å motta dette objektet.

#### Brukerhistorie 16
*   Som klient må jeg kunne sende informasjon om jeg skal i powerdown og programmet mitt inn til serveren for at spillet 
skal bli synkronisert.

*Akseptansekrav*
*   Klienten må kunne sende powerdown og programmet mitt inn til serveren.
*   Serven må forvente å motta informasjon om powerdown og programmet til klienten.

*Arbeidsoppgaver*
*   Alle klienter må kunne sende inn informasjon om de skal i powerdown og programmet spilleren har programmert. 

#### Brukerhistorie 17
*   Som server må jeg kunne sende informasjon om alle som skal i powerdown og alle sine programmer for at 
spillet skal bli synkronisert.

*Akseptansekrav*
*   Serveren må kunne sende informasjon om alle som skal i powerdown til alle klientene.
*   Klientene må forvente å motta informasjon om alle som skal i powerdown.

*Arbeidsoppgaver*
*   Informasjon om powerdown og programmeringskort må sendes tilbake til alle klienter slik at 
alle har samme informasjon.

*   Klientene må forvente å mota denne informasjon.

#### Brukerhistorie 18
*   Som Host må jeg kunne samle inn alle ulåste kort fra spillerne for at integriteten til kortstokken skal ivaretas.

*Akseptansekrav*
*   Host må samle inn alle de ulåste kortene ut ifra programmene som blir mottatt fra serveren.

*Arbeidsoppgaver*
*   Flytte ubrukte kort tilbake i hovedkortstokken hvis denne instansen av spillet er en host.

#### Brukerhistorie 19
*   Som klient må jeg få mulighet til å fortsette powerdown og sende den informasjonen inn til serveren for at ønsket om 
powerdown skal bli vedlikeholdt mellom alle klientene.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt som lar deg velge å fortsette powerdown. 
*   Valget av å fortsette powerdown må bli sent inn til serveren.
*   Serveren må forvente å motta valget om å fortsette powerdown. 

*Arbeidsoppgaver*
*   Lage et grafisk grensesnitt som lar en bruker fortsette powerdown.
*   Klienten må få mulighet til å fortsette powerdown og serveren må forvente å motta denne informasjonen. 

#### Brukerhistorie 20
*   Som server må jeg kunne sende informasjon om alle roboter i powerdown og sende nye programmerings kort til alle 
klientene for at spillet skal bli synkronisert.
 
*Akseptansekrav*
*   Serveren må kunne sende informasjon om alle roboter i powerdown og nye programmerings kort til alle klientene.
*   Klientene må forvente å motta informasjon om powerdown og de nye programmeringskortene.

*Arbeidsoppgaver*
*   Serveren må kunne sende informasjon om alle som fortsetter powerdown og nye programmeringskort. 
*   Klienten må forvente å motta denne informasjonen.
 
#### Brukerhistorie 21
*   Som server må jeg kunne håndtere spillere som taper for at klienter som har tapt ikke skal bli spurt om input.

*Akseptansekrav*
*   Serveren må kunne markere klienter den ikke skal ha input fra.

*Arbeidsoppgaver*
*   Serveren makerer alle som har død og derfor ikke skal ha input fra.
*   Serveren må håndtere en spiller som er død og blir frakoblet og som ikke er host.
 
#### Brukerhistorie 22
*   Som spiller må jeg ha et grensesnitt for å bestemme om jeg skal være server, klient eller avslutte.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt for å kunne velge å være server, klient eller å avslutte.

*Arbeidsoppgaver*
*   Lage et grafisk grensesnitt som lar brukeren velge å starte som server, koble til en server eller avslutte spillet.

#### Brukerhistorie 23
*   Som spiller må jeg ha et grensesnitt for å bestemme hvilken server jeg skal koble til.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt for å kunne velge server.

*Arbeidsoppgaver*
*   Lage en libgdx skjerm som viser servere som kjører på samme nettverk på default port.
*   Skjermen må inneholde mulighet for å kunne spesifisere ip og port for en vilkårlig server.

#### Brukerhistorie 24
*   Som spiller må jeg ha et grensesnitt for å kunne skrive inn spiller-navnet mitt.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt for å kunne skrive inn spiller-navnet.

*Arbeidsoppgaver*
*   Lage et grafisk grensesnitt som lar en klient skrive inn sitt spiller navn.

#### Brukerhistorie 25 
*   Som spiller må jeg ha et grensesnitt som viser at jeg venter på noe, for å ikke bli forvirret når ingenting skjer.

*Akseptansekrav*
*   Det finnes et grafisk grensesnitt for å varsle om venting i spillet.

*Arbeidsoppgaver*
*   Lage en libgdx skjerm som viser at spillet venter på noe.