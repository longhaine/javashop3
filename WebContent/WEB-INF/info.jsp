<%@page import="org.json.JSONObject"%>
<%@ page import ="tables.Accounts" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp"></jsp:include>
<body>
<%
	String message = (String) request.getAttribute("message");
	if(message == null){message = "default";}
	Accounts account = (Accounts) session.getAttribute("account");
	String accountName = "";
	String accountAddress ="";
	String accountPhone = "";
	if(account != null)
	{
		accountName = account.getName();
		accountAddress = account.getAddress();
		accountPhone = account.getPhone();
	}
	String path = (String) request.getAttribute("path");
%>
<jsp:include page="banner.jsp"></jsp:include>
	<% if(path.equals("/your-info")){%>
	<div class="checkout_area section-padding-80">
		<div class="container">
			<div id="checkValid" message="<%=message%>"></div>
			<div class="row">
				<div class="col-12 col-md-6">
					<div class="checkout_details_area mt-50 clearfix">
						<div class="cart-page-heading mb-30">
							<h5>Billing Address</h5>
						</div>
						<form action="your-info" method="post">
							<div class="row">
								<div class="col-12 mb-3">
									<label for="your_name">Your Name <span>*</span></label> <input
										type="text" class="form-control" id="yourname" value="<%=accountName %>" name="name"
										required>
								</div>

								<div class="col-12 mb-3">
									<label for="address">Address<span>*</span></label> <input
										type="text" class="form-control" id="address" value="<%=accountAddress %>"
										required name="address">
								</div>

								<div class="col-12 mb-3">
									<label for="phone_number">Phone No <span>*</span></label> <input
										type="number" class="form-control" id="phone_number" min="0"
										value="<%=accountPhone %>" name="phone">
								</div>
							    <div class="form-group">        
							      <div class="col-sm-offset-2 col-sm-10">
							        <button type="submit" class="btn btn-default">Submit</button>
							      </div>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%}
	else{%>
	<div class="checkout_area section-padding-80">
		<div class="container">
			<div id="checkValid" message="<%=message%>"></div>
			<div class="row">
				<div class="col-12 col-md-6">
					<div class="checkout_details_area mt-50 clearfix">
						<div class="cart-page-heading mb-30">
							<h5>Change Password</h5>
						</div>
						<form action="change-pass" method="post">
							<div class="row">
								<div class="col-12 mb-3">
									<label for="currentpassword">Current Password <span>*</span></label> <input
										type="password" class="form-control" id="currentpassword" value="" name="currentpassword"
										required>
								</div>
								<div class="col-12 mb-3">
									<label for="newpassword">New Password <span>*</span></label> <input
										type="password" class="form-control" id="newpassword" value="" name="newpassword"
										required>
								</div>								
							    <div class="form-group">        
							      <div class="col-sm-offset-2 col-sm-10">
							        <button type="submit" class="btn btn-default">Submit</button>
							      </div>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%}%>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>