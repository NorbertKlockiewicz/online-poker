package pl.edu.agh.kis.pz1;

public class GameService {
    private final ClientConnection cc;

    public GameService(ClientConnection cc){
        this.cc = cc;
    }

    public String createGame(int clientId, String ante){
        this.cc.postRequest("CREATE_GAME" + " " + clientId + " " + ante);
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

    public String check(int clientId, int gameId){
        this.cc.postRequest("CHECK " + clientId + " " + gameId);
        return this.cc.listenForServerMessage();
    }

    public String changeCards(int clientId, int gameId, int [] cardNumbers){
        StringBuilder s = new StringBuilder("CHANGE " + clientId + " " + gameId);
        for (int cardNumber : cardNumbers) {
            s.append(" ").append(cardNumber);
        }
        this.cc.postRequest(s.toString());
        return this.cc.listenForServerMessage();
    }
}
