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
import dao.ProductsD;
import tables.Accounts;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		if(account == null)
		{
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(email != null && password != null)
		{
			AccountsD accountsdao = new AccountsD();
			Accounts account = accountsdao.Login(email, password);
			if(account != null && account.getVerification() == 1) // account was verified
			{
				HttpSession session = request.getSession();
				session.setAttribute("account", account);
				session.setMaxInactiveInterval(10*60);
				// delete guest info
				session.removeAttribute("guest");
				// getting path before coming to login
				String pathBefore = null;
				for(Cookie c : request.getCookies()) 
				{
					if(c.getName().equals("path"))
					{
						pathBefore = c.getValue();
						c.setMaxAge(0);
						response.addCookie(c);//del cookie = del before path
						break;
					}
				}
				if(pathBefore != null) 
				{
					
					response.sendRedirect(request.getContextPath()+"/"+pathBefore);
				}
				else {
					response.sendRedirect(request.getContextPath()+"/");
				}

			}
			else if(account != null && account.getVerification() == 0) // account wasn't verified
			{
				request.setAttribute("email", email);
				request.setAttribute("message", "Your account wasn't verified");
				doGet(request, response);
			}
			else {
				request.setAttribute("email", email);
				request.setAttribute("message", "invalid email or password");
				doGet(request, response);
			}
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
