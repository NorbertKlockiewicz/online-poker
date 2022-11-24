package pl.edu.agh.kis.pz1;

public class GameRoom {
    final private int maxPlayers;
    private int playersCount = 0;
    final private Player[] players;
    final private Deck deck;
    private int playersTurn = 0;
    private boolean isGameOver = false;
    private int turn = 1;
    private int pot = 0;
    private int minimalBet;
    final private int ante;
    private boolean turnShouldEnd = false;

    public GameRoom(int minimalBet, int id, int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.players = new Player[maxPlayers];
        this.deck = new Deck();
        this.minimalBet = minimalBet;
        this.ante = minimalBet;
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

    public int getNextPlayerTurn(){
        int tempPlayersTurn;
        tempPlayersTurn = (playersTurn + 1) % playersCount;
        boolean temp = turnShouldEnd;
        if(tempPlayersTurn == 0 || turnShouldEnd){
            int playerNumber = checkIfEveryoneMadeEqualBets();
            if(playerNumber == -1){
                tempPlayersTurn = 0;
                turnShouldEnd = temp;
            }
        }

        return tempPlayersTurn;
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

    public String nextPlayerTurn() {
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
            return startBasicTurn();
        }
        if(turn == 2){
            return startTurnWithCardChanges();
        }
        if(turn == 3){
            return startBasicTurn();
        }
        if(turn == 4){
            return checkGameOver();
        }

        return "Error";
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

    private String checkGameOver(){
        isOnlyOnePlayerLeft();
        isGameOver = true;
        System.out.println("Game over!");
        return "GAME_OVER";
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
            players[i].setBet(this.minimalBet);
            this.addToPot(this.minimalBet);
        }
        //deal cards
        for (int i = 0; i < playersCount; i++) {
            players[i].setCards(this.deck.dealCards(5));
        }
    }

    public void fold(int clientId){
        getPlayerByClientId(clientId).setFolded(true);
    }

    public void call(int clientId){
        Player player = getPlayerByClientId(clientId);
        if (player.getBet() == this.minimalBet){
            player.setBet(this.ante + player.getBet());
            this.minimalBet = player.getBet();
            this.addToPot(this.ante);
        }
        else{
            int missingAmount = this.minimalBet - player.getBet();
            player.setBet(this.minimalBet);
            this.addToPot(missingAmount);
        }
    }

    public void raise(int clientId, int amount){
        Player player = getPlayerByClientId(clientId);
        if (player.getBet() == this.minimalBet){
            this.minimalBet = this.ante + player.getBet() + amount;
            player.setBet(this.ante + player.getBet() + amount);
            this.addToPot(this.ante + amount);
        }
        else{
            int missingAmount = this.minimalBet - player.getBet();
            this.minimalBet = this.minimalBet + amount;
            player.setBet(this.minimalBet);
            this.addToPot(missingAmount);
        }
    }

    public void changeCards(int clientId, int[] cardsToChange){
        Player player = getPlayerByClientId(clientId);
        Card [] tempCards = player.getCards();
        for (int j : cardsToChange) {
            tempCards[j] = this.deck.dealCards(1)[0];
        }
        player.setCards(tempCards);
    }

    public String startBasicTurn(){
        if(!this.turnShouldEnd){
                if(players[playersTurn].isFolded()){
                    nextPlayerTurn();
                }
                else{
                    if(players[playersTurn].getBet() == this.minimalBet){
                        return "USER_ACTION PLAY CHECK";
                    }
                    else {
                        return "USER_ACTION PLAY CALL";
                    }
                }
        }else{
            if(players[playersTurn].isFolded()){
                nextPlayerTurn();
            }
            else{
                return "USER_ACTION PLAY";
            }
        }

        return "";
    }

    private String startTurnWithCardChanges(){
        if(!players[playersTurn].isFolded()){
            return "USER_ACTION CHANGE";
        }
        else{
            nextPlayerTurn();
        }

        return "";
    }
}
