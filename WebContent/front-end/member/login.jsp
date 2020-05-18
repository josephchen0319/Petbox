<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<html>
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
	

	<form action="<%=request.getContextPath()%>/member/controller" method="post">
		<input type="text" name="email">
		<input type="password" name="password">
		<input type="hidden" name="action" value="login">
		<input type="submit" name="submit">
	</form>	



	
</body>
</html>