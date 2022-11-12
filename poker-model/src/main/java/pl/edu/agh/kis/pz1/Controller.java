package pl.edu.agh.kis.pz1;

public class Controller {
    private SocketServer server;
    private GameRoom[] rooms;
    private int numRooms = 0;

    public Controller(SocketServer server){
        this.server = server;
    }

    public void route(String request){
        String[] requestArray = request.split(" ");
        String action = requestArray[0];
        System.out.println("Action: " + action);
        System.out.println("Request: " + requestArray[1]);
        switch(action){
            case "POST":
                PostRequest postRequest = new PostRequest(request);
                post(postRequest);
                break;
            case "GET":
                GetRequest getRequest = new GetRequest(request);
                get(getRequest);
                break;
            case "PUT":
                PutRequest putRequest = new PutRequest(request);
                put(putRequest);
                break;
            default:
                server.responseToClient(Integer.parseInt(requestArray[1]), "Wrong request type");
        }
    }

    private void post(Request request){
          String action = request.getAction();
          int clientId = request.getClientId();
          int[] params = request.getParams();
            switch(action){
                case "JOIN_GAME":
                    server.responseToClient(clientId, joinGame(clientId, params[0]));
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player " + clientId + " joined the game");
                    if(this.rooms[params[0]].isReady()){
                        this.rooms[params[0]].startGame();
                    };
                    break;
                case "FOLD":
                    this.rooms[params[0]].fold(clientId);
                    server.responseToClient(clientId, "200");
                    if(this.rooms[params[0]].isOnlyOnePlayerLeft()){
                        sendResponseToEveryClientInRoom(this.rooms[params[0]].getPlayers(), "GAME_OVER");
                    }
                    sendTableStatusToEveryClientInRoom(params[0]);
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player " + clientId + " folded");
                    this.rooms[params[0]].nextPlayerTurn();
                    break;
                case "CHECK":
                    server.responseToClient(clientId, "200");
                    sendTableStatusToEveryClientInRoom(params[0]);
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player " + clientId + " checked");
                    this.rooms[params[0]].nextPlayerTurn();
                    break;
                case "CALL":
                    this.rooms[params[0]].call(clientId);
                    server.responseToClient(clientId, "200");
                    sendTableStatusToEveryClientInRoom(params[0]);
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player#" + clientId + " called");
                    this.rooms[params[0]].nextPlayerTurn();
                    break;
                case "RAISE":
                    this.rooms[params[0]].raise(clientId, params[1]);
                    server.responseToClient(clientId, "200");
                    sendTableStatusToEveryClientInRoom(params[0]);
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player#" + clientId + " raised by " + params[1]);
                    this.rooms[params[0]].nextPlayerTurn();
                    break;
                case "CHANGE":
                    int[] cardNumbers = new int[params.length - 1];
                    for(int i = 1; i < params.length; i++){
                        cardNumbers[i - 1] = params[i];
                    }
                    this.rooms[params[0]].changeCards(clientId, cardNumbers);
                    server.responseToClient(clientId, "200");
                    sendResponse(clientId, rooms[params[0]].getPlayerByClientId(clientId).printCards(rooms[params[0]].getPot(), rooms[params[0]].getMinimalBet()));
                    sendToEveryClientExcept(clientId, this.rooms[params[0]].getPlayers(), "Player#" + clientId + " changed cards");
                    this.rooms[params[0]].nextPlayerTurn();
                    break;
                default:
                    System.out.println("Unknown action");
        }
    }

    private void get(Request request){
        String action = request.getAction();
        int clientId = request.getClientId();
        if ("GET_ROOMS".equals(action)) {
            server.responseToClient(clientId, getActiveGames());
        } else {
            server.responseToClient(clientId, "Error: Unknown action");
        }
    }

    private void put(Request request){
        String action = request.getAction();
        int clientId = request.getClientId();
        int[] params = request.getParams();
        if ("CREATE_GAME".equals(action)) {
            server.responseToClient(clientId, createGame(clientId, params));
        } else {
            server.responseToClient(clientId, "Error: Unknown action");
        }
    }

    public void sendResponseToEveryClientInRoom(Player [] players, String response){
        for(Player player : players){
            if(player != null){
                server.responseToClient(player.getClientId(), response);
            }
        }
    }

    public void sendResponse(int clientId, String response){
        server.responseToClient(clientId, response);
    }

    public String createGame(int clientId, int[] params){
        int gameId;
        try {
            numRooms++;
            if (numRooms == 1) {
                this.rooms = new GameRoom[numRooms];
            } else {
                GameRoom[] temp;
                temp = rooms;
                rooms = new GameRoom[numRooms];
                if (numRooms - 1 >= 0) System.arraycopy(temp, 0, rooms, 0, numRooms - 1);
            }

            Deck deck = new Deck();
            GameRoom room = new GameRoom(deck, params[0], numRooms - 1, server.getMaxPlayers(), this);
            Player creator = new Player("Player#" + clientId, 1000, clientId);
            room.addPlayer(creator);
            this.rooms[numRooms - 1] = room;
            gameId = numRooms - 1;
            System.out.println("Game created");
        } catch (Exception e){
            return "Error";
        }

        return "200 " + gameId;
    }

    public String joinGame(int clientId, int roomId){
        try {
            GameRoom room = rooms[roomId];
            Player player = new Player("Player#" + clientId, 1000, clientId);
            System.out.println("Player#" + clientId + " joined game");
            room.addPlayer(player);
        } catch (Exception e){
            return "Error";
        }

        return "200";
    }

    public String getActiveGames(){
        if(numRooms < 0){
            return "USER_ACTION No active games";
        }

        String s = "USER_ACTION ";
        for(int i = 0; i < numRooms; i++) {
            if(!rooms[i].isGameOver()){
                s += i + " : " + rooms[i].getRoomInfo() + "\n";
            }
        }

        if(s.equals("USER_ACTION ")){
            return "USER_ACTION No active games";
        }

        return s;
    }

    public void sendTableStatusToEveryClientInRoom(int roomId){
        for(Player player : rooms[roomId].getPlayers()){
            if(player != null){
                server.responseToClient(player.getClientId(), player.printCards(rooms[roomId].getPot(), rooms[roomId].getMinimalBet()));
            }
        }
    }

    public void sendToEveryClientExcept(int clientId, Player [] players, String response){
        for(Player player : players){
            if(player != null && player.getClientId() != clientId){
                server.responseToClient(player.getClientId(), response);
            }
        }
    }
}
