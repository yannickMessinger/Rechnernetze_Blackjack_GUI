package message;

import logik.Card;
import logik.GameState;
import network.NetworkService;

public class DrawCardMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message m, GameState game, NetworkService networkService) {
        if (game.isStarted() && networkService.getClientReady()) {
           	//Spieler zieht Karte
            Card drawn = game.drawCardPlayer();
            String symbol = drawn.getSymbol().toString();
            String value = drawn.getName().toString();
            networkService.write(MessageGenerator.returnCard(symbol, value));

            //schauen ob Spieler ueber 21
            boolean over = game.checkOver("player");
            if (over) {
                networkService.write(MessageGenerator.over21("player"));
                String winner = "opponent";
                String reason ="over21player";
                game.setEndReason(reason);
                game.setWinner(winner);
                networkService.write(MessageGenerator.gameFinished(reason,winner));
                game.setTerminated(true);
            } else {
                //Gegner ist dran
            	//schauen, ob Karten aufdecken oder ziehen
                if (game.showCardsOpponent()) {
                    networkService.write(MessageGenerator.showCardsOpponent(game.getBestValue("opponent")));
                    int bestValuePlayer = game.getBestValue("player");
                    int bestValueOpponent = game.getBestValue("opponent");

                    String reason = "showCardsOpponent";
                    String winner = "";

                    if (bestValuePlayer > bestValueOpponent) {
                        winner = "player";
                    } else if (bestValuePlayer < bestValueOpponent) {
                        winner = "opponent";
                    } else {
                        winner = "nobody";
                    }
                    game.setWinner(winner);
                    game.setEndReason(reason);
                    networkService.write(MessageGenerator.gameFinished(reason, winner));

                    game.setTerminated(true);
                } else {
                    //Gegener zieht Karte
                    drawn = game.drawCardOpponent();
                    networkService.write(MessageGenerator.dealerCardDrawn());

                    //schauen ob Gegner ueber 21
                    over = game.checkOver("opponent");
                    if (over) {
                        networkService.write(MessageGenerator.over21("opponent"));
                        String winner = "player";
                        String reason ="over21opponent";
                        game.setWinner(winner);
                        game.setEndReason(reason);
                        networkService.write(MessageGenerator.gameFinished(reason, winner));
                        game.setTerminated(true);
                    }
                }

            }
        } else {
            networkService.write(MessageGenerator.incorrectMove());
        }

    }
}
