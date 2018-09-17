package dao;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import connection.JdbcConnection;
import tables.Products;

public class ProductsD {
	public ArrayList<Products> getPopularItems(){
		ArrayList<Products> list = new ArrayList<>();
		String sql = "SELECT products.id, products.name, products.price, products.image, products.gender , brands.name as name_brands FROM products inner join brands on products.id_brands = brands.id limit 5";
		Connection connection = JdbcConnection.getJdbcConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				list.add(new Products.builder(id, name, price, image, gender).setName_Brands(name_brands).add());
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Products> getProductsByGender(String genderP){
		ArrayList<Products> list = new ArrayList<>();
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "SELECT products.id, products.name, products.price, products.image, products.gender, "
				+ "brands.name as name_brands, categories.name as name_categories FROM products"
				+ " INNER JOIN brands on products.id_brands = brands.id "
				+ "INNER JOIN categories ON products.id_categories = categories.id WHERE products.gender = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, genderP);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				list.add(new Products.builder(id, name,price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add());
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Products> getProductByGenderandCategory(String genderP,String categoryP) throws IOException {
		ArrayList<Products> list = new ArrayList<>();
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "SELECT products.id, products.name, products.price, products.image, products.gender,"
				+ " brands.name as name_brands, categories.name as name_categories FROM products "+ 
			    "INNER JOIN brands on products.id_brands = brands.id "+ 
			    "INNER JOIN categories ON products.id_categories = categories.id WHERE products.gender = ? and categories.name = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, genderP);
			ps.setString(2, categoryP);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				list.add(new Products.builder(id, name, price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add());
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Products getProductById(String idP) {
		Products product = null;
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "SELECT products.id, products.name, products.price, products.image, products.gender,"
				+ " brands.name as name_brands, categories.name as name_categories FROM products "+
			    "INNER JOIN brands on products.id_brands = brands.id "+
			    "INNER JOIN categories ON products.id_categories = categories.id WHERE products.id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, idP);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				product = new Products.builder(id, name, price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add();
			}
			connection.close();
			ps.close();
			return product;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Products> getAll() throws IOException {
		ArrayList<Products> list = new ArrayList<>();
		Connection connection = null;
		Statement smt = null;
		String sql = "SELECT products.id, products.name, products.price, products.image, products.gender,"
				+ " brands.name as name_brands, categories.name as name_categories FROM products "+
			    "INNER JOIN brands on products.id_brands = brands.id "+
			    "INNER JOIN categories ON products.id_categories = categories.id order by products.id desc";
		try {
			connection = JdbcConnection.getJdbcConnection();
			smt = connection.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				list.add(new Products.builder(id, name,price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add());	
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection != null)
			{
				try {connection.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(smt != null)
			{
				try {smt.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return null;
	}
	public String Add(JSONObject product) throws IOException {
		JSONObject request = new JSONObject();
		request.put("product", product);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("https://serverjavashop.herokuapp.com/products/add");
		StringEntity params = new StringEntity(request.toString());
		post.addHeader("content-type", "application/json");
		post.setEntity(params);
		HttpResponse response = httpClient.execute(post);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = responseHandler.handleResponse(response);
		httpClient.close();
		JSONObject jsoObject = new JSONObject(responseBody);
		String message = jsoObject.getString("message");
		return message;
	}
	public String Update(JSONObject product,String id) throws IOException {
		JSONObject request = new JSONObject();
		request.put("product", product);
		request.put("id", id);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("https://serverjavashop.herokuapp.com/products/update");
		StringEntity params = new StringEntity(request.toString());
		post.addHeader("content-type", "application/json");
		post.setEntity(params);
		HttpResponse response = httpClient.execute(post);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = responseHandler.handleResponse(response);
		httpClient.close();
		JSONObject jsoObject = new JSONObject(responseBody);
		return responseBody;
	}
	public ArrayList<Products> searchProductbyQuery(String q){
		Connection connection = null;
		PreparedStatement ps = null;
		ArrayList<Products> list = new ArrayList<>();
		String sql ="SELECT  products.id, products.name, products.price, products.image, products.gender, "
				+ "brands.name as name_brands, categories.name as name_categories FROM products " + 
				"INNER JOIN brands on products.id_brands = brands.id " + 
				"INNER JOIN categories ON products.id_categories = categories.id WHERE MATCH (products.name) AGAINST (?)";
		
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, q);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				list.add(new Products.builder(id, name, price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add());
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(connection != null)
			{
				try {connection.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(ps != null)
			{
				try {ps.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return null;
	}
	public ArrayList<Products> searchProductbyQueryandCategory(String q,String category){
		ArrayList<Products> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT  products.id, products.name, products.price, products.image, products.gender, " + 
				"brands.name as name_brands, categories.name as name_categories FROM products " + 
				"INNER JOIN brands on products.id_brands = brands.id " + 
				"INNER JOIN categories ON products.id_categories = categories.id WHERE MATCH (products.name) AGAINST (?) and categories.name = ?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, q);
			ps.setString(2, category);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				String image = rs.getString("image");
				String gender = rs.getString("gender");
				String name_brands = rs.getString("name_brands");
				String name_categories = rs.getString("name_categories");
				list.add(new Products.builder(id, name, price, image, gender).setName_Brands(name_brands).setName_Categories(name_categories).add());
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection != null)
			{
				try {connection.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(ps != null)
			{
				try {ps.close();} catch (SQLException e) {e.printStackTrace();}
			}	
		}
		return null;
	}
}
