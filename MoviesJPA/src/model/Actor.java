package model;

import java.io.Serializable;
import java.lang.String;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;


@Entity

public class Actor implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	@OneToMany(mappedBy="actorInMovie", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Cast> moviesIn;
	private static final long serialVersionUID = 1L;

	public Actor() {
		super();
	}  
	
	public Actor(int id, String firstName, String lastName, Date dateOfBirth,
			List<Cast> moviesIn) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.moviesIn = moviesIn;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}   
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<Cast> getMoviesIn() {
		return moviesIn;
	}
	public void setMoviesIn(List<Cast> moviesIn) {
		this.moviesIn = moviesIn;
	}
   
}
