package checkersGUI;

import server_connection.MessageHandler;
import server_connection.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
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
	TextField player = new TextField();
	@FXML
	TextField otherPlayer = new TextField();
	@FXML
	Button submitMove;
	Board board;
	@FXML
	Label playerOneLabel;
	@FXML
	Label playerTwoLabel;
	@FXML
	Label playerTurn;
	@FXML
	TextField hostText = new TextField();
	MessageHandler messageHandler;
	ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(20);
	
	@FXML
	public void initialize(){
		player.setPromptText("Enter your name");
		otherPlayer.setPromptText("Enter opponent's name");
		hostText.setPromptText("Enter IP Address");
	}
	
	public void startMessaging(){
		new Thread(() -> {
			for (;;) {
				try {
					String msg = messages.take();
					System.out.println(msg);
					Platform.runLater(() -> {messageHandler.handleMessage(msg);});
					if (messageHandler.getMovementMessage().length() > 8){
						Platform.runLater(() -> {turnUpdate();});
					}
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
	
	/*@FXML
	public void popUp(){
		VBox popupVBox = new VBox();
		popupVBox.getChildren().add(player);
		popupVBox.getChildren().add(otherPlayer);
		popupVBox.getChildren().add(hostText);
		popupVBox.getChildren().add(connect);
		Popup popup = new Popup();
        popup.getContent().addAll(popupVBox);
        popup.show(checkerboard, 800, 150);
        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                 startGame();
                 popup.hide();
            }
        });
	} */
	
	@FXML
    public void popUp(){
        Stage window = new Stage();
        connect.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent t) {
        		startGame();
        		window.hide();
        	}
        });
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Connection Window");
        window.setMinWidth(250);
        
        VBox layout = new VBox(3);
        layout.getChildren().addAll(player, otherPlayer, hostText, connect);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
	private void startGame(){
        requestFocus();
        submitMove.setOnAction(event -> sendmove());
        canvas.setOnKeyPressed(k -> handlePress(k.getCode()));

		board = new Board(checkerboard, player.getText());
		Platform.runLater(() -> {board.setUp();});
		messageHandler = new MessageHandler(board);
		requestFocus();
		startMessaging();
		createServer(hostText.getText());
		
		sendTo(hostText.getText(), 8888, 
				messageHandler.generateSetUpMessage(player.getText(), otherPlayer.getText()));
        if (player.getText().compareTo(otherPlayer.getText()) < 0){
        	playerOneLabel.setText(player.getText());
        	playerTwoLabel.setText(otherPlayer.getText());
        } else {
        	playerOneLabel.setText(otherPlayer.getText());
        	playerTwoLabel.setText(player.getText());
        }
        
    	playerTurn.setText(playerOneLabel.getText() + "'s Turn");
       
		
	}
	
	private void createServer(String host){
		new Thread(() -> {
            try {
                Server s = new Server(8888, messageHandler, host);
                s.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}).start();
	}
	
	
	void sendmove() {
		try {
			sendTo(hostText.getText(), 8888, messageHandler.getMovementMessage());
			if (messageHandler.getMovementMessage().length() > 8){
				Platform.runLater(() -> {turnUpdate();});
			}
		} catch (NumberFormatException nfe) {
			badNews(this.hostText.getText() + " is not a valid IP Address");
		
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
		if (board.swapTurn()){
			System.out.println("swapped");
			if(playerTurn.getText().equals(playerOneLabel.getText() + "'s Turn"))
				playerTurn.setText(playerTwoLabel.getText() + "'s Turn");
			else
				playerTurn.setText(playerOneLabel.getText() + "'s Turn");
		}
		/*This will update the Label displaying the current player's turn.
		 * Can be used/used in conjunction with another method to allow the next player the ability to move their pieces
		 * while restricting the other player.
		*/
	}

}
