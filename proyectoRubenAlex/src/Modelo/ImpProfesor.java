package Modelo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import Decoder.BASE64Encoder;
import dao.DAO;
import dao.SessionFactoryUtil;
import pojos.Alumnos;
import pojos.Asignatura;
import pojos.Asistencia;
import pojos.Profesor;
import pojos.Unidadformativa;

public class ImpProfesor implements ProfesorInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();
	static AlumnosInterface a = DAO.getAlumnosInterface();
	static AsignaturaInterface as = DAO.getAsignaturaInterface();
	static UnidadFormativaInterface uf = DAO.getUnidadFormativaInterface();
	static CicloInterface c = DAO.getCicloInterface();

	/**
	 * añade un profesor a la base de datos
	 */
	@Override
	public void addProfesor(Profesor profesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		SecretKey skey = passWordKeyGeneration(profesor.getDniProfesor());
		String newPwd = encryptedData(skey, profesor.getPassword());
		profesor.setPassword(newPwd);
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
	/**
	 * elimina un profesor de la base de datos
	 */
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

	/**
	 * modifica un profesor en la base de datos
	 */
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
	/**
	 * retorna un profesor a partir de su dni
	 */
	@Override
	public Profesor verProfesorByDni(String dniProfesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		Profesor profesor = (Profesor)session.get(Profesor.class, dniProfesor);
		session.close();
		return profesor;
	}
	/**
	 * retorna un profesor a partir de su usuario
	 */
	@Override
	public Profesor verProfesorByUser(String userProfesor) {
		Session session = factory.openSession();
		Transaction tx = null;
		Profesor profesor = null;
		String hql = "FROM Profesor p WHERE p.usuari LIKE " +"'"+ userProfesor + "'";
		Query query = session.createQuery(hql);
		profesor = (Profesor) query.uniqueResult();
		session.close();
		return profesor;
	}
	/**
	 * retorna todas las asignaturas impartidas por un profesor
	 */
	public List<Asignatura> misAsignaturas(Profesor profesor){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Integer> asignaturasMias = null;
		List<Asignatura> asignaturasEnviadas = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			String sql = "select DISTINCT  u.ID_Asignatura "
					+ "from asignatura a, unidadformativa u "
					+ "where a.ID_Asignatura = u.ID_Asignatura "
					+ "and u.DNI_Profesor = " + "'" +profesor.getDniProfesor()+ "'";
			asignaturasMias = session.createNativeQuery(sql).list();
			for (Integer asignatura : asignaturasMias) {
				Asignatura a = as.verAsignaturaById(asignatura);
				a.setCiclo(c.verCicloByID(a.getCiclo().getIdCiclo()));
				asignaturasEnviadas.add(a);

			}

		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return asignaturasEnviadas;
	}
/**
 * Retorna todas las ufs impartidas por un profesor
 */
	public List<Unidadformativa>misUFs (Profesor profesor, Asignatura idAsignatura){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Unidadformativa>ufsMias = new ArrayList<Unidadformativa>();
		List<Integer>idUF = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			String sql ="select u.ID_UnidadFormativa "
					+ "from unidadformativa u "
					+ "where u.DNI_Profesor = " + "'"+ profesor.getDniProfesor() + "'"
					+ " and u.ID_Asignatura = " +  "'"+ idAsignatura.getIdAsignatura()+ "'";
			idUF = session.createNativeQuery(sql).list();
			for (Integer integer : idUF) {
				 Unidadformativa u = uf.verUnidadformativaByID(integer);
				 ufsMias.add(u);
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return ufsMias;

	}
	/**
	 * Retorna una lista de alumnos a los que le da clase el profesor
	 */
	public List<Alumnos> misAlumnosByAsignatura(Profesor profesor, Unidadformativa uf){
		Session session = factory.openSession();
		Transaction tx = null;
		List <String> alumnos = null;
		List<Alumnos> lista = new ArrayList<Alumnos>();
		try {
			tx = session.beginTransaction();
			String sql = "Select a.DNI "
					+ " from Alumnos a, Matricula m, Unidadformativa f "
					+ " WHERE a.DNI Like m.DNI_Alumno and m.ID_UnidadFormativa "
					+ " like f.ID_UnidadFormativa "
					+ " and f.DNI_Profesor like " + "'" +  profesor.getDniProfesor() + "'"
					+ " and f.ID_UnidadFormativa = " + uf.getIdUnidadFormativa() + " Order by a.Apellidos ";
			alumnos = session.createNativeQuery(sql).list();
			for (String string : alumnos) {
				Alumnos alu = a.verAlumnobyDNI(string);
				//Hibernate.initialize(alu.getMatriculas());
				lista.add(alu);
			}
		}catch  (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return lista;
	}
	/**
	 * retorna una lista con todos los profesores
	 */
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
	/**
	 * retorna todas las asignaturas impartidas por un profesor
	 */
		@Override
	public List<String> asignaturasImpartidas(String dniProfesor){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Unidadformativa> listaUnidades;
		List<String> idAsignaturas = new ArrayList<String>();
		try {
			tx = session.beginTransaction();
			Criteria query = session.createCriteria(Unidadformativa.class);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			query.add(Restrictions.like("profesor.dniProfesor", dniProfesor));
			listaUnidades = query.list();
			for (Unidadformativa unidad : listaUnidades) {
				String lista = unidad.getAsignatura().getCiclo().getNombreCiclo()+ " /"+unidad.getAsignatura().getNombreAsignatura();
				if(!idAsignaturas.contains(lista)) idAsignaturas.add(lista);
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
	//Sin uso
	/**
	 * Retorna una lista de todas las ufs impartidas por un profesor
	 */
	@Override
	public List<String> UFSimpartidas(String asignatura, String dni){
		String[] parts = asignatura.split(" /");
		String asig = parts[1];
		String modul = parts[0];
		Session session = factory.openSession();
		Transaction tx = null;
		List<Unidadformativa> listaUnidades;
		List<String> nombreUfs = new ArrayList<String>();
		try {
			tx = session.beginTransaction();
			Criteria query = session.createCriteria(Unidadformativa.class);
			query.add(Restrictions.like("profesor.dniProfesor", dni));
			listaUnidades = query.list();
			for (Unidadformativa unidad : listaUnidades) {
				if (unidad.getAsignatura().getCiclo().getNombreCiclo().equals(modul) && unidad.getAsignatura().getNombreAsignatura().equals(asig)){
					nombreUfs.add(unidad.getNombreUf());
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


	/**
	 * retorna una clave de encriptacion
	 * @param pwd password a partird e la que crea la clave
	 * @return clave de encriptacion
	 */
	public SecretKey passWordKeyGeneration(String pwd) {
		SecretKey skey = null;
		int keysize = 128;
		try {
			byte[] data = pwd.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(data);
			byte [] key = Arrays.copyOf(hash, keysize/8);
			skey = new SecretKeySpec (key, "AES");
		}catch (Exception e) {

		}
		return skey;
	}
	/**
	 * encripta una password a partir de una clave
	 * @param skey clave para encriptar
	 * @param pwd password a encriptar
	 * @return password encriptada
	 */
	public static String encryptedData(SecretKey skey, String pwd) {
		byte [] datos = null;
		String dats=null;
		try{
			byte [] pwdEncriptado = pwd.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			datos = cipher.doFinal(pwdEncriptado);
			BASE64Encoder b = new BASE64Encoder();
			dats = b.encode(datos);

		}catch (Exception ex) {

		}

		return dats;

	}
}
