package Vistas;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Modelo.AsignaturaInterface;
import Modelo.AsistenciaInterface;
import Modelo.MatriculaInterface;
import Modelo.UnidadFormativaInterface;
import dao.DAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Matricula;
import pojos.Unidadformativa;

/**
 * Controlador de la vista que contiene la información de un alumno concreto
 * @author cfgs
 *
 */
public class VistaAlumnoController {
	static MatriculaInterface mi = DAO.getMatriculaInterface();
	static UnidadFormativaInterface u = DAO.getUnidadFormativaInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static AsistenciaInterface ast = DAO.getAsistenciaInterface();
	@FXML
	private TextField DNIAlumno;

	@FXML
	private TextField ApellidosAlumno;
	@FXML
	private Label nombreLb;

	@FXML
	private TextField email;

	private ResourceBundle bundle;
	private Locale locale;

	@FXML
	private ChoiceBox<Unidadformativa> AsignaturasAlumno;

	@FXML
	private TableView<Asistencia> tablaAsistencias;
	@FXML
	private TableColumn<Asistencia, String> fechaFalta;

	@FXML
	private TableColumn<Asistencia, String> justificadoFalta;

	@FXML
	private TextField NotaAsigAlumno;

	@FXML
	private TextField NombreAlumno;

	@FXML
	private Button mostrarFaltas, btnDesmatricular;
	@FXML
	private Button calificarNota;
	@FXML
	private Button GuardarNota;
	@FXML
	private Button eliminarFalta;
	@FXML
	private Button justificarFalta;
	@FXML
	private Button volver;

	private String idioma = obtenerlang();

	private String obtenerlang() {
		VistaIniciController v = new VistaIniciController();
		idioma = v.getIdioma();
		return idioma;
	}

	private Alumnos alumno;
	private List<Matricula> listaMatriculas;
	private List<Unidadformativa> listaUFS = new ArrayList<Unidadformativa>();
	Unidadformativa uf;
	Asignatura asig;
	private List<Asistencia> listaFaltas;
	private static Unidadformativa UFActiva;

	@FXML
	public void initialize(){
		obtenerlang();
		cargarIdioma(idioma);
		UFActiva = getUFMarcada();
		VistaIniciController vistainici = new VistaIniciController();
		alumno = vistainici.getAlumnoMarcado();
		DNIAlumno.setText(alumno.getDni());
		email.setText(alumno.getEmail());
		NombreAlumno.setText(alumno.getNombreCompleto());

		listaMatriculas = mi.matriculasAlumno(alumno);

		Matricula mat = mi.verMatriculaUFDNI(UFActiva, alumno);
		refrescarLista();
		if (mat.getNota() != null)
			NotaAsigAlumno.setText(mat.getNota().toString());
		else{
			if (idioma =="es") {
				NotaAsigAlumno.setText("No puntuado");
			}else {
				NotaAsigAlumno.setText("Sense puntuació");
			}
		}

	}


	private Unidadformativa getUFMarcada() {
		VistaIniciController v = new VistaIniciController();
		UFActiva =  v.getUnidadFormativa();
		return UFActiva;
	}

	/**
	 * Metodo para modificar la nota de una matricula
	 */
	@FXML
	public void actualizarNota(){
		Matricula mat = mi.verMatriculaUFDNI(UFActiva, alumno);
		try {
			double nota = Double.valueOf(NotaAsigAlumno.getText());
			if (validarNota(nota)){
				mat.setNota(nota);
				mi.modificarNota(mat);
				Alert alert = new Alert(AlertType.INFORMATION);
				if (idioma =="es") {
					alert.setHeaderText("Se ha modificado la nota");
				}else {
					alert.setHeaderText("S'ha modificat la nota");
				}
				alert.showAndWait();
				NotaAsigAlumno.setEditable(false);
				GuardarNota.setVisible(false);
				calificarNota.setVisible(true);
			}
			else {Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			if(idioma=="es") {
				alert.setHeaderText("Error al evaluar falta");
				alert.setContentText("Nota incorrecta");
			}else {
				alert.setHeaderText("Error al evaluar la falta");
				alert.setContentText("Nota incorrecte");	
			}
			alert.showAndWait();
			}
		} catch (Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			if(idioma=="es") {
				alert.setHeaderText("Error al evaluar falta");
				alert.setContentText("Nota incorrecta");
			}else {
				alert.setHeaderText("Error al evaluar la falta");
				alert.setContentText("Nota incorrecte");	
			}
			alert.showAndWait();
		}
	}

	public boolean validarNota(double nota){
		if (nota >= 0 && nota <= 10) return true;
		else return false;
	}
	/**
	 * Metodo que habilitara las opciones para modificar una nota de una matricula
	 */
	@FXML
	public void activarNota(){
		NotaAsigAlumno.setEditable(true);
		GuardarNota.setVisible(true);
		calificarNota.setVisible(false);
		NotaAsigAlumno.setText("");
	}

	/**
	 * Metodo que eliminara la falta de asistencia seleccionada
	 */
	@FXML
	public void eliminarFalta(){
		try{
			Asistencia falta = tablaAsistencias.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			if(idioma=="es") {
				alert.setHeaderText("¿Seguro que desea eliminar la falta de asistencia en fecha "+falta.getId().getFecha()+"?");
			}else {
				alert.setHeaderText("De debó vol eliminar la falta d' assistència amb data "+falta.getId().getFecha()+"?");
			}
			Optional<ButtonType> result = alert.showAndWait();
			if(result.isPresent()&& result.get() == ButtonType.OK){
				ast.eliminarAsistencia(falta.getId());
				refrescarLista();
			}
		} catch (NullPointerException e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al eliminar falta");
			if(idioma=="es") {
				alert.setContentText("No se ha seleccionado ninguna falta");
			}else {
				alert.setContentText("No s'ha seleccionat cap falta");
			}
			alert.showAndWait();
		}
	}
	/**
	 * justifica la falta de asistencia seleccionada
	 */
	@FXML
	public void justificar(){
		try {
			Asistencia falta = tablaAsistencias.getSelectionModel().getSelectedItem();
			if (falta.isJustificante()){
				falta.setJustificante(false);
				ast.modificarAsistencia(falta);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Falta injustificada");
				alert.showAndWait();
			}

			else {
				falta.setJustificante(true);
				ast.modificarAsistencia(falta);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Falta justificada");
				alert.showAndWait();}

		} catch (NullPointerException e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error al justificar falta");
			if(idioma=="es") {
				alert.setContentText("No se ha seleccionado ninguna falta");
			}else {
				alert.setContentText("No s'ha seleccionat cap falta");	
			}
			alert.showAndWait();
		}
		refrescarLista();
	}
	/**
	 * Carga las faltas de asistencia a la tabla de faltas
	 */
	public void refrescarLista(){
		listaFaltas = ast.verAllAsistenciasAlumnoUF(alumno, UFActiva);
		tablaAsistencias.setItems(FXCollections.observableArrayList(listaFaltas));
		fechaFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Hora"));
		justificadoFalta.setCellValueFactory(new PropertyValueFactory<Asistencia, String>("Justificado"));
	}

	private void cargarIdioma(String idioma) {
		locale = new Locale(idioma);
		bundle = ResourceBundle.getBundle("resources.lang", locale);
		volver.setText(bundle.getString("volver"));
		calificarNota.setText(bundle.getString("calificarNota"));
		eliminarFalta.setText(bundle.getString("eliminarFalta"));
		fechaFalta.setText(bundle.getString("fechaFalta"));
		nombreLb.setText(bundle.getString("nombreLb"));
		btnDesmatricular.setText(bundle.getString("btnDesmatricular"));

	}
	@FXML
	public void pdf(ActionEvent event) throws SQLException, ParseException {


		String nombreFichero = "faltas_"+alumno.getNombreCompleto()+"_"+UFActiva.getNombreUf().substring(0,3);


		SimpleDateFormat  sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",new Locale("ES"));
		Date ahora = new Date();
		String hoy = sdf.format(ahora);
		sdf=new SimpleDateFormat("HH:mm 'horas'",new Locale("ES"));
		String hora = sdf.format(ahora);
		String contenido = "Institut Marianao\n"
				+ "C/ Passeig de les mimoses, 18, Sant Boi de Llobregat (Barcelona)\n"
				+ "(93) 640 77 10\n";
		if(idioma=="es") {
			contenido += "www.institutmarianao.cat\n\n\nEl/La alumno/alumna " + alumno.getNombre() + " ";
		}else {
			contenido += "www.institutmarianao.cat\n\n\nL'alumne " + alumno.getNombre() + " ";
		}
		if(idioma=="es") {
			contenido += alumno.getApellidos() + ",\n tiene las siguientes faltas de asistencia para la "+UFActiva.getNombreUf() +":  \n\n";
		} else {
			contenido += alumno.getApellidos() + ",\n té les següents faltes d'asistència per la  "+UFActiva.getNombreUf() +":  \n\n";
		}
		for (Asistencia asistencia : listaFaltas) {
			contenido += asistencia.getId().getFecha() + "     Justificada: " + asistencia.getJustificado()+"\n";
		}
		if(idioma=="es") {
			contenido += "\nSiendo el total de faltas un " + (listaFaltas.size()* 100) / UFActiva.getHoras() + "% de las horas.";
			contenido += "\n\n A fecha de "+hoy+ " a las "+hora+".";}
		else {
			contenido += "\nQue equival a un " + (listaFaltas.size()* 100) / UFActiva.getHoras() + "% de les hores.";
			contenido += "\n\n el día  "+hoy+ " a les "+hora+".";
		}
		try {
			FileOutputStream archivo = new FileOutputStream(nombreFichero + ".pdf");
			Document doc = new Document();
			PdfWriter.getInstance(doc, archivo);
			doc.open();
			doc.add(new Paragraph(contenido));
			doc.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			if(idioma=="es") {
				alert.setHeaderText("Informe faltas creado");
			}else {
				alert.setHeaderText("PDF creat");
			}
			alert.showAndWait();

		} catch (Exception e) {

		}
	}
	@FXML
	public void volver(){
		Stage Actual = (Stage) volver.getScene().getWindow();
		Actual.close();



	}
	@FXML
	public void desmatricular(){
		Matricula borrar = mi.verMatriculaUFDNI(UFActiva, alumno);
		try{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			if (idioma=="es") {
				alert.setHeaderText("¿Seguro que desea eliminar la matricula del alumno "+alumno.getNombreCompleto()+"?");
			} else {
				alert.setHeaderText("De debó vol eliminar la matricula de l'alumne "+alumno.getNombreCompleto()+"?");
			}
			Optional<ButtonType> result = alert.showAndWait();
			if(result.isPresent()&& result.get() == ButtonType.OK){
				mi.eliminarMatricula(borrar.getId());
				volver();
			}

		}catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			if (idioma =="es") {
				alert.setHeaderText("No se ha podido desmatricular");
			}else {
				alert.setHeaderText("No s'ha pogut desmatricular");
			}
			alert.showAndWait();
		}
	}
}
