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

	String actionType = request.getParameter("actionType");
	UserDao userDao = new UserDao();
	int userId = -1;
	int gender = 0;
	int position = 2;
	User user = new User();
	if(actionType != null && actionType.contains("edit")){
		userId = Integer.parseInt(request.getParameter("userId"));
		user = userDao.getUserById(userId);
		gender = user.getGender();
		position = user.getUserType();
	}
	DepartmentDao dd = new DepartmentDao();
	List<Department> depts = dd.listAllDep();
%>
	<h2 class="sub-header"><%=actionType.contains("edit")? "Edit user information" : "Add a new User"%></h2>
	<form class="form-horizontal" action="UserServlet" method="post" onsubmit="return checkFillAll();">
	<input type="hidden" name="type" id="userActionType" value=<%=actionType %>>
	<input type="hidden" name="userId" value=<%=userId %>>
	<input type="hidden" name="userGender" id="userGender" value=<%=gender %>>
	<input type="hidden" name="userPosition" id="userPosition"  value=<%=position %>>
	<input type="hidden" name="isContinue" id="isContinue" value=<%=true %>>
	
		<div class="form-group" id="usernameAddDiv">
			<label class="col-sm-2 control-label">Username</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" id="usernameAdd" name="username"
					placeholder="Username" value=<%=actionType.contains("edit")? user.getUsername() : "" %>>
				<span id="usernameAddSpan"></span>
			</div>
		</div>
		<%if(!actionType.contains("new")) {%>
		<div class="form-group" id="passwordOldDiv">
			<label class="col-sm-2 control-label">Old Password</label>
			<div class="col-sm-3">
				<input type="password" class="form-control" id="passwordOld" name="passwordOld"
					placeholder="Password">
				<span id="passwordOldSpan"></span>	
			</div>
		</div>
		<%} %>
		<div class="form-group" id="passwordDiv">
			<label class="col-sm-2 control-label">Password</label>
			<div class="col-sm-3">
				<input type="password" class="form-control" id="password" name="password"
					placeholder="Password">
			</div>
		</div>
		
		<div class="form-group" id="passwordConfirmDiv">
			<label class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-3">
				<input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm"
					placeholder="Password">
				<span id="passwordConfirmSpan"></span>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">First name</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" id="firstname" name="firstname"
					placeholder="First name" value=<%=actionType.contains("edit")? user.getFirstname() : "" %>>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">Last name</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" id="lastname" name="lastname"
					placeholder="Last name" value=<%=actionType.contains("edit")? user.getLastname() : "" %>>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Gender</label>
			<div class="col-sm-3">
			<div class="radio-inline">
				<label> <input type="radio" name="gender" id="genderM"
					value="0"> Male
				</label>
			</div>
			
			<div class="radio-inline">
				<label> <input type="radio" name="gender" id="genderF"
					value="1"> Female
				</label>
			</div>
			</div>
		</div>
		
		<%if(actionType != null && (!actionType.equals("editProfile"))){ %>
		<div class="form-group">
			<label class="col-sm-2 control-label">Department</label> 
			<div class="col-sm-3">
			<select class="form-control" name="dept">
			<%for(Department d : depts){ 
				if(actionType.equals("edit") && d.getDptId() == user.getDepartment().getDptId()){%>
				<option name="dept" value=<%=d.getDptId() %> selected><%=d.getDptName() %></option>
				<%} else{%>
				<option name="dept" value=<%=d.getDptId() %>><%=d.getDptName() %></option>
				<%}
				}%>
			</select>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">Position</label>
			<div class="col-sm-3">
			<div class="radio-inline">
				<label> <input type="radio" name="position" id="manager"
					value="1"> Manager
				</label>
			</div>
			<div class="radio-inline">
				<label> <input type="radio" name="position" id="employee"
					value="2"> Employee
				</label>
			</div>
			</div>
		</div>
		<%} %>
		<%if(user.getUserType() != 0){ %>
		<div class="form-group">
			<label class="col-sm-2 control-label">Speciality</label>
			<div class="col-sm-3">
				<textarea class="form-control" rows="3" name="speciality" placeholder="Speciality" ><%=actionType.contains("edit")? user.getSpeciality(): "" %></textarea>
			</div>
		</div>
		<%} %>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary"><%=actionType.contains("edit")? "Update" : "Create" %></button>
				
				<button type="reset" class="btn btn-default">Reset</button>
			</div>
		</div>
	</form>
</body>
</html>