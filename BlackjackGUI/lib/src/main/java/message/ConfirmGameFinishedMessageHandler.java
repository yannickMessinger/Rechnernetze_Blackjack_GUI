package message;

import logik.GameState;
import network.NetworkService;

public class ConfirmGameFinishedMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message m, GameState game, NetworkService networkService) {
    	if (game.isTerminated()) {
            game.setEndConfirmed(true);
            networkService.write(MessageGenerator.endGame());
            networkService.closeGame();
        } else {
            networkService.write(MessageGenerator.incorrectMove());
        }
    }
}
