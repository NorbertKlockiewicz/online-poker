package pl.edu.agh.kis.pz1;


public class CardPrinter {
    public static String printCards(Card [] cards){
        String s = "";
        for (int i = 0; i < cards.length; i++) {
            s = s + "\t ________________";
        }
        s+= "\n";

        for (int i = 0; i < cards.length; i++) {
            s = s + "\t|                |";
        }
        s+= "\n";

        for (int i = 0; i < cards.length; i++) {
            if(cards[i].getValue()>10){
                s = s + "\t|  " + cards[i].getName().toUpperCase().charAt(0) + "             |";
            }
            else if(cards[i].getValue() == 10){
                s = s + "\t|  " + cards[i].getName() + "            |";
            }
            else {
                s = s + "\t|  " + cards[i].getName() + "             |";
            }
        }
        s+= "\n";

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < cards.length; i++) {
                s = s + "\t|                |";
            }
            s+= "\n";
        }

        for (int i = 0; i < cards.length; i++) {
            s = s + "\t|       "+cards[i].getColorLabel()+"        |";
        }
        s+= "\n";

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < cards.length; i++) {
                s = s + "\t|                |";
            }
            s+= "\n";
        }

        for (int i = 0; i < cards.length; i++) {
            if(cards[i].getValue()>10){
                s = s + "\t|             " + cards[i].getName().toUpperCase().charAt(0) + "  |";
            }
            else if(cards[i].getValue() == 10){
                s = s + "\t|            " + cards[i].getName() + "  |";
            }
            else {
                s = s + "\t|             " + cards[i].getName() + "  |";
            }
        }
        s+= "\n";

        for (int i = 0; i < cards.length; i++) {
            s = s + "\t|________________|";
        }
        s+= "\n";

        return s;
    }
}
