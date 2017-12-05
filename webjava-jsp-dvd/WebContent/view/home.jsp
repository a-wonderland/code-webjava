<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DVD  | Home</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.userAuth}">
		<%@ include file="/view/header.jsp"%>
		        <p id="user">Welcome UserId:<c:out value="${sessionScope.userAuth.id}"></c:out>
		        </p>
		</c:when>
		<c:otherwise>
			<%@ include file="/view/login.jsp"%>
		</c:otherwise>
	</c:choose>

</body>
</html>