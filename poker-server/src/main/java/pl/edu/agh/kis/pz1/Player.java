package pl.edu.agh.kis.pz1;

public class Player {
private String name;
    private int money;
    private int bet;
    private Card[] cards;

    private boolean isFolded = false;

    public Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.bet = 0;
        this.cards = new Card[5];
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getBet() {
        return bet;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
        sortCards();
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void removeMoney(int money) {
        this.money -= money;
    }

    public void removeBet() {
        this.money += this.bet;
        this.bet = 0;
    }

    public void resetBet() {
        this.bet = 0;
    }

    public void resetCards() {
        this.cards = new Card[5];
    }

    public void setFolded(boolean isFolded) {
        this.isFolded = isFolded;
    }

    public boolean isFolded() {
        return isFolded;
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

    public void printCards(){
        for(int i = 0; i < cards.length; i++){
            System.out.println(i + " - " + cards[i].getName() + " of " + cards[i].getColor());
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", bet=" + bet +
                ", cards=" + cards[0] + cards[1] + cards[2] + cards[3] + cards[4] +
                '}';
    }
}
