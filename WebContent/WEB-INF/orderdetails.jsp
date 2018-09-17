<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="tables.Products" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%
	ArrayList<Products> productList = (ArrayList<Products>) request.getAttribute("productList");
	float totalPrice = 0;
%>
<%
	for(Products product : productList)
	{
		String name = product.getName();
		float price = product.getPrice();
		totalPrice = totalPrice + price;
%>
<li><span><%=name %></span> <span>$<%=price %></span></li>
<%}%>
<li><span>Subtotal</span> <span>$<%=totalPrice %></span></li>
<li><span>Shipping</span> <span>Free</span></li>
<li><span>Total</span> <span>$<%=totalPrice %></span></li>