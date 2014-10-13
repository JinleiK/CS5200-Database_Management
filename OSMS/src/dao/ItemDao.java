package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class ItemDao {

	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("OSMS");

	public ItemDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getStock() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Item> items = new ArrayList<Item>();
		Query query = em.createNamedQuery("Item.getStock");
		items = query.getResultList();
		List<Item> stock = new ArrayList<Item>();
		for(Item i : items){
			if(i.getAmazonId() == null){
				stock.add(i);
			}
		}

		em.getTransaction().commit();
		em.close();
		return stock;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getAmazonStock() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Item> items = new ArrayList<Item>();
		Query query = em.createNamedQuery("Item.getStock");
		items = query.getResultList();
		List<Item> stock = new ArrayList<Item>();
		for(Item i : items){
			if(i.getAmazonId() != null){
				stock.add(i);
			}
		}

		em.getTransaction().commit();
		em.close();
		return stock;
	}

	public Item getItemById(int itemId) {
		Item item = null;

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		item = em.find(Item.class, itemId);

		em.getTransaction().commit();
		em.close();

		return item;
	}

	public void createItem(Item item) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(item);

		em.getTransaction().commit();
		em.close();
	}

	public void updateItem(int itemId, int quantity) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Item item = em.find(Item.class, itemId);
		item.setInStock(quantity);
		em.merge(item);

		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemByName(String keyword) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Item> items = new ArrayList<Item>();
		Query query = em.createNamedQuery("Item.getItemByName");
		String para = "%" + keyword + "%";
		query.setParameter("itemName", para);
		items = query.getResultList();

		List<Item> validItems = new ArrayList<Item>();
		for (Item i : items) {
			if (i.getInStock() > 0) {
				validItems.add(i);
			}
		}

		em.getTransaction().commit();
		em.close();
		//System.out.println(validItems.size());
		return validItems;
	}

}
