package Modelo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import Decoder.BASE64Encoder;
import dao.SessionFactoryUtil;
import pojos.Profesor;
import pojos.Unidadformativa;

public class ImpProfesor implements ProfesorInterface{
	private static SessionFactory factory = SessionFactoryUtil.getSessionFactory();

	//Funciona NO TOCAR!!!
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

	@Override
	public List<String> UFSimpartidas(String asignatura){
		String[] parts = asignatura.split(" /");
		String asig = parts[1];
		String modul = parts[0];
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
			System.out.println("Error generando la clave");
		}
		return skey;
	}

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
			System.out.println("Error cifrando");
		}

		return dats;

	}

}
