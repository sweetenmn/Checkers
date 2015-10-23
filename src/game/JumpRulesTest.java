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
	Player player2 = new Player(PlayerID.RED, count);
	Coordinate originCoord = new Coordinate(4, 2);
	Coordinate upRightCoord = originCoord.upRightCoord();
	Coordinate upLeftCoord = originCoord.upLeftCoord();
	Coordinate downRightCoord = originCoord.downRightCoord();
	Coordinate downLeftCoord = originCoord.downLeftCoord();
	Cell origin = new Cell(board, CellState.BLACK, originCoord);
	Cell upLeft = new Cell(board, CellState.RED_KING, upLeftCoord);
	Cell upUpLeft = new Cell(board, CellState.RED, upLeftCoord.upLeftCoord());
	Cell upUpRight = new Cell(board, CellState.RED_KING, upRightCoord.upRightCoord());
	Cell downRight = new Cell(board, CellState.EMPTY, downRightCoord);
	Cell upRight = new Cell(board, CellState.RED, upRightCoord);
	Cell downLeft = new Cell(board, CellState.BLACK_KING, downLeftCoord);
	Cell upLeftRight = new Cell(board, CellState.RED, upLeftCoord.upRightCoord());
	Cell straightLeft = new Cell(board, CellState.EMPTY, 
			upLeftCoord.downLeftCoord());
	Cell downStrLeft = new Cell(board, CellState.EMPTY, 
			straightLeft.getCoords().downLeftCoord());
	
	
	@Test
	public void testBeginningJump() {
		assertFalse(jump.playerCanJump(player));
		for(int i = 0; i < board.cells.size() ; i ++){
			assertFalse(jump.hasJump(board.cells.get(i)));
			assertTrue(jump.getPossibleEnemies(board.cells.get(i)) == null);
		}
	}
	
	@Test
	public void testValidDestination(){
		Coordinate coord = new Coordinate(11,5);
		Coordinate coord2 = new Coordinate(5, 11);
		assertFalse(jump.validDestination(coord));
		assertFalse(jump.validDestination(coord2));
	}
		
	@Test
	public void testInBounds(){
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
	public void testBeginningIsJump(){
		for(int i = 0; i < board.cells.size(); i ++){
			Coordinate coord4 = new Coordinate((board.cells.get(i).getColumn() - 2), 
				(board.cells.get(i).getRow() - 2));
			assertTrue(jump.isJump(board.cells.get(i), board.getCellAt(coord4)));
		}
	}
	
	@Test
	public void testAfterSetUp(){
		setUpBoard();
		
		assertFalse(jump.playerCanJump(player));
		assertFalse(jump.hasJump(origin));
		assertTrue(jump.isJump(origin, upUpLeft));
		count.increment();
		assertTrue(jump.playerCanJump(player2));
		assertTrue(jump.hasJump(upLeft));
		assertTrue(jump.isJump(upLeft, downRight));
		assertTrue(jump.isJump(upRight, downLeft));
		assertFalse(jump.isJump(origin, upLeft));
		assertFalse(jump.hasJump(upLeftRight));
		assertFalse(jump.validEnemy(downLeft, originCoord));
		//checks corrd out of bounds.
		assertFalse(jump.validEnemy(downLeft, 
				upRightCoord.upRightCoord().upRightCoord()));
		assertTrue(jump.hasPossibleDestination(upLeft, straightLeft));
	}
	
	@Test
	public void testMiddleChip(){
		setUpBoard();
		assertEquals(jump.getMiddleChip(origin, upUpLeft), upLeft);
		assertEquals(jump.getMiddleChip(upLeft, downRight), origin);
		assertEquals(jump.getMiddleChip(origin, upUpRight), upRight);
		assertEquals(jump.getMiddleChip(upRight, downLeft), origin);
	}
	
	public void setUpBoard(){
		board.cells = new ArrayList<Cell>();
		board.cells.clear();
		board.cells.add(origin);
		board.cells.add(upLeft);
		board.cells.add(upUpLeft);
		board.cells.add(upUpRight);
		board.cells.add(downRight);
		board.cells.add(upRight);
		board.cells.add(downLeft);
		board.cells.add(straightLeft);
		board.cells.add(downStrLeft);
		board.cells.add(upLeftRight);
	}
}
