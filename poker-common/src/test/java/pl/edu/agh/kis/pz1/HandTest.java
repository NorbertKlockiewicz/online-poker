package pl.edu.agh.kis.pz1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {
    public static void main(String[] args) {

    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHandTypeStraight() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(3, "hearts", "three", "");
        cards[2] = new Card(4, "spades", "four", "");
        cards[3] = new Card(5, "hearts", "five", "");
        cards[4] = new Card(6, "hearts", "six", "");

        Hand hand = new Hand(cards);
        assertEquals(HandType.STRAIGHT, hand.getType());
    }

    @Test
    public void testHandTypeFlush() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(5, "hearts", "three", "");
        cards[2] = new Card(6, "hearts", "four", "");
        cards[3] = new Card(11, "hearts", "five", "");
        cards[4] = new Card(12, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.FLUSH, hand.getType());
    }

    @Test
    public void testHandTypeFullHouse() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(2, "spades", "two", "");
        cards[2] = new Card(2, "clubs", "two", "");
        cards[3] = new Card(5, "hearts", "five", "");
        cards[4] = new Card(5, "hearts", "five", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.FULL_HOUSE, hand.getType());
    }

    @Test
    public void testHandTypeFourOfAKind() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(2, "spades", "two", "");
        cards[2] = new Card(2, "clubs", "two", "");
        cards[3] = new Card(2, "diamonds", "two", "");
        cards[4] = new Card(5, "hearts", "five", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.FOUR_OF_A_KIND, hand.getType());
    }

    @Test
    public void testHandTypeStraightFlush() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(3, "hearts", "three", "");
        cards[2] = new Card(4, "hearts", "four", "");
        cards[3] = new Card(5, "hearts", "five", "");
        cards[4] = new Card(6, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.STRAIGHT_FLUSH, hand.getType());
    }

    @Test
    public void testHandTypeRoyalFlush() {
        Card[] cards = new Card[5];
        cards[0] = new Card(10, "hearts", "ten", "");
        cards[1] = new Card(11, "hearts", "jack", "");
        cards[2] = new Card(12, "hearts", "queen", "");
        cards[3] = new Card(13, "hearts", "king", "");
        cards[4] = new Card(14, "hearts", "ace", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.ROYAL_FLUSH, hand.getType());
    }

    @Test
    public void testHandTypeHighCard() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "spades", "two", "");
        cards[1] = new Card(4, "hearts", "three", "");
        cards[2] = new Card(6, "hearts", "four", "");
        cards[3] = new Card(8, "hearts", "five", "");
        cards[4] = new Card(10, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.HIGH_CARD, hand.getType());
    }

    @Test
    public void testHandTypePair() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(2, "spades", "two", "");
        cards[2] = new Card(4, "hearts", "four", "");
        cards[3] = new Card(5, "hearts", "five", "");
        cards[4] = new Card(6, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.PAIR, hand.getType());
    }

    @Test
    public void testHandTypeTwoPair() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(2, "spades", "two", "");
        cards[2] = new Card(4, "hearts", "four", "");
        cards[3] = new Card(4, "hearts", "four", "");
        cards[4] = new Card(6, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.TWO_PAIRS, hand.getType());
    }

    @Test
    public void testHandTypeThreeOfAKind() {
        Card[] cards = new Card[5];
        cards[0] = new Card(2, "hearts", "two", "");
        cards[1] = new Card(2, "spades", "two", "");
        cards[2] = new Card(2, "clubs", "two", "");
        cards[3] = new Card(4, "hearts", "four", "");
        cards[4] = new Card(6, "hearts", "six", "");
        Hand hand = new Hand(cards);
        assertEquals(HandType.THREE_OF_A_KIND, hand.getType());
    }
}
