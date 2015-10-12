package game;

import checkersGUI.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell {
	
	private CellState state;
	private int column;
	private int row;
	private Rules rules;
	private Pane pane;
	private Image image;
	private ImageView checker;
	private boolean kinged;
	private static final int TOP_ROW = 0;
	private static final int BOTTOM_ROW = 7;
	
	public Cell(CellState state, Pane pane, int x, int y){
		this.state = state;
		this.pane = pane;
		column = x;
		row = y;
		kinged = false;
	}
	
	public void addToBoard(Board board){
		createChip(board);
		pane.getChildren().add(checker);
	}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(pane);
	}
	
	private void createChip(Board board){
		crownIfKing();
		createRules();
		switchImage();
		createImageView();
		assignEvent(board);   
	}
	private void crownIfKing(){
		switch(state){
			case BLACK:
				if (row == TOP_ROW){
					state = CellState.BLACK_KING;
					kinged = true;
				}
				break;
			case RED:
				if (row == BOTTOM_ROW){
					state = CellState.RED_KING;
					kinged = true;
				}
				break;
			case BLACK_KING: case EMPTY: case RED_KING:
				break;
		}
	}
	
	private void switchImage(){
		switch(state){
			case EMPTY:
				image = new Image("emptyChip.png");
				break;
			case BLACK:
				image = new Image("blackChip.png");
				break;
			case BLACK_KING:
				image = new Image("blackCrown.png");
				break;
			case RED:
				image = new Image("redChip.png");
				break;
			case RED_KING:
				image = new Image("redCrown.png");
				break;		
		}
	}
	
	private void assignEvent(Board board){
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
		checker = new ImageView(image);
	}
	
	public void createRules(){
		rules = new Rules();
	}
	
	public boolean isLegal(Cell other){
		return rules.isLegal(this, other);
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public CellState getState(){return state;}
	
}
