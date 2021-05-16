package gameView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GameView extends BorderPane {
	
	Label clientName;
	Label opponentName;
	Button drawCard;
	Button showCard;
	HBox box;
	HBox labelBox;
	StackPane playerCards;
	StackPane opponentCards;
	Image cardImage;
	
	public GameView() {
		
		//-- TOP ------------------------------------------------------
		//Handkarten Gegner
		opponentCards = new StackPane();
		opponentCards.setPrefHeight(150);
		this.setTop(opponentCards);
		
		//-- CENTER ---------------------------------------------------
		//Handkarten Spieler
		playerCards = new StackPane();
		this.setCenter(playerCards);
		
		//-- BOTTOM ---------------------------------------------------
		//Karte ziehen / Karten aufdecken
		box = new HBox();
		drawCard = new Button("Karte ziehen");
		showCard = new Button("Aufdecken");
		
		box.getChildren().addAll(drawCard, showCard);
		box.setAlignment(Pos.CENTER);
		box.setTranslateY(-20);
		this.setBottom(box);
		
		//-- BackgroundImage setzen -----------------------------------
		Image backgroundImage = new Image("pokerbackground.jpg");
		BackgroundSize bSize = new BackgroundSize(800, BackgroundSize.AUTO, false, false, false, false);
		Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);
		
		
	}
	
	//-- GETTER & SETTER ----------------------------------------------
	
	public Label getClientNameLabel() {
		return clientName;
	}
	
	public Label getOpponentName() {
		return opponentName;
	}
	
	public void setClientName(String name) {
		clientName.setText(name);
	}
	
	public void setOpponentName(String name) {
		opponentName.setText(name);
	}
	
	public Button getDrawCardButton() {
		return drawCard;
	}
	
	public Button getShowCardButton() {
		return showCard;
	}
	
	public StackPane getPlayerCardsPane() {
		return playerCards;
	}
	
	public void setCards(StackPane playerCards) {
		this.setCenter(playerCards);
	}
	
	public StackPane getOponnentCards() {
		return opponentCards;
	}
	
	public void setOpponentCards(StackPane opponentCards) {
		this.setTop(opponentCards);
	}
	
	

}
