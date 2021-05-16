package message;

import logik.GameState;
import network.NetworkService;

public class ReadyToPlayMessageHandler implements MessageHandler {
	
    @Override
    public void handleMessage(Message m, GameState game, NetworkService networkService) {
        if (networkService.getClientReady()) {
            boolean startSuccessful = game.startGame();
            if (startSuccessful) {
            	//Auslosen, wer anfaengt
            	String starter = "";
                int zufall = (int)(Math.random()*10);
                System.out.println(zufall);
                if (zufall > 3) {
                	//Spieler faengt an
                    starter = "player";
                    networkService.write(MessageGenerator.startGame(starter));
                    System.out.println("du fängst an");
                } else {
                	//Gegner faengt an
                    starter = "opponent";
                    networkService.write(MessageGenerator.startGame(starter));
                    System.out.println("gegner fängt an");
                    game.drawCardOpponent();
                    networkService.write(MessageGenerator.dealerCardDrawn());
                }
            } else {
                networkService.write(MessageGenerator.incorrectMove());
            }
        } else {
            networkService.write(MessageGenerator.incorrectMove());
        }
    }
}
