package pl.mc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mc.dao.PasswordResetTokenDAO;
import pl.mc.dao.UserDAO;
import pl.mc.dao.VerificationTokenDAO;
import pl.mc.model.PasswordResetToken;
import pl.mc.model.User;
import pl.mc.model.VerificationToken;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private VerificationTokenDAO tokenRepository;
    
    @Autowired
    private PasswordResetTokenDAO passwordReserTokenRepository;

	@SuppressWarnings("unchecked")
	@Transactional
	public User findByUserName(String username) {
		List<User> users = new ArrayList<User>();
		
		users = sessionFactory.getCurrentSession()
				.createQuery("from User where username = ?")
				.setParameter(0, username)
				.list();
		
		if(users.size() > 0)
		{
			return users.get(0);
		}
		else
		{
			return null;
		}
	}

	@Transactional
	public List<User> getAllUsers() {
		
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		return users;
	}
	
	@Transactional
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}
	
	public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        daoAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
	
	public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.saveOrUpdate(myToken);
    }
	
	public void createPasswordResetToken(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordReserTokenRepository.saveOrUpdate(resetToken);
    }
	
	@Transactional
	public boolean createUserAccount(User user)
	{
		user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        String rawPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(rawPassword));
        this.saveOrUpdate(user);
        return true;
	}
	
	@Transactional
	public boolean resetUserPassword(User user, String newPassword)
	{
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        this.saveOrUpdate(user);
        return true;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public VerificationToken getVerificationToken(String token) {
		
		List<VerificationToken> listVerificationToken = new ArrayList<VerificationToken>();
		
		listVerificationToken = sessionFactory.getCurrentSession()
				.createQuery("from VerificationToken where token = ?")
				.setParameter(0, token)
				.list();
		
		if(listVerificationToken.size() > 0)
		{
			return listVerificationToken.get(0);
		}
		else
		{
			return null;
		}
	}

	@Transactional
	public void saveRegisteredUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public PasswordResetToken getPasswordResetToken(String token) {
		
		List<PasswordResetToken> listVerificationToken = new ArrayList<PasswordResetToken>();
		
		listVerificationToken = sessionFactory.getCurrentSession()
				.createQuery("from PasswordResetToken where token = ?")
				.setParameter(0, token)
				.list();
		
		if(listVerificationToken.size() > 0)
		{
			return listVerificationToken.get(0);
		}
		else
		{
			return null;
		}
	}

}
