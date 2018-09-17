<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp"></jsp:include>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<%
	String message = (String)request.getAttribute("message");
%>
<div class="container">
	<h1><%=message%></h1>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>