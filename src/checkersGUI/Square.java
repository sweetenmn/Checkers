package checkersGUI;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Square {
	
	private Pane pane;
	private int column;
	private int row;
	private boolean clicked;
	
	public Square(Pane pane, int x, int y){
		this.pane = pane;
		column = x;
		row = y;
		clicked = false;
		setUp();
	}
	
	@FXML
	private void setUp(){
		this.pane.setOnMouseClicked(k -> click());
	}
	public int getColumn(){return column;}
	public int getRow(){return row;}
	@FXML
	public void click(){clicked = true;}
	public boolean isClicked(){return clicked;}
	
	public void clear(){
		clicked = false;
	}
	
	

}
