package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import connection.JdbcConnection;
import tables.Products;

public class Orders_DetailsD {
	public Boolean insertOrder_Details(int idOrder ,ArrayList<Products> cartList) throws IOException {
		Connection connection = null;
		Statement st  = null;
		String sql = "INSERT INTO orders_details (`id_orders`,`id_products`) VALUES";
		for(int i = 0,size = cartList.size() ; i < size ; i++) // insert multi value
		{
			if(i == 0) {
				sql = sql + "("+idOrder+","+cartList.get(i).getId()+")";	
			}
			else {
				sql = sql + ",("+idOrder+","+cartList.get(i).getId()+")";	
			}
		}
		try {
			connection = JdbcConnection.getJdbcConnection();
			st = connection.createStatement();
			int check = 0; 
			check = st.executeUpdate(sql);
			if(check>0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection!= null) {
				try {connection.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(st!= null)
			{
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return false;
	}
	public ArrayList<Integer> getProductListbyOrder(int id){
		ArrayList<Integer> list = new ArrayList<>();
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "select id_products from orders_details where id_orders = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
				list.add(rs.getInt("id_products"));
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
