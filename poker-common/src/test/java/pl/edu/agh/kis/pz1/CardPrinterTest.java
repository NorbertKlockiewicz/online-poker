package pl.edu.agh.kis.pz1;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardPrinterTest {
    public static void main(String[] args) {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "Hearts", "2", "♥");
        cards[1] = new Card(3, "Hearts", "3", "♥");
        cards[2] = new Card(4, "Hearts", "4", "♥");
        cards[3] = new Card(5, "Hearts", "5", "♥");
        cards[4] = new Card(11, "Hearts", "J", "♥");
        System.out.println(CardPrinter.printCards(cards));
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCardPrinter() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "Hearts", "2", "♥");
        cards[1] = new Card(3, "Hearts", "3", "♥");
        cards[2] = new Card(4, "Hearts", "4", "♥");
        cards[3] = new Card(5, "Hearts", "5", "♥");
        cards[4] = new Card(11, "Hearts", "J", "♥");
        assertTrue(CardPrinter.printCards(cards) instanceof String);
    }
}
