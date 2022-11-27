package pl.edu.agh.kis.pz1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameRoomTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGameRoom() {
        GameRoom gameRoom = new GameRoom(20, 2);
        assertTrue(true);
    }

    @Test
    public void testGameRoomGetPlayers() {
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertEquals(gameRoom.getPlayers().length, 2);
    }

    @Test
    public void testGameRoomGetInfo() {
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertEquals(gameRoom.getRoomInfo(), "Room info: Minimal bet: 20, Players: 2/2");
    }

    @Test
    public void testGameRoomIsReady() {
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertTrue(gameRoom.isReady());
    }

    @Test
    public void testGameRoomIsNotReady() {
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        gameRoom.addPlayer(player1);
        assertFalse(gameRoom.isReady());
    }

    @Test
    public void testGameRoomIsNotGameOver() {
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertFalse(gameRoom.isGameOver());
    }

    @Test
    public void testGameRoomGetPot(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        gameRoom.addToPot(40);
        assertEquals(gameRoom.getPot(), 40);
    }

    @Test
    public void testGameRoomGetMinimalBet(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertEquals(gameRoom.getMinimalBet(), 20);
    }

    @Test
    public void testGameRoomGetNextPlayerTurn(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertEquals(gameRoom.getNextPlayerTurn(), 1);
    }

    @Test
    public void testGameRoomGetPlayerByClientId(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        assertEquals(gameRoom.getPlayerByClientId(1), player1);
    }

    @Test
    public void testGameRoomNextPlayerTurn(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        gameRoom.nextPlayerTurn();
        assertEquals(gameRoom.getNextPlayerTurn(), 0);
    }

    @Test
    public void testGameRoomCheckIfEveryoneMadeEqualBets(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        player1.setBet(20);
        player2.setBet(20);
        assertEquals(gameRoom.checkIfEveryoneMadeEqualBets(), -1);
    }

    @Test
    public void testGameRoomIsOnlyOnePlayerLeft(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);
        player1.setBet(20);
        player2.setBet(20);
        player1.setFolded(true);
        assertTrue(gameRoom.isOnlyOnePlayerLeft());
    }

    @Test
    public void testGameRoomStartBasicTurn(){
        GameRoom gameRoom = new GameRoom(20, 2);
        Player player1 = new Player("John", 1);
        Player player2 = new Player("John", 2);
        gameRoom.addPlayer(player1);
        gameRoom.addPlayer(player2);

        assertEquals(gameRoom.startBasicTurn(), "USER_ACTION PLAY CALL");
    }
}
