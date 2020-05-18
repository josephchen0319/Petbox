<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.member.model.MemberVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>


  <form action="<%=request.getContextPath()%>/member/controller" method="post">
	
    <label for="name">name*</label>
    <input type="text" name="name" id="name" value="${memberVO.name}"><br><br>
    
    <label for="email">email*</label>
    <input type="text" name="email" id="email" value="${memberVO.email}"><br><br>

    <label for="phone_num">phone_num</label>
    <input type="text" name="phone_num" id="phone_num" value="${memberVO.phone_num}"><br><br>

    <label for="password">password*</label>
    <input type="password" name="password" id="password"><br><br>

    <label for="confirm">confirm*</label>
    <input type="password" name="confirm" id="confirm"><br><br>

    <label for="address">address</label>
    <input type="text" name="address" id="address" value="${memberVO.address}"><br><br>

	<span>Sex</span>
    <input type="radio" name="sex" id="male" value="男" ${(memberVO.sex == "男") ? 'checked' : ''}>
    <label for="male">Male</label>
    <input type="radio" name="sex" id="female" value="女" ${(memberVO.sex == "女") ? 'checked' : ''}>
    <label for="female">Female</label>
    <input type="radio" name="sex" id="other" value="其他" ${(memberVO.sex == "其他") ? 'checked' : ''}>
    <label for="other">Other</label><br><br>

    <label for="birthday">Birthday</label>
    <input type="date" name="birthday" id="birthday" value="${memberVO.birthday}" required pattern="\d{4}-\d{2}-\d{2}"><br><br>


    <input type="hidden" name="action" value="signup">
    <input type="submit" name="submit" value="Register">

  </form>
  <br>
  <br>
  
  <c:if test="${not empty errorMsgs}">
		<font style="color:red">Please correct the error:</font>
		<ul>
		    <c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

</body>
</html>