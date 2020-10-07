package no.oslomet.cs.algdat;

import java.util.Iterator;

public class Main {
    public static void main(String [] args){
       //DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
         //   liste.leggInn(0, 4);

        String [] s = {"A","B","C","D"};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        Iterator<Integer> i = liste.iterator();
        i.next();


/*
        Character[] c = { 'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' ,};
        DobbeltLenketListe<Character> liste =  new  DobbeltLenketListe<>(c);
        System. out .println(liste.subliste(3,8));  // [D, E, F, G, H]
        System. out .println(liste.subliste(5,5));  // []
        System. out .println(liste.subliste(8,liste.antall()));  // [I, J]

         */
    }
}
