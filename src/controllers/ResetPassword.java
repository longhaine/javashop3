package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.AccountsD;
import dao.BrandsD;
import dao.CategoriesD;
import dao.ForgotD;
import dao.VerificationsD;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class ResetPassword
 */
@WebServlet("/forgot-password/reset-password/*")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = "get";
		if(request.getPathInfo()!= null)
		{
			ForgotD forgotdao = new ForgotD();
			LoadBanner(request, response);
			String pathInfo = request.getPathInfo().substring(1);
			String email = forgotdao.getForgotByPathInfo(pathInfo);
			if(email != null) // check existence pathInfo and get email;
			{
				request.setAttribute("method", method);
				request.setAttribute("pathInfo", pathInfo);
				request.getRequestDispatcher("/WEB-INF/reset-password.jsp").forward(request, response);
			}
			else
			{
				response.sendRedirect("/");
			}
		}
		else {
			response.sendRedirect("/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo() != null)
		{
			String method = "post";
			LoadBanner(request, response);
			ForgotD forgotdao = new ForgotD();
			AccountsD accountsdao = new AccountsD();
			String pathInfo = request.getPathInfo().substring(1);
			String email = forgotdao.getForgotByPathInfo(pathInfo);
			String password = request.getParameter("password");
			String message ="Something wrong!";
			if(email != null)
			{
				message = accountsdao.changePassword(email, password);
				if(message != null)
				{
					message = "Your password has been changed!";
					forgotdao.deleteForgot(email);
				}
			}
			request.setAttribute("message", message);
			request.setAttribute("method", method);
			request.getRequestDispatcher("/WEB-INF/reset-password.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/");
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
