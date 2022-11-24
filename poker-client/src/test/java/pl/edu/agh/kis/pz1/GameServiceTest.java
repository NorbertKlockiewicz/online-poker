package pl.edu.agh.kis.pz1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameServiceTest {
    private ClientConnection clientConnection1;
    private ClientConnection clientConnection2;
    private ClientConnection clientConnection3;
    private ClientConnection clientConnection4;
    private GameService gameService1;
    private GameService gameService2;
    private GameService gameService3;
    private GameService gameService4;

    public static void main(String[] args) {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateConnections(){
        clientConnection1 = new ClientConnection();
        clientConnection2 = new ClientConnection();
        clientConnection3 = new ClientConnection();
        clientConnection4 = new ClientConnection();
        gameService1 = new GameService(clientConnection1);
        gameService2 = new GameService(clientConnection2);
        gameService3 = new GameService(clientConnection3);
        gameService4 = new GameService(clientConnection4);
    }
    @Test
    public void testGameServiceCreateGame() {
        assertTrue(gameService1.createGame(0, "20").split(" ")[0].equals("200"));
    }

    @Test
    public void testGameServiceGetActiveGames() {
        System.out.println(gameService2.getActiveGames(1));
        assertTrue(true);
    }

    @Test
    public void testGameServiceJoinGame() {
        assertTrue(gameService2.joinGame(1, "0").split(" ")[0].equals("200") && gameService3.joinGame(2, "0").split(" ")[0].equals("200") && gameService4.joinGame(3, "0").split(" ")[0].equals("200"));
    }

    @Test
    public void testGameServiceCheck(){
        assertTrue(gameService1.check(0, 0).split(" ")[0].equals("200"));
    }

    @Test
    public void testGameServiceCall(){
        assertTrue(gameService2.call(1, 0).split(" ")[0].equals("200"));
    }

    @Test
    public void testGameServiceRaise(){
        assertTrue(gameService3.raise(2, 0, 20).split(" ")[0].equals("200"));
    }
}
