package pl.edu.agh.kis.pz1;

import java.util.Scanner;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Server started!" );
        Deck deck = new Deck();
        GameRoom room = new GameRoom(deck);
        Player player1 = new Player("Player1", 100);
        Player player2 = new Player("Player2", 100);
        room.addPlayer(player1);
        room.addPlayer(player2);

        room.startGame();


    }
}
