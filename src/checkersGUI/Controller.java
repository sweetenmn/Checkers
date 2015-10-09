package checkersGUI;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import server_connection.Server;

public class Controller {
	@FXML
	GridPane checkerboard;
	@FXML
	Button newGame;
	@FXML
	Button connect = new Button("Connect");
	@FXML
	TextField p1Name = new TextField("Enter your name");
	@FXML
	TextField p2Name = new TextField("Enter your enemy's name");
	@FXML
	Button submitMove;
	Board board;
	
	@FXML
	Label p1;
	@FXML
	Label p2;
	@FXML
	Label playerTurn;
	
	@FXML
	TextField host = new TextField("Enter Host");
	@FXML
	TextField port = new TextField("Enter Port Number");
	
	ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(20);
	
	@FXML
	public void initialize(){
		board = new Board(checkerboard);
		board.setUp();
		submitMove.setOnAction(event -> sendmove());
		new Thread(() -> {
	            try {
	                Server s = new Server(8888);
	                s.listen();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        
		}).start();
	    
		new Thread(() -> {
			for (;;) {
				try {
					String msg = messages.take();
					System.out.println("taken: " + msg);
					Platform.runLater(() -> {board.setMove(msg);});
				} catch (Exception e) {
					badNews(e.getMessage());
				}
				
			}
		}).start();
	}
	
	void badNews(String what) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}
	
	@FXML
	public void popUp(){
		/*Pops up a series of textfields and "connect" button.
		 * The user will input the ip address and then press the connect button.
		 * The connect button's method should then hide the textfield and button from view and push the screen.
		 * I think the getIP should be linked to the "connect" button.
		*/
		VBox popupVBox = new VBox();
		popupVBox.getChildren().add(p1Name);
		popupVBox.getChildren().add(p2Name);
		popupVBox.getChildren().add(host);
		popupVBox.getChildren().add(port);
		popupVBox.getChildren().add(connect);
		Popup popup = new Popup();
        popup.getContent().addAll(popupVBox);
        popup.show(checkerboard, 800, 150);
        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                 p1.setText(p1Name.getText());
                 p2.setText(p2Name.getText());
                 playerTurn.setText(p1.getText() + "'s Turn");
                 popup.hide();    
            }
        });
	}
	
	
	void sendmove() {
		try {
			sendTo(host.getText(), 8888, board.getMove());
			turnUpdate();
		} catch (NumberFormatException nfe) {
			badNews(String.format("\"%s\" is not an integer", this.port.getText()));
		}
	}
	
	void sendTo(String host, int port, String message) {
		new Thread(() -> {
			try {
				Socket target = new Socket(host, port);
				System.out.println(message);
				send(target, message);
				receive(target);
				target.close();
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}
	void send(Socket target, String message) throws IOException {
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();
	}
	
	void receive(Socket target) throws IOException {
		BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
		while (!sockin.ready()) {}
		while (sockin.ready()) {
			try {
				String msg = sockin.readLine();
				System.out.println("message r " + msg);
				messages.put(msg);
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}		
	}
	
	@FXML
	public void turnUpdate(){
		/*This will update the Label displaying the current player's turn.
		 * Can be used/used in conjunction with another method to allow the next player the ability to move their pieces
		 * while restricting the other player.
		*/
		if(playerTurn.getText() == p1.getText() + "'s Turn")
			playerTurn.setText(p2.getText() + "'s Turn");
		else
			playerTurn.setText(p1.getText() + "'s Turn");
	}

}
