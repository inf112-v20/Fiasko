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

###Oppsumering
 Overall kompetanse i java koding ligger på medium mens erfaring med git eller gui er relativt lav. 
###Roller
####Titler
Kristian -> Team leder (Ansvarlig for å vite hva alle gjør og burde gjøre til enhver tid) 

Tobias -> Komunikasjonsleder ( planlegger møter og parprogrammerings timer)

Steinar -> Møte organisere (Ansvarlig for å organisere hva som skal gjennomgås på de avtaltene møtene.)

Torbjørn -> Dukumentsjons ansvarlig (dokumenter møtene og sørger for at innleveringene er riktig)

Gabriel -> Kundeansvarlig (Ansvarlig for å vite alle spillregler)
####Grunnlag
Tobias -> vi trenger noen som kan ta ansvar for å planlegge når vi skal ha møter. også kontakt person for om noe ikke kan møte til noen timer

Kristian -> Han har mest erfaring med kode og var en naturlig leder. han har overblikk over hva som skal bli gjordt

Steinar -> skal organisere møtene slik at vi har en plan før møte. slik at vi ikke møter uten å ha noen plan

Torbjørn -> skal sørge for at det er korrrekt dokumentasjon og bra nok dokumentasjon.   

Gabriel -> leder for spillregler og ansvar for riktig spillmekanikk 

##Produktutforming
###Overordnet mål
Vi vil lage en fungerende digital versjon av RoboRally som er kompatibel med MAC, Windows og Linux.
Applikasjonen må kunne kjøre og fungere uten å ha noen ødeleggende feil eller mangler.
Det skal kunne fungere over LAN med opp til 8 spillere.
Spillet skal implementere alle spillereglene fra RoboRally (om tiden strekker til).

###Systemkrav
*   En visuell representasjon av et spillbrett
*   En spiller som kan bevege seg på brettet
*   En spiller taper når de mister alle liv 
*   En spiller dør om de tar 10 skade
*   En spiller trenger liv 
*   Spilleren trenger å kunne ta skade
*   En spiller må kunne vinne
*   Kort for å programmere roboten 
*   Forskjellige funksjonelle objekter/ruter på kartet (eks. vegg, laser)
*   Runder (Består av 5 faser. Du får nye kort, reparasjon, powerdown etc.)
*   Faser (Ett kort fra hver spiller blir brukt, og objekter på brettet intragerer. Registrering av flagg skjer etter hver fase)
*   Kunne spille med andre spillere over lan
*   Lasere skyter i slutten av hver fase
*   Spillere beveger seg hver fase og kort bestemer hvem som går først
*   Velge kort i starteren av runden
*   Kunne velge Power down etter alle har låst kortene sine
*   Slutten av runden får spillere reprasjoner hvis de står på en reprasjons brikke eller flag.
*   Sjekke om noen spilere står på riktig flag
*   Respawn etter en robot er ødelagt ved slutten av runden/fasen gitt at de har flere liv
*   Hvis en spiller er i power down blir spilleren spurt (i starten av nye runden) om han vil bli i powerdown eller ikke.
*   Roboter må kunne dytte hverandre.
*   Samle inn kort i slutten ev en runde
*   Ikke samle sammen kort som er låst.
*   Vise hendelser som skjer på skjermen med en delay slik at spillet ser riktig ut
*   Roboter dør hvis de går utenfor spillbrettet eller faller i ett hull

###Prioriterte krav
1. Vi har et brett tegnet inn med libgdx.
2. Ha en brikke tegen inn med libgdx. 

###Prosjektmetodikk
 *   2 fysiske møter i uke. en på gruppetimen og en på torsdager. 
 *   Møter på discord online med skjerm deling for parprogramering
 *   Sykluser uppsett mellom hver oblig. sette opp oppgaver over hva som skal gjøres over hver syklus
 *   Vi tenker å ta en blanding av srcum og parprogramering under prosjektet.
 *   Tisrdager brukes for å recape hva som har blitt gjordt uken før og for å avklare om vi føler oss ferdig med oppgaver.
 *   Torsdag er satt av for parprogramering
 *   Delig av documenter skjer over git eller discord
 *   Bruke projectboardet for at vi skal ha kontroll over alle oppgavene og hvem som skal gjør hva.

###Bruker historie
 #### Vise et brett
 *   Som en spiller må jeg kunne se et brett får å kunne spille spillet.
 *   Som spillbrett må jeg vise alle elemnter som er på meg 
 *   Som spillbrett må jeg vær delt inn ruter/tiles der brikkene på meg må være i bare en rute av gangen.
 *   Akseptanse krav er at vi kan se et brett der noe kan bli plassert på brette i en rute/tile
  
 ####Plasere en brikke på brettet
 *   Som brikke må jeg kunne plassere på spillbettet  
 *   Som brikke må jeg være synelig texture på brettet
 *   Akseptanse krav brikken blir plasert synelig i en bestemt tile på brettet

 
