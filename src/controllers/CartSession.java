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
import dao.ProductsD;
import tables.Products;
/**
 * Servlet implementation class Test
 */
@WebServlet(urlPatterns = { "/addcart", "/removecart"})
public class CartSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath().substring(1);// get path
		ProductsD productsdao = new ProductsD();
		String id = request.getParameter("product"); // get parameter product id
		Products product = productsdao.getProductById(id);// get product
		HttpSession session = request.getSession();
		if(id!= null && product != null)//check id and product are existence; 
		{
			ArrayList<Products> cartList = (ArrayList<Products>)session.getAttribute("cartList"); //old product list
			if(action.equals("addcart")) // addcart
			{
				if(cartList != null)
				{
					cartList.add(product);
				}
				else
				{
					cartList = new ArrayList<Products>();
					cartList.add(product);
				}
			}
			if(action.equals("removecart")) { // remove cart
				if(cartList != null)
				{
					int index = -1;
					for(Products productIndex : cartList)
					{
						if(productIndex.getId() == Integer.parseInt(id))
						{
							index = cartList.indexOf(productIndex);
							break;
						}
					}
					if(index >=0)
					{
						System.out.println("index >=0");
						cartList.remove(index);
					}
				}
			}
			session.setAttribute("cartList", cartList);
			session.setMaxInactiveInterval(5*60); // 5 minutes
			if(cartList.isEmpty()) // if session productlist doesn't have 1 product
			{
				session.removeAttribute("cartList");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
