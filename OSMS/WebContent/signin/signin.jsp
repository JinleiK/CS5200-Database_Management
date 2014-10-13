<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" import="model.*, dao.*"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>OSMS sign in</title>
    <style type="text/css"></style>

    <!-- Bootstrap core CSS -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/OSMS/signin/signin.css" rel="stylesheet">

  </head>

  <body>
  <%
  		String isValid = request.getParameter("isValid");
		if((isValid != null) && isValid.equals("nolongerValid")){
			session.invalidate();
		}
		
  %>

    <div class="container">

      <form action="/OSMS/UserServlet" method="get" class="form-signin">
      <h1 class="form-signin-heading">Office Supplies Management System</h1>
      <%if((isValid != null) && (isValid.equals("invalid"))){ %>
        <h6 class="form-signin-heading errorMessage">Invalid Username or Password!</h6>
        <%} else if((isValid != null) && (isValid.equals("visitor"))){%>
        <h6 class="form-signin-heading errorMessage">Please sign in first</h6>
        <%} %>
        <input type="hidden" name="type" value="signin">
        <input type="text" class="form-control" placeholder="Username" name="username">
        <input type="password" class="form-control" placeholder="Password" name="password">
        
<!--         <label class="checkbox"> -->
<!--           <input type="checkbox" name="rememberMe" value="remember-me"> Remember me -->
<!--         </label> -->
        <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
      </form>

    </div>
    
  </body>
</html>
