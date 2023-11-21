package backend;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Upload_To_Database {

	public static void uploadTyres(Tyre headIn) throws ClassNotFoundException {

		Tyre head = headIn;
		Account tempAcc = Main.accountHead;

		if (head != null) {

			if (head.total <= head.getAlert_value()) {
				
				while(tempAcc != null) {
					
					if (tempAcc.permission.equals("admin") && tempAcc.getEmail() != null && !tempAcc.getEmail().trim().isEmpty()) {
						
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

			if (head.next != null) {

				uploadBins(head.next);

			}

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

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			if (head.next != null) {

				uploadTyresOnShelves(head.next);

			}

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

			if (head.next != null) {

				uploadAccounts(headIn.next);

			}

		}

		System.out.println("Upload Accounts Successful");

	}

}
