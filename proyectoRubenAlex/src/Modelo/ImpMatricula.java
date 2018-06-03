package Modelo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.Matricula;
import pojos.MatriculaId;
import pojos.Profesor;
import pojos.Unidadformativa;

public class ImpMatricula  implements MatriculaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	/**
	 * Añade una matricula a la base de datos
	 * @param matricula a añadir
	 */
	@Override
	public void matricularAlumno(Matricula matricula) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(matricula);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	/**
	 * muestra un Matricula a partir de su numero
	 * @return retorna un Matricula
	 * @param Matricula, id a partir del que buscara el Matricula
	 */
	@Override
	public Matricula verMatricula(MatriculaId id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Matricula mat = null;
		try {
			tx = session.beginTransaction();
			mat = (Matricula)session.get(Matricula.class, id);

			Hibernate.initialize(mat.getAlumnos());
			Hibernate.initialize(mat.getUnidadformativa());
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

		return mat;
	}
	/**
	 * elimina un matricula de la bbdd
	 * @param id la matricula a eliminar
	 */
	@Override
	public void eliminarMatricula(MatriculaId id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Matricula mat = (Matricula)session.get(Matricula.class, id);
			session.delete(mat);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();

		}
	}


/**
 * Retorna todas las matriculas de un alumno
 * @param alumno al que pertenecen las matriculas
 * @return retorna una lista
 */
	@Override
	public List<Matricula> matriculasAlumno(Alumnos alumno) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Matricula> matriculas = new ArrayList<Matricula>();
		String sql = "select a.* "
				+ " from matricula a "
				+ " where a.DNI_Alumno LIKE '"+alumno.getDni()+"'";
		try {
			tx = session.beginTransaction();
			matriculas = session.createNativeQuery(sql, Matricula.class).list();

			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return matriculas;

	}


	/**
	 * Muestra la matricula que hay de una uf para un alumno concreto
	 * @param UF a la que esta matriculado
	 * @param alumno matriculado
	 * @return retorna un objeto matricula
	 */
	@Override
	public Matricula verMatriculaUFDNI(Unidadformativa UF, Alumnos alumno) {
		Session session = factory.openSession();
		Transaction tx = null;
		Matricula matricula = null;
		String sql = "select a.* FROM matricula a WHERE a.DNI_Alumno LIKE '" + alumno.getDni()+"'"
					+" AND ID_UnidadFormativa = "+ UF.getIdUnidadFormativa();
		try {
			tx = session.beginTransaction();
			matricula = session.createNativeQuery(sql, Matricula.class).uniqueResult();
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return matricula;
	}


	/**
	 * Modifica un matricula de la bbdd
	 * @param mat, objeto a modificar
	 */
	@Override
	public void modificarNota(Matricula mat) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(mat);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}




}
