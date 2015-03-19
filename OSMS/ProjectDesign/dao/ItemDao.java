package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class ItemDao {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("OSMS");
	
	public ItemDao(){
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getStock(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		List<Item> items = new ArrayList<Item>();
		Query query = em.createQuery("select item from Item item where item.inStock >= 0");
		items = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		return items;
	}
	
}
