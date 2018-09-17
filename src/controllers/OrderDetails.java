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

import dao.OrdersD;
import dao.Orders_DetailsD;
import dao.ProductsD;
import tables.Accounts;
import tables.Products;

/**
 * Servlet implementation class OrderList
 */
@WebServlet("/orderdetails")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductsD productsdao = new ProductsD();
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		boolean check = false;
		ArrayList<Integer> productIdList = new ArrayList<>(); 
		ArrayList<Products> productList = new ArrayList<>();
		Accounts account = (Accounts) session.getAttribute("account");
		if(account!=null){ // check account already logging in
			OrdersD ordersdao = new OrdersD();
			check = ordersdao.checkOrderbyAccount(account.getEmail(), id);// check order if placed by this account
			if(check)
			{
				Orders_DetailsD orders_detailsdao = new Orders_DetailsD();
				productIdList = orders_detailsdao.getProductListbyOrder(id); // get id list
			}
		}
		else{
			OrdersD ordersdao = new OrdersD();
			check = ordersdao.CheckOrderGuest(session.getId(), id); // check order if placed by this guest
			if(check)
			{
				Orders_DetailsD orders_detailsdao = new Orders_DetailsD();
				productIdList = orders_detailsdao.getProductListbyOrder(id);
			}
		}
		if(check)
		{
			for(int i = 0,size = productIdList.size(); i < size ; i++)
			{
				Products product = productsdao.getProductById(productIdList.get(i).toString());
				productList.add(product);
			}
			request.setAttribute("productList",productList );
			request.getRequestDispatcher("WEB-INF/orderdetails.jsp").forward(request, response);
		}
		else {
			response.getWriter().append("");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
