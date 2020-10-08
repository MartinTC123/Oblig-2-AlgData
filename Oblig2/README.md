# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Krav til innlevering

Se oblig-tekst for alle krav. Oppgaver som ikke oppfyller følgende vil ikke få godkjent:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* Ingen debug-utskrifter
* Alle testene som kreves fungerer (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Arbeidsfordeling

Oppgaven er levert av følgende studenter:
* Navn Navnesen, S981737, s981737@oslomet.no
* ...

Vi har brukt git til å dokumentere arbeidet vårt. Vi har 16 commits totalt, og hver logg-melding beskriver det vi har gjort av endringer.

I oppgaven har vi hatt følgende arbeidsfordeling:
* Per har hatt hovedansvar for oppgave 1, 3, og 5. 
* Else har hatt hovedansvar for oppgave 2, 4, og 6. 
* Fatima har hatt hovedansvar for oppgave 7 og 8. 
* Vi har i fellesskap løst oppgave 10. 

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: Løste ved å implementere..
* Oppgave 2: ...
* Oppgave 3:
    - i finnNode() brukte jeg en current node som skulle bli brukt for å returnere noden i indeks. Dette ble 
    gjort ved å bruke for-løkker for å iterere gjennom listen frem til indeks posisjonen. I hent() og oppdater() ble  
    finnNode() kalt på for å hente node i indeks og oppdatere noden til en nyverdi. I subliste() instansierte jeg franode som 
    fungerte som en current node. En for-løkke ble brukt for å kjøre gjennom listen (antall). For å skrive ut verdier til 
    sublisten brukte jeg en while løkke som loopet hver gang en int pointer ikke var lik int til. I while loopen brukte jeg 
    leggInn(T verdi) for å legge verdiene i sublisten.
* Oppgave 4:
    - I indeksTil(T verdi) opprettet jeg en node (leteNode = hode) som fungerte som en current node og ble brukt til å 
    lete etter T verdi i listen. For å finne T verdi brukte jeg en for-løkke som itererte gjennom antallet i listen. 
    I løkken brukte jeg en if setning som sammlignet leteNode sin verdi med T verdi, hvis verdiene er like ble int i returnert. 
    I inneholder(T verdi) kalte jeg på indeksTil(T verdi) for å sammenligne om T verdi er lik -1 eller ikke gjennom if setninger. 
* Oppgave 5:                                                      
* Oppgave 6:
    - I begge fjern metoder startet jeg med å instansiere forr, nest og indeksVerdi av Node. forr og nest blir brukt som 
    forrige og neste pekere. I fjern(int indeks) blir indeksVerdi brukt for å finne verdien i indeks. I fjern(T verdi) blir 
    indeksVerdi brukt for å sammenligne nodene i listen med T verdi. I begge metoder bruker jeg if og else if setninger
    for de ulike tilfellene (om indeks er hode, hale eller en node i listen). I if og else if setningene fjernes noden 
    ved hjelp av forr og nest pekerne. i fjern(int indeks) returneres verdien som blir fjernet og i fjern(T verdi) returneres
    true eller false. 
* Oppgave 7:
* Oppgave 8:
* Oppgave 9:
    - I void remove() startet jeg først med å sjekke om man passerer de nødvendige hindrene ved å bruke if setninger. Videre
    satt jeg FjernOK til false. Deretter satt jeg opp de ulike tilfellene for hvilke noder som skal fjernes (hode, hale, node).
    Dette ble gjort gjennom if og else if setninger hvor inneholdet i setningene fjernet noden til venstre for p. Dersom 
    en eller flere noder blir fjernet så reduseres antall og iteratorendringer og endringer økes. 
* Oppgave 10:

