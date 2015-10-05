package checkersGUI;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
public class Controller {
	@FXML
	GridPane checkerboard;	
	
	@FXML
	public void initialize(){
		Board board = new Board(checkerboard);
		board.setUp();
	}
	
	@FXML
	public void getIP(){
		//Connected to the New Game button, or activated upon initialization.
		//Will ask for the other user's IP Address or *MAYBE* ask if the player
		//wants to keep playing with their partner.
		//If they do, use previous IP Address. Else, add a new one.
	}

}
