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
           if (forrige!= null && forrige!=node){
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
            current = current.neste; // inne i for-løkke oppdaterer jeg current ved å bruke current.neste
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
        }catch (IndexOutOfBoundsException error){
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
    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi,"Verdien a er Null!");
        Node<T> nynode = new Node(verdi);
        antall++;
        if(hode == null){
            hale = nynode;
            hode = nynode;
            hale.forrige = null;
            hode.neste = null;
        }
        else{
            hale.neste = nynode;
            nynode.forrige = hale;
            hale = nynode;
            hale.neste = null;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder returner =new StringBuilder();
        if (tom()){ //Om arrayet er tomt, returner []
            returner.append("[]");
            return returner.toString();
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

        Node<T> t = hale;           //Starter med aa appende verdien til hodet
        returner.append(t.verdi);
        t=t.forrige;                  // noden blir satt til neste

        while(t!=null) {            //Dersom det er flere elementer som ikke er null, fortsett
            returner.append(", ").append(t.verdi);
            t = t.forrige;
        }

        returner.append("]");
        return returner.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


