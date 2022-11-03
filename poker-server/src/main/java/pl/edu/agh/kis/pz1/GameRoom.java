package pl.edu.agh.kis.pz1;

import java.util.Scanner;

public class GameRoom {
    private int maxPlayers = 4;
    private int minPlayers = 2;
    private int playersCount = 0;
    private Player[] players;
    private Deck deck;
    private int playersTurn = 0;
    private boolean isGameOver = false;
    private int turn = 1;
    private int pot = 0;
    private int minimalBet = 10;
    private Scanner action = new Scanner(System.in);

    public GameRoom(Deck deck) {
        this.players = new Player[maxPlayers];
        this.deck = deck;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getPlayersTurn(){
        return playersTurn;
    }

    public int getTurn(){
        return turn;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void addPlayer(Player player) {
        if (playersCount < maxPlayers) {
            players[playersCount] = player;
            playersCount++;
        }
    }

    public void removePlayer(Player player) {
        for (int i = 0; i < playersCount; i++) {
            if (players[i].getName().equals(player.getName())) {
                players[i] = players[playersCount - 1];
                players[playersCount - 1] = null;
                playersCount--;
                break;
            }
        }
    }

    public void resetPlayers() {
        players = new Player[maxPlayers];
        playersCount = 0;
    }

    public boolean isFull() {
        return playersCount == maxPlayers;
    }

    public boolean isReady() {
        return playersCount >= minPlayers;
    }

    public void printPlayers() {
        for (int i = 0; i < playersCount; i++) {
            System.out.println(players[i]);
        }
    }
    public void nextPlayerTurn() {
        playersTurn = (playersTurn + 1) % playersCount;
        System.out.println(playersTurn);
        if(playersTurn == 0){
            turn++;
        }
        if(turn == 1){
            startBasicTurn();
        }
        if(turn == 2){
            startTurnWithCardChanges();
        }
        if(turn == 3){
            startBasicTurn();
        }
        if(turn == 4){
            checkGameOver();
        }
    }
    public void addToPot(int amount){
        pot += amount;
    }
    private void checkGameOver(){
        isOnlyOnePlayerLeft();
        isGameOver = true;
        System.out.println("Game over!");
        System.exit(0);
        //check which player has best poker hand
        //if more than one player has the same hand, check who has the highest card

    }
    public boolean isGameOver() {
        return isGameOver;
    }

    public void isOnlyOnePlayerLeft() {
        int playersLeft = 0;
        for (int i = 0; i < playersCount; i++) {
            if (!players[i].isFolded()) {
                playersLeft++;
            }
        }

        if (playersLeft == 1) {
            isGameOver = true;
            System.out.println("Game over!");
            System.exit(0);
        }
    }
    public void startGame(){
        //every player bets 10
        for (int i = 0; i < playersCount; i++) {
            players[i].removeMoney(this.minimalBet);
            players[i].setBet(this.minimalBet);
            this.addToPot(this.minimalBet);
        }
        //deal cards
        for (int i = 0; i < playersCount; i++) {
            players[i].setCards(this.deck.dealCards(5));
        }

        //display bets
        for (int i = 0; i < playersCount; i++) {
            System.out.println(players[i].getName() + " bet: " + players[i].getBet());
        }

        //display amount in pot
        System.out.println("Pot: " + pot);

        startBasicTurn();
    }

    private void startBasicTurn(){
        System.out.println(this.players[this.playersTurn].getName() + "'s turn");
        this.players[this.playersTurn].printCards();
        System.out.println("Choose action: ");
        System.out.println("1. Fold");
        System.out.println("2. Call");
        System.out.println("3. Raise");
        int actionNumber = action.nextInt();
        switch (actionNumber) {
            case 1:
                this.players[this.playersTurn].setFolded(true);
                break;
            case 2:
                this.players[this.playersTurn].setBet(this.minimalBet + this.players[this.playersTurn].getBet());
                this.players[this.playersTurn].removeMoney(minimalBet);
                break;
            case 3:
                System.out.println("How much do you want to raise?");
                int raiseAmount = action.nextInt();
                this.players[this.playersTurn].setBet(this.minimalBet + this.players[this.playersTurn].getBet() + raiseAmount);
                this.players[this.playersTurn].removeMoney(minimalBet + raiseAmount);
                this.minimalBet += raiseAmount;
                break;
            default:
                break;
        }

        isOnlyOnePlayerLeft();
        nextPlayerTurn();
    }

    private void startTurnWithCardChanges(){
        System.out.println(this.players[this.playersTurn].getName() + "'s turn");
        players[playersTurn].printCards();
        System.out.println("How many cards you want to change?");
        int cardsToChange = action.nextInt();
        for (int i = 0; i < cardsToChange; i++) {
            System.out.println("Which card you want to change?");
            int cardToChange = action.nextInt();
            Card [] playerCards = players[playersTurn].getCards();
            playerCards[cardToChange] = deck.dealCards(1)[0];
            players[playersTurn].setCards(playerCards);
            players[playersTurn].printCards();
        }
        nextPlayerTurn();
    }
}
