package network;

import message.Message;

import java.io.*;
import java.net.Socket;

import javafx.beans.property.SimpleObjectProperty;

public class NetworkService {
	private BufferedReader reader;
	private BufferedWriter writer;
	private boolean clientReady;
	private SimpleObjectProperty<Message> messageProperty;
	private Socket socket;
	private Server server;

    public void setup(Server server, Socket socket) throws IOException {
    	this.server = server;
    	this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        messageProperty = new SimpleObjectProperty<Message>();
        
    }

    //Message lesen
    public Message read(String raw) throws IOException, ConnectionLostException {
        if (raw == null) {
            System.out.println("null von readLine()");
            throw new ConnectionLostException();
        }

        System.out.printf("Empfangen: %s%n", raw);
        Message parsedMessage = Message.parse(raw);
        System.out.println(messageProperty.getValue());
        messageProperty.setValue(parsedMessage);
        System.out.println(messageProperty.getValue());
        return parsedMessage;
    }
    
    //Fuer Konsolen Version (wird bei GUI nicht benoetigt)
    public Message read() throws IOException, ConnectionLostException {
        String raw = reader.readLine();
        if (raw == null) {
            System.out.println("null von readLine()");
            throw new ConnectionLostException();
        }

        System.out.printf("Empfangen: %s%n", raw);
        return Message.parse(raw);
    }

    //Nachricht schreiben
    public void write(Message message) {
        try {
            System.out.printf("Nachricht an network.Client senden: %s%n", message);
            String raw = message.serialize();
            writer.write(raw);
            writer.flush();
            System.out.printf("Gesendet: %s", raw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Verbindung zum Server trennen
    public void closeGame() {
    	server.closeGame(socket);
    }

    //-- GETTER & SETTER ----------------------------------------------
    
    public void setClientReady(boolean clientReady) {
        this.clientReady = clientReady;
    }

    public boolean getClientReady() {
        return clientReady;
    }
    
    public SimpleObjectProperty<Message> getMessageProperty(){
    
    	return messageProperty;
    }
    
    public Socket getSocket() {
    	return socket;
    }
}
