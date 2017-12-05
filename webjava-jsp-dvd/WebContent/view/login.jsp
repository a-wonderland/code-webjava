<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style_login.css">
	<script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-3.2.1.js'></script>
    <script type='text/javascript'>
    $(function() {
    var $forms = $("form");
    $forms.focusin(function() {
        $forms.removeClass("active-form");
        $(this).addClass("active-form");
    });

    var test = $(".container a");
test.click(function()
	{
	$(this).removeClass("red");
    $(this).addClass("active");

		});
});
    </script>
</head>
<header>
	<h2 id="logo">CD&amp;DVD</h2>
	<h2 id="logo2">Online Store</h2>
	<nav>
		<div class="container">
			<ul>
				<li class="red"><a target="_blank">Login</a></li>
				<li class="red"><a
					href="${pageContext.request.contextPath}/view/addCustomer.jsp">Register</a></li>
			</ul>
		</div>
	</nav>
</header>
	<div class="page-wrap">
		<div class="login-block">
			<h3>
				<c:if test="${not empty requestScope.msg}">
					<c:out value="${requestScope.msg}"></c:out>
				</c:if>
			</h3>
			<br><br>
			<form action="${pageContext.request.contextPath}/login" method="post">
				<p>
					<label for="usrId">User ID</label> <input type="text" name="usrId" type="number" required="required">
				</p>
				<p>
					<label for="password">Password</label> <input type="password" name="password" required="required"><br>
				</p>
				<p class="submit-wrap">
					<input type="submit" name="doLogin" value="Login" class="button" />
				</p>
			</form>
		</div>

	</div>

