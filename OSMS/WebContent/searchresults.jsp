<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
	    HashSet<User> usersSearched = (HashSet<User>) request
			    .getAttribute("usersSearched");
	%>
	<h2 class="sub-header">Search Results</h2>
	<div class="table-responsive">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Username</th>
					<th>Firstname</th>
					<th>Lastname</th>
					<th>Gender</th>
					<th>Department</th>
					<th>Position</th>
					<th>Speciality</th>
				</tr>
			</thead>
			<tbody>
				<%
				    for (User u : usersSearched) {
				%>
				<tr>
					<th><%=u.getUsername()%></th>
					<th><%=u.getFirstname()%></th>
					<th><%=u.getLastname()%></th>
					<th><%=(u.getGender() == 0 ? "Male" : "Female")%></th>
					<th><%=u.getDepartment().getDptName()%></th>
					<th><%=(u.getUserType() == 1 ? "Manager" : "Employee")%></th>
					<th><%=u.getSpeciality()%></th>
				</tr>
				<%
				    }
				%>

			</tbody>

		</table>
	</div>
</body>
</html>