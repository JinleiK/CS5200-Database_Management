<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Welcome</title>
</head>
<%
	User user = (User) session.getAttribute("user");
	int userType = user.getUserType();
	int userId = user.getUserId();
	
	DepartmentDao dd = new DepartmentDao();
	List<Department> departments = dd.listAllDep();
	
	ApplicationDao ad = new ApplicationDao();
	String p0, p1, p2, p3, p4;
	p0 = p1 = p2 = p3 = p4 = "";
	String a1, a2, a3, a4;
	a1 = a2 = a3 = a4 = "/OSMS/";
	int deptId = 0;
	TreeSet<Application> apps = null;
	switch (userType) {
	case 0:
		p0 = "Here are all the departments in the system.";
		p1 = "To create a new user to the system.";
		p2 = "To search an exist user in the system.";
		p3 = "To check the quantiies of items in stock.";
		if(departments.isEmpty()){
			p0 = "Opps! There are no departments in the system. Please create some in the database.";
		}
		a1 += "editUser.jsp?actionType=new";
		a2 += "searchUser.jsp?actiontype=search";
		a3 += "stock.jsp";
		break;
	case 1:
		p0 = "Here are the pending applications from your employees that are waiting for you to process.";
		p1 = "To see all your employees in your department";
		p2 = "To check your own requests for items.";
		p3 = "To check all applications that you have approved.";
		p4 = "To check all applications that you have rejected.";
		deptId = user.getDepartment().getDptId();
		a1 += "employeesInDept.jsp?deptId=" + deptId;
		a2 += "Applications.jsp?appStatus=6";
		a3 += "Applications.jsp?appStatus=1";
		a4 += "Applications.jsp?appStatus=-1";
		apps = ad.checkApplicationsByDptId(deptId, 0);
		if (apps.isEmpty()) {
			p0 = "Good Job! You have no pending applications to process.";
		}
		break;
	case 2:
		p0 = "Here are the latest 5 applications that you submitted.";
		p1 = "To check all your pending applications.";
		p2 = "To check all your approved applications.";
		p3 = "To check all your rejected applications.";
		a1 += "Applications.jsp?appStatus=0";
		a2 += "Applications.jsp?appStatus=1";
		a3 += "Applications.jsp?appStatus=-1";
		apps = ad.checkApplicationStatus(userId, 5);
		if (apps.isEmpty()) {
			p0 = "You haven't submitted any applications yet.";
		}
		break;
	}
%>
<body>
<div>
<div class="jumbotron">
      <div class="container">
      	<h1 class="sub-header">Welcome!</h1>
      	<p><%=p0 %></p>
      	<% if(apps != null && !apps.isEmpty()){%>
		<div class="table-responsive">
		<table class="table table-white">
		<thead>
				<tr>
					<th>AppId</th>
					<th>Applicant</th>
					<th>ProposeDate</th>
					<th>ResultDate</th>
					<th>Item</th>
					<th>Quantity</th>
					<%
					    if (user.getUserType() == 1) {
					%>
					<th>Process</th>
					<%
					    } else if(user.getUserType() == 2) {
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
					<th><%=a.getProposeDate() %></th>
					<th><%=a.getResultDate() != null? a.getResultDate() : "N/A"%></th>
					<%String itemName = a.getItem().getItemName(); %>
					<th><%=itemName.length() > 30 ? (itemName.substring(0, 29) + "...") : itemName%></th>
					<th><%=a.getQuantity()%></th>
					<%
					    if (user.getUserType() == 1) {
					%>
					<th><button class="btn btn-info btn-sm"
							onclick="location.href='processApplication.jsp?actionType=process&appId=<%=a.getAppId()%>'">Process</button></th>
					<% } else if(user.getUserType() == 2) {
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
<%} else if(!departments.isEmpty() && userType == 0){%>
		<div class="table-responsive">
		<table class="table table-white">
		<thead>
				<tr>
					<th>DeptId</th>
					<th>Department</th>
					<th># of employees</th>
					<th>See more</th>
				</tr>
			</thead>
			
			
			<tbody>
				<%
				    for (Department d : departments) {
				%>

				<tr>
					<th><%=d.getDptId()%></th>
					<th><%=d.getDptName()%></th>
					<th><%=d.getUsers().size() %></th>
					<th><button class="btn btn-info btn-sm"
							onclick="location.href='employeesInDept.jsp?deptId=<%=d.getDptId()%>'">Detail</button></th>
				
				</tr>

				<%
				    }
				%>

			</tbody>
		</table>
		</div>
<%} %>
      </div>
      </div>
      
      <div class="container marketing">
      <div class="row">
	<div class="col-lg-3">
		<p><%=p1 %></p>
		<p><a class="btn btn-default" href=<%=a1 %>>View</a></p>
	</div>
	<div class="col-lg-3">
		<p><%=p2 %></p>
		<p><a class="btn btn-default" href=<%=a2 %>>View</a></p>
	</div>
	<div class="col-lg-3">
		<p><%=p3 %></p>
		<p><a class="btn btn-default" href=<%=a3 %>>View</a></p>
	</div>
	<%if(userType == 1){ %>
	<div class="col-lg-3">
		<p><%=p4 %></p>
		<p><a class="btn btn-default" href=<%=a4 %>>View</a></p>
	</div>
	<%} %>
</div>
      
      </div>
</div>

</body>
</html>