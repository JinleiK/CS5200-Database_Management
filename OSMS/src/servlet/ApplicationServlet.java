package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import model.*;

/**
 * Servlet implementation class ApplicationServlet
 */
@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	ApplicationDao ad = new ApplicationDao();
	String type = request.getParameter("type");

	if (type.equals("approve")) {
	    int appId = Integer.parseInt(request.getParameter("appId"));
	    ad.processApplication(appId, 1);
	    response.sendRedirect("Applications.jsp?appStatus=" + 1);

	} else if (type.equals("reject")) {
	    int appId = Integer.parseInt(request.getParameter("appId"));
	    ad.processApplication(appId, -1);
	    response.sendRedirect("Applications.jsp?appStatus=" + -1);
	} else if (type.equals("create")) {
	    int itemId = Integer.parseInt(request.getParameter("itemId"));
	    int quantity = Integer.parseInt(request
		    .getParameter("applyQuantity"));
	    String description = request.getParameter("description");
	    int userId = Integer.parseInt(request.getParameter("userId"));
	    Application application = new Application();
	    int itemSource = Integer.parseInt(request
		    .getParameter("itemSource"));

	    // System.out.println(itemId);
	    // System.out.println(quantity);
	    // System.out.println(description);
	    // System.out.println(userId);
	    // System.out.println(application);

	    ad.createApplication(application, itemId, quantity, userId,
		    description, itemSource);
	    if (application.getApplicant().getUserType() == 1) {
		response.sendRedirect("Applications.jsp?appStatus="+ 6);
	    } else {
		response.sendRedirect("Applications.jsp?appStatus="+ 0);
	    }
	    
	}
    }
}
