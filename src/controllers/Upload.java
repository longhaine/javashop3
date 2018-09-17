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
import org.json.JSONArray;
import org.json.JSONObject;

import dao.ProductsD;
/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JSONObject user = (JSONObject) session.getAttribute("user");
		JSONObject product = new JSONObject();
		if(ServletFileUpload.isMultipartContent(request) && user.getInt("role")== 1)
		{
			try
			{

				String path =  getServletContext().getRealPath("")+"img"+File.separator+"h&m";
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				String newfile ="";
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
					}

				}
				ProductsD productsdao = new ProductsD();
				String message = productsdao.Add(product);
				response.getWriter().append(newfile+" "+message);
			}
			catch(Exception ex)
			{
				response.getWriter().append("something's wrong");
			}
		}
		else {
			response.getWriter().append("something's wrong");
		}
	}

}
