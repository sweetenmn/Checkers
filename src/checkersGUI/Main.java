package checkersGUI;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane) loader.load(getClass().getResource("GUI.fxml").openStream());
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> System.exit(0));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}