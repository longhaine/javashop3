package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.BrandsD;
import dao.CategoriesD;
import dao.OrdersD;
import dao.ProductsD;
import tables.Accounts;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Orders
 */
@WebServlet("/orders")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orders() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadBanner(request, response);
		HttpSession session = request.getSession();
		Accounts account = (Accounts) session.getAttribute("account");
		ArrayList<tables.Orders> orderList = new ArrayList<>();
		if(account != null){
			String email = account.getEmail();
			orderList = GetOrderUser(request, response, email);
		}
		else { // in case guest
			String sessionId = session.getId();
			orderList = GetOrderGuest(request, response,sessionId);
		}
		session.setAttribute("orderList", orderList);
		request.getRequestDispatcher("WEB-INF/orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void LoadBanner(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesD categoriesdao = new CategoriesD();
		BrandsD brandsdao = new BrandsD();
		ArrayList<Categories> categories = categoriesdao.getCategories();
		request.setAttribute("categories",categories); // specifics
		ArrayList<Brands> brands = brandsdao.getBrands();
		request.setAttribute("brands", brands);
	}
	protected ArrayList<tables.Orders> GetOrderGuest(HttpServletRequest request, HttpServletResponse response,String sessionId) throws ServletException, IOException {
		OrdersD ordersdao = new OrdersD();
		ArrayList<tables.Orders> orderList = (ArrayList<tables.Orders>) ordersdao.getOrderListbyGuest(sessionId);
		return orderList;
	}
	protected ArrayList<tables.Orders> GetOrderUser(HttpServletRequest request, HttpServletResponse response,String email) throws ServletException, IOException {
		OrdersD ordersdao = new OrdersD();
		ArrayList<tables.Orders> orderList = ordersdao.getOrderListbyAccount(email);
		return orderList;
	}
}
