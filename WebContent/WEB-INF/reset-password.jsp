<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp"></jsp:include>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<%
	String message = (String)request.getAttribute("message");
	String method = (String)request.getAttribute("method");
	String contextPath = request.getContextPath();
	String pathInfo = (String)request.getAttribute("pathInfo");
%>
<div class="container">
	<%if(method.equals("post")){ %>
<div message="<%=message%>" id="checkValid"></div>
	<%} 
	else
	{
	%>
<div class="col-md-4 col-md-offset-4 center">
	<h1>Reset Password</h1>
	<p>Please fill in this form to reset your password.</p>
	<hr>
  <form class="form-horizontal center" action="<%=contextPath%>/forgot-password/reset-password/<%=pathInfo %>" method="post">
    <div class="form-group">
      <label class="control-label col-sm-5" for="pwd">New Password:</label>
      <div class="col-sm-10">          
        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
      </div>
    </div>

    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Submit</button>
      </div>
    </div>
  </form>
  </div>
  <%} %>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>