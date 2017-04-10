package pl.mc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mc.dao.PasswordResetTokenDAO;
import pl.mc.model.PasswordResetToken;
import pl.mc.model.User;

@Repository
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	public PasswordResetToken findByToken(String token) {
		List<PasswordResetToken> foundTokensList = new ArrayList<PasswordResetToken>();
		
		foundTokensList = sessionFactory.getCurrentSession()
				.createQuery("from PasswordResetToken where token = ?")
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

	public PasswordResetToken findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void saveOrUpdate(PasswordResetToken myToken) {
		sessionFactory.getCurrentSession().saveOrUpdate(myToken);
	}

}
