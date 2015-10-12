package game;

import helpers.CellState;
import helpers.Coordinate;
import checkersGUI.Board;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Cell {
	
	private CellState state;
	private Rules rules;
	private ImageView checker;
	private Board board;
	private Coordinate coord;
	private static final int TOP_ROW = 0;
	private static final int BOTTOM_ROW = 7;
	
	public Cell(Board board, CellState state, Coordinate coord){
		this.state = state;
		this.coord = coord;
		this.board = board;
		createChip();
	}
	
	public void addTo(GridPane gridParent){
		gridParent.add(checker, getColumn(), getRow());
	}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(checker);
	}
	
	private void createChip(){
		crownIfKing();
		createRules();
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
	
	private void createImageView(){
		checker = new ImageView(state.getImage());
	}
	
	public void createRules(){
		rules = new Rules(board);
	}
	
	public boolean isLegal(Cell other){
		return rules.isLegal(this, other);
	}
	
	public int getColumn(){return coord.column();}
	public int getRow(){return coord.row();}
	
	public CellState getState(){return state;}
	
	public Coordinate getCoords(){return coord;}
	
	public boolean hasSameCoords(int x, int y){
		return coord.column() == x && coord.row() == y;
	}
	
}
