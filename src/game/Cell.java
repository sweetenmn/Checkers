package game;

import helpers.CellState;
import helpers.Coordinate;
import helpers.ImageHashMap;
import checkersGUI.Board;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Cell {
	
	private CellState state;
	private ImageView checker;
	private Board board;
	private Coordinate coord;
	static final int TOP_ROW = 0;
	static final int BOTTOM_ROW = 7;
	private ImageHashMap images = new ImageHashMap();
	
	public Cell(Board board, CellState state, Coordinate coord){
		this.state = state;
		this.coord = coord;
		this.board = board;
		crownIfKing();
	}
	
	public void addTo(GridPane gridParent){
		gridParent.add(checker, getColumn(), getRow());
	}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(checker);
	}
	
	public void createClickableChip(){
		createImageView();
		assignEvent();   
	}
	private void crownIfKing(){
		switch(state){
			case BLACK:
				if (coord.row() == TOP_ROW){
					state = CellState.BLACK_KING;
				}
				break;
			case RED:
				if (coord.row() == BOTTOM_ROW){
					state = CellState.RED_KING;
				}
				break;
			case BLACK_KING: case EMPTY: case RED_KING:
				break;
		}
	}
	
	private void assignEvent(){
		switch(state){
			case EMPTY:
				checker.setOnMouseClicked(k -> {
					board.setLastSquareClicked(this);
				});
				break;
			case BLACK: case BLACK_KING: case RED: case RED_KING:
				checker.setOnMouseClicked(k -> {
					board.setLastPieceClicked(this);
					board.setLastSquareClicked(this);
				});
				break;
		}
	}
	
	
	void createImageView(){
		checker = new ImageView(images.getImageFor(state));
	}
	public int getColumn(){return coord.column();}
	public int getRow(){return coord.row();}
	
	public CellState getState(){return state;}
	
	public Coordinate getCoords(){return coord;}
	
	public boolean hasCoords(int x, int y){
		return coord.column() == x && coord.row() == y;
	}
	
	public String compareCoords(Coordinate compareCoord){
		if (compareCoord.column() == coord.column() - Rules.NORM_RANGE){
			if (compareCoord.row() == coord.row() - Rules.NORM_RANGE){
				return "UL";
			} else {
				return "DL";
			}
		} else {
			if (compareCoord.row() == coord.row() - Rules.NORM_RANGE){
				return "UR";
			} else {
				return "DR";
			}
		}
	}
	
 	public boolean isBlackChip(){return getState() == CellState.BLACK;}
 	public boolean isRedChip(){return getState() == CellState.RED;}
 	public boolean isRedKing(){return getState() == CellState.RED_KING;}
 	public boolean isBlackKing(){return getState() == CellState.BLACK_KING;}
 	public boolean isEmpty(){return getState() == CellState.EMPTY;}
 	
	public boolean isColumnsAway(Cell other, int desired){
		return Math.abs(this.getColumn() - other.getColumn()) == desired;
	}
	public boolean isRowsOneWay(Cell other, int desired){
		return this.getRow() - other.getRow() == desired;
	}
	public boolean isRowsAway(Cell other, int desired){
		return Math.abs(this.getRow() - other.getRow()) == desired;
	}
	
}
