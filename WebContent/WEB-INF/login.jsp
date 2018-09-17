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
<%if(message.equals("Your account wasn't verified")){%>
	<a href="<%=request.getContextPath()%>/register/get-verification">Click here to get a verified link!</a>
<%} %>
</div>
<div class="col-md-4 col-md-offset-4">
  <form class="form-horizontal center" action="login" method="post">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-10">
        <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" value="<%=email%>" required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-10">          
        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox" name="remember"> Remember me</label>
        </div>
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