package game;

import helpers.CellState;
import helpers.Coordinate;
import helpers.ImageHashMap;
import checkersGUI.Board;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell {
	
	private CellState state;
	private int column;
	private int row;
	private Rules rules;
	private Pane pane;
	private ImageView checker;
	private Board board;
	private ImageHashMap images;
	private Coordinate coord;
	private static final int TOP_ROW = 0;
	private static final int BOTTOM_ROW = 7;
	
	public Cell(Board board, CellState state, Pane pane, Coordinate coord){
		this.state = state;
		this.pane = pane;
		this.coord = coord;
		this.board = board;
		images = board.images;
		column = coord.column();
		row = coord.row();
		addToBoard();
	}
	
	private void addToBoard(){
		createChip();
		pane.getChildren().add(checker);
	}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(pane);
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
				if (row == TOP_ROW){
					state = CellState.BLACK_KING;
				}
				break;
			case RED:
				if (row == BOTTOM_ROW){
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
		checker = new ImageView(images.getImageFor(state));
	}
	
	public void createRules(){
		rules = new Rules(board);
	}
	
	public boolean isLegal(Cell other){
		return rules.isLegal(this, other);
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public CellState getState(){return state;}
	
	public Coordinate getCoords(){return coord;}
	
	public boolean hasSameCoords(int x, int y){
		return coord.column() == x && coord.row() == y;
	}
	
}
