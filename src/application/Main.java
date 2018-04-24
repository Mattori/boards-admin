package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static String styleSheet = "/views/css/application.css";

	@Override
	public void start(Stage primaryStage) {
		try {
			//Chargement  du fichier Developers.fxml
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/views/Tags.fxml"));
			//Création d'une fenêtre
			Scene scene = new Scene(root, 800, 400);
			scene.getStylesheets().add(getClass().getResource(styleSheet).toString());
			primaryStage.setScene(scene);
			//Affichage de la fenêtre
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
