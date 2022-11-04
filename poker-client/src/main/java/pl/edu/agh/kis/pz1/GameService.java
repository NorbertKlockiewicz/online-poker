package pl.edu.agh.kis.pz1;

public class GameService {
    private ClientConnection cc;

    public GameService(ClientConnection cc){
        this.cc = cc;
    }

    public String createGame(int clientId, String ante){
        this.cc.putRequest("CREATE_GAME" + " " + clientId + " " + ante);
        return this.cc.listenForServerMessage();
    }

    public String joinGame(int clientId, String gameId){
        this.cc.postRequest("JOIN_GAME" + " " + clientId + " " + gameId);
        return this.cc.listenForServerMessage();
    }

    public String getActiveGames(int clientId){
        this.cc.getRequest("GET_ROOMS " + clientId);
        return this.cc.listenForServerMessage();
    }

    public String fold(int clientId, int gameId){
        this.cc.postRequest("FOLD " + clientId + " " + gameId);
        return this.cc.listenForServerMessage();
    }

    public String call(int clientId, int gameId){
        this.cc.postRequest("CALL " + clientId + " " + gameId);
        return this.cc.listenForServerMessage();
    }

    public String raise(int clientId, int gameId, int amount){
        this.cc.postRequest("RAISE " + clientId + " " + gameId + " " + amount);
        return this.cc.listenForServerMessage();
    }

    public String changeCards(int clientId, int gameId, int [] cardNumbers){
        String s = "CHANGE "+ clientId + " " + gameId;
        for(int i = 0; i < cardNumbers.length; i++){
            s += " " + cardNumbers[i];
        }
        this.cc.postRequest(s);
        return this.cc.listenForServerMessage();
    }

    public void exitGame(){
        System.exit(0);
    }

}
