package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class KingChip extends Cell {
	Rules rules;

	public KingChip(int state, Pane pane, int x, int y) {
		super(state, pane, x, y);
	}
	
	public void kingMe(){
		if (state != 0){
			kinged = true;
			rules = new Rules(kinged);
		}
	}

	public void changeImage(String imageName){
		pane.getChildren().clear();
		image = new Image(imageName);
		checker = new ImageView(image);
		pane.getChildren().add(checker);		
	}

}
