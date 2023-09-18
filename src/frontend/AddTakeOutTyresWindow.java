package frontend;

import java.awt.EventQueue;
import backend.Operations;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import backend.Main;
import backend.Operations;
import backend.TyreOnShelfRecord;
import backend.Upload_To_Database;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AddTakeOutTyresWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private static JTable table;

	static Object syncObject = 1;

	static Object[][] binTotalTable = new Object[Operations
			.searchRecordsFromProductCode(Main.recordHead, Main.currentSelection.product_code).size()][2];

	protected static boolean canceled = false;

	public static void makeTable() {

		ArrayList<TyreOnShelfRecord> list = Operations.searchRecordsFromProductCode(Main.recordHead,
				Main.currentSelection.product_code);

		for (int i = 0; i < list.size(); i++) {

			binTotalTable[i][0] = list.get(i).getBin_number();

			binTotalTable[i][1] = list.get(i).getTotal();

		}

	}

	public static void clearTable() {

		for (int i = 0; i < binTotalTable.length; i++) {

			binTotalTable[i][0] = null;

			binTotalTable[i][1] = null;

		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTakeOutTyresWindow frame = new AddTakeOutTyresWindow();
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
	public AddTakeOutTyresWindow() {
		makeTable();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 946, 519);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(binTotalTable, new String[] { "Bin Number", "Total" }));
		scrollPane.setViewportView(table);

		JButton takeOutBtn = new JButton("Take out");
		takeOutBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Main.selectedBin = Operations.searchBin(Main.binHead,
						(String) table.getValueAt(table.getSelectedRow(), 0));
				System.out.println(Main.selectedBin.getBin_number());

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							NumberOfTyresTakeOut1 frame = new NumberOfTyresTakeOut1();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

		});
		takeOutBtn.setBounds(867, 9, 89, 23);
		contentPane.add(takeOutBtn);

		JButton addBtn = new JButton("Add Tyre");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							NumberOfTyresAdd frame = new NumberOfTyresAdd();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		addBtn.setBounds(768, 9, 89, 23);
		contentPane.add(addBtn);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				dispose();

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							stockManagementMenu.tableTyre(Main.tyreHead, 0);
							stockManagementMenu frame = new stockManagementMenu();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnNewButton.setBounds(10, 9, 89, 23);
		contentPane.add(btnNewButton);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
