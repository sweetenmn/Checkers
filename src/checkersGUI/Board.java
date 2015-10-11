package checkersGUI;

import java.util.ArrayList;

import game.Cell;
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
		addChips(Cell.EMPTY, 0, 8);
		addChips(Cell.RED, 0, 3);
		addChips(Cell.BLACK, 5, 8);
	}
	

	public void addChips(int state, int rowStart, int rowEnd){
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
		lastPieceClicked.eraseFrom(checkerboard);
		lastSquareClicked.eraseFrom(checkerboard);
		cells.remove(lastPieceClicked);
		cells.remove(lastSquareClicked);
		Pane destPane = new Pane();
		Pane originPane = new Pane();
		Cell newPiece = new Cell(lastPieceClicked.getState(), destPane, lastSquareClicked.getColumn(), lastSquareClicked.getRow());
		Cell newSquare = new Cell(Cell.EMPTY, originPane, lastPieceClicked.getColumn(), lastPieceClicked.getRow());
		newPiece.addToBoard(this);
		newSquare.addToBoard(this);
		addPane(newPiece, destPane, newPiece.getColumn(), newPiece.getRow());
		addPane(newSquare, originPane, newSquare.getColumn(), newSquare.getRow());
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
			System.out.println(msg);
			setMovement();
			movePiece();
		} 		
	}
	
	private void setMovement(){
		for (Cell c: cells){
			if (c.getColumn() == oldX && c.getRow() == oldY){
				setLastPieceClicked(c);
			} 
			if (c.getColumn() == newX && c.getRow() == newY){
				setLastSquareClicked(c);
			}
		}
	}
}
