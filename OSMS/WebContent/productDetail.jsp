<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*, util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Product Detail</title>
<%
	String asin = request.getParameter("asin");
	AmazonService as = new AmazonService();
	Product p = as.itemLookup(asin);
%>



</head>
<body>
<body>
<!-- 	<form action="ApplicationServlet" method="get"> -->
		<div class="container-fluid">
			<h3 class="sub-header"><%=p.getTitle() %></h3>
			<div class="productInfo">

				<div class="img_div">
					<img class="product_img" src="<%=p.getSmallImage() %>"
						alt="product image" class="img-thumbnail">
				</div>
				<div class="form-group productDetail">
					<label>Brand: <%=p.getBrand() %></label><br /> <label>Price:
						<%=p.getFormattedPrice() %></label><br /> <label>Sales Rank: <%=p.getSalesRank() %></label><br />
					<label>Total of new products: <%=p.getTotalNew() %></label><br /> <label><a
						href=<%=p.getUrl() %> target="_blank">Click Here</a> to check more
						details on Amazon</label>
				</div>
			</div>

			<h4 class="sub-header">Product Description</h4>
			<p><%=p.getEditorialReview().substring(19) %></p>

			<button class="btn btn-info"
				onclick="location.href = 'applyItem.jsp?ASIN=<%=p.getASIN()%>&title=<%=p.getTitle()%>'">Apply</button>
		</div>

<!-- 	</form> -->
</body>
</body>
</html>