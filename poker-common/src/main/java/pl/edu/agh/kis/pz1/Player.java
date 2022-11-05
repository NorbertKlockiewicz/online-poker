package pl.edu.agh.kis.pz1;

public class Player {
    final private String name;
    private int money;
    private int bet;
    private Card[] cards;
    private CardPrinter printer;
    private boolean isFolded = false;
    final private int clientId;

    public Player(String name, int money, int clientId) {
        this.name = name;
        this.money = money;
        this.clientId = clientId;
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

    public int getClientId(){
        return clientId;
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

    public String printCards(int potAmount, int minimalBet) {
        String s = CardPrinter.printCards(cards);
        s += "\t ======================== \n";
        s += "\t |                      | \n";
        s += "\t |    Your bet: "+bet+"      | \n";
        s += "\t |    In the pot: "+potAmount+"    | \n";
        s += "\t |    Minimal bet: "+minimalBet+"   | \n";
        s += "\t |                      | \n";
        s += "\t ======================== \n";

        return s;
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
