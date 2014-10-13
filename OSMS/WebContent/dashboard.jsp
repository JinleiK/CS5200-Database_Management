<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<meta http-equiv="PRAGMA" content="NO-CACHE" />
<meta http-equiv="EXPIRES" content="0" />
<title>OSMS</title>
<!-- Bootstrap core CSS -->
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/OSMS/css/dashboard.css" rel="stylesheet">


</head>
<body>

	<%
	    User user = (User) session.getAttribute("user");
	    int userType = user.getUserType();
	    DepartmentDao dd = new DepartmentDao();
	    List<Department> depts = dd.listAllDep();
	%>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Office Supplies Management
					System</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="profile.jsp">Profile</a></li>
					<li><a href="/OSMS/signin/signin.jsp?isValid=nolongerValid">Sign
							out</a></li>
				</ul>
				<form class="navbar-form navbar-right" action="ItemServlet"
					method="get">
					<%
					    if (user.getUserType() != 0) {
					%>
					<input type="text" class="form-control" name="searchKeywords"
						placeholder="Search item...">
					<%
					    }
					%>
				</form>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="index.jsp">Overview</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<%
					    if (userType == 0) {
					%>
					<li><a href="editUser.jsp?actionType=new">Add User</a></li>
					<li><a href="searchUser.jsp?actiontype=search">Search
							User</a></li>
					<%
					    }
					    if (userType == 1) {
							for (Department d : depts) {
							    if (d.getDptId() == user.getDepartment().getDptId()) {
					%>
					<li><a
						href="employeesInDept.jsp?deptId=<%=d.getDptId()%>&deptName=<%=d.getDptName()%>">Employees</a></li>
					<%
					    }
							}
					%>
					<li><a href="Applications.jsp?appStatus=6">My Requests</a></li>
					<%
					    }
					    if (userType == 2) {
					%>
					<li><a href="Applications.jsp?appStatus=5">Latest 5
							applications</a></li>
					<%
					    }
					%>
				</ul>
				<ul class="nav nav-sidebar">
					<%
					    if (userType == 0) {
							for (Department d : depts) {
					%>
					<li><a
						href="employeesInDept.jsp?deptId=<%=d.getDptId()%>&deptName=<%=d.getDptName()%>"><%=d.getDptName()%></a></li>
					<%
					    }
					    }
					    else {
					%>
					<li><a href="Applications.jsp?appStatus=0">Pending
							Applications</a></li>
					<li><a href="Applications.jsp?appStatus=1">Approved
							Applications</a></li>
					<li><a href="Applications.jsp?appStatus=-1">Rejected
							Applications</a></li>
					<%
					    }
				
					%>
				</ul>
				<%
				    if (userType == 0) {
				%>
				<ul class="nav nav-sidebar">
					<li><a href="stock.jsp">Stock</a>
				</ul>
				<%
				    }
				%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<decorator:body />
			</div>
		</div>
	</div>
<br/>
	<div id="footer">
		<div class="container_footer">
			<p class="text-muted">Copyright 2014, Jinlei Kuang & Lianzheng
				Nie. All rights reserved.</p>
		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/OSMS/js/dashboard.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<!--      <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/docs.min.js"></script> -->
</body>
</html>