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
                post(requestArray);
                break;
            case "GET":
                get(requestArray);
                break;
            case "PUT":
                put(requestArray);
                break;
            default:
                System.out.println("Unknown action");
        }
    }

    private void post(String[] requestArray){
        String action = requestArray[1];
        int clientId = Integer.parseInt(requestArray[2]);
        int roomId;
        switch(action){
            case "JOIN_GAME":
                roomId = Integer.parseInt(requestArray[requestArray.length - 1]);
                server.responseToClient(clientId, joinGame(clientId, requestArray));
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player " + clientId + " joined the game");
                if(this.rooms[roomId].isReady()){
                    this.rooms[roomId].startGame();
                };

                break;
            case "FOLD":
                roomId = Integer.parseInt(requestArray[requestArray.length - 1]);
                this.rooms[roomId].fold(clientId);
                server.responseToClient(clientId, "200");
                if(this.rooms[roomId].isOnlyOnePlayerLeft()){
                    sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "GAME_OVER");
                }
                sendTableStatusToEveryClientInRoom(roomId);
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player " + clientId + " folded");
                this.rooms[roomId].nextPlayerTurn();
                break;
            case "CHECK":
                roomId = Integer.parseInt(requestArray[requestArray.length - 1]);
                server.responseToClient(clientId, "200");
                sendTableStatusToEveryClientInRoom(roomId);
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player " + clientId + " checked");
                this.rooms[roomId].nextPlayerTurn();
                break;
            case "CALL":
                roomId = Integer.parseInt(requestArray[requestArray.length - 1]);
                this.rooms[roomId].call(clientId);
                server.responseToClient(clientId, "200");
                sendTableStatusToEveryClientInRoom(roomId);
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player#" + clientId + " called");
                this.rooms[roomId].nextPlayerTurn();
                break;
            case "RAISE":
                roomId = Integer.parseInt(requestArray[requestArray.length - 2]);
                int amount = Integer.parseInt(requestArray[requestArray.length - 1]);
                this.rooms[roomId].raise(clientId, amount);
                server.responseToClient(clientId, "200");
                sendTableStatusToEveryClientInRoom(roomId);
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player#" + clientId + " raised by " + amount);
                this.rooms[roomId].nextPlayerTurn();
                break;
            case "CHANGE":
                roomId = Integer.parseInt(requestArray[3]);
                int[] cardNumbers = new int[requestArray.length - 4];
                for(int i = 4; i < requestArray.length; i++){
                    cardNumbers[i - 4] = Integer.parseInt(requestArray[i]);
                }
                this.rooms[roomId].changeCards(clientId, cardNumbers);
                server.responseToClient(clientId, "200");
                sendResponse(clientId, rooms[roomId].getPlayerByClientId(clientId).printCards(rooms[roomId].getPot(), rooms[roomId].getMinimalBet()));
                sendResponseToEveryClientInRoom(this.rooms[roomId].getPlayers(), "Player#" + clientId + " changed cards");
                this.rooms[roomId].nextPlayerTurn();
                break;
            default:
                System.out.println("Unknown action");
        }
    }

    private void get(String[] requestArray){
        String action = requestArray[1];
        int clientId = Integer.parseInt(requestArray[2]);
        if ("GET_ROOMS".equals(action)) {
            server.responseToClient(clientId, getActiveGames());
        } else {
            System.out.println("Unknown action");
        }
    }

    private void put(String[] requestArray){
        String action = requestArray[1];
        int clientId = Integer.parseInt(requestArray[2]);
        if ("CREATE_GAME".equals(action)) {
            server.responseToClient(clientId, createGame(clientId, requestArray));
        } else {
            System.out.println("Unknown action");
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

    public String createGame(int clientId, String[] requestArray){
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
            GameRoom room = new GameRoom(deck, Integer.parseInt(requestArray[requestArray.length - 1]), numRooms - 1, server.getMaxPlayers(), this);
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

    public String joinGame(int clientId, String[] requestArray){
        try {
            int roomId = Integer.parseInt(requestArray[requestArray.length - 1]);
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
        System.out.println(s);
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
