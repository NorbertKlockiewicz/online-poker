package pl.edu.agh.kis.pz1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DeckTest {
    public static void main(String[] args) {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDeck() {
        Deck deck = new Deck();
        assertTrue(deck.getCardsLeft() == 52);
    }

    @Test
    public void testDeckShuffle() {
        Deck deck = new Deck();
        Card[] cards = deck.dealCards(3);
        assertTrue(cards instanceof Card[] && cards.length == 3 && deck.getCardsLeft() == 49);
    }
}
