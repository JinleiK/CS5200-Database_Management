<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<title>Profile</title>
</head>
<body>

<%
	User user = (User) session.getAttribute("user");
	String position = "";
	int userType = user.getUserType();
	switch(userType){
	case 0: position = "Administrator"; break;
	case 1: position = "Manager"; break;
	case 2: position = "Employee"; break;
	}
%>
<h2 class="sub-header">Profile</h2>
<div class="form-horizontal">
<div class="form-group">
			<label class="col-sm-2 control-label">Username :</label>
			<div class="col-sm-3">
				<label class="col-sm-2 control-label"><%=user.getUsername() %></label>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">Full name :</label>
			<div class="col-sm-4">
				<label class="col-sm-5 control-label" style="text-align:left"><%=user.getFirstname() + " " + user.getLastname() %> </label>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">Gender :</label>
			<div class="col-sm-3">
				<label class="col-sm-2 control-label"><%=(user.getGender() == 0) ? "Male" : "Female"%></label>
			</div>
		</div>
		<%if(!position.equals("Administrator")){ %>
		<div class="form-group">
			<label class="col-sm-2 control-label">Department :</label>
			<div class="col-sm-3">
				<label class="col-sm-2 control-label"><%=user.getDepartment().getDptName() %> </label>
			</div>
		</div>
		<%} %>
		
		<div class="form-group">
			<label class="col-sm-2 control-label">Position :</label>
			<div class="col-sm-3">
				<label class="col-sm-2 control-label"><%=position %> </label>
			</div>
		</div>
		<%if(!position.equals("Administrator")){ %>
		<div class="form-group">
			<label class="col-sm-2 control-label">Speciality :</label>
			<div class="col-sm-3">
				<label class="col-sm-2 control-label"><%=user.getSpeciality() %> </label>
			</div>
		</div>
		<%} %>
		<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-primary" onclick="location.href='editUser.jsp?actionType=editProfile&userId=<%=user.getUserId() %>'"  >Edit</button>
			</div>
</div>

</body>
</html>