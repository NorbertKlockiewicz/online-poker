package pl.edu.agh.kis.pz1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    public static void main(String[] args) {
        Player player = new Player("Jan", 1000, 0);
        System.out.println(player);
    }

    @Test
    public void testPlayer() {
        Player player = new Player("Jan", 1000, 0);
        assertEquals("Jan", player.getName());
    }
}
