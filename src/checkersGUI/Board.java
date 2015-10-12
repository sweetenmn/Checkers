package checkersGUI;

import java.util.ArrayList;

import game.Cell;
import helpers.CellState;
import helpers.Coordinate;
import helpers.ImageHashMap;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	private GridPane checkerboard;
	private ArrayList<Cell> cells;
	private Cell lastPieceClicked;
	private Cell lastSquareClicked;
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;
	public ImageHashMap images = new ImageHashMap();
	
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
				Cell cell = new Cell(this, state, pane, new Coordinate(column, row));
				if (row % 2 != 0){
					if (column % 2 != 0){
						addPane(cell, pane);
					}
				} else {
					if (column % 2 == 0){
						addPane(cell, pane);
					}
				}
			}
		}
	}
	
	private void addPane(Cell cell, Pane pane){
		checkerboard.add(pane, cell.getColumn(), cell.getRow());
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
		Cell newPiece = new Cell(this, lastPieceClicked.getState(), destPane, lastSquareClicked.getCoords());
		Cell newSquare = new Cell(this, CellState.EMPTY, originPane, lastPieceClicked.getCoords());
		addPane(newPiece, destPane);
		addPane(newSquare, originPane);
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
			if (c.hasSameCoords(xOrg, yOrg)){
				setLastPieceClicked(c);
			} 
			if (c.hasSameCoords(xDest, yDest)){
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
