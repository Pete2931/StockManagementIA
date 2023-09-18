package backend;

import java.sql.*;

public class Database {

	// Learned how to connect to mySQL database from this website
	// https://www.javatpoint.com/example-to-connect-to-the-mysql-database
	// Using the connection as a method in order to avoid repetitive coding
	public static Connection connect() throws ClassNotFoundException {

		// This is the location of the database. Must start with jdbc:sqlite:
		String url = "jdbc:mysql://127.0.0.1:3306/tyretest";
		String username = "root";
		String password = "tsiamjo2";

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			System.out.println("Connection Successful");

		}
		return conn;

	}

	// method to add a tyre into the tyre table in the tyretest database while
	// encrypting the data before it
	public static void addTyre(Tyre adding) throws SQLException, ClassNotFoundException {

		System.out.println("Started");

		Connection conn = connect();

		String sql = "INSERT INTO tyres (product_code,product_name,total,width) VALUES (?,?,?,?)";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		// Setting the values used in the PreparedStatement
		pstmt.setString(1, AES.encrypt(adding.product_code, Main.secretKey));

		pstmt.setString(2, AES.encrypt(adding.product_name, Main.secretKey));

		pstmt.setInt(3, adding.total);

		pstmt.setInt(4, adding.width);

		pstmt.executeUpdate();

	}

}
