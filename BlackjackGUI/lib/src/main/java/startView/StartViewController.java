package startView;

import java.io.IOException;

import app.ViewController;
import gameView.GameViewController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logik.GameState;
import network.ConnectionLostException;
import network.NetworkService;


public class StartViewController extends ViewController {
	
	private NetworkService networkService;
	private GameState gameState;
	private Button startButton;
	private Stage newWindow;

	public StartViewController(GameState gameState, NetworkService networkService, Stage newWindow) {
		
		this.networkService = networkService;
		this.gameState = gameState;
		this.newWindow = newWindow;

		rootView = new StartView();
		startButton = ((StartView) rootView).getStartButton();
		initialize();
	}

	@Override
	public void initialize() {
		
		try {
			networkService.read("CLIENT_READY;");
		} catch (IOException | ConnectionLostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//-- Spielstart -----------------
		//wenn Spiel startet, wird die Szene ausgetauscht
		//GameView setzen
		startButton.addEventHandler(ActionEvent.ACTION, event -> {
			
			try {
				networkService.read("READY_TO_PLAY;");
				ViewController controller;
				controller = new GameViewController(gameState, networkService, newWindow);
				rootView = controller.getRootView();
				Scene scene = new Scene(rootView, 800, 400);
		    	
		    	newWindow.setTitle("BlackJack");
		    	newWindow.setScene(scene);
		    	newWindow.setResizable(false);
		    	newWindow.show();
				
				} catch (IOException | ConnectionLostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		});
		
		
		
	}

}
