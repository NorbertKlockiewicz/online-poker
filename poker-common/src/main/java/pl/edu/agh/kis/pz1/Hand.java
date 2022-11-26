package pl.edu.agh.kis.pz1;

public class Hand {
    private HandType type;
    private Card[] cards;

    public Hand(Card[] cards) {
        this.cards = cards;
        sortCards();
        type = HandType.HIGH_CARD;
        if (isRoyalFlush()) {
            type = HandType.ROYAL_FLUSH;
        } else if (isStraightFlush()) {
            type = HandType.STRAIGHT_FLUSH;
        } else if (isFourOfAKind()) {
            type = HandType.FOUR_OF_A_KIND;
        } else if (isFullHouse()) {
            type = HandType.FULL_HOUSE;
        } else if (isFlush()) {
            type = HandType.FLUSH;
        } else if (isStraight()) {
            type = HandType.STRAIGHT;
        } else if (isThreeOfAKind()) {
            type = HandType.THREE_OF_A_KIND;
        } else if (isTwoPairs()) {
            type = HandType.TWO_PAIRS;
        } else if (isPair()) {
            type = HandType.PAIR;
        }
    }
    public HandType getType() {
        return type;
    }

    private void sortCards(){
        for(int i = 0; i < cards.length; i++){
            for(int j = 0; j < cards.length - 1; j++){
                if(cards[j].getValue() > cards[j+1].getValue()){
                    Card temp = cards[j];
                    cards[j] = cards[j+1];
                    cards[j+1] = temp;
                }
            }
        }
    }
    private boolean isRoyalFlush(){
        return isStraightFlush() && cards[0].getValue() == 10;
    }

    private boolean isStraightFlush(){
        return isStraight() && isFlush();
    }

    private boolean isFourOfAKind(){
        return cards[0].getValue() == cards[1].getValue() && cards[1].getValue() == cards[2].getValue() && cards[2].getValue() == cards[3].getValue() ||
                cards[1].getValue() == cards[2].getValue() && cards[2].getValue() == cards[3].getValue() && cards[3].getValue() == cards[4].getValue();
    }

    private boolean isFullHouse(){
        return cards[0].getValue() == cards[1].getValue() && cards[1].getValue() == cards[2].getValue() && cards[3].getValue() == cards[4].getValue() ||
                cards[0].getValue() == cards[1].getValue() && cards[2].getValue() == cards[3].getValue() && cards[3].getValue() == cards[4].getValue();
    }

    private boolean isFlush(){
        return cards[0].getSuit() == cards[1].getSuit() && cards[1].getSuit() == cards[2].getSuit() && cards[2].getSuit() == cards[3].getSuit() && cards[3].getSuit() == cards[4].getSuit();
    }

    private boolean isStraight(){
        return cards[0].getValue() == cards[1].getValue() - 1 && cards[1].getValue() == cards[2].getValue() - 1 && cards[2].getValue() == cards[3].getValue() - 1 && cards[3].getValue() == cards[4].getValue() - 1;
    }

    private boolean isThreeOfAKind(){
        return cards[0].getValue() == cards[1].getValue() && cards[1].getValue() == cards[2].getValue() ||
                cards[1].getValue() == cards[2].getValue() && cards[2].getValue() == cards[3].getValue() ||
                cards[2].getValue() == cards[3].getValue() && cards[3].getValue() == cards[4].getValue();
    }

    private boolean isTwoPairs(){
        return cards[0].getValue() == cards[1].getValue() && cards[2].getValue() == cards[3].getValue() ||
                cards[0].getValue() == cards[1].getValue() && cards[3].getValue() == cards[4].getValue() ||
                cards[1].getValue() == cards[2].getValue() && cards[3].getValue() == cards[4].getValue();
    }

    private boolean isPair(){
        return cards[0].getValue() == cards[1].getValue() ||
                cards[1].getValue() == cards[2].getValue() ||
                cards[2].getValue() == cards[3].getValue() ||
                cards[3].getValue() == cards[4].getValue();
    }
}
