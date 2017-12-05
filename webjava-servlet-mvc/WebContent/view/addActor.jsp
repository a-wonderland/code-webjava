<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>DVD | Add Actor</title>
</head>
<body>
	<div class="add-block">
		<c:if test="${not empty requestScope.msg}">
		<c:out value="${requestScope.msg}"></c:out>
	</c:if>
<br><br>
	<c:choose>
		<c:when test="${not empty requestScope.actor}">
        Actor ID: ${actor.actorId}<br>
        First Name: ${actor.firstName}<br>
        Last Name: ${actor.lastName}<br>
		</c:when>
		<c:otherwise>
			<form action="${pageContext.request.contextPath}/addActor.do"
				method="post">
				First Name: <input type="text" name="fName" required="required"><br>
				Last Name: <input type="text" name="lName" required="required"><br>
				<input type="submit" name="doAdd" value="Add"> <br>
			</form>
		</c:otherwise>
	</c:choose>
		</div>
</body>
</html>