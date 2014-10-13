<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Stock</title>
</head>
<body>
	<%
	    ItemDao itemDao = new ItemDao();
	    List<Item> items = itemDao.getStock();
	%>

	<h2 class="sub-header">Stock</h2>
	<div class="table-responsive">
		<form action="ItemServlet">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Item Name</th>
						<th>Quantity</th>
						<th>Purchase</th>
					</tr>
				</thead>
				<tbody>
					<%
					    for (Item item : items) {
							if (item.getInStock() > 0) {
					%>

					<tr>
						<th><%=item.getItemName()%></th>
						<th><%=item.getInStock()%></th>
						<th><div class="col-xs-2">
								<input type="text" class="form-control" name="addItem" value ="0">
							</div></th>
						<th><div class="col-xs-2">
								<input type="hidden" name="itemId" value=<%=item.getItemId()%>>
							</div></th>
					</tr>


					<%
					    }
					    }
					%>

				</tbody>
			</table>
			<button class="btn btn-info btn-sm" type="submit">Add</button>
		</form>
	</div>
</body>
</html>