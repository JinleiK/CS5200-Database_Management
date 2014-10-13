package model;

import java.io.Serializable;
import java.lang.String;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;


@Entity

public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String posterImage;
	private Date releaseDate;
	@OneToMany(mappedBy="movieActedIn", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Cast> cast;
	@OneToMany(mappedBy="movieReviewed", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Review> reviews;
	private static final long serialVersionUID = 1L;

	public Movie() {
		super();
	}   

	public Movie(int id, String title, String posterImage, Date releaseDate,
			List<Cast> cast, List<Review> reviews) {
		super();
		this.id = id;
		this.title = title;
		this.posterImage = posterImage;
		this.releaseDate = releaseDate;
		this.cast = cast;
		this.reviews = reviews;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getPosterImage() {
		return this.posterImage;
	}

	public void setPosterImage(String posterImage) {
		this.posterImage = posterImage;
	}   
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Cast> getCast() {
		return cast;
	}

	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
   
}
