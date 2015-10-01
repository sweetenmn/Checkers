package application;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.effect.*;
import javafx.scene.effect.Light.Distant;

public class Controller {
	@FXML
	GridPane checkerboard;
	@FXML
	Circle circle;
	@FXML
	Pane testPane;
	
	@FXML
	public void initialize(){
		InnerShadow shadow = new InnerShadow();
		Lighting lighting = new Lighting();
		lighting.setLight(new Light.Distant());
		lighting.setSurfaceScale(3.36);
		shadow.setInput(lighting);
		shadow.setWidth(21);
		shadow.setHeight(21);
		shadow.setRadius(10);
		checkerboard = new GridPane();
		circle = new Circle(18,18,18, Color.rgb(221, 31, 6));
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(1);
		circle.setEffect(shadow);
		circle.toFront();
		testPane.getChildren().add(circle);
		
	}

}
