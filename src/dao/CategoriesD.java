package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.JdbcConnection;
import tables.Categories;



public class CategoriesD {
	
	public ArrayList<Categories> getCategories()
	{
		ArrayList<Categories> list = new ArrayList<>();
		try {
			Connection connection = JdbcConnection.getJdbcConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT name,gender FROM `categories`";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next())
			{
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				list.add(new Categories(name, gender));
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
