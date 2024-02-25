package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Main;
import backend.Operations;
import backend.Tyre;
import backend.Upload_To_Database;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditDetailsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblNewLabel_1_1_1;
	private JLabel lblNewLabel_1_1_1_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditDetailsWindow frame = new EditDetailsWindow();
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
	public EditDetailsWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Product Code:");
		lblNewLabel_1.setBounds(166, 147, 96, 19);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		textField.setBounds(272, 145, 512, 26);
		contentPane.add(textField);
		
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(272, 215, 512, 26);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Product Name:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(153, 212, 109, 29);
		contentPane.add(lblNewLabel_1_1);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);
		textField_2.setBounds(272, 283, 512, 26);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(272, 357, 512, 26);
		contentPane.add(textField_3);
		
		//Making sure the text fields display the details of the tyre that was selected in the previous screen
		textField.setText(Main.currentSelection.product_code);
		textField_1.setText(Main.currentSelection.product_name);
		textField_2.setText(Integer.toString(Main.currentSelection.total));
		textField_3.setText(Integer.toString(Main.currentSelection.width));
		
		
		lblNewLabel_1_1_1 = new JLabel("Total:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(207, 280, 55, 29);
		contentPane.add(lblNewLabel_1_1_1);
		
		lblNewLabel_1_1_1_1 = new JLabel("Width:");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(207, 354, 55, 29);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JButton btnNewButton_1 = new JButton("Confirm Changes");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tyre current = Operations.searchTyreCode(Main.tyreHead, textField.getText());
				current.product_name = textField_1.getText();
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
							stockManagementMenu.trackingNum = 0;
							stockManagementMenu.tableTyre(Main.tyreHead);
							stockManagementMenu frame = new stockManagementMenu();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(409, 447, 147, 42);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				
				stockManagementMenu.table10 = new Object[Operations.countAllTyres(Main.tyreHead)][4];
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							stockManagementMenu.trackingNum = 0;
							stockManagementMenu.tableTyre(Main.tyreHead);
							stockManagementMenu frame = new stockManagementMenu();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		contentPane.add(btnNewButton);
	}
}
