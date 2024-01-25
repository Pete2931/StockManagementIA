package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Account;
import backend.Hash;
import backend.Main;
import backend.Operations;
import backend.Upload_To_Database;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class EditAccountDetailsAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public EditAccountDetailsAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setBounds(177, 112, 96, 19);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(283, 110, 512, 26);
		textField.setText((String) null);
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		lblNewLabel_1_1.setBounds(177, 177, 96, 29);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(283, 180, 512, 26);
		textField_1.setText((String) null);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Permission:");
		lblNewLabel_1_1_1.setBounds(180, 245, 81, 29);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(283, 248, 512, 26);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		JButton btnNewButton_1 = new JButton("Confirm Changes");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Account current = Operations.searchAccount(Main.accountHead, textField.getText());
				current.permission = textField_2.getText();
				current.setEmail(textField_3.getText());
				if (textField_1.getText() != null && !textField_1.getText().trim().isEmpty()) {
					
					try {
						current.password = Hash.getHash(textField_1.getText());
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				try {
					Upload_To_Database.uploadAccounts(Main.accountHead);
				} catch (ClassNotFoundException | NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				setVisible(false);
				dispose();
				
				AccountManagementMenuAdmin.clearTable();
				
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
		});
		btnNewButton_1.setBounds(409, 402, 147, 42);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnNewButton_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(283, 310, 512, 26);
		contentPane.add(textField_3);
		
		//Making sure the text fields display the details of the tyre that was selected in the previous screen
		textField.setText(Main.selectedAccount.username);
		textField_2.setText(Main.selectedAccount.permission);
		textField_3.setText(Main.selectedAccount.getEmail());
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Email:");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(218, 307, 55, 29);
		contentPane.add(lblNewLabel_1_1_1_1);
	}
}
