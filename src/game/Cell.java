package game;

import checkersGUI.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell {
	
	protected static int STATE;
	private static int EMPTY = 0;
	private static int RED = 1; // p1
	private static int BLACK = 2; // p2
	private int column;
	private int row;
	private Rules rules;
	private boolean clicked = false;
	protected boolean kinged = false;
	protected Pane pane;
	protected Image image;
	protected ImageView checker;
	CellState state;
	
	public Cell(int state, Pane pane, int x, int y){
		STATE = state;
		column = x;
		row = y;
		this.pane = pane;
	}
	
	private void createImage(Board board){
		if (STATE == RED){
			image = new Image("redchip.png");
		} else if (STATE == BLACK){
			image = new Image("blackchip.png");
		} else if (STATE == EMPTY){
			image = new Image("emptychip.png");
		}
		checker = new ImageView(image);
		checker.setOnMouseClicked(k -> new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
            	if (STATE == RED || STATE == BLACK){
            		board.setLastPieceClicked(Cell.this);
            	} else {
            		board.setLastSquareClicked(Cell.this);
            	}
            }
        });
	}
	
	public void createRules(){
		rules = new Rules(false);
	}
	
	public void addToBoard(Board board){
		createRules();
		createImage(board);
		pane.getChildren().add(checker);		
	}
	
	@FXML
	public void click(){
		clicked = true;
	}

	public boolean isLegal(Cell other){
		return rules.isLegal(this, other);
	}
	
	public boolean isClicked(){
		return clicked;
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public void eraseFrom(GridPane gridParent){
		gridParent.getChildren().remove(pane);
	}
	
	public int getState(){return STATE;}
	
	public void unclick(){clicked = false;}
	
}
