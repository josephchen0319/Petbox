<%@page import="com.member.model.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="com.member.model.MemberVO" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Membership management</title>
</head>
<body>

<%
	MemberService mbSvc = new MemberService();
	List<MemberVO> members = mbSvc.getAllMembers();
%>





</body>
</html>
