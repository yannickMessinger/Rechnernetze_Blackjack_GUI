package message;

public enum MessageType {
    
	//MessageTypes Client
    CLIENT_READY,
    READY_TO_PLAY,
    DRAW_CARD,
    SHOW_CARDS,
    CONFIRM_GAME_FINISHED,

    //MessageTypes Server
    INVALID_PROTOCOL,
    INCORRECT_MOVE,
    SERVER_READY,
    START_GAME,
    RETURN_CARD,
    DEALER_CARD_DRAWN,
    SHOW_CARDS_OPPONENT,
    OVER_21,
    GAME_FINISHED,
    CONFIRM_CARDS_SHOWN,
    END_GAME

}
