package no.oslomet.cs.algdat;

public class Main {
    public static void main(String [] args){
        String [] s = {"A","B","C","D"};
        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(s);
        System.out.print(liste.toString());
    }
}
