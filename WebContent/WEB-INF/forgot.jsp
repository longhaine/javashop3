<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp"></jsp:include>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<%
	String message = (String) request.getAttribute("message");
	if(message== null){message = "default";}
	String email = (String) request.getAttribute("email");
	if(email==null){email = "";}
%>
<div class="container">
<div message="<%=message%>" id="checkValid">
</div>
<div class="col-md-4 col-md-offset-4 center">
	<h1>Forget Password</h1>
	<p>Please fill in this form to get a reset password link.</p>
	<hr>
  <form class="form-horizontal center" action="forgot-password" method="post">
    <div class="form-group">
      <label class="control-label col-sm-4" for="email">Your Email:</label>
      <div class="col-sm-10">
        <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" value="<%=email%>" required>
      </div>
    </div>

    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Submit</button>
      </div>
    </div>
  </form>
  </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>