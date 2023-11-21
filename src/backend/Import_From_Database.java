package backend;

import java.sql.*;

public class Import_From_Database {


	public static void importTyres() {

		try {
			// Makes connection to the database using the method above
			Connection conn = Database.connect();

			// Creates an empty SQL statement ready to send to the DB
			Statement stmt = conn.createStatement();

			// Gives the statement to the DB and then stores the returned data inside the
			// result set
			ResultSet rs = stmt.executeQuery("SELECT * FROM tyres");

			while (rs.next()) {

				// for each result, in the result set, get the string data and prints it
				String product_code = AES.decrypt(rs.getString("product_code"), Main.secretKey);

				String product_name = AES.decrypt(rs.getString("product_name"), Main.secretKey);

				int total = rs.getInt("total");

				int width = rs.getInt("width");
				
				int alert_value = rs.getInt("alert_value");

				Tyre newTyre = new Tyre(product_code, product_name, total, width, alert_value);

				if (Main.tyreHead == null) {

					Main.tyreHead = newTyre;
					System.out.println("Changed tyre head");

				} else {

					BinarySearchTree.addTyre(Main.tyreHead, newTyre);
					System.out.println("Added bus");

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void importBins() {

		Bin tail = new Bin();

		// This is the part for selecting records from SQL
		try {
			// Makes connection to the database using the method above
			Connection conn = Database.connect();

			// Creates an empty SQL statement ready to send to the DB
			Statement stmt = conn.createStatement();

			// Gives the statement to the DB and then stores the returned data inside the
			// result set
			ResultSet rs = stmt.executeQuery("select * from bins;");

			while (rs.next()) {

				// for each result, in the result set, get the string data and prints it
				String bin_number = rs.getString("bin_number");
				
				String[] binStrArray = bin_number.split("-",3);
				
				int shelfIn = Integer.parseInt(binStrArray[0]);
				
				int rowIn = Integer.parseInt(binStrArray[1]);
				
				int binIn = Integer.parseInt(binStrArray[2]);

				int lengthIn = rs.getInt("length");

				int length_leftIn = rs.getInt("length_left");

				Bin newBin = new Bin();

				newBin.setBin_number(bin_number);
				newBin.shelf = shelfIn;
				newBin.row = rowIn;
				newBin.bin = binIn;
				newBin.length = lengthIn;
				newBin.length_left = length_leftIn;

				if (Main.binHead == null) {

					Main.binHead = newBin;
					tail = newBin;

				} else {

					tail.next = newBin;
					tail = newBin;

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void importTyresOnShelves() {
		
		TyreOnShelfRecord tail = new TyreOnShelfRecord();
		
		// This is the part for selecting records from SQL
				try {
					// Makes connection to the database using the method above
					Connection conn = Database.connect();

					// Creates an empty SQL statement ready to send to the DB
					Statement stmt = conn.createStatement();

					// Gives the statement to the DB and then stores the returned data inside the
					// result set
					ResultSet rs = stmt.executeQuery("select * from tyresonshelves;");

					while (rs.next()) {
						
						int id = rs.getInt("id");
						
						String product_code = rs.getString("product_code");
						
						String bin_number = rs.getString("bin_number");

						int total = rs.getInt("total");

						TyreOnShelfRecord newRecord = new TyreOnShelfRecord();
						
						newRecord.id = id;
						newRecord.product_code = AES.decrypt(product_code, Main.secretKey);
						newRecord.setBin_number(bin_number);
						newRecord.setTotal(total);
	

						if (Main.recordHead == null) {

							Main.recordHead = newRecord;
							Main.recordTail = newRecord;
							tail = newRecord;

						} else {

							tail.next = newRecord;
							tail = newRecord;
							Main.recordTail = tail;

						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	public static void importAccounts() {

		Account tail = new Account();

		// This is the part for selecting records from SQL
		try {
			// Makes connection to the database using the method above
			Connection conn = Database.connect();

			// Creates an empty SQL statement ready to send to the DB
			Statement stmt = conn.createStatement();

			// Gives the statement to the DB and then stores the returned data inside the
			// result set
			ResultSet rs = stmt.executeQuery("select * from logins;");

			while (rs.next()) {

				// for each result, in the result set, get the string data and prints it
				String username = rs.getString("username");

				String password = rs.getString("password_hash");

				String permission = rs.getString("permission");
				
				String email = rs.getString("email");

				Account newAcc = new Account();

				newAcc.username = username;
				newAcc.password = password;
				newAcc.permission = permission;
				newAcc.setEmail(email);
				
				if (Main.accountHead == null) {

					Main.accountHead = newAcc;
					tail = newAcc;
					Main.accountTail = newAcc;

				} else {

					tail.next = newAcc;
					tail = newAcc;
					Main.accountTail = newAcc;

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
