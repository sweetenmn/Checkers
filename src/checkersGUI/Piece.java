package checkersGUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Piece {
	private ImageView chip;
	private Image image;
	private int player;
	private Pane pane;
	private int column;
	private int row;
	private boolean clicked;
	
	public Piece(int player, Pane pane, int x, int y){
		this.player = player;
		this.pane = pane;
		column = x;
		row = y;
		clicked = false;
	}
	
	public void createChip(){
		if (player == 1){
			image = new Image("redchip.png");
		} else {
			image = new Image("blackchip.png");
		}
		chip = new ImageView(image);
		chip.setOnMouseClicked(k -> handleClick());
	}
	
	public void addToBoard(){
		createChip();
		pane.getChildren().add(chip);		
	}
	
	@FXML
	public void handleClick(){
		System.out.println("clicked piece");
		clicked = true;
		//talk to the rules/player in game package
		//FIRST if player # in piece matches player click (and no other pieces are active--if so, cancel?)
		//THEN check for legal moves. if so, highlight possible/mandatory
		//add list of legal destinations to arraylist. add panes to those cells in the grid
		//need to create destination square and origin square classes probably
		//add event handlers if panes get clicked move the piece--origin gets changed to a "reset" method
		//obviously the "game" package is going to need a coords system
		//clear the array list on submit move
		//if there's a jump get the coord of the jumped and then on submit move erase that pane
	}
	
	public boolean isLegal(Square square){
		return (Math.abs(column - square.getColumn()) == 1 && Math.abs(row - square.getRow()) == 1);
			
	}
	
	public boolean isClicked(){
		return clicked;
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public void clear(){
		pane.getChildren().clear();
		pane.setVisible(false);
		clicked = false;
	}
	
	public int getPlayer(){
		return player;
	}

}