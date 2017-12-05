<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-3.2.1.js'></script>
<header>
	<h2 id="logo">CD&amp;DVD</h2>
	<h2 id="logo2">Online Store</h2>
	<c:if test="${not empty sessionScope.userAuth}">
		<p id="user">
			Welcome UserId:
			<c:out value="${sessionScope.userAuth.id}"></c:out>
		</p>
	</c:if>

	<nav>
		<div class="container">
			<ul>
				<li class="red"><a
					href="${pageContext.request.contextPath}/showActor?doShow=0">Show
						All Actors</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/view/addActor.jsp">Add
						New Actor</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/view/updateActor.jsp">Update Actor</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/view/deleteActor.jsp">Delete
						Actor</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/view/searchActor.jsp">Search
						Actor</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</div>
	</nav>
</header>