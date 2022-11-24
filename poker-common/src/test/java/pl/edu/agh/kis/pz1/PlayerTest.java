package pl.edu.agh.kis.pz1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerTest {
    public static void main(String[] args) {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPlayer() {
        Player player = new Player("John", 1);
        assertTrue(player.getName() == "John");
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
        assertTrue(player.getCards() instanceof Card[] && player.getCards().length == 5 && player.getCards() == cards);
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
        assertTrue(player.printCards(10, 10) instanceof String);
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
        assertTrue(player.toString() instanceof String);
    }

    @Test
    public void testPlayerFold(){
        Player player = new Player("John", 1);
        player.setFolded(true);
        assertTrue(player.isFolded());
    }

}
