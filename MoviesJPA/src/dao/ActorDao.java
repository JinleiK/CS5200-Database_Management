package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class ActorDao {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("MoviesJPA");
	
	public void createActor(Actor newActor){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(newActor);
		
		em.getTransaction().commit();
		em.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Actor> getAllActors(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("Select actor from Actor actor");
		List<Actor> actors = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return actors;
	}
	
	public Actor getActor(int actorId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Actor actor = em.find(Actor.class, actorId);
		
		em.getTransaction().commit();
		em.close();
		return actor;
	}
	
	public List<Cast> getCastForActor(int actorId){
		return getActor(actorId).getMoviesIn();
	}
	
	public List<Movie> getMoviesForActor(int actorId){
		List<Movie> movies = new ArrayList<Movie>();
		List<Cast> cast = getCastForActor(actorId);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		for(Cast c : cast){
			Movie mv = em.find(Movie.class, c.getMovieActedIn().getId());
			movies.add(mv);
		}
		
		em.getTransaction().commit();
		em.close();
		return movies;
	}

}
