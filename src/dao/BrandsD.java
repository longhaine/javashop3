package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.JdbcConnection;
import tables.Brands;

public class BrandsD {
	public ArrayList<Brands> getBrands()
	{
		ArrayList<Brands> list = new ArrayList<>();
		String sql = "SELECT name FROM brands";
		Connection connection = JdbcConnection.getJdbcConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next())
			{
				String name = rs.getString("name");
				list.add(new Brands(name));
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
