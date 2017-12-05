<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style.css">
<script type='text/javascript'
	src='${pageContext.request.contextPath}/assets/js/lib/jquery-3.2.1.min.js'></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/common.js"></script>
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
				<li class="red" id="show"><a
					href="${pageContext.request.contextPath}/showActor.do?doShow=0">Show Actors</a></li>
				<li class="red" id="add"><a
					href="${pageContext.request.contextPath}/view/addActor.jsp">Add Actor</a></li>
				<li class="red" id="update"><a
					href="${pageContext.request.contextPath}/view/updateActor.jsp">Update
						Actor</a></li>
				<li class="red" id="delete"><a
					href="${pageContext.request.contextPath}/view/deleteActor.jsp">Delete
						Actor</a></li>
				<li class="red" id="searchActor"><a
					href="${pageContext.request.contextPath}/view/searchActor2.jsp">Search
						Actor</a></li>
				<li class="red" id="searchFilm"><a
					href="${pageContext.request.contextPath}/view/searchFilm.jsp">Search
						DVD</a></li>
				<li class="red" id="cart"><a
					href="${pageContext.request.contextPath}/view/showCart.jsp">Cart
					<c:if test="${not empty sessionScope.cart.cartItems}">
					<span style="color: red;">
					<c:out value="(${sessionScope.cart.cartItems.size()})"></c:out></span>
					</c:if>
					</a></li>
				<li class="red" id="logout"><a
					href="${pageContext.request.contextPath}/logout.do">Logout</a></li>
			</ul>
		</div>
	</nav>
</header>