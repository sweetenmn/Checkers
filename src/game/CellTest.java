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
	TurnCounter counter = new TurnCounter();

	@Test
	public void stateTest(){
		redCheck();
		blackCheck();
		emptyCheck();
	}
	
	public void redCheck(){
		Coordinate redCoord = new Coordinate(0, 1);
		Cell redCell = new Cell(board, CellState.RED, redCoord);
		assertTrue(redCell.isRedChip());
		assertFalse(redCell.isRedKing());
		assertFalse(redCell.isEmpty());
	}
	
	public void blackCheck(){
		Coordinate blackCoord = new Coordinate(1, 7);
		Cell blackCell = new Cell(board, CellState.BLACK, blackCoord);
		assertTrue(blackCell.isBlackChip());
		assertFalse(blackCell.isBlackKing());
	}
	
	public void emptyCheck(){
		Coordinate emptyCoord = new Coordinate(0, 5);
		Cell emptyCell = new Cell(board, CellState.EMPTY, emptyCoord);
		assertTrue(emptyCell.isEmpty());
		
	}
	
	@Test
	public void getCoordTest(){
		Coordinate testCoord = new Coordinate(6, 2);
		Cell blackCell = new Cell(board, CellState.BLACK, testCoord);
		assertEquals(6, blackCell.getColumn());
		assertEquals(2, blackCell.getRow());
		assertEquals(testCoord, blackCell.getCoords());
		assertTrue(blackCell.hasCoords(testCoord.column(), testCoord.row()));
	}
	
	@Test
	public void kingTest() {
		Coordinate topRow = new Coordinate(2, 0);
		Coordinate bottomRow = new Coordinate(1, 7);
		Cell blackKing = new Cell(board, CellState.BLACK, topRow);
		Cell redKing = new Cell(board, CellState.RED, bottomRow);
		assertTrue(blackKing.isBlackKing());
		assertFalse(blackKing.isBlackChip());
		assertTrue(redKing.isRedKing());
		assertFalse(redKing.isRedChip());
	}

}
