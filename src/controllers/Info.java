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
import org.mindrot.jbcrypt.BCrypt;

import dao.AccountsD;
import dao.BrandsD;
import dao.CategoriesD;
import dao.ProductsD;
import tables.Accounts;
import tables.Brands;
import tables.Categories;

/**
 * Servlet implementation class Info
 */
@WebServlet(urlPatterns = { "/your-info", "/change-pass"})
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Info() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("account") != null) {
			Accounts account = (Accounts) session.getAttribute("account");
			if(account.getAddress().equals(""))
			{
				request.setAttribute("message","pls add your address before checkout");
			}
			String path = request.getServletPath();
			request.setAttribute("path", path);// send path your-info or change-pass
			LoadBanner(request, response);
			request.getRequestDispatcher("WEB-INF/info.jsp").forward(request, response);
		}
		else
		{
			Cookie path = new Cookie("path",request.getServletPath().substring(1));
			path.setMaxAge(5*60);
			response.addCookie(path);
			response.sendRedirect("/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Accounts account = (Accounts) session.getAttribute("account");
		String message="";
		Boolean check = false;
		String path = request.getServletPath();
		if(path.equals("/your-info") && account != null) {// changing info
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			if(name.length() >= 1 && address.length() >=1 && phone.length() >= 1 && account!=null)
			{
				String email = account.getEmail();
				AccountsD accountsdao = new AccountsD();
				check = accountsdao.updateInfo(email, name, address, phone);
				if(check)
				{
					account.setEmail(email);
					account.setName(name);
					account.setAddress(address);
					account.setPhone(phone);
					session.setAttribute("account", account);
					message = "Your info has been updated !";
				}
				else
				{
					message = "Something's wrong!";
				}
				request.setAttribute("message", message);
				request.setAttribute("path",path);
				LoadBanner(request, response);
				request.getRequestDispatcher("WEB-INF/info.jsp").forward(request, response);
			}
			else
			{
				message = "invalid";
				request.setAttribute("message", message);
				request.setAttribute("path",path);
				LoadBanner(request, response);
				request.getRequestDispatcher("WEB-INF/info.jsp").forward(request, response);
			}
		}
		else {//changing pass equal change-pass
			String currentPasswordP = request.getParameter("currentpassword");
			String newPasswordP = request.getParameter("newpassword");
			if(currentPasswordP.length() >= 1 && newPasswordP.length() >=1 && account!=null)
			{
				String email = account.getEmail();
				String currentPassword = account.getPassword();
				if(BCrypt.checkpw(currentPasswordP, currentPassword)) // if currentPassword true
				{
					AccountsD accountsdao = new AccountsD();
					String hash = accountsdao.changePassword(email, newPasswordP);
					if(hash != null && !hash.isEmpty())
					{
						account.setPassword(hash);// set new password for user object
						session.setAttribute("account", account);// reset user session
						message = "Your password has been changed !";
					}
					else
					{
						message = "Something's wrong !";
					}
					request.setAttribute("message", message);
					request.setAttribute("path",path);
					LoadBanner(request, response);
					request.getRequestDispatcher("WEB-INF/info.jsp").forward(request, response);
				}
				else {
					message = "invalid";
					request.setAttribute("message", message);
					request.setAttribute("path",path);
					LoadBanner(request, response);
					request.getRequestDispatcher("WEB-INF/info.jsp").forward(request, response);
				}
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
