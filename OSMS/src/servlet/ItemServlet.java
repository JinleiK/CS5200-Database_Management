package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;
import dao.ItemDao;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keywords = request.getParameter("searchKeywords");
		if(keywords != null){
		response.sendRedirect("searchItem.jsp?keywords=" + keywords);
		}
		else{
		    ItemDao id = new ItemDao();
		    String[] itemIds = request.getParameterValues("itemId");
		    String[] addItems = request.getParameterValues("addItem");
		    for(int i = 0; i< itemIds.length; i++){
			System.out.println(itemIds[i]);
			System.out.println(addItems[i]);
			System.out.println("-------");
		    }
		    for(int i = 0; i < itemIds.length; i++){
			int itemId = Integer.parseInt(itemIds[i]);
			Item item = id.getItemById(itemId);
			int newStock= item.getInStock() + Integer.parseInt(addItems[i]);
			System.out.println(newStock);
			
			id.updateItem(itemId, newStock);
		    }
		    response.sendRedirect("stock.jsp");
		    
		}
	}


}
