<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>DVD | Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type='text/javascript' src="${pageContext.request.contextPath}/assets/js/lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/messages.js?v20171114"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/register.js?v20171114"></script>
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style.css">
<style type="text/css">
p {
	display: inline;
}

input.invalid {
	border-color: #DD2C00;
}

.OK {
	background-color: #4CAF50;
}

.NG {
	background-color: #DD2C00;
	;
}

.textOK {
	color: #4CAF50;
}

.textNG {
	color: #DD2C00;
}
</style>
</head>
<body>
<header>
	<h2 id="logo">CD&amp;DVD</h2>
	<h2 id="logo2">Online Store</h2>
	<nav>
		<div class="container">
			<ul>
				<li class="red"><a href="${pageContext.request.contextPath}/index.jsp">Login</a></li>
				<li class="active"><a target="_blank">Register</a></li>
			</ul>
		</div>
	</nav>
</header>
<div class="reg-block">
		<c:if test="${not empty requestScope.msg}">
		<c:out value="${requestScope.msg}"></c:out>
	</c:if>
<br><br>
	<c:choose>
		<c:when test="${not empty requestScope.customer}">
        Name: ${customer.firstName} ${customer.lastName}<br>
        Email: ${customer.email}<br>
        Address ID: ${customer.addressId}<br>
        Store ID: ${customer.storeId}<br>
        Use User ID as ${customer.id} and your password to login.<br>
		</c:when>
		<c:otherwise>
	<form action="${pageContext.request.contextPath}/addCustomer.do" id="regForm">
		<table>

			<tr>
				<td>Store ID:</td>
				<td><input name="storeId" id="storeId" type="text" pattern="[1-9]{1,4}"
					required="required"></td>
				<td>
					<p id="storeIdMsg"></p>
				</td>
			</tr>

			<tr>
				<td>First Name:</td>
				<td><input type="text" name="fName" id="fname"
					pattern="[a-zA-Z]{2,30}" required="required"></td>
				<td>
					<p id="fnameMsg"></p>
				</td>
			</tr>

			<tr>
				<td>Last Name:</td>
				<td><input type="text" name="lName" id="lname"
					pattern="[a-zA-Z]{2,30}" required="required"></td>
				<td>
					<p id="lnameMsg"></p>
				</td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><input name="email" id="email" type="email" required="required"></td>
				<td>
					<p id="emailMsg"></p>
				</td>
			</tr>

			<tr>
				<td>Address ID:</td>
				<td><input name="addressId" id="addressId" type="text" pattern="[1-9]{1,4}"
					required="required"></td>
				<td>
					<p id="addressIdMsg"></p>
				</td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" id="password"
					pattern="[a-zA-Z0-9]{6,20}" required="required"></td>
				<td>
					<p id="passwordMsg"></p>
				</td>
			</tr>

		</table>
		<br> <input type="submit" value="register" id=register
			style="width: 150px;">
	</form>
	</c:otherwise>
	</c:choose>
</div>
</body>

</html>