package pl.mc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mc.dao.VerificationTokenDAO;
import pl.mc.model.User;
import pl.mc.model.VerificationToken;

@Repository
public class VerificationTokenDAOImpl implements VerificationTokenDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	public VerificationToken findByToken(String token) {
		List<VerificationToken> foundTokensList = new ArrayList<VerificationToken>();
		
		foundTokensList = sessionFactory.getCurrentSession()
				.createQuery("from VerificationToken where token = ?")
				.setParameter(0, token).list();
		
		if(foundTokensList.size() > 0)
		{
			return foundTokensList.get(0);
		}
		else
		{
			return null;
		}
	}

	public VerificationToken findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void saveOrUpdate(VerificationToken myToken) {
		sessionFactory.getCurrentSession().saveOrUpdate(myToken);
	}

}
