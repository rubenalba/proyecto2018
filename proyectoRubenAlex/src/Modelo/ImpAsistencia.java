package Modelo;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.AsistenciaId;
import pojos.Unidadformativa;

public class ImpAsistencia implements AsistenciaInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	@Override

	public void addAsistencia(Asistencia asistencia) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(asistencia);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	@Override
	public void eliminarAsistencia(AsistenciaId id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Asistencia asistencia = (Asistencia)session.get(Asistencia.class, id);
			session.delete(asistencia);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	@Override
	public void modificarAsistencia(Asistencia asistenciaModificada) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(asistenciaModificada);
			tx.commit();
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	@Override
	public Asistencia verAsistenciaById(AsistenciaId id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Asistencia asistencia = (Asistencia)session.get(Asistencia.class, id);
		session.close();
		return asistencia;
	}

	@Override
	public List<Asistencia> verAllAsistenciasAlumnoUF(Alumnos alumno, Unidadformativa uf){
		Session session = factory.openSession();
		Transaction tx = null;
		List <Asistencia> listaAsistencia = null;
		String sql = "SELECT a.* FROM Asistencia a WHERE a.ID_UnidadFormativa = "
					 +uf.getIdUnidadFormativa()+" AND a.DNI_Alumno like '"+alumno.getDni()+"'";
		//String sql = "SELECT a.* FROM Asistencia a WHERE a.ID_UnidadFormativa = 1 AND a.DNI_Alumno like '11111111a'";
		try {
			tx = session.beginTransaction();
			listaAsistencia = session.createNativeQuery(sql, Asistencia.class).list();
			tx.commit();
			for (Asistencia asistencia : listaAsistencia) {
				System.out.println(asistencia.getId().getFecha());
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return listaAsistencia;
	}

}
