package checkersGUI;

import static org.junit.Assert.*;
import helpers.GameState;
import javafx.scene.layout.GridPane;

import org.junit.Test;

public class BoardTest {
	private GridPane grid;
	Board board = new Board(grid, "name");

	//fails because of initialization in Board of Cell lastpiececlicked
	@Test
	public void PlayerTurn() {
		assertTrue(board.getGameState() == GameState.BLACK_TURN);
		board.swapPlayerTurn();
		assertTrue(board.getGameState() == GameState.RED_TURN);
		board.swapPlayerTurn();
		assertTrue(board.getGameState() == GameState.BLACK_TURN);
	}

}
