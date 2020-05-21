<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import = "com.member.model.MemberVO"%>
<%@page import = "java.sql.Date"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update personal info</title>
<style type="text/css">
* {
  box-sizing: border-box;
}

#update_info {
	width:25%;
	float: left;
}

/* #image_holder { */
/* 	width: 50%; */
/* 	float: right; */

/* } */



</style>

</head>
<body>

	<h1>Update personal info</h1>

	<form id="update_info" action="<%=request.getContextPath()%>/member/controller" method="post" enctype="multipart/form-data" style="display:inline-block;">
		<label for="name">姓名</label>
		<input type="text" id="name" name="name" value="${memberVO.name}"><br><br>
		
		<label for="address">地址</label>
		<input type="text" id="address" name="address" value="${memberVO.address}"><br><br>
		
		<label for="phone_num">電話</label>
		<input type="text" id="phone_num" name="phone_num" value="${memberVO.phone_num}"><br><br>
		
		<label for="birthday">出生日期</label>
		<input type="date" id="birthday" name="birthday" value="${memberVO.birthday}"><br><br>
		
		<span>性別</span>
	    <input type="radio" name="sex" id="male" value="男" ${(memberVO.sex == "男") ? 'checked' : ''}>
	    <label for="male">男</label>
	    <input type="radio" name="sex" id="female" value="女" ${(memberVO.sex == "女") ? 'checked' : ''}>
	    <label for="female">女</label>
	    <input type="radio" name="sex" id="other" value="其他" ${(memberVO.sex == "其他") ? 'checked' : ''}>
	    <label for="other">其他</label><br><br>
		
		<label for="profile_image">上傳照片</label>
		<input type="file" id="profile_image" name="profile_image" value=""><br><br>
		
		<label for="password">舊密碼</label>
		<input type="password" id="password" name="password"><br><br>
		
		<label for="new_password">新密碼</label>
		<input type="password" id="new_password" name="new_password"><br><br>
		
		<label for="confirm">確認密碼</label>
		<input type="password" id="confirm" name="confirm"><br><br>
		
		<input type="hidden" name="action" value="update_info">
		<input type="submit" name="submit">
		
	</form>
	<br><br>
	
	
	
	<div id="image_holder" style="display: inline-block; width: 400px; height: 400px; border: 1px solid black;">
<%-- 		<img id="profile_image" width="100%" alt="test" src="data:image/png;base64,${memberVO.profile_image}"> --%>
	</div>
	<br><br>
	
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">Please correct the error:</font>
		<ul>
		    <c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<script src="<%=request.getContextPath()%>/front-end/member/showImage.js"></script>
</body>
</html>