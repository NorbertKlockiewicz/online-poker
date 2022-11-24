package pl.edu.agh.kis.pz1;

public class Deck {
    private final Card[] cards;
    private int cardsLeft;

    public Deck() {
        cards = new Card[52];
        cardsLeft = 52;
        int i = 0;
        for (int value = 2; value <= 14; value++) {
            for (String color : new String[]{"hearts", "diamonds", "clubs", "spades"}) {
                String colorLabel = "";
                switch(color){
                    case "hearts":
                        colorLabel = "\u2665";
                        break;
                    case "diamonds":
                        colorLabel = "\u2666";
                        break;
                    case "clubs":
                        colorLabel = "\u2663";
                        break;
                    case "spades":
                        colorLabel = "\u2660";
                        break;
                }
                cards[i] = new Card(value, color, valueToName(value), colorLabel);
                i++;
            }
        }

        shuffleDeck();
    }

    private String valueToName(int value) {
        switch (value) {
            case 11:
                return "jack";
            case 12:
                return "queen";
            case 13:
                return "king";
            case 14:
                return "ace";
            default:
                return Integer.toString(value);
        }
    }

    protected void shuffleDeck(){
        for (int i = 0; i < cards.length; i++) {
            int random = (int) (Math.random() * cards.length);
            Card temp = cards[i];
            cards[i] = cards[random];
            cards[random] = temp;
        }
    }

    public Card[] dealCards(int numberOfCards){
        Card[] dealtCards = new Card[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            dealtCards[i] = cards[cardsLeft - 1];
            cardsLeft--;
        }
        return dealtCards;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }
}
