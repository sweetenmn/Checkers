package game;

import checkersGUI.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell {
	
	protected int state;
	public static final int EMPTY = 1;
	public static final int RED = 2; // p1
	public static final int BLACK = 3; // p2
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
	
	private void createImage(Board board){
		System.out.println(String.valueOf(this.state));
		if (this.state == RED){
			image = new Image("redchip.png");
		} else if (this.state == BLACK){
			image = new Image("blackchip.png");
		} else if (this.state == EMPTY){
			image = new Image("emptychip.png");
		}
		checker = new ImageView(image);
		if (this.state == RED || this.state == BLACK){
			checker.setOnMouseClicked(k -> {
	        	board.setLastPieceClicked(this);
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
