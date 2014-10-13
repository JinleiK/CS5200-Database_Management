package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Site;

@Path("/site")
public class SolutionDao {
	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("XSLTJPA");
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("id") int siteId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Site site = null;
		site = em.find(Site.class, siteId);
		
		em.getTransaction().commit();
		em.close();
		return site;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> findAllSites(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		List<Site> sites = new ArrayList<Site>();
		Query query = em.createNamedQuery("Site.findAll");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("id") int siteId, Site site){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site.setId(siteId);
		em.merge(site);
		
		List<Site> sites = new ArrayList<Site>();
		Query query = em.createNamedQuery("Site.findAll");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> removeSite(@PathParam("id") int siteId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Site site = em.find(Site.class, siteId);
		em.remove(site);
		List<Site> sites = new ArrayList<Site>();
		Query query = em.createNamedQuery("Site.findAll");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> createSite(Site site){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(site);
		List<Site> sites = new ArrayList<Site>();
		Query query = em.createNamedQuery("Site.findAll");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return sites;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
