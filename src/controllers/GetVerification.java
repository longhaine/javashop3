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
import dao.MailD;
import dao.VerificationsD;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class GetVerification
 */
@WebServlet("/register/get-verification")
public class GetVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVerification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadBanner(request, response);
		request.getRequestDispatcher("/WEB-INF/get-verification.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountsD accountsdao = new AccountsD();
		VerificationsD verificationsdao = new VerificationsD();
		MailD maildao = new MailD();
		String message;
		if(request.getParameter("email")!= null)
		{
			String email = request.getParameter("email");
			if(accountsdao.checkAccount(email) == false)
			{
				String pathInfo = verificationsdao.getPathInfoByEmail(email);
				String requestURL = String.valueOf(request.getRequestURL());
				requestURL = requestURL.replace(request.getRequestURI(),request.getContextPath()+"/register/verification/"+pathInfo);
				if(pathInfo != null && maildao.sendVerifiedLink(requestURL, email) == true)
				{
					message = "We've sent you a link, please check your email for verifying your account !"; 	
				}
				else if(accountsdao.checkVerifiedAccount(email) == true){
					message = "This email already was verified!";
				}
				else {
					message = "Something wrong!";
				}
			}
			else
			{
				message = "This email already haven't registered yet!!!";
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
}
