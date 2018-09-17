package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.AccountsD;
import dao.BrandsD;
import dao.CategoriesD;
import dao.ProductsD;
import dao.VerificationsD;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadBanner(request, response);
		request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String message = "";
		if(email.length() >= 1 && password.length() >= 1 & name.length() >= 1) 
		{
			AccountsD accountsdao = new AccountsD();
			VerificationsD verificationsdao = new VerificationsD();
			String requestURLShortofPathInfo = String.valueOf(request.getRequestURL());
			requestURLShortofPathInfo = requestURLShortofPathInfo.replace(request.getRequestURI(),request.getContextPath()+"/register/verification/");
			message = accountsdao.Register(email, password, name,requestURLShortofPathInfo);
			request.setAttribute("message", message);
			doGet(request, response);
		}
		else
		{
			message = "invalid";
			request.setAttribute("message", message);
			doGet(request, response);
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
