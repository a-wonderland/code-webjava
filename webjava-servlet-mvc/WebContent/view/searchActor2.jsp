<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/view/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DVD | Search Actor</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/lib/pagination.css">
<script type='text/javascript' src='${pageContext.request.contextPath}/assets/js/lib/pagination.min.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}/assets/js/customPagination.js'></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_table.css">
<script type="text/javascript">
function template (data) {
    var html = '<table id="table"><thead><tr><th>Actor ID</th><th>First Name</th><th>Last Name</th><th>More</th></tr></thead>';

      $.each(data, function(index, actor) {
        html += '<tr>'+
        '<td>'+ actor.actorId +'</td>'+
        '<td>'+ actor.firstName +'</td>'+
        '<td>'+ actor.lastName +'</td>'+
        '<td>'+
        '<a href="./searchActor2.do?detailId='+actor.actorId+'">Detail</a></td>'+
        '<tr>';
      });

    html += '</table>';

    return html;
  }

 function getLocator(){
	 return 'actors';
 };
</script>
</head>
<body>
	<div class="search-block">
		<p id="msg"></p>
		<br>
		<form id="searchForm" action="${pageContext.request.contextPath}/searchActor2.do" method="get" >
			 <input type="text" name="keyword" required="required" placeholder="Enter keyword">
			<input type="submit" value="Search" id="search"> <br> <br>
			<input type="radio" checked="checked" name="searchBy" value="fName">By
			First Name <input type="radio" name="searchBy" value="lName">By
			Last Name<br> <input type="radio" name="searchBy"
				value="fullName">By Full Name&nbsp; <input type="radio"
				name="searchBy" value="actorId">By ID
		</form>

	</div>
<div id="pagination-container" style="margin-left: 40%; margin-bottom: 4px;"></div>
<div id="data-container"></div>
	<br>
	<br>
</body>

</html>