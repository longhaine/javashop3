package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountsD;
import dao.BrandsD;
import dao.CategoriesD;
import dao.VerificationsD;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Verification
 */
@WebServlet("/register/verification/*")
public class Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo()!= null)
		{
			VerificationsD verificationdao = new VerificationsD();
			AccountsD accountdao = new AccountsD();
			LoadBanner(request, response);
			String message;
			String pathInfo = request.getPathInfo().substring(1);
			String email = verificationdao.getVerificationByPathInfo(pathInfo);
			if(email != null) // check existence pathInfo and email
			{
				if(verificationdao.deleteVerified(email) && accountdao.updateVerification(email))
				{
					message = "Verification success!";
				}
				else {
					message = "Something Wrong!";
				}
				request.setAttribute("message", message);
				request.getRequestDispatcher("/WEB-INF/verification.jsp").forward(request, response);
			}
			else
			{
				response.sendRedirect(request.getContextPath()+"/");
			}
		}
		else {
			response.sendRedirect(request.getContextPath()+"/");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
