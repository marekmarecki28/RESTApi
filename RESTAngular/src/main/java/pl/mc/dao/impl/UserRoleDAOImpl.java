package pl.mc.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mc.dao.UserRoleDAO;
import pl.mc.model.UserRole;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void createUserRole(UserRole userrole) {
		sessionFactory.getCurrentSession().saveOrUpdate(userrole);
	}

}
