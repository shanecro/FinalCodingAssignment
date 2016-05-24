package rocketBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import rocketDomain.RateDomainModel;
import util.HibernateUtil;

public class RateDAL {

	public static ArrayList<RateDomainModel> getAllRates() throws HibernateException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = null;
		
		ArrayList<RateDomainModel> allRates = new ArrayList<RateDomainModel>();		
		
		try {
			tx = session.beginTransaction();	
		
			List lstRates = session.createQuery("FROM RateDomainModel ORDER BY MinCreditScore DESC").list();
			for (Iterator iterator = lstRates.iterator(); iterator.hasNext();) {
				RateDomainModel rte = (RateDomainModel) iterator.next();
				allRates.add(rte);
			}
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return allRates;
	}

}