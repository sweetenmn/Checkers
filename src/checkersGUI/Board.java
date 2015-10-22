package checkersGUI;

import java.util.ArrayList;

import game.Cell;
import game.Player;
import game.Rules;
import helpers.TurnCounter;
import helpers.CellState;
import helpers.Coordinate;
import helpers.PlayerID;
import javafx.scene.layout.GridPane;

public class Board {
	private TurnCounter counter = new TurnCounter();
	private GridPane checkerboard;
	public ArrayList<Cell> cells;
	private Cell lastPieceClicked = null;
	private Cell lastSquareClicked = null;
	private Player thisPlayer;
	private String playerName;
	private Rules rules;
	
	public Board(GridPane grid, String player){
		playerName = player;
		checkerboard = grid;
		cells = new ArrayList<Cell>();
		rules = new Rules(this);
	}
	
	public TurnCounter getCounter(){return counter;}
	
	public String getName(){return playerName;}
	
	public void createPlayer(PlayerID playerState){thisPlayer = new Player(playerState, counter);}
	
	public void setUp(){
		addChips(CellState.EMPTY, 0, 8);
		addChips(CellState.RED, 0, 3);
		addChips(CellState.BLACK, 5, 8);
	}
	
	public int redChipCount(){
		int red = 0;
		for(Cell c:cells){
			if(c.isRedChip() || c.isRedKing()){red++;}
			
		}
		return red;
	}
	
	public int blackChipCount(){
		int black = 0;
		for(Cell c:cells){
			if(c.isBlackChip()|| c.isBlackKing()){black++;}
		}
		return black;
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
		cell.createClickableChip();
		cell.addTo(checkerboard);
		cells.add(cell);
	}
	
	public void removeCell(Cell cell){
		cells.remove(cell);
		cell.eraseFrom(checkerboard);
	}
	
	public void setLastPieceClicked(Cell cell){lastPieceClicked = cell;}
	public void setLastSquareClicked(Cell cell){lastSquareClicked = cell;}
	
	public void movePiece(){
		removeCell(lastPieceClicked);
		removeCell(lastSquareClicked);
		Cell newPiece = new Cell(this, lastPieceClicked.getState(), lastSquareClicked.getCoords());
		Cell newSquare = new Cell(this, CellState.EMPTY, lastPieceClicked.getCoords());
		addCell(newPiece);
		addCell(newSquare);
		if (rules.isJump(lastPieceClicked, lastSquareClicked)){
			Cell captured = rules.getMiddleChip(lastPieceClicked, lastSquareClicked);
			removeCell(captured);
			addCell(new Cell(this, CellState.EMPTY, captured.getCoords()));
			if (!rules.hasJump(newPiece)){
				counter.increment();
			}
		} else {
			counter.increment();
		}
	}
		
	public String getMove(){
		String oldX = "";
		String oldY = "";
		String newX = "";
		String newY = "";
		if (rules.isLegal(lastPieceClicked, lastSquareClicked, thisPlayer)){
			oldX = getString(lastPieceClicked.getColumn());
			oldY = getString(lastPieceClicked.getRow());
			newX = getString(lastSquareClicked.getColumn());
			newY = getString(lastSquareClicked.getRow());
		}
		return (oldX + ":" + oldY + ":" + newX + ":" + newY).trim();
	}
	
	private String getString(int x){return Integer.toString(x);}
	
	public void setMovement(int xOrg, int yOrg, int xDest, int yDest){
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
			}
		}
		if (result == null){throw (new NullPointerException());}
		return result;		
	}
}
