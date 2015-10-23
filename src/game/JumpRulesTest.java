package game;

import static org.junit.Assert.*;
import javafx.scene.layout.GridPane;
import helpers.Coordinate;
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
		Coordinate coord = new Coordinate(11,5);
		Coordinate coord2 = new Coordinate(5, 11);
		for(int i = 0; i < board.cells.size(); i ++){
			assertTrue(jump.getPossibleEnemies(board.cells.get(i)) == null);
		}
		assertFalse(jump.validDestination(coord));
		assertFalse(jump.validDestination(coord2));
		for(int i = 0; i < board.cells.size(); i ++){
			Coordinate coord3 = new Coordinate((board.cells.get(i).getColumn() - 1), 
					(board.cells.get(i).getRow() - 1));
			if((board.cells.get(i).getColumn()!=0)&&(board.cells.get(i).getRow()!=0)){
				assertTrue(jump.hasPossibleDestination(board.cells.get(i), 
						board.getCellAt(coord3)));
				
				
			}else{
				assertFalse(jump.hasPossibleDestination(board.cells.get(i), 
						board.getCellAt(coord3)));
			}
			assertFalse(jump.isEnemy(board.cells.get(i), board.getCellAt(coord3)));
			assertFalse(jump.validEnemy(board.cells.get(i), coord3));
		}	
	}
}
