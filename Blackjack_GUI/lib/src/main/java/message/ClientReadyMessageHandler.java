package message;

import logik.GameState;
import network.NetworkService;

public class ClientReadyMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message m, GameState game, NetworkService networkService) {
    	
        if (!networkService.getClientReady()) {
            networkService.setClientReady(true);
            networkService.write(MessageGenerator.serverReady());
        } else {
            networkService.write(MessageGenerator.incorrectMove());
        }

    }
}
