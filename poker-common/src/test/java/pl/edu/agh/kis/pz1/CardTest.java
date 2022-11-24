package pl.edu.agh.kis.pz1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    public static void main(String[] args) {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals(2, card.getValue());
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCardValueMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals(2, card.getValue());
    }

    @Test
    public void testCardSuitMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals("hearts", card.getSuit());
    }

    @Test
    public void testCardNameMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals("two", card.getName());
    }

    @Test
    public void testCardColorLabelMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals("", card.getColorLabel());
    }

    @Test
    public void testCardToStringMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals("Card{value=2, color='hearts', name='two'}", card.toString());
    }

    @Test
    public void testCardValueNotMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertNotEquals(3, card.getValue());
    }

    @Test
    public void testCardSuitNotMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertNotEquals("spades", card.getSuit());
    }

    @Test
    public void testCardNameNotMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertNotEquals("three", card.getName());
    }

    @Test
    public void testCardColorLabelNotMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertNotEquals("red", card.getColorLabel());
    }

    @Test
    public void testCardToStringNotMatch() {
        Card card = new Card(2, "hearts", "two", "");
        assertNotEquals("Card{value=3, color='hearts', name='two'}", card.toString());
    }
}

