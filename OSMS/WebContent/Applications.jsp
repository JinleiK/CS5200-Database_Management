<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<%
    User user = (User) session.getAttribute("user");
    int userId = user.getUserId();
    int userType = user.getUserType();
    int appStatus = Integer.parseInt(request.getParameter("appStatus"));
    String applistByStatus = null;
    if (appStatus == 0) {
		applistByStatus = "Pending Applications";
    } else if (appStatus == 1) {
		applistByStatus = "Approved Applications";
    } else if (appStatus == -1) {
		applistByStatus = "Rejected Applications";
    } else if (appStatus == 5) {
		applistByStatus = "Latest 5 Applications";
    } else if (appStatus == 6) {
		applistByStatus = "My Requests";
    }

    TreeSet<Application> apps = null;
    ApplicationDao ad = new ApplicationDao();
    if (appStatus == 5 || appStatus == 6) {
		apps = ad.checkApplicationStatus(userId, appStatus);
    } else {
		if (user.getUserType() == 1) {
		    int deptId = user.getDepartment().getDptId();
		    apps = ad.checkApplicationsByDptId(deptId, appStatus);

		} else if (user.getUserType() == 2) {
		    apps = ad.checkApplicationStatus(userId, appStatus);
		}
    }
%>
<title>applistByStatus</title>
</head>
<body>

	<h2 class="sub-header" id=""><%=applistByStatus%></h2>

	<!--  <form action="UserServlet" method="get"> -->
	<input type="hidden" name="dptId" id="dptId" value="">
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>AppId</th>
					<th>Applicant</th>
					<th>ProposeDate</th>
					<%if(appStatus != 6) {%>
					<th>ResultDate</th>
					<%} %>
					<th>Item</th>
					<th>Quantity</th>
					<%
					    if (appStatus == 0 && user.getUserType() == 1) {
					%>
					<th>Process</th>
					<%
					    }else if(user.getUserType() == 2 && appStatus == 5) {
							%>
							<th>Status</th>
							<%
						    }
					%>
				</tr>
			</thead>
			<tbody>
				<%
				    for (Application a : apps) {
				%>

				<tr>
					<th><%=a.getAppId()%></th>
					<th><%=a.getApplicant().getFirstname()%>&nbsp;&nbsp;<%=a.getApplicant().getLastname()%></th>
					<th><%=a.getProposeDate()%></th>
					<%if(appStatus != 6) {%>
					<th><%=a.getResultDate() != null? a.getResultDate() : "N/A"%></th>
					<%} %>
					<%String itemName = a.getItem().getItemName(); %>
					<th><%=itemName.length() > 40 ? (itemName.substring(0, 39) + "...") : itemName%></th>
					<th><%=a.getQuantity()%></th>
					<%
					    if (appStatus == 0 && user.getUserType() == 1) {
					%>
					<th><button class="btn btn-info btn-sm"
							onclick="location.href='processApplication.jsp?actionType=process&appId=<%=a.getAppId()%>'">Process</button></th>
					<%}else if(user.getUserType() == 2 && appStatus == 5) {
						%>
						<th><%=a.getAppStatus() == 0 ? "pending" : (a.getAppStatus() == 1 ? "approved" : "rejected") %></th>
						<%
					    }
					%>
				</tr>

				<%
				    }
				%>

			</tbody>
		</table>
	</div>


</body>
</html>