package game;

import static org.junit.Assert.*;
import javafx.scene.layout.GridPane;
import helpers.PlayerID;
import helpers.TurnCounter;

import org.junit.Test;

import checkersGUI.Board;

public class JumpRulesTest {
	GridPane grid = new GridPane();
	Board board = new Board(grid, "name");
	Rules rules = new Rules(board);
	JumpRules jump = new JumpRules(board, rules);
	TurnCounter count = new TurnCounter();
	Player player = new Player(PlayerID.BLACK, count);
	
	
	
	@Test
	public void test() {
		assertFalse(jump.playerCanJump(player));
		for(int i = 0; i < board.cells.size() ; i ++){
			assertFalse(jump.hasJump(board.cells.get(i)));
		}
	}
	
	@Test
	public void test2(){
		for(int i = 0; i < board.cells.size(); i ++){
			assertTrue(jump.getPossibleEnemies(board.cells.get(i)) == null);
		}
	}
}
