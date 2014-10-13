package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void service(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	UserDao userDao = new UserDao();
	String type = request.getParameter("type");
	PrintWriter out = response.getWriter();
	

	if (type.equals("signin")) {
	    username = request.getParameter("username");
	    password = request.getParameter("password");

	    // System.out.println(username);

	    User user = userDao.validUser(username, password);
	    if (user == null) {
		response.sendRedirect("/OSMS/signin/signin.jsp?isValid=invalid");
	    } else {
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		response.sendRedirect("index.jsp");
	    }
	} else if (type.equals("check")) {
	    String newUsername = request.getParameter("newUsername");

	    if (userDao.isExist(newUsername)) {
		out.print(1);
	    } else {
		out.print(0);
	    }
	} else if (type.equals("new")) {
	    User user = newUser(request);
	    int deptId = Integer.parseInt(request.getParameter("dept"));
	    userDao.createUser(deptId, user);
	    response.sendRedirect("employeesInDept.jsp?deptId=" + deptId);
	} else if (type.equals("edit")) {
	    User user = newUser(request);
	    int userId = Integer.parseInt(request.getParameter("userId"));
	    user.setUserId(userId);
	    int deptId = Integer.parseInt(request.getParameter("dept"));
	    userDao.updateUser(userId, deptId, user);
	    response.sendRedirect("employeesInDept.jsp?deptId=" + deptId);
	} else if (type.equals("editProfile")) {
		HttpSession session = request.getSession();
		User login = (User) session.getAttribute("user");
		int userId = login.getUserId();
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		user.setSpeciality(request.getParameter("speciality"));
		user.setUserType(login.getUserType());
		userDao.updateUser(userId, user);
		User newUser = userDao.getUserById(userId);
		session.setAttribute("user", newUser);
		response.sendRedirect("/OSMS/profile.jsp");
	}else if (type.equals("checkPassword")) {
	    username = request.getParameter("username");
	    password = request.getParameter("password");
	    User user = null;
	    user = userDao.validUser(username, password);
	    if (user != null) {
		out.print(1);
	    } else {
		out.print(0);
	    }
	} else if (type.equals("delete")) {
	    int deptId = Integer.parseInt(request.getParameter("deptId"));
	    String ids = request.getParameter("ids");
	    String[] idArray = ids.split(",");
	    for (String id : idArray) {
		userDao.deleteUser(Integer.parseInt(id), deptId);
	    }
	    response.sendRedirect("employeesInDept.jsp?deptId=" + deptId);
	} else if (type.equals("search")) {

	    String username = "";
	    if (request.getParameter("username") != null) {
		username = request.getParameter("username");
	    }

	    String firstName = "";
	    if (request.getParameter("firstname") != null) {
		firstName = request.getParameter("firstname");
	    }

	    String lastName = "";
	    if (request.getParameter("lastname") != null) {
		lastName = request.getParameter("lastname");
	    }

	    String speciality = "";
	    if (request.getParameter("speciality") != null) {
		speciality = request.getParameter("speciality");
	    }

	    int deptId = 0;
	    if (request.getParameter("dept") != null) {
		deptId = Integer.parseInt(request.getParameter("dept"));
	    }

	    String[] userTypes = { "", "", "" };
	    if (request.getParameterValues("position") != null) {
		userTypes = request.getParameterValues("position");
	    }

	    int gender = -1;
	    if (request.getParameter("gender") != null) {
		gender = Integer.parseInt(request.getParameter("gender"));
	    }

	    Set<User> usersSearched = userDao.searchUser(username, firstName,
		    lastName, speciality, deptId, userTypes, gender);
	    
	    
	    request.setAttribute("usersSearched", usersSearched);
	    
	    request.getRequestDispatcher("searchUser.jsp").forward(request,
		    response);
	} 

    }

    public User newUser(HttpServletRequest request) {
	User user = new User();
	user.setUsername(request.getParameter("username"));
	user.setPassword(request.getParameter("password"));
	user.setFirstname(request.getParameter("firstname"));
	user.setLastname(request.getParameter("lastname"));
	user.setGender(Integer.parseInt(request.getParameter("gender")));

	user.setUserType(Integer.parseInt(request.getParameter("position")));
	user.setSpeciality(request.getParameter("speciality"));
	return user;
    }

}
