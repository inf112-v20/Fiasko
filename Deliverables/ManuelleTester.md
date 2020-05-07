# Manuelle tester

Hver runde får du en spesifik kortstokk: en rotate right, en rotate left, en u-turn, en back up, en move 3, en move 2, tre move 1
Tester som krever flere roboter må testes med multiplayer (de er i stor grad dekket av automatiske tester).

## Roboten blir rotert av tannhjul, flyttet av transportbånd, registrerer flagg og vinner

### Runde 1 - tester tannhjul til høyre, tannhjul til venstre, sakte transport bånd, høyre vrid sakte transport bånd, raskt transport bånd, flagg 1 blir registrert og vist
Program som skal kjøres: back up, move 1, move 2, rotate left, u-turn. Trykk confirm

Roboten beveger seg sør fra spawnpoint og blir rotert av tannhjul så den peker øst.
Robot beveger seg med move 1 ett steg øst og blir rotert av tannhjul så den peker nord.
Robot beveger seg med move 2 to steg nord og blir flyttet av transportbånd ett steg vest, og blir rotert mot øst siden
det er en sving.
Robot roteres mot nord av rotate left og blir flyttet av transportbånd ett steg nord, til et raskt transportbånd.
Robot roteres mot sør av u-turn og blir flyttet av raskt transportbånd to steg nord, til flagg 1, som blir registrert.
Flagget forventes markert nede til høyre for bildet av roboten.

### Runde 2 - tester at flagg 2 blir registrert

Program som skal kjøres: rotate left, move 1, move 1, u-turn, turn right. Trykk confirm

Roboten roteres mot øst av rotate left, og står på flagg 1.
Roboten beveger seg med move 1 ett steg øst til ruten mellom flagg 1 og flagg 2.
Roboten roteres seg med move 1 ett steg øst, og står på flagg 2. Flagg 2 blir så registrert.
Roboten roteres mot vest av u-turn, og står på flagg 2.
Roboten roteres mot nord av rotate right, og står på flagg 2.

### Runde 3 - tester at når du treffer alle flaggene så vinner du, når du vinner får du vinnerskjermen, du kan trykke quit knappen på vinnerskjermen for å lukke spillet

Program som skal kjøres: rotate right, move 1, tilfeldig, tilfeldig, tilfeldig. Trykk confirm

Roboten roteres mot øst av rotate right, og står på flagg 2.
Roboten beveger seg med move 1 ett steg mot øst til flagg 3.
Spiller får opp vinnerskjerm som viser spillernavn og en quit knapp.
Spiller trykker på quit og spillet terminerer med melding: "Process finished with exit code -1"

## Robot tar skade av laser, får låste kort i registerne, dør og respawner på skiftenøkkel
###Runde 1 - tester at robot tar korrekt skade fra alle lasertyper

Program som skal kjøres: rotate left, move 1, move 1, move 1, back up. Trykk confirm

Roboten roteres mot vest av rotate left, og står på sin spawn.
Roboten beveger seg med move 1 ett steg mot vest, og tar tre skade av trippel laser.
Roboten beveger seg med move 1 ett steg mot vest, og tar to skade av dobbel laser.
Roboten beveger seg med move 1 ett steg mot vest, og tar én skade av enkel laser.
Roboten beveger seg med back up ett steg mot øst, og tar to skade av dobbel laser. Robot skal nå ha 8 skade, som viser
nede til høyre på skjermen.

###Runde 2 - tester at kort blir låst

Fire kort skal vise som låst. Ett kan velges.
Program som skal kjøres: tilfeldig. Trykk confirm

Roboten utfører tilfelige kort og blir drept av laser.
Respawner på skiftenøkkel med to i skade.
Spiller krysser ut spillet så det terminerer med melding: "Process finished with exit code -1"

## Robot faller i hull, går i powerdown, fortsetter powerdown, faller i pit, dør, respawer og blir stoppet av vegger
### Runde 1,2,3 - tester hull, powerdown, continue powerdown og fortsette spillet etter powerdown

Program som skal kjøres: rotate right, move 1, rotate left, u-turn, move3. Trykk confirm + powerdown,
continue powerdown, ikke ta continue powerdown.

Roboten roteres mot øst av rotate right, og står på spawn.
Roboten beveger seg med move 1 ett steg mot øst.
Roboten roteres mot nord av rotate left.
Roboten roteres mot sør av u-turn.
Roboten beveger seg med move 3 to steg mot sør, inn i hullet og dør.
Roboten respawner på spawn og går i powerdown for å få 0 skade.
Spiller trykker continue powerdown og robot går i powerdown enda en runde.
Spiller trykker ikke contine powerdown og robot går ikke i powerdown.

### Runde 4,5 - tester pit

Program som skal kjøres: rotate right, move 2, rotate left, u-turn, move 3. Trykk confirm + powerdown

Roboten roteres mot øst av rotate right, og står på spawn.
Roboten beveger seg med move 2 to steg mot øst.
Roboten roteres mot nord av rotate left.
Roboten roteres mot sør av u-turn.
Roboten beveger seg med move 3 to steg mot sør, inn i pit-en og dør.
Roboten respawner på spawn og går i powerdown for å få 0 skade.

### Runde 6 - tester vegg som står fremfor robot

Program som skal kjøres: rotate right, move 3, rotate left, u-turn, move 2. Trykk confirm

Roboten roteres mot øst av rotate right, og står på spawn.
Roboten beveger seg med move 3 tre steg mot øst.
Roboten roteres mot nord av rotate left.
Roboten roteres mot sør av u-turn.
Roboten beveger seg med move 2 ett steg mot sør, og blir stoppet av en vegg som står fremfor den.

### Runde 7 - tester vegg som står på samme tile som robot

Program som skal kjøres: rotate left, move 1, move 1, move 1, move 3. Trykk confirm

Roboten roteres mot øst av rotate left, og står mot en vegg som står på samme tile.
Roboten beveger seg med move 1 null steg mot øst, siden den blir stoppet av veggen.
Roboten beveger seg med move 1 null steg mot øst, siden den blir stoppet av veggen.
Roboten beveger seg med move 1 null steg mot øst, siden den blir stoppet av veggen.
Roboten beveger seg med move 3 null steg mot øst, siden den blir stoppet av veggen.
Spiller krysser ut spillet så det terminerer med melding: "Process finished with exit code -1"

## Robot blir dyttet av pushere, får oppdatert backup, dør 3 ganger og spill avsluttes
### Runde 1 - flytter roboten helt til østre del av brett

Program som skal kjøres: rotate right, move 3, move 2, move 1, move 1. Trykk confirm

Roboten roteres mot øst av rotate right, og står på sin spawn.
Roboten beveger seg med move 3 tre steg mot øst.
Roboten beveger seg med move 2 to steg mot øst.
Roboten beveger seg med move 1 ett steg mot øst.
Roboten beveger seg med move 1 ett steg mot øst, blir flyttet av transportbånd ett steg mot nord.

### Runde 2 - tester partallspusher

Program som skal kjøres: rotate right, rotate left, tilfeldig, tilfeldig, tilfeldig. Trykk confirm

Roboten roteres mot sør av rotate right, står på partallspusher og skiftenøkkel og får oppdatert backup.
Roboten roteres mot øst av rotate left, og blir skubbet av brettet av partallspusheren så roboten dør.

### Runde 3 - tester oddetallspusher

Program som skal kjøres: move 1, tilfeldig, tilfeldig, tilfeldig, tilfeldig. Trykk confirm

Roboten respawner på skiftenøkkelen den ble dyttet av. Roboten beveger seg med move 1 ett steg mot nord, og blir skubbet
av brettet av oddetallspusheren så roboten dør.
Neste runde endres ikke backup siden den dør før første fase er ferdig.

### Runde 4 - tester terminering av spill når alle spillere er død

Program som skal kjøres: move 1, tilfeldig, tilfeldig, tilfeldig, tilfeldig. Trykk confirm

Roboten respawner på skiftenøkkelen du respawnet sist. Roboten beveger seg med move 1 ett steg mot nord, og blir skubbet
av brettet av oddetallspusheren så roboten dør.
Du er nå tom for liv og spillet skal terminere neste runde med meldingen:\
"\[Critical\] All players died. Cannot continue playing."