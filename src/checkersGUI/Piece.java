package checkersGUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Piece implements Square_Piece{
	private ImageView chip;
	private Image image;
	private int player;
	public Pane pane;
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
		clicked = true;

	}
	
	//This may need to be moved to Rules.
	public boolean isLegal(Square square){
		return (Math.abs(column - square.getColumn()) == 1 && row - square.getRow() == 1);
			
	}
	
	public boolean isClicked(){
		return clicked;
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}
	
	public void clear(){
		pane.getChildren().clear();
		pane.setVisible(false);
		unclick();
	}
	
	public int getPlayer(){
		return player;
	}
	
	public void unclick(){
		clicked = false;
	}


}