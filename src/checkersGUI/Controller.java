package checkersGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
public class Controller {
	@FXML
	GridPane checkerboard;
	@FXML
	Button newGame;
	@FXML
	Button connect = new Button("Connect");
	@FXML
	TextField ipAddress = new TextField("Enter enemy IP Address");
	@FXML
	Button submitMove;
	Board board;
	
	@FXML
	public void initialize(){
		board = new Board(checkerboard);
		board.setUp();
	}
	@FXML
	public void popUp(){
		/*Pops up a textfield and "connect" button.
		 * The user will input the ip address and then press the connect button.
		 * The connect button's method should then hide the textfield and button from view and push the screen.
		 * I think the getIP should be linked to the "connect" button.
		*/
		VBox popupVBox = new VBox();
		popupVBox.getChildren().add(ipAddress);
		popupVBox.getChildren().add(connect);
		Popup popup = new Popup();
        popup.getContent().addAll(popupVBox);
        popup.show(checkerboard, 800, 150);
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

}
