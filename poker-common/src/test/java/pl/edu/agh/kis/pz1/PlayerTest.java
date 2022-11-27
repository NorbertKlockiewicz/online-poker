package pl.edu.agh.kis.pz1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    public static void main(String[] args) {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPlayer() {
        Player player = new Player("John", 1);
        assertSame("John", player.getName());
    }

    @Test
    public void testPlayerHand() {
        Player player = new Player("John", 1);
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "Hearts", "2", "hearts");
        cards[1] = new Card(3, "Hearts", "3", "hearts");
        cards[2] = new Card(4, "Hearts", "4", "hearts");
        cards[3] = new Card(5, "Hearts", "5", "hearts");
        cards[4] = new Card(11, "Hearts", "J", "hearts");
        player.setCards(cards);
        assertTrue(player.getCards() != null && player.getCards().length == 5 && player.getCards() == cards);
    }

    @Test
    public void testPlayerCardPrinter(){
        Player player = new Player("John", 1);
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "Hearts", "2", "hearts");
        cards[1] = new Card(3, "Hearts", "3", "hearts");
        cards[2] = new Card(4, "Hearts", "4", "hearts");
        cards[3] = new Card(5, "Hearts", "5", "hearts");
        cards[4] = new Card(11, "Hearts", "J", "hearts");
        player.setCards(cards);
        assertNotNull(player.printCards(10, 10));
    }

    @Test
    public void testPlayerToString(){
        Player player = new Player("John", 1);
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "Hearts", "2", "hearts");
        cards[1] = new Card(3, "Hearts", "3", "hearts");
        cards[2] = new Card(4, "Hearts", "4", "hearts");
        cards[3] = new Card(5, "Hearts", "5", "hearts");
        cards[4] = new Card(11, "Hearts", "J", "hearts");
        player.setCards(cards);
        assertNotNull(player.toString());
    }

    @Test
    public void testPlayerFold(){
        Player player = new Player("John", 1);
        player.setFolded(true);
        assertTrue(player.isFolded());
    }
}
