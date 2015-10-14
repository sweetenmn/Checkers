package game;

import static org.junit.Assert.*;
import javafx.scene.layout.GridPane;
import helpers.CellState;
import helpers.Coordinate;
import helpers.TurnCounter;

import org.junit.Test;

import checkersGUI.Board;

public class CellTest {
	private GridPane grid;
	Board board = new Board(grid, "name");
	Coordinate coord = new Coordinate(1,1);
	Coordinate coord2 = new Coordinate(1,0);
	TurnCounter counter = new TurnCounter();
	
	Cell cell = new Cell(board, CellState.BLACK, coord);
	Cell cell2 = new Cell(board, CellState.BLACK, coord2);
	
	@Test
	public void test() {
		cell.createChip();
		assertTrue(cell.getState() != CellState.BLACK_KING);
		cell2.createChip();
		assertTrue(cell2.getState() == CellState.BLACK_KING);
	}

}
