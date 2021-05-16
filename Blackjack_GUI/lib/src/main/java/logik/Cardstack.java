package logik;

import java.util.Collections;
import java.util.Stack;

public class Cardstack {
    private Stack<Card> stack;

    public Cardstack() {
        this.stack = new Stack<>();
        for (Kartensymbol symbol : Kartensymbol.values()) {
            for (Kartenname name : Kartenname.values()) {
                Card newCard;
                if (name == Kartenname.ASS) {
                    newCard = new Ass(symbol, name);
                } else {
                    newCard = new Card(symbol, name);
                    newCard.setValue(getCardvalue(name));
                }

                stack.push(newCard);

            }
        }
        Collections.shuffle(stack);
    }

    public Card drawCard() {
        return stack.pop();
    }

    public int getCardvalue(Kartenname name) {
        switch (name) {
            case ZWEI:
                return 2;
            case DREI:
                return 3;
            case VIER:
                return 4;
            case FUENF:
                return 5;
            case SECHS:
                return 6;
            case SIEBEN:
                return 7;
            case ACHT:
                return 8;
            case NEUN:
                return 9;
            default:
                return 10;
        }
    }
}
