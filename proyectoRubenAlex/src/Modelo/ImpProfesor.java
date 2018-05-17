package Modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Aula;
import pojos.Profesor;
import pojos.Unidadformativa;

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
	//NO TOCAR
	@Override
	public List<String> asignaturasImpartidas(String usuarioActivo){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Unidadformativa> listaUnidades;
		List<String> idAsignaturas = new ArrayList<String>();
		try {
			tx = session.beginTransaction();
			Criteria query = session.createCriteria(Unidadformativa.class);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			query.add(Restrictions.like("profesor.dniProfesor", usuarioActivo));
			listaUnidades = query.list();
			for (Unidadformativa unidad : listaUnidades) {
				if(!idAsignaturas.contains(unidad.getAsignatura().getNombreAsignatura()))
				idAsignaturas.add(unidad.getAsignatura().getNombreAsignatura());
			}
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return idAsignaturas;
	}

	@Override
	public List<String> UFSimpartidas(String asignatura){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Unidadformativa> listaUnidades;
		List<String> nombreUfs = new ArrayList<String>();
		String dni = "11111111p";
		try {
			tx = session.beginTransaction();
			Criteria query = session.createCriteria(Unidadformativa.class);
			query.add(Restrictions.like("profesor.dniProfesor", dni));
			listaUnidades = query.list();
			for (Unidadformativa unidad : listaUnidades) {
				if (unidad.getAsignatura().getNombreAsignatura().equals(asignatura)){
					nombreUfs.add(unidad.getIdUnidadFormativa());
				}
			}
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return nombreUfs;
	}
	

}
