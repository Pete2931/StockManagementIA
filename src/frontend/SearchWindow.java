package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Main;
import backend.Operations;
import backend.Tyre;
import backend.Upload_To_Database;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public SearchWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(265, 70, 512, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Input product code here:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(84, 68, 171, 28);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String product_code = textField.getText();
				Tyre current = Operations.searchTyreCode(Main.tyreHead, product_code);

				Main.currentSelection = current;

				textField_1.setText(current.product_code);
				textField_2.setText(current.product_name);
				textField_3.setText(Integer.toString(current.total));
				textField_4.setText(Integer.toString(current.width));

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(787, 70, 79, 28);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Product Code:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(157, 164, 98, 28);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(265, 167, 512, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(265, 239, 512, 26);
		contentPane.add(textField_2);

		JLabel lblNewLabel_1_1 = new JLabel("Product Name:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(146, 236, 109, 29);
		contentPane.add(lblNewLabel_1_1);

		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(265, 306, 512, 26);
		contentPane.add(textField_3);

		JLabel lblNewLabel_1_1_1 = new JLabel("Total:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(200, 303, 55, 29);
		contentPane.add(lblNewLabel_1_1_1);

		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setColumns(10);
		textField_4.setBounds(265, 376, 512, 26);
		contentPane.add(textField_4);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Width:");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(200, 373, 55, 29);
		contentPane.add(lblNewLabel_1_1_1_1);

		JButton btnNewButton_1 = new JButton("Confirm Changes");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Main.permission.equals("admin")) {

					Tyre current = Operations.searchTyreCode(Main.tyreHead, textField_1.getText());
					current.product_name = textField_2.getText();
					try {
						Upload_To_Database.uploadTyres(Main.tyreHead);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					setVisible(false);
					dispose();

					stockManagementMenu.clearTable();

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
				}else {
					
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
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(301, 462, 171, 42);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Add / Take out tyres");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				
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
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(509, 462, 171, 42);
		contentPane.add(btnNewButton_2);
	}
}
