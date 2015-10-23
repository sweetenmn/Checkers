package game;

import static org.junit.Assert.*;
import helpers.CellState;
import helpers.Coordinate;
import javafx.scene.layout.GridPane;

import org.junit.Test;

import checkersGUI.Board;

public class JumpRulesTest {
	GridPane grid = new GridPane();
	Board board = new Board(grid, "name");
	Rules rules = new Rules(board);
	JumpRules jump = new JumpRules(board, rules);
	Coordinate coord = new Coordinate(2, 6);
	Cell origin = new Cell(board, CellState.BLACK, coord);
	
	
	@Test
	public void test() {
		assertFalse(jump.hasJump(origin));
	}

}
