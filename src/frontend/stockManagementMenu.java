package frontend;

import java.awt.EventQueue;
import backend.Upload_To_Database;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import backend.Import_From_Database;
import backend.Main;
import backend.Operations;
import backend.Tyre;
import backend.Upload_To_Database;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class stockManagementMenu extends JFrame {
	
	static int trackingNum = 0;

	// To keep track of the 2D array that is used to display the table
		static Object[][] table10 = new Object[Operations.countAllTyres(Main.tyreHead)][4];

	// A method to turn the Tyre Binary Search Tree into a 2D array
	public static void tableTyre(Tyre n) {

		if (n != null) {

			table10[trackingNum][0] = n.product_code;
			table10[trackingNum][1] = n.product_name;
			table10[trackingNum][2] = n.total;
			table10[trackingNum][3] = n.width;

			trackingNum = trackingNum + 1;

			tableTyre(n.left);
			tableTyre(n.right);

		}
	}

	public static void clearTable() {

		for (int i = 0; i < table10.length; i++) {

			table10[i][0] = null;
			table10[i][1] = null;
			table10[i][2] = null;
			table10[i][3] = null;

		}

	}

	public static int updateTyre(Tyre n, Object product_code, Object product_name, Object total, Object width) {

		if (n != null) {

			if (n.product_code.equals(product_code)) {

				n.product_name = (String) product_name;

				n.total = (int) total;

				n.width = (int) width;

				return 1;

			} else if (((String) product_code).compareTo(n.product_code) > 0) {

				return updateTyre(n.right, product_code, product_name, total, width);

			} else if (((String) product_code).compareTo(n.product_code) < 0) {

				return updateTyre(n.left, product_code, product_name, total, width);

			} else {

				return 0;
			}

		} else {

			return 0;

		}
	}

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		table10 = new Object[Operations.countAllTyres(Main.tyreHead)][4];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trackingNum = 0;
					tableTyre(Main.tyreHead);
					stockManagementMenu frame = new stockManagementMenu();
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
	public stockManagementMenu() {
		
		//used to stop making the tables funky
		Main.tyreCount = 0;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 946, 519);
		contentPane.add(scrollPane);

		// Turn the binary search tree into 2D Array so I can display it as a table
		trackingNum = 0;
		tableTyre(Main.tyreHead);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setModel(
				new DefaultTableModel(table10, new String[] { "Product Code", "Product Name", "Total", "Width" }) {
					Class[] columnTypes = new Class[] { String.class, String.class, Integer.class, Integer.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					// Learned how to make cells not editable from
					// https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable#:~:text=You%20can%20use%20a%20TableModel%20.&text=Then%20use%20the%20setModel()%20method%20of%20your%20JTable%20.&text=Without%20editors%2C%20data%20will%20be%20not%20editable.&text=I%20used%20this%20and%20it,very%20simple%20and%20works%20fine.

					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}

				});

		table.getColumnModel().getColumn(0).setPreferredWidth(140);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Upload_To_Database.uploadTyres(Main.tyreHead);
					Upload_To_Database.uploadBins(Main.binHead);
					Upload_To_Database.uploadTyresOnShelves(Main.recordHead);
					Main.recordHead = null;
					Import_From_Database.importTyresOnShelves();
					Upload_To_Database.uploadAccounts(Main.accountHead);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		saveBtn.setBounds(867, 11, 89, 23);
		contentPane.add(saveBtn);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SearchWindow frame = new SearchWindow();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton.setBounds(768, 11, 89, 23);
		contentPane.add(btnNewButton);

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
						Main.currentSelection = Operations.searchTyreCode(Main.tyreHead, selectedCellValue);

						setVisible(false);
						dispose();

						// Launch the next window
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									ChoosingAddingOrTakingAwayOrEditing frame = new ChoosingAddingOrTakingAwayOrEditing();
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						
					}else {
						
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
		btnNewButton_1.setBounds(669, 11, 89, 23);
		contentPane.add(btnNewButton_1);

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
		
		JButton btnNewButton_1_2 = new JButton("Add New Tyre");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AddNewTypeOfTyre frame = new AddNewTypeOfTyre();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton_1_2.setBounds(538, 11, 121, 23);
		contentPane.add(btnNewButton_1_2);
	}
}
