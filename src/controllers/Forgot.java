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
import dao.ForgotD;
import dao.MailD;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Forgot
 */
@WebServlet("/forgot-password")
public class Forgot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Forgot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadBanner(request, response);
		request.getRequestDispatcher("WEB-INF/forgot.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountsD accountsdao = new AccountsD();
		ForgotD forgotdao = new ForgotD();
		String message;
		if(request.getParameter("email")!= null)
		{
			String email = request.getParameter("email");
			if(accountsdao.checkAccount(email) == false) // if account exists == false
			{
				String pathInfo = forgotdao.getPathInfoByEmail(email);
				if(pathInfo != null) // if forgotten of the email already inserted
				{
					message = sendingMail(request, response, email, pathInfo);
				}
				else {// if it didn't
					boolean check = forgotdao.insertForgot(email); // insert forgot
					if(check == true)
					{
						pathInfo = forgotdao.getPathInfoByEmail(email);
						message = sendingMail(request, response, email, pathInfo);
					}
					else {
						message ="Something wrong!";
					}
				}
			}
			else
			{
				message = "The email doesn't exist";
			}
		}
		else {
			message = "Invalid!";
		}
		request.setAttribute("message", message);
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
	protected String sendingMail(HttpServletRequest request, HttpServletResponse response,String email, String pathInfo) throws ServletException, IOException {
		MailD maildao = new MailD();
		String requestURL = String.valueOf(request.getRequestURL());
		requestURL = requestURL.replace(request.getRequestURI(),request.getContextPath()+"/forgot-password/reset-password/"+pathInfo);
		if(maildao.sendResetPasswordLink(requestURL, email))
		{
			return "We've sent you a link, please check your email for resetting your password!";
		}
		else {
			return "Something wrong!";
		}
	}
}
