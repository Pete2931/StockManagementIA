package backend;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Operations {

	public static int addTyreToShelf(String product_code, int amount) {

		if (Main.permission.equals("admin")) {

			Bin smallestBin = null;

			Bin tempBin = new Bin();

			int minLengthLeft;

			Tyre actualProduct = searchTyreCode(Main.tyreHead, product_code);

			boolean updatedRecord = false;

			actualProduct.total = actualProduct.total + amount;

			// The loop runs one stage of the bin-packing algorithm every time it completes
			// a loop
			for (int i = 0; i < amount; i++) {

				smallestBin = null;

				System.out.println("Runs " + (i + 1) + " stage");

				tempBin = Main.binHead;

				minLengthLeft = 1000000;

				var shelf = 0;

				var row = 0;

				var bin = 0;

				TyreOnShelfRecord tempRec = new TyreOnShelfRecord();

				if (Main.recordHead != null) {

					tempRec = Main.recordHead;

				} else {

					tempRec = new TyreOnShelfRecord();

					Main.recordHead = tempRec;

				}

				updatedRecord = false;

				// Finding the bin with the least amount of space left for best fit bin packing
				// algorithm
				while (tempBin != null) {

					System.out.println("1");

					if (tempBin.length_left < minLengthLeft && tempBin.length_left > actualProduct.width) {

						minLengthLeft = tempBin.length_left;

						shelf = tempBin.shelf;

						row = tempBin.row;

						bin = tempBin.bin;

						smallestBin = tempBin;

					}

					tempBin = tempBin.next;

				}

				// If there is not enough space left in any bin.
				if (smallestBin == null) {

					System.out.println("There is not enough space left in any shelves anymore. There are "
							+ (amount - (i)) + "tyres left over.");
					
					actualProduct.total = actualProduct.total - (amount - i);

					return amount - (i);

				}

				System.out.println(shelf + "-" + row + "-" + bin);

				tempBin = Main.binHead;

				smallestBin.length_left = smallestBin.length_left - actualProduct.width;

				// Running through the Records of tyres on shelves
				while (tempRec != null && updatedRecord == false) {

					System.out.println("3");

					// If there is an existing record, add 1 to the total tyres on that shelf
					if (tempRec.getBin_number().equals(shelf + "-" + row + "-" + bin)
							&& tempRec.product_code.equals(product_code)) {

						tempRec.setTotal(tempRec.getTotal() + 1);
						
						System.out.println("Updated a record");

						// Indicating that the record has been updated
						updatedRecord = true;

					}

					tempRec = tempRec.next;

				}

				// If the record was not updated
				if (updatedRecord == false) {

					System.out.println("4");

					// Creating a new record
					TyreOnShelfRecord adding = new TyreOnShelfRecord();

					adding.setBin_number(shelf + "-" + row + "-" + bin);

					adding.product_code = product_code;

					adding.setTotal(1);

					Main.recordTail.next = adding;

					Main.recordTail = adding;

					System.out.println("Created a new record");

					updatedRecord = true;

				}

			}

			return 0;

		} else {

			System.out.println("You do not have the permission to do that.");

		}

		return -1;

	}

	public static int TyresLeft(Tyre n, String product_code) {

		if (n != null) {

			if (n.product_code.equals(product_code)) {

				return n.total;

			} else if (product_code.compareTo(n.product_code) > 0) {

				return TyresLeft(n.right, product_code);

			} else if (product_code.compareTo(n.product_code) < 0) {

				return TyresLeft(n.left, product_code);

			} else {

				return 0;
			}

		} else {

			return 0;

		}

	}

	public static void printBins(Bin head) {

		// Running through the entire linked list
		while (true) {

			// Stuff we want to do with the data inside the node
			System.out.println("Bin: " + head.shelf + "-" + head.row + "-" + head.bin);
			System.out.println("Length Left: " + head.length_left);

			// Checking if the node has a pointer to another node
			if (head.next != null) {

				// If there is, set the temp1 as the next node
				head = head.next;

			} else {
				// If there is not, the while loop is broken
				break;
			}

		}

	}

	public static void printTyreShelfRecords(TyreOnShelfRecord head) {

		// Running through the entire linked list
		while (true) {

			// Stuff we want to do with the data inside the node
			System.out.println("Product Code: " + head.product_code);
			System.out.println("Bin_number: " + head.getBin_number());
			System.out.println("Total: " + head.getTotal());

			// Checking if the node has a pointer to another node
			if (head.next != null) {

				// If there is, set the temp1 as the next node
				head = head.next;

			} else {
				// If there is not, the while loop is broken
				break;
			}

		}

	}

	public static void showAllTyres(Tyre n) {

		if (n != null) {

			System.out.println("Product code: " + n.product_code);
			System.out.println("Product name: " + n.product_name);
			System.out.println("Total in warehouse: " + n.total);

			showAllTyres(n.left);
			showAllTyres(n.right);

		}

	}

	public static Tyre searchTyreCode(Tyre n, String product_code) {

		if (n != null) {

			if (n.product_code.equals(product_code)) {

				return n;

			} else if (product_code.compareTo(n.product_code) > 0) {

				return searchTyreCode(n.right, product_code);

			} else if (product_code.compareTo(n.product_code) < 0) {

				return searchTyreCode(n.left, product_code);

			} else {

				return null;
			}

		} else {

			return null;

		}
	}

	public static Bin searchBin(Bin head, String number) {

		if (head != null) {

			if ((head.shelf + "-" + head.row + "-" + head.bin).equals(number)) {

				return head;

			} else {

				return searchBin(head.next, number);
			}

		}

		return null;

	}

	public static ArrayList<TyreOnShelfRecord> searchRecordsFromBin(TyreOnShelfRecord head, String binIn) {

		ArrayList<TyreOnShelfRecord> masterList = new ArrayList<TyreOnShelfRecord>();

		while (head != null) {

			if (head.getBin_number().equals(binIn) && !masterList.contains(head) && head.getTotal() > 0) {

				masterList.add(head);

			}

			head = head.next;

		}

		return masterList;

	}

	public static ArrayList<TyreOnShelfRecord> searchRecordsFromProductCode(TyreOnShelfRecord head,
			String product_codeIn) {

		ArrayList<TyreOnShelfRecord> masterList = new ArrayList<TyreOnShelfRecord>();

		while (head != null) {

			if (head.product_code.equals(product_codeIn) && !masterList.contains(head) && head.getTotal() > 0) {

				masterList.add(head);

			}

			head = head.next;

		}

		return masterList;

	}

	public static void printTyresInBin(String binIn) {

		ArrayList<TyreOnShelfRecord> List = searchRecordsFromBin(Main.recordHead, binIn);

		for (int i = 0; i < List.size(); i++) {

			System.out.println("Product Code: " + List.get(i).product_code);
			System.out.println("Total of Tyres: " + List.get(i).getTotal());
			System.out.println();

		}

	}

	public static void printBinsWithTyres(String product_code) {

		ArrayList<TyreOnShelfRecord> List = searchRecordsFromProductCode(Main.recordHead, product_code);

		for (int i = 0; i < List.size(); i++) {

			System.out.println("Bin: " + List.get(i).getBin_number());
			System.out.println("Total of Tyres: " + List.get(i).getTotal());
			System.out.println();

		}

	}

	public static void takeOutTyres(String product_code, String bin, int number) {

		if (Main.permission.equals("admin")) {

			TyreOnShelfRecord temp = Main.recordHead;

			Bin binObj = searchBin(Main.binHead, bin);

			Tyre tyreObj = searchTyreCode(Main.tyreHead, product_code);

			binObj.length_left = binObj.length_left + tyreObj.width * number;

			tyreObj.total = tyreObj.total - number;

			while (temp != null) {

				if (temp.getBin_number().equals(bin) && temp.product_code.equals(product_code)) {

					temp.setTotal(temp.getTotal() - number);

				}

				temp = temp.next;

			}

		} else {

			System.out.println("You do not have the permission to do that, please contact an admin.");

		}

	}

	public static boolean loggingIn() throws NoSuchAlgorithmException {

		Scanner keyboard = new Scanner(System.in);

		System.out.println("Please enter your username: ");

		String username = keyboard.nextLine();

		Account temp = Main.accountHead;

		while (temp != null) {

			if (username.equals(temp.username)) {

				System.out.println("Please enter your password: ");

				String password = keyboard.nextLine();

				if (Hash.getHash(password).equals(temp.password)) {

					Main.permission = temp.permission;

					Main.accountName = temp.username;

					System.out.println("Login Successful");

					return true;

				} else {

					System.out.println("The password is incorrect, please try again.");

					return false;

				}

			} else {

				temp = temp.next;

			}

		}

		System.out.println("The username is incorrect, please try again");

		return false;

	}

	public static void printAccounts(Account head) {

		// Running through the entire linked list
		while (true) {

			// Stuff we want to do with the data inside the node
			System.out.println("Username: " + head.username);
			System.out.println("Password: " + head.password);
			System.out.println("Permission: " + head.permission);

			// Checking if the node has a pointer to another node
			if (head.next != null) {

				// If there is, set the temp1 as the next node
				head = head.next;

			} else {
				// If there is not, the while loop is broken
				break;
			}

		}

	}

	public static void changePassword(String username) throws NoSuchAlgorithmException {

		Scanner keyboard = new Scanner(System.in);

		Account temp = Main.accountHead;

		if (username.equals(Main.accountName)) {

			while (temp != null) {

				if (username.equals(temp.username)) {

					while (true) {

						System.out.println("Please enter your old password: ");

						String password = keyboard.nextLine();

						if (Hash.getHash(password).equals(temp.password)) {

							Main.permission = temp.permission;

							System.out.println("Please enter your new password");

							String newPass = keyboard.nextLine();

							temp.password = Hash.getHash(newPass);

							return;

						} else {

							System.out.println("The password is incorrect, please try again.");

						}

					}

				} else {

					temp = temp.next;

				}

			}
		} else if (Main.permission.equals("admin")) {

			System.out.println("Please enter the new password");

			String newPass = keyboard.nextLine();

			temp.password = Hash.getHash(newPass);

			System.out.println("Password for " + username + " updated.");

		} else {

			System.out.println("You do not have the permission to do that, please contact an admin.");

		}

	}

	public static void addNewAccount(String username, String password, String permission, String email)
			throws NoSuchAlgorithmException {

		if (Main.permission.equals("admin")) {

			Account adding = new Account();

			adding.username = username;

			adding.password = Hash.getHash(password);

			adding.permission = permission;

			adding.setEmail(email);

			Main.accountTail.next = adding;

			Main.accountTail = adding;

			System.out.println("Account added");

		} else {

			System.out.println("You do not have the permission to do that, please contact an admin.");

		}

	}

	public static void changeEmail(String username, String email) throws NoSuchAlgorithmException {

		Scanner keyboard = new Scanner(System.in);

		Account temp = Main.accountHead;

		if (Main.permission.equals("admin") || username.equals(Main.accountName)) {

			while (temp != null) {

				if (username.equals(temp.username)) {

					temp.setEmail(email);

					System.out.println("Email for " + username + " updated.");

					break;

				} else {

					temp = temp.next;

				}

			}
		} else {

			System.out.println("You do not have the permission to do that, please contact an admin.");

		}

	}

	public static int countAllTyres(Tyre n) {

		if (n != null) {

			Main.tyreCount = Main.tyreCount + 1;

			countAllTyres(n.left);
			countAllTyres(n.right);

		}

		return Main.tyreCount;

	}

	public static void addNewTyre(String product_code, String product_name, int total, int width, int alertValue) {

		Tyre add = new Tyre(product_code, product_name, total, width, alertValue);

		BinarySearchTree.addTyre(Main.tyreHead, add);

	}

	public static int countAccounts(Account head) {

		int i = 0;

		// Running through the entire linked list
		while (true) {

			i = i + 1;

			// Checking if the node has a pointer to another node
			if (head.next != null) {

				// If there is, set the temp1 as the next node
				head = head.next;

			} else {
				// If there is not, the while loop is broken
				break;
			}

		}

		return i;

	}

	public static Account searchAccount(Account head, String username) {

		if (head != null) {

			if (head.username.equals(username)) {

				return head;

			} else {

				return searchAccount(head.next, username);
			}

		}

		return null;

	}

	// Got the two methods below from the website
	// https://www.baeldung.com/java-email-validation-regex

	public static boolean patternMatches(String emailAddress, String regexPattern) {

		return Pattern.compile(regexPattern).matcher(emailAddress).matches();

	}

	static public boolean testUsingStrictRegex(String emailAddress) {

		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

		return patternMatches(emailAddress, regexPattern);

	}

}
