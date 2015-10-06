package checkersGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
public class Controller {
	
	@FXML
	GridPane checkerboard;
	
	@FXML
	Label p1 = new Label();
	@FXML
	Label p2 = new Label();
	@FXML
	Label playerTurn = new Label();
	
	@FXML
	Button newGame = new Button();
	@FXML
	Button connect = new Button("Connect");
	@FXML
	Button submitMove;
	
	@FXML
	TextField host = new TextField("Enter Host");
	@FXML
	TextField port = new TextField("Enter Port Number");
	@FXML
	TextField p1Name = new TextField("Enter your name");
	@FXML
	TextField p2Name = new TextField("Enter your enemy's name");
	
	Board board;
	
	@FXML
	public void initialize(){
		board = new Board(checkerboard);
		board.setUp();
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
                 getIP();
                 p1.setText(p1Name.getText());
                 p2.setText(p2Name.getText());
                 playerTurn.setText(p1.getText() + "'s Turn");
                 popup.hide();    
            }
        });
        	
    }
	@FXML
	public void getIP(){
		//Connected to the New Game button, or activated upon initialization.
		//Will ask for the other user's IP Address or *MAYBE* ask if the player
		//wants to keep playing with their partner.
		//If they do, use previous IP Address. Else, add a new one.
		
	}
	
	@FXML
	public void submove(){
		board.move();
	}
	
	@FXML
	public void turnUpdate(){
		/*This will update the Label displaying the current player's turn.
		 * Can be used/used in conjuction with another method to allow the next player the ability to move their pieces
		 * while restricting the other player.
		*/
		if(playerTurn.getText() == p1.getText() + "'s Turn")
			playerTurn.setText(p2.getText() + "'s Turn");
		else
			playerTurn.setText(p1.getText() + "'s Turn");
	}
}
