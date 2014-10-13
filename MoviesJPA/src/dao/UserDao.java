package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.*;

public class UserDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("MoviesJPA");
	
	public void createUser(User newUser){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(newUser);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public User getUser(int userId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = em.find(User.class, userId);
		
		em.getTransaction().commit();
		em.close();
		return user;
	}
	
	public List<Review> getReviewsForUser(int userId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = em.find(User.class, userId);
		List<Review> reviews = user.getReviews();
		
		em.getTransaction().commit();
		em.close();
		return reviews;
	}
	
	public void addReviewForMovie(int userId, int movieId, Review review){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(review);
		
		User user = em.find(User.class, userId);
		Movie movie = em.find(Movie.class, movieId);
		
		review.setReviewer(user);
		review.setMovieReviewed(movie);
		em.merge(review);
		
		user.getReviews().add(review);
		em.merge(user);
		
		movie.getReviews().add(review);
		em.merge(movie);
		
		em.getTransaction().commit();
		em.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		UserDao ud = new UserDao();
//		User user = new User();
//		user.setUsername("aaa");
//		user.setPassword("aaa");
//		user.setFirstName("Alice");
//		user.setLastName("Wonderland");
//		user.setEmail("aaa@gmail.com");
//		
//		Calendar cal = Calendar.getInstance();
//		user.setDateOfBirth(new java.sql.Date(cal.getTime().getTime()));
//		ud.createUser(user);
		
		Review rv = new Review();
		rv.setId(1);
		rv.setComment("asdfg");
		Calendar cal = Calendar.getInstance();
		//rv.setDateCommented(new java.sql.Date(cal.getTime().getTime()));
		rv.setDateCommented(new java.util.Date(cal.getTime().getTime()));
		rv.setStars(2);
		User user = new User();
		user.setId(1);
		user.setUsername("aaa");
		user.setPassword("aaa");
		user.setFirstName("Alice");
		user.setLastName("Wonderland");
		user.setEmail("aaa@gmail.com");
		rv.setReviewer(user);
		rv.setMovieReviewed(null);
		
		ReviewDao rd = new ReviewDao();
		rd.updateReview(3, rv);
	}

}
