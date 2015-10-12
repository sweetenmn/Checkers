package helpers;

import javafx.scene.image.Image;

public enum CellState {
	EMPTY(new Image("emptyChip.png")),
	RED(new Image("redChip.png")), 
	BLACK(new Image("blackChip.png")), 
	RED_KING(new Image("redCrown.png")), 
	BLACK_KING(new Image("blackCrown.png"));
	
	private Image image;
	
	private CellState(Image image){
		this.image = image;
	}
	
	public Image getImage(){
		return image;
	}
}
