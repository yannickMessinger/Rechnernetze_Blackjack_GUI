package message;

import logik.GameState;
import network.NetworkService;

public class ShowCardsMessageHandler implements MessageHandler {
	
    @Override
    public void handleMessage(Message m, GameState game, NetworkService networkService) {
        if (game.isStarted() && networkService.getClientReady()) {
        	//Karten aufdecken
            int bestValuePlayer = game.getBestValue("player");
            int bestValueOpponent = game.getBestValue("opponent");
            networkService.write(MessageGenerator.confirmCardsShown(bestValuePlayer, bestValueOpponent));

            String reason = "showCardsPlayer";
            String winner = "";
            
            //Handkarten vergleichen
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
            networkService.write(MessageGenerator.incorrectMove());
        }

    }
}
