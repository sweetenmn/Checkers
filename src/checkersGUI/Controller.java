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

}
