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
import dao.ColumnsD;
import dao.ProductsD;
import tables.Accounts;
import tables.Brands;
import tables.Categories;
import tables.Products;

/**
 * Servlet implementation class Login
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			Accounts account = (Accounts)session.getAttribute("account");
			if(account != null && account.getRole() == 1) {
			ColumnsD columnsdao = new ColumnsD();
			ProductsD productsdao = new ProductsD();
			CategoriesD categoriesdao = new CategoriesD();
			BrandsD brandsdao = new BrandsD();
			ArrayList<String> columns = columnsdao.Columns("products");
			ArrayList<Products> productList = productsdao.getAll();
			ArrayList<Categories> categories = categoriesdao.getCategories();
			ArrayList<Brands> brands = brandsdao.getBrands();
			request.setAttribute("brands", brands);
			request.setAttribute("categories", categories);
			request.setAttribute("columns", columns);
			request.setAttribute("productList", productList);
			request.getRequestDispatcher("WEB-INF/admin/admin.jsp").forward(request, response);
			}
			else {
				response.sendRedirect("/");
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
