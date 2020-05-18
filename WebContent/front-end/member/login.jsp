<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Login page</title>
</head>
<body>


	<h1>Login</h1>
	
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">Please correct the error:</font>
		<ul>
		    <c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	

	<form action="<%=request.getContextPath()%>/member/controller" method="post" style="{display: inline-block;}">
		<label for="login_email">email</label>
		<input type="text" name="email" id="login_email"> <br><br>
		
		<label for="login_password">password</label>
		<input type="password" name="password" id="login_password">
		<input type="hidden" name="action" value="login"><br><br>
		<input type="submit" name="submit">
	</form>	<br><br>
	
	




	
</body>
</html>