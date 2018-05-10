package Vistas;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public abstract class ColumnButton<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
	private String text;

    public ColumnButton(String text) {
        super();
        this.text = text;
    }

    public abstract void buttonAction(S element);

    @Override
	public TableCell<S, T> call( TableColumn<S, T> param )
	{
		TableCell<S, T> cell = new TableCell<S, T>()
		{
			@Override
			public void updateItem( T item, boolean empty )
			{
				super.updateItem( item, empty );

				if (item == null || empty) {

                    setGraphic( null );
                } else {
                	Image imageOk = new Image(getClass().getResourceAsStream("Imagenes/ajustes.png"));
                	Button btn = new Button("asd",new ImageView(imageOk));

					btn.setOnAction( ( ActionEvent event ) ->
					{
						S element = getTableView().getItems().get( getIndex() );
						buttonAction(element);

					} );
					setGraphic( btn );
				}
			}
		};
		return cell;
	}

	public void buttonAction() {
		// TODO Auto-generated method stub

	}


}