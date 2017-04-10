package pl.mc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_attempts")
public class UserAttempts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "attemptId", unique = true, nullable = false)
	private int attemptId;
	private String username;
	private int attempts;
	private Date lastModified;
	
	public int getAttemptId() {
		return attemptId;
	}
	
	public void setAttemptId(int attemptId) {
		this.attemptId = attemptId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
