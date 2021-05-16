package network;

import logik.GameState;
import message.Message;
import message.MessageGenerator;
import message.MessageHandler;
import message.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

import app.GuiMain;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Server {
    private int port;
    

    private HashMap<SocketAddress, GameState> gameStates;
    private HashMap<MessageType, MessageHandler> messageHandlers;
    private GuiMain guiMain;

    public Server(int port) {
        this.port = port;
        this.messageHandlers = new HashMap<>();
        this.gameStates = new HashMap<>();
    }

    public void registerMessageHandler(MessageType type, MessageHandler handler) {
        messageHandlers.put(type, handler);
    }

    public void start() {
    	
        System.out.printf("Starte network.Server auf Port %d%n", port);
        final Thread serverThread = new Thread(()->{
	        try {
	        	try (ServerSocket serverSocket = new ServerSocket(port)) {
					
	        		//Gui-MainThread starten (JavaFX Application Thread)
	        		Platform.startup(() ->{
						 guiMain = new GuiMain();
					});
					
					while (true) {
					    try {
					    	//Warte auf Anfrage von Client
					        final Socket socket = serverSocket.accept();
					        System.out.println("Neue Verbindung: " + socket.getInetAddress());
	
					        //neuer GameState pro Client
					        GameState gameState = new GameState();
					        gameStates.put(socket.getRemoteSocketAddress(), gameState);
	
					        //neuer NetworkService pro Client
					        NetworkService networkService = new NetworkService();
					        networkService.setup(this, socket);
					       
					        //neues Window pro Client
					        Platform.runLater(() -> {
					        	 guiMain.newStartWindow(gameState, networkService);
					        });
					       
					        final Thread thread = new Thread(() -> {
					            
					        	//auf Messages warten
			                	networkService.getMessageProperty().addListener(new ChangeListener<Message>() {
	
			            			@Override
			            			public void changed(ObservableValue<? extends Message> observable, Message oldValue, Message newValue) {
			            				Message message = newValue;
			                            if (message == null) {
			                                System.out.println("Nachricht unguelltig (Format)");
			                                networkService.write(MessageGenerator.invalidProtocol());
			                            }
	
			                            System.out.printf("Nachricht empfangen: %s%n", message);
	
			                            //MessageHandler auswaehlen
			                            MessageType type = message.getType();
			                            MessageHandler targetHandler = messageHandlers.get(type);
			                            if (targetHandler == null) {
			                                System.out.println("Nachricht ungueltig (Protokollversion)");
			                                networkService.write(MessageGenerator.invalidProtocol());
			                            }
	
			                            System.out.printf("Handler ausgewaehlt: %s%n", targetHandler.getClass().getName());
			                            
			                            //MessageHandler anstossen
			                            targetHandler.handleMessage(message, gameState, networkService);
			            			}
			                	
			                	});
	
	
					        });
					        thread.start();
	
					    } catch (IOException e) {
					        e.printStackTrace();
					    } catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        });
        serverThread.start();
        
    }
    
    //Verbindung zu Client trennen
    public void closeGame(Socket socket) {
    	gameStates.remove(socket.getRemoteSocketAddress());
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
