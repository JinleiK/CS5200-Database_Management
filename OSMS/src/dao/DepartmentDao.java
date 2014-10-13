package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

public class DepartmentDao {
	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("OSMS");

	public DepartmentDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Department> listAllDep() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Department> depts = new ArrayList<Department>();
		Query query = em.createNamedQuery("Dpt.findAll");
		depts = query.getResultList();

		em.getTransaction().commit();
		em.close();
		return depts;
	}

	public Department getDeptById(int deptId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Department d = em.find(Department.class, deptId);

		em.getTransaction().commit();
		em.close();
		return d;
	}

	public List<User> getUsersByDept(int deptId) {
		return getDeptById(deptId).getUsers();
	}

	// public void updateManager(int deptId, int newManagerId) {
	// EntityManager em = factory.createEntityManager();
	// em.getTransaction().begin();
	//
	// Department d = em.find(Department.class, deptId);
	// List<User> users = d.getUsers();
	// for(User user : users){
	// if(user.getUserType() == 1){
	// user.setUserType(2);
	// }
	// if(user.getUserId() == newManagerId){
	// user.setUserType(1);
	// }
	// }
	//
	// em.merge(d);
	// em.getTransaction().commit();
	// em.close();
	// }
}
