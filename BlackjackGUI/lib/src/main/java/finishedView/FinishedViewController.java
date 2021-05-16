package finishedView;

import java.io.IOException;

import app.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logik.Card;
import logik.GameState;
import network.ConnectionLostException;
import network.NetworkService;

public class FinishedViewController extends ViewController {
	private NetworkService networkService;
	private GameState gameState;
	private Stage newWindow;
	private Label resultLabel;
	private Button disconnectButton;
	private Label reasonLabel;
    private	StackPane playerCards;
    private	StackPane opponentCards;
	
	
	public FinishedViewController(GameState gameState, NetworkService networkService, Stage newWindow) {
		this.networkService = networkService;
		this.gameState = gameState;
		this.newWindow = newWindow;
		
		rootView = new FinishedView();
		resultLabel = ((FinishedView) rootView).getResultLabel();
		reasonLabel = ((FinishedView) rootView).getReasonLabel();
		disconnectButton = ((FinishedView) rootView).getDisconnectButton();
		playerCards = ((FinishedView) rootView).getPlayerCards();
		opponentCards = ((FinishedView) rootView).getOpponentCards();
		
		initialize();
		
	}

	@Override
	public void initialize() {
		
		
		//Spielerkarten auslesen und anzeigen
		int versatz = -150;
		for(Card card : gameState.getPlayerCards()) {
			String cardName = card.toString()+".png";
			Label cardLabel = new Label();
			Image img = new Image("cardPics/"+cardName);
			ImageView view = new ImageView(img);
			view.setFitHeight(150);
		    view.setPreserveRatio(true);
		    cardLabel.setGraphic(view);
		    cardLabel.setTranslateX(versatz);
		    versatz += 70;
		    playerCards.getChildren().add(cardLabel);
		}
		
		//Gegnerkarten auslesen und anzeigen
		versatz = -150;
		for(Card card : gameState.getOpponentCards()) {
			String cardName = card.toString()+".png";
			Label cardLabel = new Label();
			Image img = new Image("cardPics/"+cardName);
			ImageView view = new ImageView(img);
			view.setFitHeight(150);
		    view.setPreserveRatio(true);
		    cardLabel.setGraphic(view);
		    cardLabel.setTranslateX(versatz);
		    versatz += 70;
		    opponentCards.getChildren().add(cardLabel);
		}
		
		//Verbindung zum Server trennen
		disconnectButton.addEventHandler(ActionEvent.ACTION,  event -> {
			
			try {
				networkService.read("CONFIRM_GAME_FINISHED;");
				this.newWindow.close();
			} catch (IOException | ConnectionLostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
		//Result-Text generieren
		switch (gameState.getWinner()) {
			case "player":
				resultLabel.setText("DU HAST GEWONNEN");
				break;
			
			case "opponent":
				resultLabel.setText("DU HAST VERLOREN");
				break;
			
			default:
				resultLabel.setText("UNENTSCHIEDEN");
		}
		
		//Reason-Text generieren
		switch(gameState.getEndReason()) {
			case"showCardsPlayer":
				reasonLabel.setText("Du hast die Karten aufgedeckt");
				break;
			
			case "showCardsOpponent":
				reasonLabel.setText("Der Gegner hat die Karten aufgedeckt");
				break;
			
			case "over21player":
				reasonLabel.setText("Du hast über 21 Punkte");
				break;
				
			case"over21opponent":
				reasonLabel.setText("Der Gegner hat über 21 Punkte");
				break;
				
			default:
				resultLabel.setText("");
		}
		
		
		
	}

}
