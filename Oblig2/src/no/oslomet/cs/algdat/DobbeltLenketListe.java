package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import javax.swing.*;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        antall = 0;
        endringer = 0;
        hode = hale = null;
       // throw new UnsupportedOperationException();
    }

    public DobbeltLenketListe(T[] a) {
       Objects.requireNonNull(a,"Tabellen a er Null!"); //kaster exception om tabellen er tom eller bare "Null"
       int nullverdi = 0; // initialiserer nullverditeller
        Node <T> node=null;
        Node <T> forrige = null;

       for (T t : a){ //går gjennom listen
           if(t == null){ // om verdien paa indeksen er == null oekes null verditelleren
                nullverdi++;
           }
           else if (tom()){ //om listen er tom og verdien ikke er null oppdateres foerste verdi
               node = new Node<>(t,hode,null);
               hode = hale = node;
               antall++; //øker antall verdier som != Null
           }
           else{ // Oppdaterer halen i tabellen.
               node = new Node<>(t, forrige, null);
               hale = node;
               antall++; //øker antall verdier som != Null
           }
           if (forrige!= null && forrige!=node){ //Oppretter neste peker for node og passer på at vi ikke får en gjentakene peker på siste noden
               forrige.neste = node;
           }
           forrige=node;
       }
       // throw new UnsupportedOperationException();
    }

    private Node<T> finnNode(int indeks){
        Node<T> current= new Node<>(null, null, null);// Oppretter en "current" node som skal returnere noden som er i indeks

        if (indeks <= antall/2) {// Bruker en if (indeks <= antall/2) slik at current starter på node Hode.
            current = hode;
            for (int i= 0; i < indeks; i++){ // Inne i if setningen bruker jeg en for-løkke som skal iterere seg frem til indeks
                if (current!=null) {
                    current = current.neste; // inne i for-løkke oppdaterer jeg current ved å bruke current.neste
                }
            }
        }
        else if(indeks > antall/2){ // Dersom indeksen er høyere enn antall/2 bruker jeg en else if hvor current starter på hale noden.
            current = hale;
            for (int j= antall - 1; j > indeks; j--){ // Inne i denne for-løkken oppdaterer jeg current ved å bruke current.forrige
                current = current.forrige;
            }
        }
        return current;
    }

    private static void fratilKontroll(int antall, int fra, int til){ // brukes for å kontrollere fra og til
        if (fra < 0){
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }
        if (til > antall){
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }
        if (fra > til){
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    public Liste<T> subliste(int fra, int til){
        Liste<T> liste= new DobbeltLenketListe<>(); // oppretter en instans liste av Dobbeltlenkeliste
        Node<T> franode= finnNode(0);
        int pointer= 0;
        try { // bruker en try og catch ved å kontrollere om indeksene i fra og til er lovlige.
            fratilKontroll(antall, fra, til); // for å kontrollere indeksene skal fratilKontroll() kalles på.
            for (int i = 0; i < antall; i++) { // dersom verdiene er lovlige bruker jeg en for-løkke [fra:til> for å legge inn verdiene i sublisten.
                if (fra == pointer) {
                    while (pointer < til) { // intervall [fra:til>
                        liste.leggInn(franode.verdi); // legger inn verdiene i sublisten
                        franode = franode.neste; // går videre til neste node
                        pointer++; // pointer øker til den er lik til
                    }
                    break; // bryter ut av while løkken
                }
                franode = franode.neste;
                pointer++;
            }
        } catch (IndexOutOfBoundsException error) {
            throw error;
        }
        return liste; // til slutt returneres listen.
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Verdien a er Null!");
        Node<T> nynode = new Node(verdi);
        antall++;
        endringer++;
        if (hode == null) {
            hale = nynode;
            hode = nynode;
            hale.forrige = null;
            hode.neste = null;
        } else {
            hale.neste = nynode;
            nynode.forrige = hale;
            hale = nynode;
            hale.neste = null;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med Nullverdier");
        indeksKontroll(indeks, true);

        if (indeks == antall && indeks != 0) { // koden kjoeres hvis indeksen er like lang som listen
            hale = hale.neste = new Node<>(verdi, hale, null); //setter hale lik sin neste siden indeksen tar den gammle hale plassen
        } else if (indeks == 0) {
            hode = new Node<>(verdi, null, hode); //om indeksen er 0 settes det som hode.
            if (antall == 0){ //setter om antall = 0 og indeks = 0 saa er hale =hode, siden det ikke er flere indekser.
                hale = hode;
            }
            else{
                hode.neste.forrige = hode; // maa peke paa hode via aa bruke neste sin forrige for å ikke få nullpeker.
            }
        } else {
            Node<T> node = hode;
            for (int i = 1; i < indeks; i++) { //loeper gjennom for loekke for aa finne den designerte indeksen til node.
                node = node.neste; //selvforklart, setter node = node.neste
            }
            Node<T> temp = new Node<>(verdi, node, node.neste); //oppretter en hjelpenode
            node.neste.forrige = temp; // peker igjen paa node fra tidligere, med neste.forrige for aa ikke faa feil i peker
            node.neste = temp; // setter nye plassen til temp, altsaa hva den tidligere noden var.
        }
        antall++;
        endringer++;
    // throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        boolean sjekk; // oppretter en boolean sjekk som skal enten returnere true eller false.
        if (indeksTil(verdi) != -1) {// her tenker jeg å bruke en if setning som sammenligner verdi med -1.
            sjekk = true; // dersom verdien != -1 så skal sjekk returnere true.
        }
        else {
            sjekk = false;
        }
        return sjekk;
    }

    @Override
    public T hent(int indeks) {
        T indeksVerdi; // oppretter en instans av T indeksVerdi;
        try { // bruker en try og catch for å sjekke indeks med indeksKontroll()
            indeksKontroll(indeks, false);
            indeksVerdi = finnNode(indeks).verdi; // inne i try kaller jeg på finnNode og setter den lik indeksVerdi
        }catch (IndexOutOfBoundsException error){
            throw error;
        }
        return indeksVerdi; // til slutt returneres indeksVerdi
    }

    @Override
    public int indeksTil(T verdi) {
        Node<T> leteNode= hode;// Oppretter en instans av node (leteNode) som skal bli brukt til å lete etter verdi.
        for (int i = 0; i < antall; i++) { // bruker en for-løkke for å søke etter verdi.
            if (leteNode.verdi.equals(verdi)){ // inne i løkken bruker jeg en if setning som skal sammeligne leteNode og verdi
                return i; // dersom verdiene er like returnerer jeg int i fra løkken.
            }
            leteNode = leteNode.neste;
        }
        return -1; // ellers blir -1 returnert.
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        T indeksVerdi; // oppretter en instans av T
        Node<T> node= finnNode(indeks);

        if (nyverdi == null) {// bruker en if setning for å kontrollere at nyverdi ikke er null
            throw new NullPointerException("Nullverdi er ikke tillat"); // kaster NullPointerException
        }
        else {
            try { // dersom nyverdi ikke er null så bruker jeg en try og catch for å sjekke indeks med indeksKontroll()
                indeksKontroll(indeks, false);
                indeksVerdi = node.verdi;
                node.verdi = nyverdi; // i try oppdaterer jeg verdiene ved å sette indeksVerdi = nyverdi.
                endringer++; // endringer økes også (endringer++).
            }catch (IndexOutOfBoundsException error){
                throw error;
            }
        }
        return indeksVerdi; // til slutt returneres indeksVerdi
    }

    @Override
    public boolean fjern(T verdi) {
        boolean sjekk= false; // oppretter en boolean sjekk som enten returnerer true/false
        Node<T> indeksVerdi= hode.neste; // oppretter en instans av node som = hode (indeksVerdi).
        Node<T> forr, nest; // oppretter to pekere av Node, forr og nest

        while (indeksVerdi != null){
            if (indeksVerdi.verdi.equals(verdi)){
                if (indeksVerdi.equals(hode)){
                    nest = indeksVerdi.neste;
                    nest.forrige = null;
                    hode = nest;
                    antall--;
                    endringer++;
                    sjekk = true;
                }
                else if (indeksVerdi.equals(hale)){
                    forr = indeksVerdi.forrige;
                    forr.neste = null;
                    hale = forr;
                    antall--;
                    endringer++;
                    sjekk = true;
                }
                else {
                    forr = indeksVerdi.forrige;
                    nest = indeksVerdi.neste;
                    forr.neste = nest;
                    nest.forrige = forr;
                    antall--;
                    endringer++;
                    sjekk = true;
                }
            }
            indeksVerdi = indeksVerdi.neste;
        }
        return sjekk;
    }

    @Override
    public T fjern(int indeks) {
        Node<T> indeksVerdi; // oppretter en instans av Node som skal være indeks
        Node<T> nest, forr;// oppretter en forrige peker og en neste peker av Node

        try { // bruker en try og catch for å kontrollere indeks
            indeksKontroll(indeks, false); // sjekker indeks
            indeksVerdi = finnNode(indeks); 
            if (antall >= 1 && indeks == 0){ // for å fjerne hode
                indeksVerdi = hode;
                nest = indeksVerdi.neste;
                nest.forrige = null;
                hode = nest;
                antall--;
                endringer++;
            }
            else if (indeks == antall -1 && antall >= 1){ // for å fjerne hale
                indeksVerdi = hale;
                forr = indeksVerdi.forrige;
                forr.neste = null;
                hale = forr;
                antall--;
                endringer++;
            }
            else { // for å fjerne andre elementer
                forr = indeksVerdi.forrige;
                nest = indeksVerdi.neste;
                forr.neste = nest;
                nest.forrige = forr;
                antall--;
                endringer++;
            }
        }catch (IndexOutOfBoundsException error){
            throw error;
        }
        return indeksVerdi.verdi; // returnerer til slutt instansen av noden som skal ha verdien til indeks.
    }

    @Override
    public void nullstill() {
        if (tom()) { //dersom listen er tom skal den ikke gjoere noe
            return;
        }
        Node<T> p,q = hode;      // oppretter hjelpenoder for å nullstille

        while(q!=null){         // loeper gjennom nodene og nullstiller
            p=q.neste;          //setter foerst hjelpenode p til neste node
            q.forrige=null;
            q.verdi=null;       //nullstiller verdiene
            q.neste=null;
            q=p;                //setter deretter nullstilt node til neste node
        }
        antall=0;           //setter til slutt antall til 0 og endringer oekes
        endringer++;
    }

    @Override
    public String toString() {
        StringBuilder returner =new StringBuilder();
        if (tom()){ //Om arrayet er tomt, returner []
            return "[]";
        }
        returner.append("[");

        Node<T> t = hode;           //Starter med aa appende verdien til hodet
        returner.append(t.verdi);
        t=t.neste;                  // noden blir satt til neste

        while(t!=null) {            //Dersom det er flere elementer som ikke er null, fortsett
                returner.append(", ").append(t.verdi);
                t = t.neste;
        }

        returner.append("]");
        return returner.toString();
    }

    public String omvendtString() {
        StringBuilder returner =new StringBuilder();
        if (tom()){ //Om arrayet er tomt, returner []
            return "[]";
        }
        returner.append("[");

        Node<T> t = hale;           //Starter med aa appende verdien til halen
        returner.append(t.verdi);
        t=t.forrige;                  // noden blir satt til forrige

        while(t!=null) {            //Dersom det er flere elementer som ikke er null, fortsett
            returner.append(", ").append(t.verdi);
            t = t.forrige;
        }

        returner.append("]");
        return returner.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();      //returnerer dobbeltlenkelisteIterator
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks,false);                    //Sjekker om indeksen er lovlig ved aa kalle på indekskontroll
        return new DobbeltLenketListeIterator(indeks);          // returnerer instans av iterator
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            denne = finnNode(indeks);                       // kaller på finnNode() med variabelen indeks og setter denne til finnNode sin return
            fjernOK=false;
            iteratorendringer=endringer;                    //setter fjernOK til false og iteratorendringer til endringer som i konstruktoeren
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            if (iteratorendringer!=endringer){                          //En if setning som sjekker om iteratorendringer er lik endringer
                throw new ConcurrentModificationException();            //Throw ConCurrentModification exception om false
            }
            if (!hasNext()){                                            //saa hvis !hasNext() kastes en noSuchElementException
                throw new NoSuchElementException();
            }
            fjernOK=true;                                               //Fjernok settes til TRUE
            T retur= denne.verdi;
            denne = denne.neste;
            return  retur;                                              //oppretter node der denne.verdi blir lagt inn og denne.neste blir denne
        }

        @Override
        public void remove(){
            Node<T> forr, nest; // forrige og neste node som brukes i tilfelle 4
            // Jeg tenker først å kontrollere hindrene i oppgaven.
            if (iteratorendringer != endringer){
                throw new ConcurrentModificationException("iteratorendringer != endringer");
            }
            if (antall == 0 || !fjernOK){
                throw new IllegalStateException("Kan ikke fjerne noe når listen er tom!");
            }
            // Jeg tenker først å sjekke om endringer og iteratorendringer med en if setning.
            fjernOK = false;
            // Setter fjernOk til false.

            if (antall == 1){ // tilfelle 1: hode og hale nulles ut.
                hode = null;
                hale = null;
            }
            else if (denne == null){ // tilfelle 2: den siste fjernes og halen oppdateres.
                hale = hale.forrige;
                hale.neste = null;
            }
            else if (denne.forrige == hode){ // tilfelle 3: den første fjernes og hode oppdateres.
                denne.forrige = null;
                hode = denne;
            }
            else { // tilfelle 4: en node i listen fjernes og pekerne på hver side oppdateres.
                forr = denne.forrige.forrige;
                nest = denne;
                forr.neste = nest;
                nest.forrige = forr;
            }
            // Implementerer tilfeller slik at noden rett til venstre for p skal fjernes.

            antall--;
            iteratorendringer++;
            endringer++;
            // antall reduseres og endringer og iteratorendringer økes.
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


