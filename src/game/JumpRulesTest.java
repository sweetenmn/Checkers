package game;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import helpers.CellState;
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
	Coordinate originCoord = new Coordinate(4, 2);
	Coordinate upRightCoord = originCoord.upRightCoord();
	Coordinate upLeftCoord = originCoord.upLeftCoord();
	Coordinate downRightCoord = originCoord.downRightCoord();
	Coordinate downLeftCoord = originCoord.downLeftCoord();
	Cell origin = new Cell(board, CellState.BLACK, originCoord);
	Cell upLeft = new Cell(board, CellState.RED, upLeftCoord);
	Cell upUpLeft = new Cell(board, CellState.RED, upLeftCoord.upLeftCoord());
	Cell downRight = new Cell(board, CellState.EMPTY, downRightCoord);
	Cell upRight = new Cell(board, CellState.EMPTY, upRightCoord);
	Cell straightLeft = new Cell(board, CellState.EMPTY, 
			upLeftCoord.downLeftCoord());
	Cell downStrLeft = new Cell(board, CellState.EMPTY, 
			straightLeft.getCoords().downLeftCoord());
	
	
	@Test
	public void testBeginningJump() {
		setUpBoard();
		assertFalse(jump.playerCanJump(player));
		for(int i = 0; i < board.cells.size() ; i ++){
			assertFalse(jump.hasJump(board.cells.get(i)));
			assertTrue(jump.getPossibleEnemies(board.cells.get(i)) == null);
		}
	}
	
	@Test
	public void testValidDestination(){
		setUpBoard();
		Coordinate coord = new Coordinate(11,5);
		Coordinate coord2 = new Coordinate(5, 11);
		assertFalse(jump.validDestination(coord));
		assertFalse(jump.validDestination(coord2));
	}
		
	@Test
	public void testInBounds(){
		setUpBoard();
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
	
	@Test
	public void test4(){
		setUpBoard();
		for(int i = 0; i < board.cells.size(); i ++){
			Coordinate coord4 = new Coordinate((board.cells.get(i).getColumn() - 2), 
				(board.cells.get(i).getRow() - 2));
			assertTrue(jump.isJump(board.cells.get(i), board.getCellAt(coord4)));
		}
	}
	
	public void setUpBoard(){
		board.cells = new ArrayList<Cell>();
		board.cells.clear();
		board.cells.add(origin);
		board.cells.add(upLeft);
		board.cells.add(upUpLeft);
		board.cells.add(downRight);
		board.cells.add(upRight);
		board.cells.add(straightLeft);
		board.cells.add(downStrLeft);
	}
}
