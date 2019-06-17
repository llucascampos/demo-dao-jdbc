package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String DRIVE = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/oficina?useSSL=false";
	private final static String USER = "root";
	private final static String PASS = "";

	public static Connection getConnection() {
		try {
			Class.forName(DRIVE);

			return DriverManager.getConnection(URL, USER, PASS);

		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexão: "+ e);

		}

	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeConnection(Connection conn, PreparedStatement stmt) {
		closeConnection(conn);

		try {
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
		closeConnection(conn, stmt);

		try {
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}