package pl.mc.dao;

import pl.mc.model.UserAttempts;

public interface UserAttemptsDAO {
	
	void updateFailAttempts(String username);
	void resetFailAttempts(String username);
	UserAttempts getUserAttempts(String username);

}
