package game;

import static org.junit.Assert.*;
import helpers.TurnCounter;
import javafx.scene.layout.GridPane;

import org.junit.Test;

import checkersGUI.Board;

public class RulesTest {
	GridPane grid= new GridPane();
	Board board = new Board(grid, "name");
	TurnCounter counter = new TurnCounter();
	Rules rules = new Rules(board);
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
