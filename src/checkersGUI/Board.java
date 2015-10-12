package checkersGUI;

import java.util.ArrayList;

import game.Cell;
import game.CellState;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	GridPane checkerboard;
	ArrayList<Cell> cells;
	Cell lastPieceClicked;
	Cell lastSquareClicked;
	int oldX;
	int oldY;
	int newX;
	int newY;
	
	public Board(GridPane grid){
		checkerboard = grid;
		cells = new ArrayList<Cell>();
	}
	
	public void setUp(){
		addChips(CellState.EMPTY, 0, 8);
		addChips(CellState.RED, 0, 3);
		addChips(CellState.BLACK, 5, 8);
	}
	
	public void addChips(CellState state, int rowStart, int rowEnd){
		for (int column = 0; column < 8; column++){
			for (int row = rowStart; row < rowEnd; row++){
				Pane pane = new Pane();
				Cell cell = new Cell(state, pane, column, row);
				cell.addToBoard(this);
				if (row % 2 != 0){
					if (column % 2 != 0){
						addPane(cell, pane, column, row);
					}
				} else {
					if (column % 2 == 0){
						addPane(cell, pane, column, row);
					}
				}
			}
		}
	}
	
	private void addPane(Cell cell, Pane pane, int column, int row){
		checkerboard.add(pane, column, row);
		cells.add(cell);
	}
	
	public void setLastPieceClicked(Cell cell){
		lastPieceClicked = cell;
	}
	
	public void setLastSquareClicked(Cell cell){
		lastSquareClicked = cell;
	}
	

	public void movePiece(){
		removeCell(lastPieceClicked);
		removeCell(lastSquareClicked);
		Pane destPane = new Pane();
		Pane originPane = new Pane();
		Cell newPiece = new Cell(lastPieceClicked.getState(), destPane, lastSquareClicked.getColumn(), lastSquareClicked.getRow());
		Cell newSquare = new Cell(CellState.EMPTY, originPane, lastPieceClicked.getColumn(), lastPieceClicked.getRow());
		newPiece.addToBoard(this);
		newSquare.addToBoard(this);
		addPane(newPiece, destPane, newPiece.getColumn(), newPiece.getRow());
		addPane(newSquare, originPane, newSquare.getColumn(), newSquare.getRow());
	}
	
	//used for deletion
	public void removeCell(Cell cell){
		cells.remove(cell);
		cell.eraseFrom(checkerboard);
	}
	
	public String getMove(){
		String oldX = "";
		String oldY = "";
		String newX = "";
		String newY = "";
		if (lastPieceClicked.isLegal(lastSquareClicked)){
			oldX = getString(lastPieceClicked.getColumn());
			oldY = getString(lastPieceClicked.getRow());
			newX = getString(lastSquareClicked.getColumn());
			newY = getString(lastSquareClicked.getRow());
		}
		return (oldX + ":" + oldY + ":" + newX + ":" + newY).trim();
	}
	
	private String getString(int x){
		return Integer.toString(x);
	}
	
	public void handleMessage(String msg){
		if (msg.length() > 3){
			String[] values = msg.split(":");
			oldX = Integer.valueOf(values[0]);
			oldY = Integer.valueOf(values[1]);
			newX = Integer.valueOf(values[2]);
			newY = Integer.valueOf(values[3]);
			setMovement(oldX, oldY, newX, newY);
			movePiece();
		} 		
	}
	
	public void setMovement(int xOrg, int yOrg, int xDest, int yDest){
		//have a type param as well
		for (Cell c: cells){
			if (c.getColumn() == xOrg && c.getRow() == yOrg){
				setLastPieceClicked(c);
			} 
			if (c.getColumn() == xDest && c.getRow() == yDest){
				setLastSquareClicked(c);
			}
		}
	}
	/*
	public Cell getCellAt(int column, int row){
		for (Cell c: cells){
			if (c.getColumn() == column && c.getRow() == row){
				return c;
			}
		}
		return new Cell(CellState.EMPTY, new Pane(), 0, 0);
		
	}*/
}
