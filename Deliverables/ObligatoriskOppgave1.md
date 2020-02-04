#Team Fiasko
##Organisering av teamet
###Kompetanse
Petter Tobias Madsen:
Relevante fag: INF101. Kan grunnleggende ting i Java men har ikke utført krevende prosjekter. Nesten ingen GUI erfaring.

Steinar Aalstad Lillesund:
Føler seg komfortabel med Java men ikke noe veldig avansert. Ingen GUI erfaring.

Kristian Knarvik:
Relevante fag: INF100, INF101 og INF102. Ganske erfaren i Java og har gjort ting med GUI før.

Torbjørn lunde jensen:
Litt erfaring men ikke veldig mye. Ingen GUI erfaring.

Gabriel Ingolf og Olav Magnus:
Relevante fag: INF101 og INF102. Litt komfortable med Java.
###Roller
####Titler
Kristian -> Team leder (Ansvarlig for å vite hva alle gjør og burde gjøre til enhver tid) 

Tobias -> Tester (Ansvar for kvalitet av tester)

Steinar -> Tester (Ansvarlig for kvalitet av tester)

Torbjørn -> dukumentsjons ansvarlig (dokumenter møtene og sørger for at innleveringene er riktig)

Gabriel -> Kundeansvarlig (Ansvarlig for å vite alle spillregler)
####Grunnlag
Tobias -> Sa ja til å være ansvarlig for tester

Kristian -> Han har mest erfaring med kode og var en naturlig leder.

Steinar -> Sa ja til å være ansvarlig for tester

Torbjørn -> sa ja til å være ansvarlig over å dokumentere møter og dokumentasjonen  

Gabriel -> sa ja til å være den som har kontroll over alle spillreglene

##Produktutforming
###Overordnet mål
Vi vil lage en fungerende digital versjon av RoboRally som er kompatibel med MAC, Windows og Linux.
Applikasjonen må kunne kjøre og fungere uten å ha noen ødeleggende feil eller mangler.
Det skal kunne fungere over LAN med opp til 8 spillere.
Spillet skal implementere alle spillereglene fra RoboRally (om tiden strekker til).

###Systemkrav
* En visuell representasjon av et spillbrett
* En spiller som kan bevege seg på brettet
* En spiller må kunne dø og tape
* En spiller trenger liv 
* Spilleren trenger å kunne ta skade
* En spiller må kunne vinne
* Kort for å programmere roboten 
* Forskjellige funksjonelle objekter/ruter på kartet (eks. vegg, laser)
* Runder (Består av 5 faser. Du får nye kort, reparasjon, powerdown etc.)
* Faser (Ett kort fra hver spiller blir brukt, og objekter på brettet intragerer. Registrering av flagg skjer etter hver fase)
* Kunne spille med andre spillere over nettet
* Lasere skyter i slutten av hver fase
* Spillere beveger seg hver fase og kort bestemer hvem som går først
* Velge kort i starteren av runden
* Kunne velge Power down etter alle har låst kortene sine
* Slutten av runden får spillere reprasjoner hvis de står på en reprasjons brikke eller flag.
* Etter lasere må vi sjekke om noen spilere står på riktig flag
* Respawn etter den er ødelagt ved slutten av runden/fasen
* hvis en spiller er i power down blir spilleren spurt (i starten av nye runden) om han vil bli i powerdown eller ikke.
* Roboter må kunne dytte hverandre.
* Samle inn kort i slutten ev en runde
* Ikke samle sammen kort som er låst.
* Vise hendelser som skjer på skjermen med en delay slik at spillet ser riktig ut
###Prioriterte krav
1. Vi har et brett på størelse N*N tegnet i libgdx.
2. Ha en spiller eller figur tegen inn i libgdx. 
###prosjketmetodikk
 * 2 fysiske møter i uke. en på gruppetimen og en på torsdager. 
 * møter på discord online med skjerm deling for parprogramering
 * sykluser uppsett mellom hver oblig. sette opp oppgaver over hva som skal gjøres over hver syklus
 * vi tenker å ta en blanding av srcum og parprogramering under prosjektet.
 * Tisrdager brukes for å recape hva som har blitt gjordt uken før og for å avklare om vi føler oss ferdig med oppgaver.
 * torsdag er satt av for parprogramering
 * delig av documenter skjer over git eller discord
 * bruke projectboardet for at vi skal ha kontroll over alle oppgavene og hvem som skal gjør hva.
###burker historie
 * vise et brett
 
 * som en spiller må jeg kunne se et brett får å kunne spille spillet.
 * som spillbrett må jeg vise alle elemnter som er på meg 
 * som spillbrett må jeg vær delt inn ruter/tiles der brikkene på meg må være i bare en rute av gangen.
 * akseptanse krav er at vi kan se et brett der noe kan bli plassert på brette i en rute/tile
  
 * plasere en brikke på brettet

 som brikke må jeg kunne plassere på spillbettet  
 som brikke må jeg være synelig texture på brettet
 *akseptanse krav brikken blir plasert synelig i en bestemt tile på brettet

 
