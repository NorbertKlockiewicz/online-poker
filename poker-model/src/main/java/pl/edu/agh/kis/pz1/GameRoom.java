package pl.edu.agh.kis.pz1;

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
    private Controller controller;

    public GameRoom(Deck deck, int minimalBet, int id, int maxPlayers, Controller controller) {
        this.maxPlayers = maxPlayers;
        this.players = new Player[maxPlayers];
        this.deck = deck;
        this.controller = controller;
        this.minimalBet = minimalBet;
    }

    public String getRoomInfo(){
        String info = "Room info: ";
        info += "Minimal bet: " + this.minimalBet + ", ";
        info += "Players: " + this.playersCount + "/" + this.maxPlayers;
        return info;
    }

    public boolean isReady() {
            return playersCount == maxPlayers;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Player [] getPlayers(){
        return players;
    }

    public Player getPlayerByClientId(int clientId){
        for(int i = 0; i < this.playersCount; i++){
            if(this.players[i].getClientId() == clientId){
                return this.players[i];
            }
        }
        return null;
    }

    public void addPlayer(Player player) {
        if (playersCount < maxPlayers) {
            players[playersCount] = player;
            playersCount++;
        }
        else{
            try {
                throw new Exception("Room is full");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        this.controller.sendResponseToEveryClientInRoom(this.players, "GAME_OVER");
        //check which player has best poker hand
        //if more than one player has the same hand, check who has the highest card
    }

    public boolean isOnlyOnePlayerLeft() {
        int playersLeft = 0;
        for (int i = 0; i < playersCount; i++) {
            if (!players[i].isFolded()) {
                playersLeft++;
            }
        }

        if (playersLeft == 1) {
            isGameOver = true;
            return true;
        }

        return false;
    }

    public void startGame(){
        for (int i = 0; i < playersCount; i++) {
            players[i].removeMoney(this.minimalBet);
            players[i].setBet(this.minimalBet);
            this.addToPot(this.minimalBet);
        }
        //deal cards
        for (int i = 0; i < playersCount; i++) {
            players[i].setCards(this.deck.dealCards(5));
        }

        startBasicTurn();
    }

    public void fold(int clientId){
        getPlayerByClientId(clientId).setFolded(true);
    }

    public void call(int clientId){
        Player player = getPlayerByClientId(clientId);
        player.setBet(this.minimalBet + player.getBet());
        player.removeMoney(this.minimalBet);
    }

    public void raise(int clientId, int amount){
        Player player = getPlayerByClientId(clientId);
        player.setBet(this.minimalBet + player.getBet() + amount);
        player.removeMoney(this.minimalBet + amount);
    }

    public void changeCards(int clientId, int[] cardsToChange){
        Player player = getPlayerByClientId(clientId);
        Card [] tempCards = player.getCards();
        for(int i = 0; i < cardsToChange.length; i++){
            tempCards[cardsToChange[i]] = this.deck.dealCards(1)[0];
        }
        player.setCards(tempCards);
    }

    private void startBasicTurn(){
        System.out.println(this.players[this.playersTurn].getName() + "'s turn");

        for(int i = 0; i < playersCount; i++){
            if(playersTurn == 0) {
                this.controller.sendResponse(players[i].getClientId(), players[i].printCards());
            }
            if(i == playersTurn){
                this.controller.sendResponse(players[i].getClientId(), "USER_ACTION PLAY Your turn");
            }
            else{
                this.controller.sendResponse(players[i].getClientId(), "Waiting for " + players[playersTurn].getName());
            }
        }
    }

    private void startTurnWithCardChanges(){
        System.out.println(this.players[this.playersTurn].getName() + "'s turn");

        for(int i = 0; i < playersCount; i++){
            if(playersTurn == 0) {
                this.controller.sendResponse(players[i].getClientId(), players[i].printCards());
            }
            if(i == playersTurn){
                this.controller.sendResponse(players[i].getClientId(), "USER_ACTION CHANGE Your turn, choose which cards do you want to change(starting with 0): ");
            }
            else{
                this.controller.sendResponse(players[i].getClientId(), "Waiting for " + players[playersTurn].getName());
            }
        }
    }
}
