package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import connection.JdbcConnection;
import tables.Accounts;

public class AccountsD {
	public Accounts Login(String emailL, String passwordL){
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT * FROM accounts WHERE email = ?";
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, emailL);
			ResultSet rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				return null;
			}
			while(rs.next()){
				String email  = rs.getString("email");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				int role = rs.getInt("role");
				int verification = rs.getInt("verification");
				if(BCrypt.checkpw(passwordL, password))
				{
					Accounts account = new Accounts(email, password, name, address, phone, role,verification);
					return account;
				}
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
		return null;
	}
	public boolean checkAccount(String email) {
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "select `email` from accounts where email =?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(!rs.isBeforeFirst())
			{
				connection.close();
				return true;
			}
			else{
				connection.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean insertAccount(String email,String password, String name) {
		String hash = BCrypt.hashpw(password, BCrypt.gensalt(6));
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "INSERT INTO `accounts`(`email`, `password`, `name`) VALUES (?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, hash);
			ps.setString(3, name);
			int check = ps.executeUpdate();
			connection.close();
			if(check ==1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String Register(String email, String password, String name, String requestURLShortofPathInfo) throws IOException {
		if(checkAccount(email)) 
		{
			VerificationsD verification = new VerificationsD();
			MailD mail = new MailD();
			if(insertAccount(email, password, name) && verification.insertVerified(email))
			{
				String pathInfo = verification.getPathInfoByEmail(email);
				if(mail.sendVerifiedLink(requestURLShortofPathInfo+pathInfo, email))
					return "Your account has been successfully created, please check your email for verified link.";
				else
					return "Something's wrong";
			}
			else
				return "Something's wrong";
		}
		else {
			return "The email-address you entered is already in use.";
		}
	}
	public boolean updateInfo(String email, String name, String address,String phone) throws IOException {
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "UPDATE `accounts` SET `name`= ?,`address`= ?,`phone`= ?  WHERE email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, address);
			ps.setString(3, phone);
			ps.setString(4, email);
			int check = ps.executeUpdate();
			connection.close();
			if(check == 1) 
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String changePassword(String email, String password) throws IOException {
		String hash = BCrypt.hashpw(password, BCrypt.gensalt(6));
		Connection connection = JdbcConnection.getJdbcConnection();
		String sql = "UPDATE `accounts` SET `password`= ?  WHERE email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, hash);
			ps.setString(2,email);
			int check = ps.executeUpdate();
			connection.close();
			if(check == 1)
			return hash;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateVerification(String email){
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "UPDATE `accounts` SET `verification` = 1 WHERE `email` = ?";
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
	public boolean checkVerifiedAccount(String email){ // true if It already verified
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "SELECT `verification` FROM `accounts` WHERE `email` = ?";
		int verification = 0;
		try {
			connection = JdbcConnection.getJdbcConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				verification = rs.getInt("verification");
			}
			if(verification == 1)
			{
				return true;
			}
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
		return false;
	}
}
