package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.*;

public class ApplicationDao {

	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("OSMS");

	public ApplicationDao() {
		super();
	}

	public Application getApplicationById(int appId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Application application = em.find(Application.class, appId);

		em.getTransaction().commit();
		em.close();
		return application;
	}

	// Apply for items
	public void createApplication(Application application, int itemId,
			int quantity, int userId, String description, int itemSource) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Item item = em.find(Item.class, itemId);
		User applicant = em.find(User.class, userId);

		application.setItem(item);
		application.setQuantity(quantity);
		// todo :set propose date
		Calendar cal = Calendar.getInstance();
		application.setProposeDate(new java.sql.Date(cal.getTime().getTime()));
		if (applicant.getUserType() == 1) {
			application.setAppStatus(1);
		} else {
			application.setAppStatus(0);
		}
		application.setApplicant(applicant);
		application.setDescription(description);
		application.setItemSource(itemSource);

		item.getApplications().add(application);
		applicant.getApplied().add(application);

		em.persist(application);
		em.merge(item);
		em.merge(applicant);

		em.getTransaction().commit();
		em.close();
	}

	// list the applicaitons by appId descending according to specific
	// check all my requests(employees)
	public TreeSet<Application> checkApplicationStatus(int userId, int appStatus) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Application> results = null;
		TreeSet<Application> resultsPending = new TreeSet<Application>(
				new MyComparator());
		TreeSet<Application> resultsApproved = new TreeSet<Application>(
				new MyComparator());
		TreeSet<Application> resultsRejected = new TreeSet<Application>(
				new MyComparator());
		TreeSet<Application> resultsAll = new TreeSet<Application>(
				new MyComparator());

		User user = em.find(User.class, userId);
		results = user.getApplied();

		for (Application app : results) {

			if (app.getAppStatus() == 0) {
				resultsPending.add(app);
			}

			else if (app.getAppStatus() == 1) {
				resultsApproved.add(app);
			}

			else if (app.getAppStatus() == -1) {
				resultsRejected.add(app);
			}
		}

		if (appStatus == 0) {
			return resultsPending;
		}

		else if (appStatus == 1) {
			return resultsApproved;
		}

		else if (appStatus == -1) {
			return resultsRejected;
		} else if (appStatus == 6) {
			TreeSet<Application> resultsAllTemporary = new TreeSet<Application>(
					new MyComparator());
			for (Application a : results) {
				resultsAllTemporary.add(a);
			}
			for (Application a : resultsAllTemporary) {
				resultsAll.add(a);
			}
			return resultsAll;
		} else if (appStatus == 5) {
			TreeSet<Application> resultsAllTemporary = new TreeSet<Application>(
					new MyComparator());
			for (Application a : results) {
				resultsAllTemporary.add(a);
			}
			int count = 0;
			for (Application a : resultsAllTemporary) {
				resultsAll.add(a);
				count++;
				if (count >= 5) {
					break;
				}
			}
			return resultsAll;
		}
		em.getTransaction().commit();
		em.close();
		return null;
	}

	// List the the department applications (manager)
	public TreeSet<Application> checkApplicationsByDptId(int deptId,
			int appStatus) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		DepartmentDao dd = new DepartmentDao();
		List<User> users = dd.getUsersByDept(deptId);
		List<Application> apps = new ArrayList<Application>();

		TreeSet<Application> resultsPending = new TreeSet<Application>(
				new MyComparator());
		TreeSet<Application> resultsApproved = new TreeSet<Application>(
				new MyComparator());
		TreeSet<Application> resultsRejected = new TreeSet<Application>(
				new MyComparator());

		for (User user : users) {
			List<Application> userApplications = user.getApplied();
			for (Application app : userApplications) {
				apps.add(app);
			}
		}

		for (Application app : apps) {

			if (app.getAppStatus() == 0) {
				resultsPending.add(app);
			}

			else if (app.getAppStatus() == 1) {
				if (app.getApplicant().getUserType() != 1) {
					resultsApproved.add(app);
				}
			} else if (app.getAppStatus() == -1) {
				resultsRejected.add(app);
			}
		}

		if (appStatus == 0) {
			return resultsPending;
		}

		else if (appStatus == 1) {
			return resultsApproved;
		}

		else if (appStatus == -1) {
			return resultsRejected;
		}

		em.getTransaction().commit();
		em.close();
		return null;
	}

	// Comparator to sort the applications by appId descending
	class MyComparator implements Comparator<Application> {
		public int compare(Application app1, Application app2) {
			Integer appId1 = app1.getAppId();
			Integer appId2 = app2.getAppId();

			return appId2.compareTo(appId1);
		}
	}

	// approve or reject applications
	public void processApplication(int appId, int status) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Application application = em.find(Application.class, appId);
		application.setAppStatus(status);
		// todo :set result date
		Calendar cal = Calendar.getInstance();
		application.setResultDate(new java.sql.Date(cal.getTime().getTime()));
		em.merge(application);

		if (status == 1 && application.getItemSource() == 0) {
			Item item = application.getItem();
			int num = application.getQuantity();
			int stock = item.getInStock();
			item.setInStock(stock - num);
			em.merge(item);
		}

		em.getTransaction().commit();
		em.close();
	}

}
