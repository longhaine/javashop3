package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import dao.ProductsD;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JSONObject user = (JSONObject) session.getAttribute("user");
		JSONObject product = new JSONObject();
		ProductsD productsdao = new ProductsD();
		if(user.getInt("role")== 1 && ServletFileUpload.isMultipartContent(request))
		{
			try
			{

				String path =  getServletContext().getRealPath("")+"img"+File.separator+"h&m";
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				String newfile ="";
				String id = "";
				for(FileItem item : multiparts)
				{
					if(!item.isFormField())
					{
						String nameFile = new File(item.getName()).getName();
						newfile = path + File.separator + nameFile;
						item.write(new File(newfile));
					}
					if(item.isFormField())
					{
						product.put(item.getFieldName(), item.getString());
						if(item.getFieldName().equals("name"))
						{
							product.put("image", "img/h&m/"+item.getString());
						}
						if(item.getFieldName().equals("id"))
						{
							id = item.getString();
						}
					}

				}
				product.remove("id");
				String message = productsdao.Update(product,id);
				response.getWriter().append(newfile+" "+message);
			}
			catch(Exception ex)
			{
				try
				{
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					String newfile ="";
					for(FileItem item : multiparts)
					{
						if(item.isFormField())
						{
							product.put(item.getFieldName(), item.getString());
							if(item.getFieldName().equals("name"))
							{
								product.put("image", "img/h&m/"+item.getString());
							}
						}

					}
					String id = product.getString("id");
					product.remove("id");
					String message = productsdao.Update(product,id);
					response.getWriter().append(message);
				}
				catch(Exception ex2)
				{
					response.getWriter().append(ex2.toString());
				}

			}
		}
		else {
			response.getWriter().append("something's wrong");
		}
	}

}
