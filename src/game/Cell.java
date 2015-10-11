package game;

import checkersGUI.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell {
	
	protected int state;
	//need enum
	public static final int EMPTY = 1;
	public static final int RED = 2;
	public static final int BLACK = 3;
	public static final int RED_KING = 4;
	public static final int BLACK_KING = 5;
	private int column;
	private int row;
	private Rules rules;
	protected boolean kinged = false;
	protected Pane pane;
	protected Image image;
	protected ImageView checker;
	
	public Cell(int state, Pane pane, int x, int y){
		this.state = state;
		column = x;
		row = y;
		this.pane = pane;
	}
	
	private void checkKing(){
		if (this.state == RED){
			if (row == 7){
				this.state = RED_KING;
			}
		} else if (this.state == BLACK){
			if (row == 0){
				this.state = BLACK_KING;
			}
		}
	}
	
	private void createImage(Board board){
		checkKing();
		if (this.state == RED){
			image = new Image("redchip.png");
		} else if (this.state == BLACK){
			image = new Image("blackchip.png");
		} else if (this.state == EMPTY){
			image = new Image("emptychip.png");
		} else if (this.state == RED_KING){
			image = new Image("redCrown.png");
		} else if (this.state == BLACK_KING){
			image = new Image("blackCrown.png");
		}
		checker = new ImageView(image);
		assignEvent(board);   
	}
	
	private void assignEvent(Board board){
		if (this.state != EMPTY){
			checker.setOnMouseClicked(k -> {
	        	board.setLastPieceClicked(this);
	        	board.setLastSquareClicked(this);
	        	System.out.println(String.valueOf(this.column));
			});
		} else {
			checker.setOnMouseClicked(k -> {
				board.setLastSquareClicked(this);
				System.out.println(String.valueOf(this.column));
			});
		}  
	}
	
	public void createRules(){
		rules = new Rules(false);
	}
	
	public void addToBoard(Board board){
		createRules();
		createImage(board);
		pane.getChildren().add(checker);
	}
	
	public boolean isLegal(Cell other){
		return rules.isLegal(this, other);
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(pane);
	}
	
	public int getState(){return this.state;}
	
}
