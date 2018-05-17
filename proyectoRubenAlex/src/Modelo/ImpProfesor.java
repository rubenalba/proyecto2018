package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Profesor;

public class ImpProfesor implements ProfesorInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	//Funciona NO TOCAR!!!
	@Override
	public void addProfesor(Profesor profesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(profesor);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	//FUNCIONA, NO TOCAR!!
	@Override
	public void eliminarProfesor(String dniProfesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Profesor pro = (Profesor)session.get(Profesor.class, dniProfesor);
			session.delete(pro);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	@Override
	public void modificarProfesor(Profesor profesorModificado) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(profesorModificado);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}
	//FUNCIONA NO BORRAR!
	@Override
	public Profesor verProfesorByDni(String dniProfesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		Profesor profesor = (Profesor)session.get(Profesor.class, dniProfesor);
		session.close();
		return profesor;
	}
	
	@Override
	public Profesor verProfesorByUser(String userProfesor) {
		/*Session session = factory.openSession();
		Transaction tx = null;
		Clients cliente  = null;
		String hql = "FROM Clients C WHERE C.nom LIKE " + "'"+ "%"+clienteID + "%" + "' OR C.cognoms LIKE " +"'"+ "%" + clienteID + "%"+ "'" ;
		Query query = session.createQuery(hql);
		List results = query.list();
		return results;*/
		Session session = factory.openSession();
		Transaction tx = null;
		Profesor profesor = null;
		String hql = "FROM Profesor p WHERE p.usuari LIKE " +"'"+ userProfesor + "'";
		Query query = session.createQuery(hql);
		profesor = (Profesor) query.uniqueResult();
		return profesor;
	}
	//FUNCIONA NO BORRAR!
	@Override
	public List<Profesor> verProfesores() {
		Session session = factory.openSession();
		Transaction tx = null;
		List <Profesor> listaProfesor = null;
		try {
			tx = session.beginTransaction();
			listaProfesor = session.createQuery("FROM Profesor").list();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaProfesor;
	}

	@Override
	public List<Asignatura> asignaturasImpartidas(){
		Session session = factory.openSession();
		System.out.println(session.isConnected());
		Transaction tx = null;
		List<String> idAsignaturas = null;
		String dni = "11111111p";
		List<Asignatura> listaAsignaturas = null;
		String sql = "SELECT a FROM Unidadformativa u LEFT JOIN Asignatura a WHERE u.profesor LIKE :dni AND (a.idAsignatura = u.asignatura) ";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("dni", "11111111p");
			listaAsignaturas = query.list();
			for (String string : idAsignaturas) {
				System.out.println(string);
			}
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAsignaturas;
	}

}
