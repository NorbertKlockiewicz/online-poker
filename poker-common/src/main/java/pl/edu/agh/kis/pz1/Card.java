package pl.edu.agh.kis.pz1;

public class Card {
    private int value;
    private String color;
    private String name;
    private String colorLabel;

    public Card(int value, String color, String name, String colorLabel) {
        this.value = value;
        this.color = color;
        this.name = name;
        this.colorLabel = colorLabel;
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
