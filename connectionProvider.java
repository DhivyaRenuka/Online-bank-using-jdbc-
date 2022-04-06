package BankMngtApp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionProvider {

	static Connection conn;

	public static Connection connect() {

		try {
		final String url = "jdbc:postgresql://localhost:5432/BankAccountMgtApp";
			final String user = "postgres";
			final String password = "SRDS@2000s";
			conn = DriverManager.getConnection(url, user, password);
			if (conn.isClosed()) {
				System.out.println("Connection made unsuccessfully.");
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;

	}
}
