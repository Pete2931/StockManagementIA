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

import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MailAlertSystemMenu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static Object[][] mailAlertTable = new Object[Operations.countAllTyres(Main.tyreHead)][2];
	static int trackingNum = 0;

	// A method to turn the Tyre Binary Search Tree into a 2D array
	public static void tableTyre(Tyre n) {

		if (n != null) {

			mailAlertTable[trackingNum][0] = n.product_code;
			mailAlertTable[trackingNum][1] = n.getAlert_value();

			trackingNum = trackingNum + 1;

			tableTyre(n.left);
			tableTyre(n.right);

		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		trackingNum = 0;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MailAlertSystemMenu frame = new MailAlertSystemMenu();
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
	public MailAlertSystemMenu() {
		
		//used to make the tables less funky
		Main.tyreCount = 0;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 946, 519);
		contentPane.add(scrollPane);

		// Making the table here
		trackingNum = 0;
		tableTyre(Main.tyreHead);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(mailAlertTable, new String[] { "Product Code", "Tyre alert number" }));
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);

		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (table.getSelectedRow() != -1) {

					// The method that I am using in order to know if a cell is selected and where
					// Learnt from
					// https://stackoverflow.com/questions/29345792/java-jtable-getting-the-data-of-the-selected-row
					String selectedCellValue = (String) table.getValueAt(table.getSelectedRow(), 0);

					// current Selection is used to keep track of the selection made in this window
					// and to carry it on to the next window
					Main.currentSelection = Operations.searchTyreCode(Main.tyreHead, selectedCellValue);

					setVisible(false);
					dispose();

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								MailAlertNumberChange frame = new MailAlertNumberChange();
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

			}
		});
		btnNewButton_1.setBounds(768, 11, 89, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MailAlertSearchWindow frame = new MailAlertSearchWindow();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton.setBounds(867, 11, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1_1 = new JButton("Back");
		btnNewButton_1_1.addActionListener(new ActionListener() {
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
		btnNewButton_1_1.setBounds(10, 11, 89, 23);
		contentPane.add(btnNewButton_1_1);
	}
}
