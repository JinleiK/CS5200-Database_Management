package model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import model.Movie;
import model.User;


@Entity

public class Review implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String comment;
	private int stars;
	@Temporal(TemporalType.DATE)
	private Date dateCommented;
	@ManyToOne
	@JoinColumn(name="movieId")
	private Movie movieReviewed;
	@ManyToOne
	@JoinColumn(name="userId")
	private User reviewer;
	private static final long serialVersionUID = 1L;

	public Review() {
		super();
	} 
	
	public Review(int id, String comment, int stars, Date dateCommented,
			Movie movieReviewed, User reviewer) {
		super();
		this.id = id;
		this.comment = comment;
		this.stars = stars;
		this.dateCommented = dateCommented;
		this.movieReviewed = movieReviewed;
		this.reviewer = reviewer;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}   
	public int getStars() {
		return this.stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}   
	public Date getDateCommented() {
		return this.dateCommented;
	}

	public void setDateCommented(Date dateCommented) {
		this.dateCommented = dateCommented;
	}   
	public Movie getMovieReviewed() {
		return this.movieReviewed;
	}

	public void setMovieReviewed(Movie movieReviewed) {
		this.movieReviewed = movieReviewed;
	}   
	public User getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}
   
}
