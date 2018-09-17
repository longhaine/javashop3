package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcConnection {
	
	public static Connection getJdbcConnection() {
		//test
//		final String url = "jdbc:mysql://localhost:3306/webshop";
		final String url = "jdbc:mysql://45.61.159.32/NLbWWQGOLS";
		final String user = "NLbWWQGOLS";
		final String password = "jNeyTUIhgu";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
