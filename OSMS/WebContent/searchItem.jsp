<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*, util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Search Item</title>

</head>
<body>
	<% String keywords = request.getParameter("keywords");
	ItemDao itemDao = new ItemDao();
	List<Item> items = itemDao.getItemByName(keywords);
	AmazonService as = new AmazonService();
	List<Product> products = new ArrayList<Product>();
	if(as.ItemSearch(keywords).isEmpty()){
	    out.print("No such items!");
	} else{
		products = as.ItemSearch(keywords);
	}
%>

	<h2 class="sub-header">Search results</h2>
	
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<!--                   <th><input type="checkbox" name="checkAllHandler" id="checkAllHandler" onclick="checkAll()"></th> -->
					<th>Name</th>
					<th>Stock</th>
					<th>Apply</th>
				</tr>
			</thead>
			<tbody>
				<%for(Item item : items){%>
				<tr>
					<%--                   <th> <input type="checkbox" name="userIdCheckbox" id=<%=.getUserId() %> value=<%=u.getUserId() %>> </th> --%>
					<th><%=item.getItemName() %></th>
					<th><%=item.getInStock() %></th>
					<th><button class="btn btn-info btn-sm" onclick="location.href = 'applyItem.jsp?itemId=<%=item.getItemId()%>'">Apply</button></th>
				</tr>
				<%} %>

			</tbody>
		</table>
	</div>
	
	<div class="form-group">
	<%if(items.isEmpty()){ %>
	<h4>No such items in stock, display search results on Amazon?</h4>
	<%} else{%>
	<h4>Want to see more search results on Amazon?</h4>
	<%} %>
	<button class="btn btn-success" onclick="displayProducts();">Display</button>
	</div>
	<div class="table-responsive">
		<table class="table table-striped" id="resultsOnAmazon">
			<thead>
				<tr>
					<!--                   <th><input type="checkbox" name="checkAllHandler" id="checkAllHandler" onclick="checkAll()"></th> -->
					<th>Title</th>
					<th>Brand</th>
					<th>Price</th>
					<th>Sales Rank</th>
					<th>Apply</th>
				</tr>
			</thead>
			<tbody>
				<%for(Product p : products){%>
				<tr>
					<%--                   <th> <input type="checkbox" name="userIdCheckbox" id=<%=.getUserId() %> value=<%=u.getUserId() %>> </th> --%>
					<th><a href="/OSMS/productDetail.jsp?asin=<%=p.getASIN()%>"><%=p.getTitle() %></a></th>
					<th><%=p.getBrand() %></th>
					<th><%=p.getFormattedPrice() %></th>
					<th><%=p.getSalesRank() %></th>
					<th><button class="btn btn-info btn-sm" onclick="location.href = 'applyItem.jsp?ASIN=<%=p.getASIN()%>&title=<%=p.getTitle()%>'">Apply</button></th>
				</tr>

				<%} %>

			</tbody>
		</table>
	</div>
</body>
</html>