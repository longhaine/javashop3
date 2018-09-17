<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="tables.Categories" %>
<%@ page import ="tables.Brands" %>
<%@ page import ="tables.Products" %>
<%@ page import ="tables.Accounts" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	ArrayList<Categories> categories = (ArrayList<Categories>) request.getAttribute("categories");
	ArrayList<Brands> brands = (ArrayList<Brands>) request.getAttribute("brands");
	ArrayList<Products> cartList = (ArrayList<Products>) session.getAttribute("cartList");
	Accounts account = (Accounts) session.getAttribute("account");
	String accountName = "";
	if(account != null){
		accountName = account.getName();
	}
	String searchQuery = request.getParameter("q");
%>
 <!-- ##### Header Area Start ##### -->
    <header class="header_area">
        <div class="classy-nav-container breakpoint-off d-flex align-items-center justify-content-between">
            <!-- Classy Menu -->
            <nav class="classy-navbar" id="essenceNav">
                <!-- Logo -->
                <a class="nav-brand" href="<%=contextPath%>/"><img src="<c:url value='/img/logo/tlogovogue.png'/>" alt=""></a>
                <!-- Navbar Toggler -->
                <div class="classy-navbar-toggler">
                    <span class="navbarToggler"><span></span><span></span><span></span></span>
                </div>
                <!-- Menu -->
                <div class="classy-menu">
                    <!-- close btn -->
                    <div class="classycloseIcon">
                        <div class="cross-wrap"><span class="top"></span><span class="bottom"></span></div>
                    </div>
                    <!-- Nav Start -->
                    <div class="classynav">
                        <ul>
                            <li><a href="#">Shop</a>
                                <div class="megamenu">
                                    <ul class="single-mega cn-col-4">
                                        <li class="title">Ladies's Collection</li>
										<%
											for(Categories category : categories)
											{
											
												String name = category.getName();
												String gender = category.getGender();
												if(gender.equals("women") || gender.equals("both")){
										%>
										<li><a href="<%=contextPath%>/shop/women/<%=name%>"><%=name %></a></li>
										<%		}	
											} %>
                                        
                                    </ul>
                                    <ul class="single-mega cn-col-4">
                                        <li class="title">Men's Collection</li>
										<%
										for(Categories category : categories)
										{
										
											String name = category.getName();
											String gender = category.getGender();
											if(gender.equals("men") || gender.equals("both")){
										%>
										<li><a href="<%=contextPath%>/shop/men/<%=name%>"><%=name %></a></li>
										<%	}
										} %>
                                    </ul>
                                    <ul class="single-mega cn-col-4">
                                        <li class="title">Brands</li>
										<%
											for(Brands brand : brands)
											{
											String name = brand.getName();
										%>
										<li><a href="#"><%=name %></a></li>
										<%} %>
                                    </ul>
                                    <div class="single-mega cn-col-4">
                                        <img src="<%=contextPath%>/img/bg-img/bg-6.jpg" alt="">
                                    </div>
                                </div>
                            </li>
                            <li><a href="#">Pages</a>
                                <ul class="dropdown">
                                    <li><a href="<%=contextPath%>/">Home</a></li>
                                    <li><a href="<%=contextPath%>/women">Shop</a></li>
                                    <li><a href="<%=contextPath%>/check-out">Checkout</a></li>
                                    <li><a href="<%=contextPath%>/blog">Blog</a></li>
                                    <li><a href="<%=contextPath%>/single-blog">Single Blog</a></li>
                                    <li><a href="<%=contextPath%>/contact">Contact</a></li>
                                </ul>
                            </li>
                            <li><a href="<%=contextPath%>/blog.html">Blog</a></li>
                            <li><a href="contact.html">Contact</a></li>
                        </ul>
                    </div>
                    <!-- Nav End -->
                </div>
            </nav>
            <!-- Header Meta Data -->
            <div class="header-meta d-flex clearfix justify-content-end">
                <!-- Search Area -->
                <div class="search-area">
                    <form action="<%=contextPath%>/search" method="get">
                        <input type="search" name="q" id="headerSearch" placeholder="Type for search" pattern=".{3,}" required title="3 characters minimum"
                        value="<%if(searchQuery !=null){%><%=searchQuery%><%} %>">
                        <button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                    </form>
                </div>
                <!-- Favourite Area -->
                <div class="favourite-area">
                    <a href="#"><img src="<%=contextPath%>/img/core-img/heart.svg" alt=""></a>
                </div>
                <!-- User Login Info -->
                <div class="user-login-info">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="caret"></span><img src="<%=contextPath%>/img/core-img/user.svg" alt="">
                    </a>
                      <ul class="dropdown-menu text-center">
                     	<%
                      		if(account == null){
                      	%>
    					<li class="cursor-link" id="login">Login</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="forgotPassword">Forgot Password?</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="register">Register</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="history">History</li> <!-- Link in js -->
    					<%	}
                      		else{
            			%>		
						<li class="cursor-default">Hi <%=accountName %>!</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="yourinfo">Your info</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="changepassword">Change Password</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<li class="cursor-link" id="history">History</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<%
    					if(account.getRole() ==1)
    					{%>
    					<li class="cursor-link" id="admin">Admin page</li> <!-- Link in js -->
    					<div class="dropdown-divider"></div>
    					<%} %>
    					<li class="cursor-link" id="logout">Logout</li> <!-- Link in js -->
    					<%	} %>
  					</ul>
                </div>
                <!-- Cart Area -->
                <div class="cart-area">
                    <a href="#" id="essenceCartBtn"><img src="<%=contextPath%>/img/core-img/bag.svg" alt=""> <span></span></a>
                </div>
            </div>

        </div>
    </header>
    <!-- ##### Header Area End ##### -->

    <!-- ##### Right Side Cart Area ##### -->
    <div class="cart-bg-overlay"></div>

    <div class="right-side-cart-area">

        <!-- Cart Button -->
        <div class="cart-button">
            <a href="#" id="rightSideCart"><img src="<%=contextPath%>/img/core-img/bag.svg" alt=""> <span></span></a>
        </div>

        <div class="cart-content d-flex">

            <!-- Cart List Area -->
            <div class="cart-list">
            	<% 	if(cartList != null){
	            		for(Products product : cartList)
	            		{
	            			int id = product.getId();
	            			String name = product.getName();
	            			String brand = product.getName_Brands();
	            			String image = product.getImage();
	            			float price = product.getPrice();
            	 %>
            	
                <!-- Single Cart Item -->
                <div class="single-cart-item" id="c<%=id%>">
                    <a href="#" class="product-image">
                        <img src="<%=contextPath%>/<%=image %>1.jpg" class="cart-thumb" alt="">
                        <!-- Cart Item Desc -->
                        <div class="cart-item-desc">
                          <span class="product-remove" cid="c<%=id%>"><i class="fa fa-close" aria-hidden="true"></i></span>
                            <span class="badge"><%=brand %></span>
                            <h6><%=name %></h6>
                            <p class="size">Size: S</p>
                            <p class="color">Color: Red</p>
                            <p class="price">$<%=price %></p>
                        </div>
                    </a>
                </div>
                
                <%	}
            	}%>
            	
            </div>
			
            <!-- Cart Summary -->
            <div class="cart-amount-summary">

                <h2>Summary</h2>
                <ul class="summary-table">
                    <li><span>subtotal:</span> <span></span></li>
                    <li><span>delivery:</span> <span>Free</span></li>
                    <li><span>discount:</span> <span>0%</span></li>
                    <li><span>total:</span> <span></span></li>
                </ul>
                <form action="<%=contextPath %>/check-out" method="get">
                <div class="checkout-btn mt-100">
                    <input type="submit"class="btn essence-btn" value="CHECKOUT">
                </div>
                </form>
            </div>
            
        </div>
    </div>
    <!-- ##### Right Side Cart End ##### -->