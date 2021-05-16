package gameView;

import java.io.IOException;

import app.ViewController;
import finishedView.FinishedViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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

public class GameViewController extends ViewController {
	
	private NetworkService networkService;
	private GameState gameState;
	private Stage newWindow;
	private Button drawCard;
	private Button showCards;
	private int kartenVersatz;
	private int kartenVersatzOpponent;

	public GameViewController(GameState gameState, NetworkService networkService, Stage newWindow) {
		
	    rootView = new GameView();
	    this.kartenVersatz = -150;
	    this.kartenVersatzOpponent=-150;
		this.networkService = networkService;
		this.gameState = gameState;
		this.newWindow = newWindow;
		
		drawCard = ((GameView) rootView).getDrawCardButton();
		showCards = ((GameView) rootView).getShowCardButton();
		
		initialize();
}

	@Override
	public void initialize() {

		//Karte ziehen
		drawCard.addEventHandler(ActionEvent.ACTION, event -> {
			if(!gameState.isTerminated()) {
			try {
			networkService.read("DRAW_CARD;");
			} catch (IOException | ConnectionLostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		});
		
		//Karten aufdecken
		showCards.addEventHandler(ActionEvent.ACTION, event -> {
			
			try {
				networkService.read("SHOW_CARDS;");
			} catch (IOException | ConnectionLostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		//Wenn Gegner anfängt
		//Handkarten vom Gegner durchiterieren und anzeigen, da ChangeListener noch nicht aktiv ist
		if (gameState.getOpponentCardsProperty().getValue() != null) {
			StackPane replacePane = ((GameView) rootView).getOponnentCards();
			
			String cardName = "RUECKEN.png";
			Label cardLabel = new Label();
			Image img = new Image("cardPics/"+cardName);
			ImageView view = new ImageView(img);
			view.setFitHeight(100);
		    view.setPreserveRatio(true);
		    cardLabel.setGraphic(view);
		    cardLabel.setTranslateX(kartenVersatzOpponent);
		    kartenVersatzOpponent += 40;
		    replacePane.getChildren().add(cardLabel);
		}
		
		
		//-- ChangeListener: Handkarten Gegner -----------------------------------
		//zuletzt gezogene Handkarte anzeigen
		gameState.getOpponentCardsProperty().addListener(new ChangeListener<Card>() {

			@Override
			public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
				 
				/*	1. Label erstellen
				 *	2. Image setzen
				 *	3. Label verschieben
				 *	4. 1 Sekunde warten
				 *	5. Karte anzeigen */
				
				StackPane replacePane = ((GameView) rootView).getOponnentCards();
				String cardName = "RUECKEN.png";
				Label cardLabel = new Label();
				Image img = new Image("cardPics/"+cardName);
				ImageView view = new ImageView(img);
				view.setFitHeight(100);
			    view.setPreserveRatio(true);
			    cardLabel.setGraphic(view);
			    cardLabel.setTranslateX(kartenVersatzOpponent);
			    kartenVersatzOpponent += 40;
			    
			    Task<Void> sleeper = new Task<Void>() {
		            @Override
		            protected Void call() throws Exception {
		                try {
		                	
		                    Thread.sleep(1000);
		                } catch (InterruptedException e) {
		                }
		                return null;
		            }
		        };
		        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		            @Override
		            public void handle(WorkerStateEvent event) {
		            	  replacePane.getChildren().add(cardLabel);
		            }
		        });
		        new Thread(sleeper).start();
			  
			}
			
		});
		
		
		//-- ChangeListener: Handkarten Spieler -----------------------------------
		//zuletzt gezogene Handkarte anzeigen
		gameState.getPlayerCardsProperty().addListener(new ChangeListener<Card>() {
			
			@Override
			public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
				
					/* Vorgehen: siehe oben */
				
				    StackPane replacePane = ((GameView) rootView).getPlayerCardsPane();
					String cardName = newValue.toString()+".png";
					Label cardLabel = new Label();
					Image img = new Image("cardPics/"+cardName);
					ImageView view = new ImageView(img);
					view.setFitHeight(150);
				    view.setPreserveRatio(true);
				    cardLabel.setGraphic(view);
				    cardLabel.setTranslateX(kartenVersatz);
				    kartenVersatz += 40;
				    replacePane.getChildren().add(cardLabel);				
				
			}
			
		});
		
		
		//-- ChangeListener: Spielende -----------------------------------
		//wenn Spiel terminiert, wird die Szene ausgetauscht
		//FinishedView setzen
		gameState.getTerminateProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					ViewController controller;
					controller = new FinishedViewController(gameState, networkService, newWindow);
					rootView = controller.getRootView();
					Scene scene = new Scene(rootView, 800, 800);
			    	
			    	newWindow.setTitle("BlackJack");
			    	newWindow.setScene(scene);
			    	newWindow.setResizable(false);
			    	newWindow.show();
				}
				
			}
			
		});
	
		
	}

}
