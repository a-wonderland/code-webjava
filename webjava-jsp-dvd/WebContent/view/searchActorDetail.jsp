<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DVD | Actor Detail</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_table.css">
</head>
<body>
	<c:if test="${not empty requestScope.msg}">
		<c:out value="${requestScope.msg}"></c:out>   <br>
    <br>
	</c:if>
	<div>
	<c:if test="${not empty requestScope.actor}">
     <hr>
	<p style=" line-height: 38px;font-size: 32px; background-color: #d7d7d7;text-align: center;"> ${actor.firstName} ${actor.lastName}</p>
	 <hr>
	</c:if>

	<c:if test="${not empty requestScope.actorDetail}">
		<table style="width: 40%">
			<thead>
			<tr> <th colspan="3" style="background-color: #ca9d26; color: #464b4c; text-align: center">Detail Film History </th></tr>
			<tr>
				<th>Film ID</th>
				<th>Title</th>
				<th>Category Name</th>
			</tr>
			</thead>
			<c:forEach var="actor" items="${requestScope.actorDetail}">
				<tr>
					<td>${actor.filmId}</td>
					<td>${actor.title}</td>
					<td>${actor.categoryName}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br>
		</div>
</body>
</html>