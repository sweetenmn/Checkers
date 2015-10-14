package checkersGUI;

import static org.junit.Assert.*;
import game.Player;
import helpers.CellState;
import helpers.Coordinate;
import helpers.GameState;
import helpers.PlayerID;
import javafx.scene.layout.GridPane;

import org.junit.Test;

public class BoardTest {
	private GridPane grid;
	Board board = new Board(grid, "name");
	Player thisplayer;
	Coordinate coord1 = new Coordinate(0,0);
	Coordinate coord2 = new Coordinate(0,1);
	Coordinate coord3 = new Coordinate(7,7);
	Coordinate coord4 = new Coordinate(7,6);
	//fails because of initialization in Board of Cell lastpiececlicked may be because
	//of the "this" in the constructor.
	@Test
	public void playerTurn() {
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

	@Test
	public void checkerBoard(){
		board.setUp();
		assertTrue(board.getCellAt(coord1).getState() == CellState.RED);
		assertTrue(board.getCellAt(coord2).getState() == CellState.EMPTY);
		assertTrue(board.getCellAt(coord3).getState() == CellState.BLACK);
		assertTrue(board.getCellAt(coord4).getState() == CellState.EMPTY);
	}
}
