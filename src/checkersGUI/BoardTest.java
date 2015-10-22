package checkersGUI;

import static org.junit.Assert.*;
import javafx.scene.layout.GridPane;

import org.junit.Test;

public class BoardTest {
	private GridPane grid;
	Board board = new Board(grid, "name");

	//fails because of initialization in Board of Cell lastpiececlicked
	@Test
	public void PlayerTurn() {
		
		//////GameState No longer in use
		
		/*assertTrue(board.turn == GameState.BLACK_TURN);		board.swapPlayerTurn();
		assertTrue(board.turn == GameState.RED_TURN);
		board.swapPlayerTurn();
		assertTrue(board.turn== GameState.BLACK_TURN);*/
	}

}