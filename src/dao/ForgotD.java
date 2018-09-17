package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import connection.JdbcConnection;
import controllers.Verification;

public class ForgotD {
	public boolean insertForgot(String email) {
		Connection connection = null;
		PreparedStatement ps = null;
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String pathInfo = email+date+"forgot";
		String sql = "INSERT INTO `forgot`(`email`, `pathInfo`) VALUES (?,MD5(?))"; 
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pathInfo);
			int check = ps.executeUpdate();
			if(check == 1)
			{
				return true;
			}
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
	public boolean deleteForgot(String email) {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM `forgot` WHERE `email` = ?"; 
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			int check = ps.executeUpdate();
			if(check == 1)
			{
				return true;
			}
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
	public String getForgotByPathInfo(String pathInfo) {
		Connection connection = null;
		PreparedStatement ps = null;
		String email = null;
		String sql = "SELECT `email` FROM `forgot` WHERE `pathInfo` = ?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, pathInfo);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				email = rs.getString("email");
			}
			return email;
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
		return email;
	}
	public String getPathInfoByEmail(String email) {
		Connection connection = null;
		PreparedStatement ps = null;
		String pathInfo = null;
		String sql = "SELECT `pathInfo` FROM `forgot` WHERE `email` = ?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				pathInfo = rs.getString("pathInfo");
			}
			return pathInfo;
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
		return pathInfo;
	}
}
