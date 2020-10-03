package no.oslomet.cs.algdat;

public class Main {
    public static void main(String [] args){
        Integer [] s = {1,null, 2,null,3,4,null};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(s);
        System.out.print(liste.toString());
        System.out.print(liste.omvendtString());
    }
}
