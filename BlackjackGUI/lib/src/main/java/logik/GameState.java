package logik;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

//gesamte Spiellogik (ein GameState pro Client)
public class GameState {
	
    private Cardstack stack;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> opponentCards;
    private boolean started;
    private boolean terminated;
    private SimpleObjectProperty<Card> playerCardsProperty;
    private SimpleObjectProperty<Card> opponentCardsProperty;
    private SimpleBooleanProperty terminateProperty;
    private boolean endConfirmed;
    private String winner;
    private String endReason;

    private final int SPIELENDE_PUNKTE = 21;

    //Spielstart (Kartenstapel erzeugen)
    public boolean startGame() {
        if (!started) {
            this.started = true;
            this.stack = new Cardstack();
            this.playerCards = new ArrayList<>();
            this.opponentCards = new ArrayList<>();
            this.terminated = false;
            playerCardsProperty = new SimpleObjectProperty<Card>();
            opponentCardsProperty = new SimpleObjectProperty<Card>();
            terminateProperty = new SimpleBooleanProperty();
            return true;
        } else {
            return false;
        }
    }

    //Spieler zieht Karte
    public Card drawCardPlayer() {
        Card drawnCard = this.stack.drawCard();
        this.playerCards.add(drawnCard);
        System.out.println("Du hast die Karte " + drawnCard.toString() + " gezogen");
        this.playerCardsProperty.setValue(drawnCard);
        return drawnCard;
    }

    //Gegner zieht Karte
    public Card drawCardOpponent() {
        Card drawnCard = this.stack.drawCard();
        this.opponentCards.add(drawnCard);
        this.opponentCardsProperty.setValue(drawnCard);
        return drawnCard;
    }

    //alle moeglichen Wertkombinationen der Handkarten berechnen und in ArrayList zurück geben
    public ArrayList<Integer> getPossibleValues(String who) {
        
        ArrayList<Card> cards;
        if (who.equals("player")) {
            cards = this.playerCards;
        } else if (who.equals("opponent")) {
            cards = this.opponentCards;
        } else {
            return null;
        }
        ArrayList<Integer> values = new ArrayList<>();
        int withoutAss = 0;
        int countAss = 0;
        int assValueLow = 0;
        int assValueHigh = 0;
        //schauen ob Ass vorhanden
        for (Card a : cards) {
            if (a instanceof Ass) {
                assValueLow = ((Ass) a).getValueLow();
                assValueHigh = ((Ass) a).getValueHigh();
                countAss++;
            } else {
                withoutAss = withoutAss + a.getValue();
            }
        }
        if (countAss == 0) {
            values.add(withoutAss);
        } else {
        	//wenn Ass vorhanden Werte: Werte berechnen und in ArrayList speichern
            for (int i = 0; i < countAss; i++) {
                for (int j = 0; j <= i + 1; j++) {
                    int assValue = j * assValueLow + (i+1-j) * assValueHigh;
                    values.add(withoutAss + assValue);
                }
            }
        }
        return values;
    }

    //niedrigsten Wert zurueckgeben
    //benoetigt fuer checkOver() (wenn niedrigster Wert ueber 21 -> Spiel vorbei) 
    public int getLowestValue(String who) {
        ArrayList<Integer> allValues = this.getPossibleValues(who);
        return Collections.min(allValues);
    }

    //besten Wert berechnen
    //bester Wert ist unter 21 & möglichst nah an der 21
    public int getBestValue(String who) {
        ArrayList<Integer> allValues = this.getPossibleValues(who);
        int bestValue = allValues.get(0);
        for (int v : allValues) {
            if (v == SPIELENDE_PUNKTE) {
                bestValue = v;
                break;
            } else if (v > SPIELENDE_PUNKTE && bestValue > SPIELENDE_PUNKTE) {
                if (Math.abs(v - SPIELENDE_PUNKTE) < Math.abs(bestValue - SPIELENDE_PUNKTE)) {
                    bestValue = v;
                }
            } else if (v < SPIELENDE_PUNKTE && bestValue > SPIELENDE_PUNKTE) {
                bestValue = v;
            } else if (v < SPIELENDE_PUNKTE && bestValue < SPIELENDE_PUNKTE) {
                if (Math.abs(v - SPIELENDE_PUNKTE) < Math.abs(bestValue - SPIELENDE_PUNKTE)) {
                    bestValue = v;
                }
            }
        }
        return bestValue;
    }

    //pruefen ob niedrigster Wert ueber 21
    public boolean checkOver(String who) {
        int lowestValue = this.getLowestValue(who);

        if (lowestValue > 21) {
            return true;
        } else {
            return false;
        }
    }

    //KI: wenn Gegner zwischen 16 und 21 Punkte hat, fordert er, Karten anzuzeigen
    public boolean showCardsOpponent() {
        int best = this.getBestValue("opponent");
        if (best == 21) {
            return true;
        } else if (best < this.SPIELENDE_PUNKTE && Math.abs(best - this.SPIELENDE_PUNKTE) < 6) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
    	this.terminateProperty.setValue(terminated);
        this.terminated = terminated;
    }

    public int getSPIELENDE_PUNKTE() {
        return this.SPIELENDE_PUNKTE;
    }

    public boolean isStarted() {
        return started;
    }
    public SimpleObjectProperty<Card> getPlayerCardsProperty(){
    	return playerCardsProperty;
    }
    
    public SimpleObjectProperty<Card> getOpponentCardsProperty(){
    	return opponentCardsProperty;
    }
    
    public void setEndConfirmed(boolean endConfirmed) {
        this.endConfirmed = endConfirmed;
    }

    public boolean isEndConfirmed() {
        return endConfirmed;
    }
    
    public SimpleBooleanProperty getTerminateProperty() {
    	return terminateProperty;
    }
    
    public String getWinner() {
    	return winner;
    }
    
    public void setWinner(String winner) {
    	this.winner = winner;
    }
    
    public String getEndReason() {
    	return endReason;
    }
    
    public void setEndReason(String reason) {
    	this.endReason = reason;
    }
    
    public ArrayList<Card> getPlayerCards(){
    	return playerCards;
    }
    
    public ArrayList<Card> getOpponentCards(){
    	return opponentCards;
    }
    
    
    
}
