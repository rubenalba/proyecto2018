package Modelo;

import java.util.List;

import pojos.Ciclo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SessionFactoryUtil;

public interface CicloInterface {

	public List<Ciclo> verAllCiclos();
	public Ciclo verCicloByName(String name);
}
