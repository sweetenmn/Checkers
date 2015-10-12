package checkersGUI;

import server_connection.Server;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Controller {
	@FXML
	BorderPane canvas;
	@FXML
	GridPane checkerboard;
	@FXML
	Button newGame;
	@FXML
	Button connect = new Button("Connect");
	@FXML
	TextField p1Name = new TextField();
	@FXML
	TextField p2Name = new TextField();
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
	TextField host = new TextField();
	
	ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(20);
	
	@FXML
	public void initialize(){
		p1Name.setPromptText("Enter your name");
		p2Name.setPromptText("Enter opponent's name");
		host.setPromptText("Enter IP Address");
		board = new Board(checkerboard);
		board.setUp();
		requestFocus();
		startMessaging();
	}
	
	public void startMessaging(){
		new Thread(() -> {
			for (;;) {
				try {
					String msg = messages.take();
					Platform.runLater(() -> {board.handleMessage(msg);});
				} catch (Exception e) {
					badNews(e.getMessage());
				}
				
			}
		}).start();
		
	}
	
	@FXML
	public void requestFocus(){
		canvas.requestFocus();		
	}
	
	void badNews(String what) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}
	
	void handlePress(KeyCode code){
		if (code == KeyCode.ENTER){
			sendmove();
		}
	}
	
	@FXML
	public void popUp(){
		VBox popupVBox = new VBox();
		popupVBox.getChildren().add(p1Name);
		popupVBox.getChildren().add(p2Name);
		popupVBox.getChildren().add(host);
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
                 requestFocus();
        		 submitMove.setOnAction(event -> sendmove());
        		 canvas.setOnKeyPressed(k -> handlePress(k.getCode()));
        		 createServer();
                 popup.hide();
            }
        });
	}
	
	private void createServer(){
		new Thread(() -> {
            try {
                Server s = new Server(8888, board, host.getText());
                s.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}).start();
	}
	
	
	void sendmove() {
		try {
			sendTo(host.getText(), 8888, board.getMove());
			turnUpdate();
		} catch (NumberFormatException nfe) {
			badNews(this.host.getText() + " is not a valid IP Address");
		
		}
	}
	
	void sendTo(String host, int port, String message) {
		new Thread(() -> {
			try {
				Socket target = new Socket(host, port);
				send(target, message);
				receive(target);
				target.close();
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
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
