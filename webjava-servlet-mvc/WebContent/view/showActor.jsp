<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_table.css">
<title>DVD | Show Actor</title>
</head>
<body>
	<c:if test="${not empty requestScope.msg}">
		<c:out value="${requestScope.msg}"></c:out>
	</c:if>
	<br>
	<br>
	<c:if test="${not empty requestScope.actorList}">
		<table>
			<thead>
			<tr>
				<th>Actor ID</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="actor" items="${requestScope.actorList}">
				<tr>
					<td>${actor.actorId}</td>
					<td>${actor.firstName}</td>
					<td>${actor.lastName}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
<%@ include file="/view/pagination.jsp"%>

</body>
</html>