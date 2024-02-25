package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.Account;
import backend.Main;
import backend.Operations;
import backend.Tyre;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountManagementMenuAdmin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static Object[][] MainTable = new Object[Operations.countAccounts(Main.accountHead)][3];

	// A method to turn the Tyre Binary Search Tree into a 2D array
	public static void makeTable(Account n, int trackingNum) {

		if (n != null) {

			MainTable[trackingNum][0] = n.username;
			MainTable[trackingNum][1] = n.permission;
			MainTable[trackingNum][2] = n.getEmail();

			makeTable(n.next, trackingNum + 1);

		}
	}

	public static void clearTable() {

		for (int i = 0; i < MainTable.length; i++) {

			MainTable[i][0] = null;
			MainTable[i][1] = null;
			MainTable[i][2] = null;

		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountManagementMenuAdmin frame = new AccountManagementMenuAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AccountManagementMenuAdmin() {
		
		MainTable = new Object[Operations.countAccounts(Main.accountHead)][3];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 946, 519);
		contentPane.add(scrollPane);

		MainTable = new Object[Operations.countAccounts(Main.accountHead)][3];
		makeTable(Main.accountHead, 0);

		table = new JTable();
		table.setModel(new DefaultTableModel(MainTable, new String[] { "Username", "Permission", "e-mail" }));
		scrollPane.setViewportView(table);

		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Main.permission.equals("admin")) {

					if (table.getSelectedRow() != -1) {
						// The method that I am using in order to know if a cell is selected and where
						// Learnt from
						// https://stackoverflow.com/questions/29345792/java-jtable-getting-the-data-of-the-selected-row
						String selectedCellValue = (String) table.getValueAt(table.getSelectedRow(), 0);

						// current Selection is used to keep track of the selection made in this window
						// and to carry it on to the next window
						Main.selectedAccount = Operations.searchAccount(Main.accountHead, selectedCellValue);

						setVisible(false);
						dispose();

						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									EditAccountDetailsAdmin frame = new EditAccountDetailsAdmin();
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} else {

						try {
							NotSelectedRow dialog = new NotSelectedRow();
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

				} else {

					try {
						NoPermission dialog = new NoPermission();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btnNewButton_1.setBounds(770, 9, 89, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AccountManagementSearch frame = new AccountManagementSearch();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton.setBounds(867, 9, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1_1 = new JButton("Add");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AddAccountWindow frame = new AddAccountWindow();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton_1_1.setBounds(671, 9, 89, 23);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("Back");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MainMenu frame = new MainMenu();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton_1_1_1.setBounds(10, 9, 89, 23);
		contentPane.add(btnNewButton_1_1_1);
	}
}
