package pl.edu.agh.kis.pz1;

import java.util.Scanner;

public class Game {
    private final Scanner action = new Scanner(System.in);
    private final ClientConnection cc;
    private final GameService gameService;
    private int gameId;
    private boolean inGame = false;

    public Game(ClientConnection cc){
        this.cc = cc;
        this.gameService = new GameService(cc);
        System.out.println("You've been correctly connected to the server.");
        openMenu();
    }

    public void openMenu(){
        System.out.println("Choose an option:");
        System.out.println("1. Create a new game(CREATE <ANTE>");
        System.out.println("2. Join an existing game(JOIN <ROOM_ID>");
        System.out.println("3. Display active games(SHOW)");
        String option = action.nextLine();
        String [] options = option.split(" ");
        String res;
        switch(options[0]){
            case "CREATE":
                res = this.gameService.createGame(cc.getClientId(), options[1]);
                int id = Integer.parseInt(res.split(" ")[1]);
                res = res.split(" ")[0];
                if(res.equals("200")){
                    inGame = true;
                    System.out.println("Game created successfully");
                    this.gameId = id;
                }
                else{
                    System.out.println("Game creation failed");
                }
                catchServerMessage();
                break;
            case "JOIN":
                res = this.gameService.joinGame(cc.getClientId(), options[1]);
                if(res.equals("200")){
                    inGame = true;
                    System.out.println("Game joined successfully");
                    this.gameId = Integer.parseInt(options[1]);
                }
                else{
                    System.out.println("Game joining failed");
                }
                catchServerMessage();
                break;
            case "SHOW":
                System.out.println(this.gameService.getActiveGames(cc.getClientId()));
                openMenu();
                break;
            default:
                System.out.println("Wrong option. Try again.");
                openMenu();
                break;
        }
    }

    public void openGameMenu(Boolean canCheck){
        System.out.println("Choose an option:");
        System.out.println("1. FOLD(FOLD)");
        System.out.println("2. CALL(CALL)");
        System.out.println("3. RAISE(RAISE <amount>)");
        if(canCheck){
            System.out.println("4. CHECK(CHECK)");
        }
        String option = action.nextLine();
        String [] options = option.split(" ");
        String res;
        System.out.println(options[0]);
        switch(options[0]){
            case "FOLD":
                res = this.gameService.fold(cc.getClientId(), this.gameId);
                if(res.equals("200")){
                    System.out.println("You folded!");
                }
                else{
                    System.out.println("Something went wrong");
                }
                catchServerMessage();
                break;
            case "CALL":
                res = this.gameService.call(cc.getClientId(), this.gameId);
                System.out.println(res);
                if(res.equals("200")){
                    System.out.println("You called!");
                }
                else{
                    System.out.println("Something went wrong");
                }
                catchServerMessage();
                break;
            case "RAISE":
                res = this.gameService.raise(cc.getClientId(), this.gameId, Integer.parseInt(options[1]));
                if(res.equals("200")){
                    System.out.println("You raised by!" + options[1]);
                }
                else{
                    System.out.println("Something went wrong");
                }
                catchServerMessage();
                break;
            case "CHECK":
                if(canCheck){
                    res = this.gameService.check(cc.getClientId(), this.gameId);
                    if(res.equals("200")){
                        System.out.println("You checked!");
                    }
                    else{
                        System.out.println("Something went wrong");
                    }
                    catchServerMessage();
                }
                else{
                    System.out.println("Wrong option. Try again.");
                    openGameMenu(false);
                }
                break;
            default:
                System.out.println("Wrong option. Try again.");
                openMenu();
                break;
        }
    }

    public void chooseCardsToChange(){
        String option = action.nextLine();
        String [] options = option.split(" ");
        String res;
        if(options[0].equals("")){
            System.out.println("You didn't change any cards");
            res = this.gameService.changeCards(cc.getClientId(), this.gameId, new int[0]);
            if(res.equals("200")){
                System.out.println("You changed cards!");
            }
            else{
                System.out.println("Something went wrong");
            }
        }
        else if(options[0].equals("CHANGE")){
            int [] cardNumbers = new int[options.length - 1];
            for(int i = 1; i < options.length; i++){
                cardNumbers[i - 1] = Integer.parseInt(options[i]);
                System.out.println(cardNumbers[i - 1]);
            }

            res = this.gameService.changeCards(cc.getClientId(), this.gameId, cardNumbers);
            if(res.equals("200")){
                System.out.println("You changed cards!");
            }
            else{
                System.out.println(res);
                System.out.println("Something went wrong");
            }
            catchServerMessage();
        }
        else{
            System.out.println("Wrong option. Try again.");
            chooseCardsToChange();
        }
    }

    public void catchServerMessage(){
        while(true) {
            String message = this.cc.listenForServerMessage();
            System.out.println(message);
            String[] messageArray = message.split(" ");
            if (messageArray[0].equals("USER_ACTION")) {
                if(inGame && messageArray[1].equals("PLAY")){
                    openGameMenu(messageArray[2].equals("CHECK"));
                }
                else if(inGame && messageArray[1].equals("CHANGE")){
                    chooseCardsToChange();
                }
                else {
                    openMenu();
                }
            }
            else if(messageArray[0].equals("GAME_OVER")){
                inGame = true;
                openMenu();
            }
        }
    }
}
