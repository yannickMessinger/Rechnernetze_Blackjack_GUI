package message;

import java.util.HashMap;

/*Fuer jeden MessageType, der vom Server geschickt werden kann,
 * gibt es eine passende Methode, die ein Message-Objekt zurueckgibt*/
public class MessageGenerator {
	
    public static Message invalidProtocol() {
        HashMap<String, String> body = new HashMap<>();
        return new Message(MessageType.INVALID_PROTOCOL, body);
    }

    public static Message incorrectMove() {
        HashMap<String, String> body = new HashMap<>();
        return new Message(MessageType.INCORRECT_MOVE, body);
    }

    public static Message serverReady() {
        HashMap<String, String> body = new HashMap<>();
        return new Message(MessageType.SERVER_READY, body);
    }

    public static Message startGame(String starter) {
        HashMap<String, String> body = new HashMap<>();
        body.put("starter", starter);
        return new Message(MessageType.START_GAME, body);
    }

    public static Message returnCard(String symbol, String name) {
        HashMap<String, String> body = new HashMap<>();
        body.put("symbol", symbol);
        body.put("name", name);
        return new Message(MessageType.RETURN_CARD, body);
    }

    public static Message dealerCardDrawn() {
        HashMap<String, String> body = new HashMap<>();
        return new Message(MessageType.DEALER_CARD_DRAWN, body);
    }

    public static Message showCardsOpponent(int value) {
        HashMap<String, String> body = new HashMap<>();
        body.put("value", Integer.toString(value));
        return new Message(MessageType.SHOW_CARDS_OPPONENT, body);
    }

    public static Message over21(String who) {
        HashMap<String, String> body = new HashMap<>();
        body.put("who", who);
        return new Message(MessageType.OVER_21, body);
    }

    public static Message gameFinished(String reason, String winner) {
        HashMap<String, String> body = new HashMap<>();
        body.put("reason", reason);
        body.put("winner", winner);
        return new Message(MessageType.GAME_FINISHED, body);
    }

    public static Message confirmCardsShown(int valueP, int valueO) {
        HashMap<String, String> body = new HashMap<>();
        body.put("valueP", Integer.toString(valueP));
        body.put("valueO", Integer.toString(valueO));
        return new Message(MessageType.CONFIRM_CARDS_SHOWN, body);
    }

    public static Message endGame() {
        HashMap<String, String> body = new HashMap<>();
        return new Message(MessageType.END_GAME, body);
    }
}
