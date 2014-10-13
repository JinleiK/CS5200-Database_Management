package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Department
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Dpt.findAll", query="select department from Department department")
})
public class Department implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int dptId;
	private String dptName;
	@OneToMany(mappedBy="department")
	private List<User> users;
	private static final long serialVersionUID = 1L;

	public Department() {
		super();
	}   
	
	public Department(int dptId, String dptName, List<User> users) {
		super();
		this.dptId = dptId;
		this.dptName = dptName;
		this.users = users;
	}

	public int getDptId() {
		return this.dptId;
	}

	public void setDptId(int dptId) {
		this.dptId = dptId;
	}   
	public String getDptName() {
		return this.dptName;
	}

	public void setDptName(String dptName) {
		this.dptName = dptName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
   
}
