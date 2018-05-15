package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
			Parent root = FXMLLoader.load(getClass().getResource("../Vistas/VistaLogin.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 *	SELECT asignatura.id_asignatura, unidadformativa.ID_UnidadFormativa
		FROM unidadformativa inner Join asignatura
		WHERE unidadformativa.DNI_Profesor LIKE "11111111p" AND asignatura.ID_Asignatura = unidadformativa.ID_Asignatura;
	 */
}
