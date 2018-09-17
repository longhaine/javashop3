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
	String contextPath = (String) request.getContextPath();
%>
	<div class="container">
		<div message="<%=message%>" id="checkValid"></div>
		<div class="col-md-4 col-md-offset-4">
			<h1>Sign Up</h1>
			<p>Please fill in this form to create an account.</p>
			<hr>
			<form class="form-horizontal center" action="register" method="post">
				<div class="form-group">
					<label class="control-label col-sm-6" for="name">Your Name:</label>
					<div class="col-sm-10">
						<input type="name" class="form-control" id="name"
							placeholder="Enter your name" name="name">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="email">Email:</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="email"
							placeholder="Enter email" name="email">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="pwd">Password:</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="pwd"
							placeholder="Enter password" name="password">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<p>
							By creating an account you agree to our <a href="#"
								style="color: dodgerblue">Terms & Privacy</a>.
						</p>
						<a href="<%=contextPath %>/register/get-verification" style="color: dodgerblue">Don't Get Verification Link ?</a>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">Register</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>