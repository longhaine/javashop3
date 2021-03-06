package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BrandsD;
import dao.CategoriesD;
import dao.ProductsD;
import tables.Brands;
import tables.Categories;
import tables.Products;

/**
 * Servlet implementation class Product
 */
@WebServlet("/product/*")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Product() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadBanner(request, response);//load banner
		if(request.getPathInfo() != null)
		{
			String id = request.getPathInfo().substring(1); // get id product
			Products product = LoadProduct(request, response, id);// pass id and get product
			if(product != null)
			{
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/product.jsp").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/WEB-INF/default.jsp").forward(request, response);
			}
		}
		else {
			request.getRequestDispatcher("/WEB-INF/default.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected Products LoadProduct(HttpServletRequest request, HttpServletResponse response,String id) throws ServletException, IOException {
		ProductsD productsdao = new ProductsD();
		Products product = productsdao.getProductById(id);
		return product;
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
