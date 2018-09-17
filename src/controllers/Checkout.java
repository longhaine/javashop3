package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.AccountsD;
import dao.BrandsD;
import dao.CategoriesD;
import dao.OrdersD;
import dao.Orders_DetailsD;
import dao.ProductsD;
import tables.Accounts;
import tables.Brands;
import tables.Categories;
import tables.Products;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/check-out")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		HttpSession session = request.getSession();
		ArrayList<Products> cartList = (ArrayList<Products>) session.getAttribute("cartList");
		Accounts account = (Accounts) session.getAttribute("account");
		String accountAddress ="";
		if(account != null) {
			accountAddress = account.getAddress();
		}
		if(cartList != null && account != null && accountAddress.equals("") == false)// okay
		{
			LoadBanner(request, response);
			request.getRequestDispatcher("WEB-INF/checkout.jsp").forward(request, response);
		}
		else if(cartList !=null && account != null && accountAddress.equals(""))// in case user doesn't have address
		{
			Cookie path = new Cookie("path",request.getServletPath().substring(1));
			path.setMaxAge(5*60);
			response.addCookie(path);
			request.setAttribute("message","pls add your address before checkout");
			response.sendRedirect(request.getContextPath()+"/your-info");
		}
		else if(cartList !=null && account == null) // in case guest
		{
			LoadBanner(request, response);
			request.getRequestDispatcher("WEB-INF/checkout.jsp").forward(request, response);
		}
		else
		{
			response.sendRedirect(request.getContextPath()+"/");
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArrayList<Products> cartList = (ArrayList<Products>) session.getAttribute("cartList");
		Accounts account = (Accounts)session.getAttribute("account");
		String email = "unknow";
		String accountName ="";
		String accountAddress ="";
		String accountPhone ="";
		if(account != null)
		{
			email = account.getEmail();
			accountAddress = account.getAddress();
			accountName = account.getName();
			accountPhone = account.getPhone();
		}
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		if(cartList != null && account != null && accountAddress.equals("")) // in case email doesn't have address
		{
			Cookie path = new Cookie("path",request.getServletPath().substring(1));
			path.setMaxAge(5*60);
			response.addCookie(path);
			request.setAttribute("message","pls add your address before checkout");
			request.getRequestDispatcher("/your-info").forward(request, response);
		}
		else if(cartList !=null && account != null && accountAddress != "" ) // everything's okay
		{
				float price = 0;
				for(Products product : cartList) // counting total price
				{
					price = price + product.getPrice();
				}
				OrdersD ordersdao = new OrdersD();
				Orders_DetailsD orders_detailsdao = new Orders_DetailsD();
				JSONObject orderInfo = new JSONObject();
				orderInfo.put("email", email);
				orderInfo.put("name", accountName);
				orderInfo.put("address", accountAddress);
				orderInfo.put("phone", accountPhone);
				orderInfo.put("sessionId", "");
				int idOrder = ordersdao.Order(orderInfo,price);// inserting and getting idOrder just inserted
				System.out.println(idOrder);
				if(idOrder > 0)
				{
					
					Boolean check = orders_detailsdao.insertOrder_Details(idOrder, cartList); // check 
					if(check == true) // everything's okay!
						{
							session.removeAttribute("cartList");// delete productList just placed order
							session.setAttribute("message", "Place order successfully!!! The products will be delivered to your address tomorrow!");
						}
						response.sendRedirect(request.getContextPath()+"/orders");		
				}
		}
		else if(cartList !=null && account == null) // in case guest
		{
			if(name.length() >=1 && address.length() >= 1 && phone.length() >= 1) // in case doesn't have name, address, phone
			{
				float price = 0;
				for(Products product : cartList) // counting total price
				{
					price = price + product.getPrice();
				}
				String sessionId = session.getId();
				OrdersD ordersdao = new OrdersD();
				Orders_DetailsD orders_detailsdao = new Orders_DetailsD();
				JSONObject order = new JSONObject();
				order.put("email", email);
				order.put("name", name);
				order.put("address", address);
				order.put("phone", phone);
				order.put("sessionId", sessionId);
				int idOrder = ordersdao.Order(order,price);// get idOrder just insert
				Boolean check = orders_detailsdao.insertOrder_Details(idOrder, cartList); // check 
				if(check == true) // everything's okay!
				{
					session.removeAttribute("cartList");// delete productList just place order
					session.setAttribute("guest", order);// generate guest info
					session.setAttribute("message", "Place order successfully!!! The products will be delivered to your address tomorrow!");
				}
				response.sendRedirect(request.getContextPath()+"/orders");
			}
			else {
				request.setAttribute("message", "invalid");
				doGet(request, response);
			}
		}
		else // something's wrong 
		{
			response.sendRedirect(request.getContextPath()+"/");
		}
		
	}
	protected void LoadBanner(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesD categoriesdao = new CategoriesD();
		BrandsD brandsdao = new BrandsD();
		ArrayList<Categories> categories = categoriesdao.getCategories();
		request.setAttribute("categories",categories); // specifics
		ArrayList<Brands> brands = brandsdao.getBrands();
		request.setAttribute("brands", brands);
	}
}
