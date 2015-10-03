package checkersGUI;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	GridPane checkerboard;
	
	public Board(GridPane grid){
		checkerboard = grid;
	}
	
	public void setUp(){
		addChips(1, 0);
		addChips(2, 5);
	}
	
	public void addChips(int player, int rowIndex){
		for (int column = 0; column < 8; column++){
			for (int row = rowIndex; row < rowIndex + 3; row++){
				Pane pane = new Pane();
				Piece piece = new Piece(player, pane);
				piece.addToBoard();
				if (row % 2 != 0){
					if (column % 2 != 0){
						checkerboard.add(pane, column, row);
					}
				} else {
					if (column % 2 == 0){
						checkerboard.add(pane, column, row);
					}
				}
			}
			
		}
		
	}
	
			
	
	
	
	
}
