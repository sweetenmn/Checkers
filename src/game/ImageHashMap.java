package game;

import java.util.HashMap;

import javafx.scene.image.Image;

public class ImageHashMap {
	HashMap<CellState, Image> images = new HashMap<CellState, Image>();
	
	public ImageHashMap(){
		addImages();
	}
	
	private void addImages(){
		images.put(CellState.BLACK, new Image("blackChip.png"));
		images.put(CellState.BLACK_KING, new Image("blackCrown.png"));
		images.put(CellState.RED, new Image("redChip.png"));
		images.put(CellState.RED_KING, new Image("redCrown.png"));
		images.put(CellState.EMPTY, new Image("emptyChip.png"));
	}
	
	public Image getImageFor(CellState state){
		return images.get(state);
	}

}
