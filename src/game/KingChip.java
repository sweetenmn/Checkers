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
		if (STATE != 0){
			kinged = true;
			rules = new Rules(kinged);
		}
	}
	//can be use for "removing"?
	public void changeImage(String imageName){
		pane.getChildren().clear();
		unclick();
		image = new Image(imageName);
		checker = new ImageView(image);
		checker.setOnMouseClicked(k -> click());
		pane.getChildren().add(checker);		
	}

}
