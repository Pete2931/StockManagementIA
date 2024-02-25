package backend;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Upload_To_Database {

	public static void uploadTyres(Tyre headIn) throws ClassNotFoundException {

		Tyre head = headIn;
		Account tempAcc = Main.accountHead;

		if (head != null) {

			if (head.total <= head.getAlert_value()) {

				while (tempAcc != null) {

					if (tempAcc.permission.equals("admin") && tempAcc.getEmail() != null
							&& !tempAcc.getEmail().trim().isEmpty()) {

						Mail.sendEmailTo(tempAcc.getEmail(), head.product_code, head.total);

					}

					tempAcc = tempAcc.next;

				}

			}

			String sql1 = "REPLACE INTO tyres values (?,?,?,?,?);";

			// Getting the connection and creating the PreparedStatement as an object
			try (Connection conn = Database.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

				pstmt1.setString(1, AES.encrypt(head.product_code, Main.secretKey));

				pstmt1.setString(2, AES.encrypt(head.product_name, Main.secretKey));

				pstmt1.setInt(3, head.total);

				pstmt1.setInt(4, head.width);

				pstmt1.setInt(5, head.getAlert_value());

				pstmt1.execute();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			if (head.left != null) {

				uploadTyres(head.left);

			}

			if (head.right != null) {

				uploadTyres(head.right);

			}

		}

		System.out.println("Upload Tyres Successful");

	}

	public static void uploadBins(Bin headIn) throws ClassNotFoundException {

		Bin head = headIn;

		if (head != null) {

			String bin_number = head.shelf + "-" + head.row + "-" + head.bin;

			String sql1 = "REPLACE INTO bins values (?,?,?);";

			// Getting the connection and creating the PreparedStatement as an object
			try (Connection conn = Database.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

				pstmt1.setString(1, bin_number);

				pstmt1.setInt(2, head.length);

				pstmt1.setInt(3, head.length_left);

				pstmt1.execute();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			uploadBins(head.next);

		}

		System.out.println("Upload Bins Successful");

	}

	public static void uploadTyresOnShelves(TyreOnShelfRecord headIn) throws ClassNotFoundException, SQLException {

		TyreOnShelfRecord head = headIn;

		Connection conn = Database.connect();

		if (head != null) {

			String sql1 = "REPLACE INTO tyresonshelves values (?,?,?,?);";

			// Getting the connection and creating the PreparedStatement as an object
			try (PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

				pstmt1.setInt(1, head.id);

				pstmt1.setString(2, AES.encrypt(head.product_code, Main.secretKey));

				pstmt1.setString(3, head.getBin_number());

				pstmt1.setInt(4, head.getTotal());

				pstmt1.execute();

				System.out.println(head.id);
				System.out.println(head.product_code);
				System.out.println(head.getBin_number());
				System.out.println(head.getTotal());

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			uploadTyresOnShelves(head.next);

		}

		System.out.println("Upload Records Successful");

	}

	public static void uploadAccounts(Account headIn) throws ClassNotFoundException, NoSuchAlgorithmException {

		Account head = headIn;

		if (head != null) {

			String sql1 = "REPLACE INTO logins values (?,?,?,?);";

			// Getting the connection and creating the PreparedStatement as an object
			try (Connection conn = Database.connect(); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {

				pstmt1.setString(1, head.username);

				pstmt1.setString(2, head.password);

				pstmt1.setString(3, head.permission);

				pstmt1.setString(4, head.getEmail());

				pstmt1.execute();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			uploadAccounts(headIn.next);

		}

		System.out.println("Upload Accounts Successful");

	}

	public static int findId(String product_code, String bin_number) throws ClassNotFoundException, SQLException {

		// Makes connection to the database using the method above
		Connection conn = Database.connect();

		// Creates an empty SQL statement ready to send to the DB
		Statement stmt = conn.createStatement();

		// Gives the statement to the DB and then stores the returned data inside the
		// result set
		ResultSet rs = stmt.executeQuery("SELECT * FROM tyresonshelves");

		while (rs.next()) {

			int id = rs.getInt("id");

			String product_code_data = rs.getString("product_code");

			String bin_number_data = rs.getString("bin_number");

			if (product_code.equals(product_code_data) && bin_number.equals(bin_number_data)) {

				return id;

			}

		}

		return -1;

	}

}
