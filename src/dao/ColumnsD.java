package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import connection.JdbcConnection;

public class ColumnsD {
	public ArrayList<String> Columns(String table) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		Connection connection = null;
		Statement smt = null;
		String sql = "SHOW columns FROM "+table;
		try {
			connection = JdbcConnection.getJdbcConnection();
			smt = connection.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getString("Field"));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(smt != null)
			{
				try {
					smt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
