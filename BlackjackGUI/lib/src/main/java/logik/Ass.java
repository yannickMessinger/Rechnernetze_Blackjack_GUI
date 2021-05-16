package logik;

public class Ass extends Card {

    private int value2;

    public Ass(Kartensymbol symbol, Kartenname name) {
        super(symbol, name);
        super.setValue(1);
        this.value2 = 11;
    }

    public int getValueLow() {
        return super.getValue();
    }

    public int getValueHigh() {
        return this.value2;
    }
}
