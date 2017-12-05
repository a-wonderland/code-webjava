<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="pagination">
    <c:set var="total" value="${requestScope.totalActors}"></c:set>
    <c:if test="${total > 20}">
        <c:forEach begin="1" end="${total}" var="i">

            <c:set var="pagination" value="${i%20}"></c:set>
            <fmt:parseNumber var="pageNo" integerOnly="true" type="number"
                value="${i/20}" />
            <c:if test="${pagination == 0}">
                <a href="${requestScope.servletName}.do?doShow=${(pageNo-1)*20}${requestScope.searchKey}"> ${pageNo}${i == total ? "" : " - "} </a>
            </c:if>

        </c:forEach>
        <c:if test="${(total%20) != 0}">
            <a href="${requestScope.servletName}.do?doShow=${(pageNo)*20}"> ${pageNo+1} </a>
        </c:if>
    </c:if>
 </div>
