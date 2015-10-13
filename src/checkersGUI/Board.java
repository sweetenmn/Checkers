package checkersGUI;

import java.util.ArrayList;

import game.Cell;
import game.Player;
import helpers.CellState;
import helpers.Coordinate;
import helpers.PlayerID;
import javafx.scene.layout.GridPane;

public class Board {
	private GridPane checkerboard;
	private ArrayList<Cell> cells;
	private Cell lastPieceClicked;
	private Cell lastSquareClicked;
	private Player thisPlayer;
	
	public Board(GridPane grid){
		checkerboard = grid;
		cells = new ArrayList<Cell>();
	}
	
	public void createPlayer(PlayerID playerState){
		thisPlayer = new Player(playerState);
	}
	
	public void setUp(){
		addChips(CellState.EMPTY, 0, 8);
		addChips(CellState.RED, 0, 3);
		addChips(CellState.BLACK, 5, 8);
	}
	
	public void addChips(CellState state, int rowStart, int rowEnd){
		for (int column = 0; column < 8; column++){
			for (int row = rowStart; row < rowEnd; row++){
				Cell cell = new Cell(this, state, new Coordinate(column, row));
				if (row % 2 != 0){
					if (column % 2 != 0){
						addCell(cell);
					}
				} else {
					if (column % 2 == 0){
						addCell(cell);
					}
				}
			}
		}
	}
	
	private void addCell(Cell cell){
		cell.addTo(checkerboard);
		cells.add(cell);
	}
	
	public void removeCell(Cell cell){
		cells.remove(cell);
		cell.eraseFrom(checkerboard);
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
		Cell newPiece = new Cell(this, lastPieceClicked.getState(), lastSquareClicked.getCoords());
		Cell newSquare = new Cell(this, CellState.EMPTY, lastPieceClicked.getCoords());
		addCell(newPiece);
		addCell(newSquare);
	}
	
	public String getMove(){
		String oldX = "";
		String oldY = "";
		String newX = "";
		String newY = "";
		if (lastPieceClicked.isLegal(lastSquareClicked, thisPlayer)){
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
			int xOrg = Integer.valueOf(values[0]);
			int yOrg = Integer.valueOf(values[1]);
			int xDest = Integer.valueOf(values[2]);
			int yDest = Integer.valueOf(values[3]);
			setMovement(xOrg, yOrg, xDest, yDest);
			movePiece();
		} 
	}
	
	public void setMovement(int xOrg, int yOrg, int xDest, int yDest){
		//have a type param as well??
		for (Cell c: cells){
			if (c.hasCoords(xOrg, yOrg)){
				setLastPieceClicked(c);
			} 
			if (c.hasCoords(xDest, yDest)){
				setLastSquareClicked(c);
			}
		}
	}
	
	public Cell getCellAt(Coordinate coord) {
		Cell result = null;
		for (Cell c: cells){
			if (c.hasCoords(coord.column(), coord.row())){
				result = c;
				System.out.println("found");
			}
		}
		//put this where it's called
		if (result == null){throw (new NullPointerException());}
		
		return result;		
	}
}
