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
	    int appId = Integer
			    .parseInt((String) request.getParameter("appId"));
	    ApplicationDao ad = new ApplicationDao();
	    Application a = ad.getApplicationById(appId);
	%>
	<form action="ApplicationServlet">
		<%-- 	<input type="hidden" name="type" value=<%=actionType %>> --%>
		<h2 class="sub-header" id="">Application Process</h2>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>AppId</th>
						<th>Applicant</th>
						<th>ProposeDate</th>
						<th>ResultDate</th>
						<th>Item</th>
						<th>Quantity</th>
						<th>Description</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<th><%=a.getAppId()%></th>

						<th><%=a.getApplicant().getFirstname()%>&nbsp;&nbsp;<%=a.getApplicant().getLastname()%></th>
						<th><%=a.getProposeDate()%></th>
						<th><%=a.getResultDate()%></th>
						<th><%=a.getItem().getItemName()%></th>
						<th><%=a.getQuantity()%></th>
						<th><%=a.getDescription()%></th>
					</tr>
				</tbody>
			</table>
		</div>
		<br>

		<div class="table-responsive">
			<table class="table table-striped">
				<tr>
					<th><input type="hidden" name="appId"
						value="<%=a.getAppId()%>"></th>
					<th>Approve<input type="radio" name="type" value="approve">&nbsp;&nbsp;
						Reject<input type="radio" name="type" value="reject"></th>
					<th><button type="submit" class="btn btn-primary">Confirm</button></th>
				</tr>
			</table>
		</div>
	</form>

</body>
</html>