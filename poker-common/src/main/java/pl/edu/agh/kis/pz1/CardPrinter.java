package pl.edu.agh.kis.pz1;


public class CardPrinter {
    public static String printCards(Card [] cards){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cards.length; i++) {
            s.append("\t ________________");
        }
        s.append("\n");

        for (int i = 0; i < cards.length; i++) {
            s.append("\t|                |");
        }
        s.append("\n");

        for (Card card : cards) {
            if (card.getValue() > 10) {
                s.append("\t|  ").append(card.getName().toUpperCase().charAt(0)).append("             |");
            } else if (card.getValue() == 10) {
                s.append("\t|  ").append(card.getName()).append("            |");
            } else {
                s.append("\t|  ").append(card.getName()).append("             |");
            }
        }
        s.append("\n");

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < cards.length; i++) {
                s.append("\t|                |");
            }
            s.append("\n");
        }

        for (Card card : cards) {
            s.append("\t|       ").append(card.getColorLabel()).append("        |");
        }
        s.append("\n");

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < cards.length; i++) {
                s.append("\t|                |");
            }
            s.append("\n");
        }

        for (Card card : cards) {
            if (card.getValue() > 10) {
                s.append("\t|             ").append(card.getName().toUpperCase().charAt(0)).append("  |");
            } else if (card.getValue() == 10) {
                s.append("\t|            ").append(card.getName()).append("  |");
            } else {
                s.append("\t|             ").append(card.getName()).append("  |");
            }
        }
        s.append("\n");

        for (int i = 0; i < cards.length; i++) {
            s.append("\t|________________|");
        }
        s.append("\n");

        return s.toString();
    }
}
