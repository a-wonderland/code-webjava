<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DVD | Cart</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style_table.css">
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.cart.cartItems}">
			<hr>
			<p
				style="line-height: 38px; font-size: 32px; background-color: #d7d7d7; text-align: center;">Your
				Cart</p>
			<hr>
			<table id="initialTable" style="width: 80%;">
				<thead>
					<tr>
						<th>Title</th>
						<th>Category Name</th>
						<th>Description</th>
						<th>Price</th>
						<th>Rating</th>
						<th>Rental Rate</th>
						<th>Remove Cart</th>
					</tr>
				</thead>
				<c:forEach var="film" items="${sessionScope.cart.cartItems}" varStatus="count">
					<tr>
						<td>${film.title}</td>
						<td>${film.categoryName}</td>
						<td>${film.description}</td>
						<td>${film.price}</td>
						<td>${film.rating}</td>
						<td>${film.rentalRate}</td>
						<td>
							<form action="${pageContext.request.contextPath}/cart.do"
								method="post">
								<input type="hidden" value="${film.filmId}" name="filmId">
								<input type="hidden" value="${count.index}" name="index">
								<input type="submit" value="Remove" id="removeCart"
									style="width: 100px;" name="doRemove">
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			<form id="checkOutForm"
				action="${pageContext.request.contextPath}/checkOut.do"
				method="post">
				<input type="submit" value="Check Out" id="checkOut"
					style="width: 200px; background-color: #f3ca83; float: right; margin-right: 135px;" name="doCheckOut">
			</form>
		</c:when>
		<c:otherwise>
			<hr>
			<p
				style="line-height: 38px; font-size: 32px; background-color: #d7d7d7; text-align: center;">Your
				Cart is Empty</p>
			<hr>
		</c:otherwise>
	</c:choose>
</body>

</html>