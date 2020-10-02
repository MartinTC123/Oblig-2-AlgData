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

       for (T t : a){ //går gjennom listen
           if(t == null){ // om verdien paa indeksen er == null oekes null verditelleren
                nullverdi++;
           }
           else if (tom()){ //om listen er tom og verdien ikke er null oppdateres foerste verdi
               Node<T> node = new Node<>(t,hode,null);
               hode = hale = node;
               antall++; //øker antall verdier som != Null
           }
           else{ // Oppdaterer halen i tabellen.
               Node<T> node = hale;
               hale = new Node<>(t, hale, null);
               hale.neste = hale;
               antall++; //øker antall verdier som != Null
           }

       }
       // throw new UnsupportedOperationException();
    }

    public Liste<T> subliste(int fra, int til){
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
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
        returner.append("[");
        Node t = new Node(hode);

        while(t!=null) {
                returner.append(t.verdi);
                t = t.neste;
        }
        returner.append("]");
        return returner.toString();
    }

    public String omvendtString() {
        throw new UnsupportedOperationException();
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


