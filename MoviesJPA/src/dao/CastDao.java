package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.*;

public class CastDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("MoviesJPA");
	
	public void createCast(int actorId, int movieId, Cast cast){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(cast);
		
		Actor actor = em.find(Actor.class, actorId);
		Movie mv = em.find(Movie.class, movieId);
		
		actor.getMoviesIn().add(cast);
		em.merge(actor);
		
		mv.getCast().add(cast);
		em.merge(mv);
		
		cast.setActorInMovie(actor);
		cast.setMovieActedIn(mv);
		em.merge(cast);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public Cast getCast(int castId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Cast cast = em.find(Cast.class, castId);
		
		em.getTransaction().commit();
		em.close();
		return cast;
	}
	
	public void changeCharacterForCast(int castId, String newCharacter){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Cast cast = em.find(Cast.class, castId);
		cast.setCharacterName(newCharacter);
		em.merge(cast);
		
		em.getTransaction().commit();
		em.close();
	}

}
