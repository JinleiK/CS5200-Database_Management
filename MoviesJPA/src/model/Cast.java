package model;

import java.io.Serializable;
import java.lang.String;
import java.sql.Date;

import javax.persistence.*;

import model.Actor;
import model.Movie;


@Entity

public class Cast implements Serializable {
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="movieId")
	private Movie movieActedIn;
	@ManyToOne
	@JoinColumn(name="actorId")
	private Actor actorInMovie;
	private String characterName;
	private Date dateActedInMovie;
	private static final long serialVersionUID = 1L;

	public Cast() {
		super();
	}  
	
	public Cast(int id, Movie movieActedIn, Actor actorInMovie,
			String characterName, Date dateActedInMovie) {
		super();
		this.id = id;
		this.movieActedIn = movieActedIn;
		this.actorInMovie = actorInMovie;
		this.characterName = characterName;
		this.dateActedInMovie = dateActedInMovie;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public Movie getMovieActedIn() {
		return this.movieActedIn;
	}

	public void setMovieActedIn(Movie movieActedIn) {
		this.movieActedIn = movieActedIn;
	}   
	public Actor getActorInMovie() {
		return this.actorInMovie;
	}

	public void setActorInMovie(Actor actorInMovie) {
		this.actorInMovie = actorInMovie;
	}   
	public String getCharacterName() {
		return this.characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}   
	public Date getDateActedInMovie() {
		return this.dateActedInMovie;
	}

	public void setDateActedInMovie(Date dateActedInMovie) {
		this.dateActedInMovie = dateActedInMovie;
	}
   
}
