<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title></title>
<link href="/OSMS/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<%
	    DepartmentDao dd = new DepartmentDao();
	    List<Department> depts = dd.listAllDep();
	%>
	<h2 class="sub-header">Search User</h2>
	<form onsubmit="return searchUserValidate();" action="UserServlet"
		class="form-inline" role="form">
		<input type="hidden" name="type" value="search">
		<div class="form-group">
			<label class="col-sm-5 control-label">Username</label> <input
				type="text" name="username" class="form-control" id=""
				placeholder="Username">
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">FirstName</label> <input
				type="text" name="firstname" class="form-control" id=""
				placeholder="FirstName">
		</div>
		<div class="form-group">
			<label class="col-sm-5 control-label">LastName</label> <input
				type="text" name="lastname" class="form-control" id=""
				placeholder="LastName"> <br>
		</div>
		<br><br>
		<div class="form-group">
			<label class="col-sm-5 control-label">Speciality</label> <input
				type="text" name="speciality" class="form-control" id=""
				placeholder="Speciality">
		</div>
		<!-- 		<br> <br> -->
		<div class="form-group">
			<label class="col-sm-5 control-label">Department</label>
			<!-- 			<div class="form-group"> -->
			<select class="form-control" name="dept" value="0">
				<option name="dept" value="0">Select Department</option>
				<%
				    for (Department d : depts) {
				%>
				<option name="dept" Id="department" value=<%=d.getDptId()%>><%=d.getDptName()%></option>
				<%
				    }
				%>
			</select>
		</div>
		
		<br><br>
		<div class="form-group">
			<label class="col-sm-5 control-label">Gender</label>
			<div class="col-sm-10">
				<div class="radio-inline">
					<label> <input type="radio" name="gender" id="maleG"
						value="0" checked> Male
					</label>
				</div>

				<div class="radio-inline">
					<label> <input type="radio" name="gender" id="femaleG"
						value="1"> Female
					</label>
				</div>
			</div>
		</div>
	
		<div class="form-group">
			<label class="col-sm-5 control-label">Position</label>
			<div class="col-sm-10">
				<div class="radio-inline">
					<label> <input type="checkbox" name="position" id="manager"
						value="1" checked> Manager
					</label>
				</div>

				<div class="radio-inline">
					<label> <input type="checkbox" name="position" id="employee"
						value="2"> Employee
					</label>
				</div>
			</div>
		</div>
		<br><br>


		<div class="form-group">
			<label class="col-sm-10 control-label"></label>
			<button type="submit" class="btn btn-default" value="submit">Search</button>
		</div>
	</form>
	<br>
	<br>
	<%
	    HashSet<User> usersSearched = (HashSet<User>) request
			    .getAttribute("usersSearched");
	    if (usersSearched != null) {
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
	<%
	    }
	%>
	<!-- Search results -->
</body>
</html>