package message;

import logik.GameState;
import network.NetworkService;

public interface MessageHandler {
	//INTERFACE fuer spezielle MessageHandler
	//fuer jeden MessageType, der vom Client gesendet werden kann, gibt es einen MessageHandler
    void handleMessage(Message m, GameState game, NetworkService networkService);
}
