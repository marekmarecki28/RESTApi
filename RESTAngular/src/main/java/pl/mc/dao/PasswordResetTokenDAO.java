package pl.mc.dao;

import pl.mc.model.PasswordResetToken;
import pl.mc.model.User;

public interface PasswordResetTokenDAO {
	
	PasswordResetToken findByToken(String token);
	 
	PasswordResetToken findByUser(User user);

	void saveOrUpdate(PasswordResetToken myToken);

}
