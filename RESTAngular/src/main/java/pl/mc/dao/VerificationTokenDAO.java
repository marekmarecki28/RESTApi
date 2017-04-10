package pl.mc.dao;

import pl.mc.model.User;
import pl.mc.model.VerificationToken;

public interface VerificationTokenDAO {
	
	VerificationToken findByToken(String token);
	 
    VerificationToken findByUser(User user);

	void saveOrUpdate(VerificationToken myToken);

}
