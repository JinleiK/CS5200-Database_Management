package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="User.getValidUser", query="select user from User user where user.username = :userName and user.password = :password"),
	@NamedQuery(name="User.getUserByUsername", query="select user from User user where user.username = :userName")
})
public class User implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	//0:male; 1:female
	private int gender;
	private String speciality;
	//0:admin; 1: manager; 2: employee
	private int userType;
	@ManyToOne
	@JoinColumn(name="dptId")
	private Department department;
	@OneToMany(mappedBy="applicant")
	private List<Application> applied;
	@OneToMany(mappedBy="watcher")
	private List<Application> watching;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
	
	public User(int userId, String username, String password, String firstname,
			String lastname, int gender, String speciality, int userType,
			Department department, List<Application> applied,
			List<Application> watching) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.speciality = speciality;
		this.userType = userType;
		this.department = department;
		this.applied = applied;
		this.watching = watching;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}   
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}   
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}   
	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}   
	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}   
	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Application> getApplied() {
		return applied;
	}

	public void setApplied(List<Application> applied) {
		this.applied = applied;
	}

	public List<Application> getWatching() {
		return watching;
	}

	public void setWatching(List<Application> watching) {
		this.watching = watching;
	}
   
}
