package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import dao.BrandsD;
import dao.CategoriesD;
import dao.ProductsD;
import tables.Products;
/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gender = request.getServletPath();
		if(gender == null)
		{
			response.getWriter().append("null");
		}
		else
		{
			gender = gender.substring(1);
			System.out.println(gender);
			if(gender.equals("men")||gender.equals("women"))
			{
				CategoriesD specificsdao = new CategoriesD();
				BrandsD branddao = new BrandsD();
				ProductsD itemsdao = new ProductsD();
//				JSONArray specifics = specificsdao.getCategories();
//				request.setAttribute("specifics",specifics); // specifics
//				JSONArray brand = branddao.getBrands();
//				request.setAttribute("brand", brand);
				//load banner
				// load distinct stuff
				String stuff_parameter = request.getParameter("stuff");
				request.setAttribute("stuff_parameter", stuff_parameter);
				String specifics_parameter = request.getParameter("specifics");
				request.setAttribute("specifics_parameter", specifics_parameter);
				//
				ArrayList<Products> allItems = itemsdao.getProductsByGender(gender);
				request.setAttribute("allItems",allItems );
				request.setAttribute("gender", gender);
				request.getRequestDispatcher("WEB-INF/shop.jsp").forward(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response,String gender) throws ServletException, IOException {

	}
	protected void doGender(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Men or Women?");
	}
}
