package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Parser;

import org.json.JSONArray;

import dao.BrandsD;
import dao.CategoriesD;
import dao.ProductsD;
import tables.Brands;
import tables.Categories;
import tables.Products;

/**
 * Servlet implementation class Shop
 */
@WebServlet(urlPatterns = { "/shop/*"})
public class Shop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Shop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo()!= null)
		{
			String genderAndCategory [] = request.getPathInfo().substring(1).split("/");
			String gender = genderAndCategory[0];
			String category = null;
			if(genderAndCategory.length > 1)
			{
//				System.out.println(genderAndCategory[1]);
//				int indexOfQuestionMark = genderAndCategory[1].indexOf("?");
//				System.out.println(indexOfQuestionMark);
//				if(indexOfQuestionMark > -1)
//				{
					category = genderAndCategory[1];	
//				}
			}
			if(gender.equals("men")||gender.equals("women"))
			{
				doHandle(request, response, gender,category);
			}
			else
			{
				doGender(request, response);
			}
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response,String gender,String category) throws ServletException, IOException {
		//load banner
		LoadBanner(request, response);
		//load products
		LoadProductByGender(request, response, gender,category);
		request.getRequestDispatcher("/WEB-INF/shop.jsp").forward(request, response);
	}
	protected void doGender(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Men or Women?");
	}
	protected void LoadBanner(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesD categoriesdao = new CategoriesD();
		BrandsD brandsdao = new BrandsD();
		ArrayList<Categories> categories = categoriesdao.getCategories();
		request.setAttribute("categories",categories); // specifics
		ArrayList<Brands> brands = brandsdao.getBrands();
		request.setAttribute("brands", brands);
	}
	protected void LoadProductByGender(HttpServletRequest request, HttpServletResponse response,String gender,String category) throws ServletException, IOException {
		ProductsD productsdao = new ProductsD();
		ArrayList<Products> productList = null;
		if(category == null){
			category = "all";
			productList = productsdao.getProductsByGender(gender);
		}
		else {
			productList = productsdao.getProductByGenderandCategory(gender, category);
		}
		request.setAttribute("categoryParameter", category);
		request.setAttribute("productList",productList );
		request.setAttribute("gender", gender);
		
	}
}
