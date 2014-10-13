<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title></title>
<link href="css/bootstrap.min.css" rel="stylesheet">


</head>
<body>
	<%
	    User user = (User) session.getAttribute("user");
	    String itemGet = request.getParameter("itemId");
	    String amazonId = request.getParameter("ASIN");
	    String itemName = request.getParameter("title");
// 	    System.out.println(amazonId);
	    ItemDao id = new ItemDao();
	    ApplicationDao ad = new ApplicationDao();
	    Item item = new Item();
	    int flag = 0;
	    if (itemGet != null) {
			int itemId = Integer.parseInt(itemGet);
			item = id.getItemById(itemId);
	    } else {
			List<Item> items = id.getAmazonStock();
			for (Item i : items) {
			    if (i.getAmazonId().equals(amazonId)) {
				item = i;
				flag = 1;
				break;
			    }
			}
			System.out.println(flag);
			if (flag == 0) {
			    item.setAmazonId(amazonId);
			    item.setItemName(itemName);
			    id.createItem(item);
			}
	    }
	%>

	<h2 class="sub-header">Application Fillin</h2>
	<form class="form-horizontal" action="ApplicationServlet">
		<input type="hidden" name="type" value="create"> <input
			type="hidden" name="userId" value=<%=user.getUserId()%>> <input
			type="hidden" name="itemId" value=<%=item.getItemId()%>> <input
			type="hidden" name="itemSource"
			value=<%=itemGet != null ? "0" : "1"%>>

		<div class="form-group">
			<h4 class="sub-header"><%=item.getItemName()%></h4>

		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">applyQuantity</label>
			<div class="col-sm-3">
				<input type="text" class="form-control"
					name="applyQuantity" placeholder="applyQuantity">
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">description</label>
			<div class="col-sm-3">
				<input type="text" class="form-control"
					name="description" placeholder="description">
			</div>
		</div>
		<br>

		<div class="form-group">
			<div class="col-sm-3">
				<button type="submit" class="btn btn-default" value="apply">Apply</button>
			</div>
		</div>

	</form>

</body>
</html>