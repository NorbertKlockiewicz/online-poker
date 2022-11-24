package pl.edu.agh.kis.pz1;

public class Card {
    private final int value;
    private final String color;
    private final String name;
    private final String colorLabel;

    public Card(int value, String color, String name, String colorLabel) {
        this.value = value;
        this.color = color;
        this.name = name;
        this.colorLabel = colorLabel;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getColorLabel() {
        return colorLabel;
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
