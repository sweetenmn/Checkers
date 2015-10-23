package checkersGUI;

import static org.junit.Assert.*;
import game.Cell;
import helpers.CellState;
import helpers.Coordinate;
import helpers.PlayerID;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;

import org.junit.Test;

public class BoardTest {
	private GridPane grid = new GridPane();
	Board board = new Board(grid, "name");
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
	Cell straightLeft = new Cell(board, CellState.EMPTY, upLeftCoord.downLeftCoord());
	Cell downStrLeft = new Cell(board, CellState.EMPTY, straightLeft.getCoords().downLeftCoord());
	
	@Test
	public void getMoveTest(){
		setUpBoard();
		board.createPlayer(PlayerID.BLACK);
		board.setLastPieceClicked(origin);
		board.setLastSquareClicked(upRight);
		assertEquals("4:2:5:1", board.getMove());
		board.setLastSquareClicked(downRight);
		assertEquals(":::", board.getMove());
	}
	
	@Test
	public void setMoveTest(){
		board.getCounter().increment();
		board.createPlayer(PlayerID.RED);
		upUpLeft = new Cell(board, CellState.BLACK, upLeftCoord.upLeftCoord());
		setUpBoard();
		board.setMovement(upLeft.getColumn(), upLeft.getRow(),
				downRight.getColumn(), downRight.getRow());
		assertEquals("3:1:5:3", board.getMove());
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