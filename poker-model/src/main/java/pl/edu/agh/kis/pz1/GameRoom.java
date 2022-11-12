package pl.edu.agh.kis.pz1;

public class GameRoom {
    private int maxPlayers;
    final private int minPlayers = 2;
    private int playersCount = 0;
    private Player[] players;
    private Deck deck;
    private int playersTurn = 0;
    private boolean isGameOver = false;
    private int turn = 1;
    private int pot = 0;
    private int minimalBet;
    private int ante;
    private Controller controller;
    private boolean turnShouldEnd = false;
    private int roomId;

    public GameRoom(Deck deck, int minimalBet, int id, int maxPlayers, Controller controller) {
        this.maxPlayers = maxPlayers;
        this.players = new Player[maxPlayers];
        this.deck = deck;
        this.controller = controller;
        this.minimalBet = minimalBet;
        this.ante = minimalBet;
        this.roomId = id;
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

    public int getPot(){
        return pot;
    }

    public int getMinimalBet(){
        return minimalBet;
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
        if(playersTurn == 0 || turnShouldEnd){
            int playerNumber = checkIfEveryoneMadeEqualBets();
            if(playerNumber == -1){
                turn++;
                turnShouldEnd = false;
                playersTurn = 0;
            }
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

    public int checkIfEveryoneMadeEqualBets(){
        for(int i = 0; i < playersCount; i++){
            if(players[i].getBet() != minimalBet && !players[i].isFolded()){
                this.turnShouldEnd = true;
                return i;
            }
        }
        return -1;
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
        this.controller.sendTableStatusToEveryClientInRoom(roomId);
        startBasicTurn();
    }

    public void fold(int clientId){
        getPlayerByClientId(clientId).setFolded(true);
    }

    public int call(int clientId){
        Player player = getPlayerByClientId(clientId);
        if (player.getBet() == this.minimalBet){
            player.setBet(this.ante + player.getBet());
            this.minimalBet = player.getBet();
            player.removeMoney(this.ante);
            this.addToPot(this.ante);
            return this.ante;
        }
        else{
            int missingAmount = this.minimalBet - player.getBet();
            player.setBet(this.minimalBet);
            player.removeMoney(missingAmount);
            this.addToPot(missingAmount);
            return missingAmount;
        }
    }

    public int raise(int clientId, int amount){
        Player player = getPlayerByClientId(clientId);
        if (player.getBet() == this.minimalBet){
            this.minimalBet = this.ante + player.getBet() + amount;
            player.setBet(this.ante + player.getBet() + amount);
            player.removeMoney(this.ante + amount);
            this.addToPot(this.ante + amount);
            return this.ante + amount;
        }
        else{
            int missingAmount = this.minimalBet - player.getBet();
            this.minimalBet = this.minimalBet + amount;
            player.setBet(this.minimalBet);
            player.removeMoney(missingAmount + amount);
            this.addToPot(missingAmount);
            return missingAmount;
        }
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
        if(!this.turnShouldEnd){
                if(players[playersTurn].isFolded()){
                    nextPlayerTurn();
                }
                else{
                    if(players[playersTurn].getBet() == this.minimalBet){
                        this.controller.sendResponse(players[playersTurn].getClientId(), "USER_ACTION PLAY CHECK Your turn");
                    }
                    else {
                        this.controller.sendResponse(players[playersTurn].getClientId(), "USER_ACTION PLAY Your turn");
                    }
                    this.controller.sendToEveryClientExcept(players[playersTurn].getClientId(),players, "Waiting for " + players[playersTurn].getName());
                }
        }else{
            if(players[playersTurn].isFolded()){
                nextPlayerTurn();
            }
            else{
                this.controller.sendResponse(players[playersTurn].getClientId(), "USER_ACTION PLAY Your turn");
                this.controller.sendToEveryClientExcept(players[playersTurn].getClientId(),players, "Waiting for " + players[playersTurn].getName());
            }
        }
    }

    private void startTurnWithCardChanges(){
        if(!players[playersTurn].isFolded()){
            this.controller.sendResponse(players[playersTurn].getClientId(), "USER_ACTION CHANGE Your turn, choose which cards do you want to change(starting with 0): ");
        }
        else if(players[playersTurn].isFolded()){
            nextPlayerTurn();
        }
        else{
            this.controller.sendToEveryClientExcept(players[playersTurn].getClientId(), players, "Waiting for " + players[playersTurn].getName());
        }
    }
}
