package logik;

public class Card {

    private Kartensymbol symbol;
    private Kartenname name;
    private int value;

    public Card(Kartensymbol symbol, Kartenname name) {
        this.symbol = symbol;
        this.name = name;
        this.value = 0;
    }

    public Kartensymbol getSymbol() {
        return symbol;
    }

    public Kartenname getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        return this.symbol.toString() + this.name.toString();
    }
}
