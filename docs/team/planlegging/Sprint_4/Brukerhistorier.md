#brukerhistorie:

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




- Som spiller må jeg kunne velge kort for å programere roboten min.
    - det finnes et grafisk grensesnitt som velger kort
    
- Som spiller når jeg treffer det siste flagget mitt skal/må spillet stoppe for at jeg skal kunne vinne.
    - det blir vist en skjerm som sier hvem som vinner

- Som spiller må en person også være en severe som kan dele ut information til alle andre kilenter for å ha et sentralt 
distribusjons senter for information
    - det finnes en funksjon som lar deg velge å bli en server

- Som klient må jeg kunne sende inn spillernavnet mitt til serveren for å bli identifisert i spillet
    - det finnes et grafisk grensesnitt som lar kliente skrive inn navnet sitt og sender det til serveren 
    - serveren må forvente å mota navnet fra spilleren
- Som spiller må jeg kunne se hvilken robot som er min for at jeg skal kunne identifisere roboten min
    - brettet må kunne vise hvilken robot som passere med hvilken spiller
- Som server må jeg kunne sende programmerings kortene, brett navn og en player list med alle spillerene for at spillet 
skal bli synkroniserte
    - serveren må sende programmeringskort, brett navnet og en liset med alle spilleren til alle klientene
    - klientene må forvente å mota programmeringskort, brett navnet og en liset med alle spilleren
- Som klient må jeg kunne sende information om jeg skal i powerdown, programmet mitt og de ubrukte 
kortene mine inn til serveren for at spillet skal bli synkroniserte
    - klienten må kunne sende powerdown, programmet mitt og de ubrukte kortene inn til serveren
    - serven må forvente å mota information om powerdown, programmet til klienten og det ubrukte kortene til klienten
- Som server må jeg kunne sende information om alle som skal i powerdown og alle sine programmer for at spillet skal bli 
synkroniserte
    - servern må kunne sende information om alle som skal i powerdown til alle klientene
    - klientene må forvente å mota informatiom om alle skal i powerdown 

- Som Klient må jeg kunne sende inn alle mine ulåste kort fra programmet mitt til serveren for at kortstokken ikke 
mister kort
    - klienten må sende inn alle de ulåstekortene sine inn til serveren
    - serveren må forvente å mota de ulåstekortene fra alle spillerene

- Som klient må jeg få mulighet til å fortsett powerdown og sende den informationen inn til serveren for at ønsket om 
powerdown skal bli vedlikeholdt mellom alle klientene
    - det finnes et grafisk grensesnitt som lar deg velge powerdown 
    - valget av å fortsette powerdown må bli sent inn til serveren
    - serveren må forvente å mota valget om å fortsett powerdown 

- Som server må jeg kunne sende information om alle roboter i powerdown og sende nye programmerings kort til alle
 klientene for at spillet skal bli synkroniserte
    - serveren må kunne sende information om alle roboter i powerdown og nye porgrammerings kort til alle klientene
    - klientene må forvente å mota information om powerdown og de nye programmeringskortene
 
- Som server må jeg kunne håndtere spillere som taper for at kilenter som har tapt ikke skal bli spurt om input
    - serveren må kunne makere klienter det ikke skal ha input fra.

- Som spiller må jeg ha et grensesnitt for å kunne velge powerdown
    - det finnes et grafisk grensesnitt for å kunne velge powerdown   
- Som spiller må jeg ha et grensesnitt for å kunne fortsette powerdown
    - det finnes et grafisk grensesnitt for å kunne fortsett powerdown  
- Som spiller må jeg ha et grensesnitt for å kunne velg programmet til roboten min
    - det finnes et grafisk grensesnitt for å kunne velge programmet til roboten
- Som spiller må jeg ha et grensesnitt for å besteme om jeg skal være server, klinet eller avslutte
    - det finnes et grafisk grensesnitt for å kunne velge å være server, klient eller å avslutte 
- Som spiller må jeg ha et grensesnitt for å besteme om hvilken server jeg skal bli med
    - det finnes et grafisk grensesnitt for å kunne velge server
- Som spiller må jeg ha et grensesnitt for å kunne skrive inn spiller navnet mitt
    - det finnes et grafisk grensesnitt for å kunne skrive inn spiller navnet
- Som spiller må jeg ha et grensesnitt som viser at jeg venter på noe, for å ikke bli forvirret når ingen ting skjer
    
    
### GUI/Nettverk
- klientene må kunne sende inn information om den skal i powerdown, programmet dens og de ubrukte kortene inn til 
serveren og serveren må forvente å mota denne informationen

- Serveren må kunne sende programmeringskortene, spiller listen og brett navnet til alle klientene og alle klientene må
 forvente å mota dette objektet
 
- Serveren må kunne sende information om alle som skal i powerdown og alle sine programmer til alle klientene og 
klientene må forvente å mota denne information

- Klienten må kunne sende inn alle de ulåste kortene til serveren og serveren må forvente å mota disse kortene
- Klienten må få mulighet til å fortsett powerdown og serveren må forvente å mota denne informationen 
- Serveren må kunne sende information om alle som fortsetter powerdown og nye programmeringskort. klienten må forvente å
mota denne informationen

- Serveren makerer alle som har død og derfor ikke skal ha input fra
- Serveren må håndere en spiller som er dø og blir avkoblet og som ikke er host
- Lage et grafisk grensesnitt som lar deg se alle spiller navneten og hvilken robot som hører til den spilleren
- Serveren må forvente å mota navn fra spillere å må bruke det navnet til lage en spiller
- Lage et grafisk grensesnitt som lar en bruker velge kort
- Lage en libgdx skjerm som sier hvilken spiller som har vunnet
- Lage et grafisk grensesnitt som lar brukeren velge å starte som server, koble til en server eller avslutte spillet
- Legge til funksjonalitet som starter en server dersom brukeren velger å starte som server
- Lage en libgdx skjerm som viser at spillet venter på noe
- Lage et grafisk grensesnitt som lar en bruker gå i powerdown
- Lage et grafisk grensesnitt som lar en klient koble seg til en server
- Lage et grafisk grensesnitt som lar en klient skrive inn sitt spiller navn

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