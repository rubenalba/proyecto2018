package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Franjas;
import pojos.Horas;
import pojos.Profesor;

public class ImpFranjas implements FranjaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();


	/**
	 * Añade un franja a la bbdd
	 * @param franja, objeto a añadir a la bbdd
	 */
	@Override
	public void addFranja(Franjas franja) throws Exception {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(franja);
			tx.commit();
		}catch  (Exception e) {
			if (tx!=null) tx.rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	/**
	 * elimina una Franja de la bbdd
	 * @param idFranja numero del aula a eliminar
	 */
	@Override
	public void eliminarFranja(int idFranja) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Franjas franja = (Franjas)session.get(Franjas.class, idFranja);
			session.delete(franja);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	/**
	 * Modifica una franja de la bbdd
	 * @param franjaModificada, objeto a modificar
	 */
	@Override
	public void modificarFranja(Franjas franjaModificada) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(franjaModificada);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	/**
	 * retorna todas las franjas
	 * @return Lista de franjas
	 */
	@Override
	public List<Franjas> verAlFranjas() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Franjas> listaFranjas = null;
		try {
			tx = session.beginTransaction();
			listaFranjas = session.createQuery("FROM Franjas").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaFranjas;
	}
	/**
	 * Muestra una franja a partir de su id
	 * @param idFranja id de la que buscara informacion
	 * @return objeto franja
	 */
	@Override
	public Franjas verFranjaByID(int idFranja) {
		Session session = factory.openSession();
		Transaction tx = null;
		Franjas franja = (Franjas)session.get(Franjas.class, idFranja);
		session.close();
		return franja;
	}
	/**
	 * Obtiene una franja a partir de todos sus atributos para poder acceder a la id de esta
	 * @param Hora de la frnaja
	 * @param profesor de la franja
	 * @param dia de la franja
	 * @param asignatura de la franja
	 * @return objeto franja
	 */
	@Override
	public Franjas verFranjaFalta(Horas Hora, Profesor profesor, String dia, Asignatura asignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT * FROM Franjas h WHERE h.id_horas = "+Hora.getIdHoras()+" AND h.DNI_Profesor LIKE '"+profesor.getDniProfesor()+"' AND h.Dia LIKE '"+dia+"' AND h.Asignatura = "+asignatura.getIdAsignatura();
		Franjas r = null;
		List<Franjas> franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();

			for (Franjas franj : franjaList) {
				r = franj;
				System.out.println("ha entrado en el bucle");
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return r;
	}
	/**
	 * Retorna todas las franjas de una asignatura para un profesor concreto
	 * @param profesor al que pertenece la franja
	 * @param asignatura que se da en esa franja
	 * @return Lista de franjas
	 */
	@Override
	public List<Franjas> verFranjaAsignatura(Profesor profesor, Asignatura asignatura) {
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT * FROM Franjas h WHERE h.DNI_Profesor LIKE '"+profesor.getDniProfesor()+"' AND h.Asignatura = "+asignatura.getIdAsignatura();
		//Franjas r = null;
		List<Franjas> franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();

		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return franjaList;
	}
	/**
	 * Retorna todas las franjas de un profesor en un dia concreto
	 * @param dni del profesor al que pertenecen las franjas
	 * @param dia de las franjas
	 * @return Lista de franjas
	 */
	public List<Franjas>completarHorario(String dni, String dia){
		Session session = factory.openSession();
		Transaction tx = null;
		String sql = "SELECT f.* from franjas f where f.DNI_Profesor = '" + dni+"' and f.Dia = '"+ dia +"'";
		Franjas r = null;
		List<Franjas>franjaList = new ArrayList<Franjas>();
		try {
			tx = session.beginTransaction();
			franjaList = session.createNativeQuery(sql, Franjas.class).list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return franjaList;
	}

}
