package application;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author cfgs
 *
 */
public class Main extends Application {
	@Override
	/**
	 * Inicia el programa, para ello ejecuta la vista del Login.
	 */
	public void start(Stage primaryStage) throws IOException {
	
			Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaLogin.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
