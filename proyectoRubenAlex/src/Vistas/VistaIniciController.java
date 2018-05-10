package Vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;

public class VistaIniciController {
	/*<!--<fx:define>
   		<Image fx:id="btnImageAjustes" url="Imagenes/ajustes.jpeg" />
	</fx:define>-->
        <Button fx:id="BtnAjustes" layoutX="14.0" layoutY="276.0" mnemonicParsing="false" prefHeight="25.0" prefWidth="53.0" text="Ajustes">
	    	<!--<graphic>
	        	<ImageView image="$btnImageAjustes" />
			</graphic>-->
  		</Button>*/

	@FXML
	private Button BtnInfo;

	@FXML
	private TableColumn<?, ?> TablaHorario;

	@FXML
	private ListView<?> ListaCursos;

	@FXML
	private Button BtnCerrarSession;

	@FXML
	private Button BtnAjustes;

	 @FXML
	 public void initialize() {
	 }
}

