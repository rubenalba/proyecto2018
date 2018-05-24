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

	//FUNCIONA; NO TOCAR
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



	@Override
	public void modificarNota(MatriculaId id, Double nota) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean validarNota(Double nota) {
		// TODO Auto-generated method stub
		return null;
	}
	//FUNCIONA NO TOCAR!!
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
	//FUNCIONA NO TOCAR!!
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

	@Override
	public void addNota(MatriculaId id, Double nota) {

	}



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


}
