package finishedView;

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
import javafx.scene.layout.VBox;

public class FinishedView extends BorderPane {

	
	Label resultLabel;
	Label reasonLabel;
	Button disconnectButton;
	VBox center;
	StackPane playerCards;
	StackPane opponentCards;
	
	
	public FinishedView() {
		
		//-- TOP ------------------------------------------------------
		// Ausgabe wie das Spiel ausgegangen ist
		HBox topBox = new HBox();
		resultLabel = new Label();
		resultLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black; -fx-font-weight: bold;");
		topBox.setPrefHeight(200);
		topBox.setPrefWidth(800);
		topBox.setStyle("-fx-background-color: rgba(255,255,255,0.5);");
		topBox.getChildren().add(resultLabel);
		topBox.setAlignment(Pos.CENTER);
		
		this.setTop(topBox);
		
		//-- CENTER ---------------------------------------------------
		// Ausgabe warum das Spiel zu Ende ist
		HBox reasonBox = new HBox();
		reasonLabel = new Label();
		reasonLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
		reasonBox.setPrefHeight(50);
		reasonBox.setPrefWidth(800);
		reasonBox.getChildren().add(reasonLabel);
		reasonBox.setAlignment(Pos.CENTER);
		
		//Handkarten Spieler
		Label playerHeader = new Label("Deine Karten:");
		playerHeader.setTranslateX(50);
		playerHeader.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
		playerCards = new StackPane();
		playerCards.setPrefHeight(200);
		
		//Handkarten Gegner
		Label opponentHeader = new Label("Gegner:");
		opponentHeader.setTranslateX(50);
		opponentHeader.setStyle("-fx-font-size: 20; -fx-text-fill: white;");
		opponentCards = new StackPane();
		opponentCards.setPrefHeight(200);
		
		center = new VBox();
		center.getChildren().addAll(reasonBox, opponentHeader, opponentCards, playerHeader, playerCards);
		this.setCenter(center);
		
		//-- Bottom ---------------------------------------------------
		// Verbindung zum Server Trennen
		HBox bottomBox = new HBox();
		disconnectButton = new Button("Ende");
		bottomBox.getChildren().add(disconnectButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setTranslateY(-20);
		
		this.setBottom(bottomBox);

		//-- BackgroundImage setzen -----------------------------------
		Image backgroundImage = new Image("backgroundfinish.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false);
		Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);
		
	}
	
	//-- GETTER & SETTER ----------------------------------------------
	
	public Label getResultLabel() {
		return resultLabel;
	}
	
	public Button getDisconnectButton() {
		return disconnectButton;
	}
	
	public Label getReasonLabel() {
		return reasonLabel;
	}
	
	public StackPane getPlayerCards() {
		return playerCards;
	}
	
	public StackPane getOpponentCards() {
		return opponentCards;
	}
}
