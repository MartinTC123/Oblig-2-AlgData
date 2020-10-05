package no.oslomet.cs.algdat;

public class Main {
    public static void main(String [] args){

        String [] s = {"A","B","C","D"};
        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.fjern("A"));
        System.out.print(liste.toString());
        //System.out.print(liste.omvendtString());

         /*

        Character[] c = { 'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' ,};
        DobbeltLenketListe<Character> liste =  new  DobbeltLenketListe<>(c);
        System. out .println(liste.subliste(3,8));  // [D, E, F, G, H]
        System. out .println(liste.subliste(5,5));  // []
        System. out .println(liste.subliste(8,liste.antall()));  // [I, J]
   */ }
}
