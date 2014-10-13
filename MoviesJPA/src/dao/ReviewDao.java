package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Movie;
import model.Review;
import model.User;

public class ReviewDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("MoviesJPA");
	
	public void createReview(int userId, int movieId, Review review){
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
	
	public Review getReview(int reviewId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Review review = em.find(Review.class, reviewId);
		
		em.getTransaction().commit();
		em.close();
		return review;
	}
	
	public void updateReview(int reviewId, Review review){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Review rev = em.find(Review.class, reviewId);

		rev.setComment(review.getComment());
		rev.setStars(review.getStars());
		rev.setDateCommented(review.getDateCommented());
		rev.setMovieReviewed(review.getMovieReviewed());
		rev.setReviewer(review.getReviewer());
		em.merge(rev);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteReview(int reviewId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Review rev = em.find(Review.class, reviewId);
		
		//delete review in User
		User user = em.find(User.class, rev.getReviewer().getId());
		user.getReviews().remove(rev);
		em.merge(user);
	
		//delete review in Movie
		Movie movie = em.find(Movie.class, rev.getMovieReviewed().getId());
		movie.getReviews().remove(rev);
		em.merge(movie);
		
		//delete review
		em.remove(rev);
		
		em.getTransaction().commit();
		em.close();
	}
	
}
