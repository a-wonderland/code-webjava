<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DVD | Search Actor</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_table.css">
</head>
<body>
	<div class="search-block">
	<c:if test="${not empty requestScope.msg}">
		<c:out value="${requestScope.msg}"> </c:out>
	</c:if>
	<br>
	<br>
<c:if test="${empty requestScope.actorList}">
    <c:remove var="keyword" scope="session" />
</c:if>

	<form action="${pageContext.request.contextPath}/searchActor.do"
		method="get">
		Keyword <input type="text" name="keyword" required="required" autocomplete="off">
		<input type="submit" value="Search"> <br><br>
		<input type="radio" checked="checked" name="searchBy" value="fName">By First Name
		<input type="radio" name="searchBy" value="lName">By Last Name<br>
		<input type="radio" name="searchBy" value="fullName">By Full Name&nbsp;
		<input type="radio" name="searchBy" value="actorId">By ID
	</form>
</div>
	<c:if test="${not empty requestScope.actorList}">
		<table>
			<thead>
			<tr>
				<th>Actor ID</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>More</th>
			</tr>
			</thead>
			<c:forEach var="actor" items="${actorList}">
				<tr>
					<td>${actor.actorId}</td>
					<td>${actor.firstName}</td>
					<td>${actor.lastName}</td>
					<td>
					 <a href="${pageContext.request.contextPath}/searchActor.do?detailId=${actor.actorId}"> Detail </a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br><br>
</body>
</html>