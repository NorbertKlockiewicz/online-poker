package pl.edu.agh.kis.pz1;

public class Card {
    private int value;
    private String color;
    private String name;

    public Card(int value, String color, String name) {
        this.value = value;
        this.color = color;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
