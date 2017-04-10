package pl.mc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import pl.mc.dao.UserAttemptsDAO;
import pl.mc.model.UserAttempts;

public class LimiLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserAttemptsDAO userAttemptsDao;

	@Autowired
	@Qualifier("myUserDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {

	  try {

		Authentication auth = super.authenticate(authentication);

		//if reach here, means login success, else an exception will be thrown
		//reset the user_attempts
		userAttemptsDao.resetFailAttempts(authentication.getName());

		return auth;

	  } catch (BadCredentialsException e) {

		//invalid login, update to user_attempts
		  userAttemptsDao.updateFailAttempts(authentication.getName());
		throw e;

	  } catch (LockedException e){

		//this user is locked!
		String error = "";
		UserAttempts userAttempts =
				userAttemptsDao.getUserAttempts(authentication.getName());

               if(userAttempts!=null){
			Date lastAttempts = userAttempts.getLastModified();
			error = "User account is locked! <br><br>Username : "
                           + authentication.getName() + "<br>Last Attempts : " + lastAttempts;
		}else{
			error = e.getMessage();
		}

	  throw new LockedException(error);
	}

	}
	
}
