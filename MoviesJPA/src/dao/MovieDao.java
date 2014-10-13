package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class MovieDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("MoviesJPA");
	
	@SuppressWarnings("unchecked")
	public List<Movie> listAllMovies(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		List<Movie> movies = new ArrayList<Movie>();
		Query query = em.createQuery("select movie from Movie movie");
		movies = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return movies;
	}
	
	public Movie getMovie(int movieId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Movie mv = em.find(Movie.class, movieId);
		
		em.getTransaction().commit();
		em.close();
		return mv;
	}
	
	public List<Review> getReviewsForMovie(int movieId){
		return getMovie(movieId).getReviews();
	}
	
	public List<Cast> getCastForMovie(int movieId){
		return getMovie(movieId).getCast();
	}

}
