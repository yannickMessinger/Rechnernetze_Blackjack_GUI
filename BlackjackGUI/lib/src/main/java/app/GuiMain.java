package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logik.GameState;
import network.NetworkService;
import startView.StartViewController;

public class GuiMain extends Application {
	
	private Pane root;
	private Scene scene;
	private Stage primaryStage;
	
	@Override
	public void init() throws IOException {

	}

    @Override
    public void start(Stage primaryStage) throws Exception{
      this.primaryStage = primaryStage;
      root = new Pane();
      scene = new Scene(root, 800, 800);
      this.primaryStage.setTitle("BlackJack");
      this.primaryStage.setScene(scene);
      
      this.primaryStage.show();

    }
    
    //pro Client wird ein StartWindow geöffnet (mit eigenem GameState und NetworkService)
    public void newStartWindow(GameState gameState, NetworkService networkService) {
    	
    	ViewController controller;
    	Stage newWindow = new Stage();
		controller = new StartViewController(gameState, networkService, newWindow);
    	root = controller.getRootView();
        scene = new Scene(root, 800, 800);
    	
    	newWindow.setTitle("BlackJack");
    	newWindow.setScene(scene);
    	newWindow.show();
    	
    }
 
}
