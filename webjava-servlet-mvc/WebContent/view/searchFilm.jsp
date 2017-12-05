<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>DVD | Search Film</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/lib/pagination.css">
<script type='text/javascript'
	src='${pageContext.request.contextPath}/assets/js/lib/pagination.min.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/assets/js/customPagination.js'></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style_table.css">
<script type="text/javascript">
	function template(data) {
		var formName= 'filmForm';
		var html = '<table id="table" style="width: 80%;"><thead><tr><th>Title</th><th>Category Name</th><th>Description</th><th>Price</th><th>Rating</th><th>Rental Rate</th><th>Add To Cart</th></tr></thead>';
		$.each(data, function(index, films) {
			html += '<tr>'
			+'<form id="'+formName+index+'" action="./cart.do" method="POST"></form>'
			+ '<td>'+films.title +'<input form="'+formName+index+'" type="hidden" name="title" value="'+films.title+'"></td>'
			+ '<td>'+ films.categoryName +'<input form="'+formName+index+'" type="hidden" name="categoryName" value="'+films.categoryName+'"></td>'
			+ '<td>'+ films.description +'<input form="'+formName+index+'" type="hidden" name="description" value="'+films.description+'"></td>'
			+ '<td>'+ films.price +'<input form="'+formName+index+'" type="hidden" name="price" value="'+films.price+'"></td>'
			+ '<td>'+ films.rating +'<input form="'+formName+index+'" type="hidden" name="rating" value="'+films.rating+'"></td>'
			+ '<td>'+ films.rentalRate +'<input form="'+formName+index+'" type="hidden" name="rentalRate" value="'+films.rentalRate+'"></td>'
			+ '<td><input form="'+formName+index+'" type="hidden" name="filmId" value="'+films.filmId+'"><input form="'+formName+index+'" type="submit" value="Cart" id="addToCart" style="width: 100px;" name="doAdd"></td>'
			+ '</tr>';
		});
		html += '</table>';
		return html;
	}

	 function getLocator(){
		 return 'films';
	 };

</script>
</head>
<body>
	<div class="search-block">
		<p id="msg"></p>
		<br>
		<form id="searchForm"
			action="${pageContext.request.contextPath}/searchFilm.do"
			method="get">
			<input type="text" name="keyword" required="required"
				placeholder="Enter movie" style="width: 400px"> <input
				type="submit" value="Search" id="search"> <br> <br>
		</form>

	</div>
	<div id="pagination-container" style="margin-left: 40%; margin-bottom: 4px"></div>
	<div id="data-container"></div>
	<br>
	<br>

</body>

</html>