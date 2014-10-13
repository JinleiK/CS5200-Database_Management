<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="java.util.*, model.*, dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<!-- <meta http-equiv="CACHE-CONTROL" content="NO-CACHE" /> -->
<!-- <meta http-equiv="PRAGMA" content="NO-CACHE" /> -->
<!-- <meta http-equiv="EXPIRES" content="0" /> -->
<!-- <link href="../css/bootstrap.min.css" rel="stylesheet"> -->
<%
	User login = (User) session.getAttribute("user");
	int userType = login.getUserType();
	int dptId = Integer.parseInt(request.getParameter("deptId"));
	//String dptName = request.getParameter("deptName");
	DepartmentDao dd = new DepartmentDao();
	String dptName = dd.getDeptById(dptId).getDptName();
	List<User> users = dd.getUsersByDept(dptId);
%>
<title><%=dptName %></title>
</head>
<body>
 <h2 class="sub-header" id="dptName"><%=dptName %></h2>
<!--  <form action="UserServlet" method="get"> -->
<input type="hidden" name="dptId" id="dptId" value=<%=dptId %>>
	<div class="table-responsive">	
	<table class="table table-striped">
	<thead>
                <tr>
                <%if(userType == 0) {%>
                  <th><input type="checkbox" name="checkAllHandler" id="checkAllHandler" onclick="checkAll()"></th>
                  <%} %>
                  <th>userId</th>
                  <th>Username</th>
                  <th>Firstname</th>
                  <th>Lastname</th>
                  <th>Gender</th>
                     <%if(userType == 0) {%>
                  <th>Edit</th>
                  <%} %>
                </tr>
              </thead>
      <tbody>
     <%for(User u : users){
     	if(u.getUserId() != login.getUserId()){%>
     
                <tr>
                <%if(userType == 0) {%>
                  <th> <input type="checkbox" name="userIdCheckbox" id=<%=u.getUserId() %> value=<%=u.getUserId() %>> </th>
                  <%} %>
                  <th><%=u.getUserId() %></th>
                  <th><%=u.getUsername() %></th>
                  <th><%=u.getFirstname() %></th>
                  <th><%=u.getLastname() %></th>
                  <th><%=(u.getGender()==0? "Male" : "Female") %></th>
                  <%if(userType == 0){ %>
                  <th><button class="btn btn-info btn-sm" onclick="location.href='editUser.jsp?actionType=edit&userId=<%=u.getUserId() %>'" >Edit</button></th>
                <%} %>
                </tr>
     <%	}
     } %>

     </tbody>
	</table>
	<%if(userType == 0){ %>
	<button class="btn btn-danger btn-sm"  onclick="deleteUser();">Delete</button>
	<%} %>
	</div>
<!-- </form> -->
</body>
</html>