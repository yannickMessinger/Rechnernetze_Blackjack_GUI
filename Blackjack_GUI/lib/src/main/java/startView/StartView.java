package startView;

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

public class StartView extends BorderPane {
private	Button startButton;
private	HBox box;
	
	public StartView() {
		
		//-- TOP ------------------------------------------------------
		//Ueberschrift
		HBox topBox = new HBox();
		Label header = new Label("BLACK JACK");
		header.setStyle("-fx-font-size: 40; -fx-text-fill: black; -fx-font-weight: bold;");
		topBox.getChildren().add(header);
		topBox.setAlignment(Pos.CENTER);
		topBox.setTranslateX(10);
		this.setTop(topBox);
		
		//-- BOTTOM ---------------------------------------------------
		//Spiel starten
		box = new HBox();
		startButton = new Button("Start");
		box.getChildren().addAll(startButton);
		box.setAlignment(Pos.CENTER);
		box.setTranslateY(-20);
		this.setBottom(box);
		
		//-- BackgroundImage setzen -----------------------------------
		Image backgroundImage = new Image("3850.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);

	}
	
	//-- GETTER & SETTER ----------------------------------------------
	
	public Button getStartButton() {
		return startButton;
	}
	
}
