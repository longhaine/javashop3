package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import org.json.JSONObject;

import connection.JdbcConnection;
import tables.Orders;

public class OrdersD {
	public int Order(JSONObject orderInfo ,float price) throws IOException {
		int idOrder = -1;
		String email = orderInfo.getString("email");
		String name = orderInfo.getString("name");
		String address = orderInfo.getString("address");
		String phone = orderInfo.getString("phone");
		String sessionId = orderInfo.getString("sessionId");
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "INSERT INTO `orders`(`email`,`name`,`address`,`phone`,`sessionId`,`price`) VALUES ('"+email+"','"+name+"','"+address+"','"+phone+"','"+sessionId+"','"+price+"')";
		try {
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql, stm.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
			{
				idOrder = rs.getInt(1);
			}
			return idOrder;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idOrder;
	}
	public ArrayList<Orders> getOrderListbyGuest(String sessionId){
		Connection connection = JdbcConnection.getJdbcConnection();
		ArrayList<Orders> list = new ArrayList<>();
		String sql = "SELECT id,date,price FROM orders WHERE sessionId =  ?  ORDER BY id DESC";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sessionId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String date = rs.getString("date");
				float price = rs.getFloat("price");
				list.add(new Orders(id, date, price));
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Orders> getOrderListbyAccount(String email){
		ArrayList<Orders> list = new ArrayList<>();
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "SELECT id,date,price FROM orders WHERE email =  ?  ORDER BY id DESC";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs =ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String date = rs.getString("date");
				float price = rs.getFloat("price");
				list.add(new Orders(id, date, price));
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean CheckOrderGuest(String sessionId,int id) throws IOException {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM orders WHERE id =  ? and sessionId = ?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, sessionId);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst())
				return true;
			else
				return false;
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
		return false;
	}
	public boolean checkOrderbyAccount(String email,int id) throws IOException {
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "SELECT id FROM orders WHERE id =  ? and email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, email);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst())
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
