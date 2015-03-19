package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class UserDao {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("OSMS");
	
	public UserDao(){
		super();
	}
	
	public User validUser(String username, String password){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = null;
		Query query = em.createQuery("select user from User user where user.username = :userName and user.password = :password");
		query.setParameter("userName", username);
		query.setParameter("password", password);
		try{
			user = (User) query.getSingleResult();
		} catch(Exception e){
			
		}
		
		em.getTransaction().commit();
		em.close();
		return user;
	}
	
	public boolean isExist(String username){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = null;
		Query query = em.createQuery("select user from User user where user.username = :userName");
		query.setParameter("userName", username);
		try{
			user = (User) query.getSingleResult();
		} catch(Exception e){
			return false;
		}
		
		em.getTransaction().commit();
		em.close();
		return (user == null) ? false : true;
	}
	
	public void createUser(int dptId, User user){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(user);
		
		Department dpt = em.find(Department.class, dptId);
		dpt.getUsers().add(user);
		em.merge(dpt);
		
		user.setDepartment(dpt);
		em.merge(user);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public User getUserById(int userId){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = em.find(User.class, userId);
		
		em.getTransaction().commit();
		em.close();
		return user;
	}
	
	public void updateUser(int userId, int deptId, User newUser){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		User user = em.find(User.class, userId);
		Department d0 = user.getDepartment();
		d0.getUsers().remove(user);
		em.merge(d0);
		
		Department d = em.find(Department.class, deptId);
		
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());
		user.setFirstname(newUser.getFirstname());
		user.setLastname(newUser.getLastname());
		user.setGender(newUser.getGender());
		user.setDepartment(d);
		user.setUserType(newUser.getUserType());
		user.setSpeciality(newUser.getSpeciality());
		em.merge(user);
		
		d.getUsers().add(user);
		em.merge(d);
		
		em.getTransaction().commit();
		em.close();
	}
	
}
