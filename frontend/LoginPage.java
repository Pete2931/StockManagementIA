package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Account;
import backend.Hash;
import backend.Import_From_Database;
import backend.Main;
import backend.Operations;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField usernameIn;
	private JTextField passwordIn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
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
	public LoginPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel usernameLabel = new JLabel("Username :");
		usernameLabel.setBounds(314, 281, 83, 37);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setBounds(314, 341, 83, 37);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.setLayout(null);
		contentPane.add(usernameLabel);
		contentPane.add(passwordLabel);

		usernameIn = new JTextField();
		usernameIn.setBounds(413, 286, 241, 30);
		contentPane.add(usernameIn);
		usernameIn.setColumns(10);

		passwordIn = new JTextField();
		passwordIn.setBounds(413, 346, 241, 30);
		contentPane.add(passwordIn);
		passwordIn.setColumns(10);

		JLabel lblNewLabel = new JLabel("T.SIAM Tyre Management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel.setBounds(313, 59, 340, 122);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Pete\\OneDrive\\Desktop\\tsiam-map-2015.jpg"));
		lblNewLabel_1.setBounds(264, 166, 438, 87);
		contentPane.add(lblNewLabel_1);
		
		JLabel announcement = new JLabel("");
		announcement.setForeground(Color.RED);
		announcement.setFont(new Font("Tahoma", Font.PLAIN, 15));
		announcement.setHorizontalAlignment(SwingConstants.CENTER);
		announcement.setBounds(249, 498, 467, 25);
		contentPane.add(announcement);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				Import_From_Database.importAccounts();

				String username = usernameIn.getText();
				String password = passwordIn.getText();

				Account temp = Main.accountHead;

				while (temp != null) {

					if (username.equals(temp.username)) {

						try {
							if (Hash.getHash(password).equals(temp.password)) {

								Main.permission = temp.permission;

								Main.accountName = temp.username;

								announcement.setText("Login Successful");
								
								Import_From_Database.importBins();
								Import_From_Database.importTyres();
								Import_From_Database.importTyresOnShelves();
								
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

								break;

							} else {

								announcement.setText("The username or password might be incorrect, please try again.");
								
								usernameIn.setText("");
								passwordIn.setText("");
								
								break;

							}
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {

						temp = temp.next;

					}

				}
				
				announcement.setText("The username or password might be incorrect, please try again.");
				usernameIn.setText("");
				passwordIn.setText("");

			}

		});
		btnNewButton.setBounds(415, 424, 135, 43);
		contentPane.add(btnNewButton);
		
		
	}
}
