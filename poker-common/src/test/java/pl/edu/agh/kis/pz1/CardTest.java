package pl.edu.agh.kis.pz1;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    public static void main(String[] args) {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals(2, card.getValue());
    }

    @Test
    public void testCard() {
        Card card = new Card(2, "hearts", "two", "");
        assertEquals(2, card.getValue());
    }
}

