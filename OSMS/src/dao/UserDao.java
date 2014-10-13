package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Department;
import model.User;

public class UserDao {

	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("OSMS");

	public UserDao() {
		super();
	}

	public User validUser(String username, String password) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		User user = null;
		Query query = em.createNamedQuery("User.getValidUser");
		query.setParameter("userName", username);
		query.setParameter("password", password);
		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {

		}

		em.getTransaction().commit();
		em.close();
		return user;
	}

	public boolean isExist(String username) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		User user = null;
		Query query = em.createNamedQuery("User.getUserByUsername");
		query.setParameter("userName", username);
		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {
			return false;
		}

		em.getTransaction().commit();
		em.close();
		return (user == null) ? false : true;
	}

	public void createUser(int dptId, User user) {
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

	public User getUserById(int userId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		User user = em.find(User.class, userId);

		em.getTransaction().commit();
		em.close();
		return user;
	}

	public void updateUser(int userId, int deptId, User newUser) {
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

	public void updateUser(int userId, User newUser) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		User user = em.find(User.class, userId);
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());
		user.setFirstname(newUser.getFirstname());
		user.setLastname(newUser.getLastname());
		user.setGender(newUser.getGender());
		user.setSpeciality(newUser.getSpeciality());

		em.merge(user);

		em.getTransaction().commit();
		em.close();
	}

	public void deleteUser(int userId, int deptId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		User user = em.find(User.class, userId);

		Department d = em.find(Department.class, deptId);
		d.getUsers().remove(user);
		em.remove(user);
		em.merge(d);

		em.getTransaction().commit();
		em.close();
	}

	public Set<User> searchUser(String username, String firstName,
			String lastName, String speciality, int deptId, String[] userTypes,
			int gender) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		DepartmentDao departmentDao = new DepartmentDao();
		Department department = null;
		department = departmentDao.getDeptById(deptId);
		List<User> users = new ArrayList<User>();
		Set<User> usersSearched = new HashSet<User>();
		Set<User> delUsers = new HashSet<User>();
		// filt by department
		if (department != null) {
			users = department.getUsers();
		} else {
			List<Department> departments = departmentDao.listAllDep();
			for (Department dept : departments) {
				List<User> usersInDepartment = dept.getUsers();
				for (User user : usersInDepartment) {
					users.add(user);
				}
			}
		}
		for (User user : users) {
			usersSearched.add(user);
		}
		// further filt by other conditions
		if (!"".equals(username)) {
			for (User user : usersSearched) {
				if (!username.equals(user.getUsername())) {
					delUsers.add(user);
				}
			}
		}

		if (!"".equals(firstName)) {
			for (User user : usersSearched) {
				if (!firstName.equals(user.getFirstname())) {
					delUsers.add(user);
				}
			}
		}

		if (!"".equals(lastName)) {
			for (User user : usersSearched) {
				if (!lastName.equals(user.getLastname())) {
					delUsers.add(user);
				}
			}
		}

		if (!"".equals(speciality)) {
			for (User user : usersSearched) {
				if (!speciality.equals(user.getSpeciality())) {
					delUsers.add(user);
				}
			}
		}

		if (userTypes.length == 1) {
			int userType = Integer.parseInt(userTypes[0]);
			for (User user : usersSearched) {
				if (user.getUserType() != userType) {
					delUsers.add(user);
				}
			}
		}

		for (User user : usersSearched) {
			if (user.getGender() != gender) {
				delUsers.add(user);
			}
		}
		usersSearched.removeAll(delUsers);
		em.getTransaction().commit();
		em.close();
		return usersSearched;
	}
}
