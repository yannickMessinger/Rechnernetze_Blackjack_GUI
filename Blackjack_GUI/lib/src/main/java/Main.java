import message.ClientReadyMessageHandler;
import message.ConfirmGameFinishedMessageHandler;
import message.DrawCardMessageHandler;
import message.MessageType;
import message.ReadyToPlayMessageHandler;
import message.ShowCardsMessageHandler;
import network.Server;

public class Main {

	public static void main(String[] args) {
		
		Server s = new Server(29004);
        s.registerMessageHandler(MessageType.CLIENT_READY, new ClientReadyMessageHandler());
        s.registerMessageHandler(MessageType.READY_TO_PLAY, new ReadyToPlayMessageHandler());
        s.registerMessageHandler(MessageType.DRAW_CARD, new DrawCardMessageHandler());
        s.registerMessageHandler(MessageType.SHOW_CARDS, new ShowCardsMessageHandler());
        s.registerMessageHandler(MessageType.CONFIRM_GAME_FINISHED, new ConfirmGameFinishedMessageHandler());
        s.start();

	}

}
