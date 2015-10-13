package checkersGUI;

import static org.junit.Assert.*;
import game.Player;
import helpers.GameState;
import helpers.PlayerID;
import javafx.scene.layout.GridPane;

import org.junit.Test;

public class BoardTest {
	private GridPane grid;
	Board board = new Board(grid, "name");
	Player thisplayer;
	//fails because of initialization in Board of Cell lastpiececlicked
	@Test
	public void PlayerTurn() {
		board.createPlayer(PlayerID.BLACK);
		assertTrue(board.getGameState() == GameState.BLACK_TURN);
		assertTrue(board.isTurn(thisplayer));
		board.swapPlayerTurn();
		assertTrue(board.getGameState() == GameState.RED_TURN);
		assertFalse(board.isTurn(thisplayer));
		board.swapPlayerTurn();
		assertTrue(board.getGameState() == GameState.BLACK_TURN);
		assertTrue(board.isTurn(thisplayer));
	}

}
