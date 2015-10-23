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
	Coordinate topRowCoord = new Coordinate(0, 0);
	Coordinate bottomRowCoord = new Coordinate(1, 7);
	Coordinate someCoord = new Coordinate(6, 2);
	

	@Test
	public void stateTest(){
		redCheck();
		blackCheck();
		emptyCheck();
	}
	
	public void redCheck(){
		Cell redCell = new Cell(board, CellState.RED, topRowCoord);
		assertTrue(redCell.isRedChip());
		assertFalse(redCell.isRedKing());
		assertFalse(redCell.isEmpty());
	}
	
	public void blackCheck(){
		Cell blackCell = new Cell(board, CellState.BLACK, bottomRowCoord);
		assertTrue(blackCell.isBlackChip());
		assertFalse(blackCell.isBlackKing());
	}
	
	public void emptyCheck(){
		Cell emptyCell = new Cell(board, CellState.EMPTY, someCoord);
		assertTrue(emptyCell.isEmpty());
		
	}
	
	@Test
	public void getCoordTest(){
		Cell blackCell = new Cell(board, CellState.BLACK, someCoord);
		assertEquals(6, blackCell.getColumn());
		assertEquals(2, blackCell.getRow());
		assertEquals(someCoord, blackCell.getCoords());
		assertTrue(blackCell.hasCoords(someCoord.column(), someCoord.row()));
	}
	
	@Test
	public void kingTest() {
		Cell blackKing = new Cell(board, CellState.BLACK, topRowCoord);
		Cell redKing = new Cell(board, CellState.RED, bottomRowCoord);
		assertTrue(blackKing.isBlackKing());
		assertFalse(blackKing.isBlackChip());
		assertTrue(redKing.isRedKing());
		assertFalse(redKing.isRedChip());
	}
	
	@Test
	public void compareCoordsTest(){
		downRightTest();
		upRightTest();
		downLeftTest();
		upLeftTest();
		
	}
	
	private void downRightTest(){
		Cell cell = new Cell(board, CellState.RED, topRowCoord);
		Coordinate coord = new Coordinate(1,1);
		assertEquals("DR", cell.compareCoords(coord));
	}
	
	private void upRightTest(){
		Cell cell = new Cell(board, CellState.RED, bottomRowCoord);
		Coordinate coord = new Coordinate(2, 6);
		assertEquals("UR", cell.compareCoords(coord));
	}
	
	private void downLeftTest(){
		Cell cell = new Cell(board, CellState.RED_KING, someCoord);
		Coordinate coord = new Coordinate(5, 3);
		assertEquals("DL", cell.compareCoords(coord));
	}
	
	private void upLeftTest(){
		Cell cell = new Cell(board, CellState.RED, bottomRowCoord);
		Coordinate coord = new Coordinate(0, 6);
		assertEquals("UL", cell.compareCoords(coord));
	}
	
	@Test
	public void simpleDistanceTest(){
		Cell cell = new Cell(board, CellState.BLACK, topRowCoord);
		Cell other = new Cell(board, CellState.EMPTY, new Coordinate(1, 1));
		assertTrue(cell.isColumnsAway(other, 1) && other.isRowsOneWay(cell, 1));
		assertTrue(cell.isRowsAway(other, 1));
		assertFalse(cell.isRowsAway(other, 2));
	}
	
	@Test
	public void biggerDistanceTest(){
		Cell cell = new Cell(board, CellState.BLACK, someCoord);
		Cell other = new Cell(board, CellState.RED, new Coordinate(2, 5));
		assertFalse(cell.isColumnsAway(other,1));
		assertTrue(cell.isColumnsAway(other, 4));
		assertTrue(cell.isRowsOneWay(other, -3));
		assertTrue(cell.isRowsAway(other, 3));
		assertFalse(cell.isRowsOneWay(other, 1));
	}
	

}
