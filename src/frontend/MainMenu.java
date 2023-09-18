package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Main;
import backend.Tyre;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
	
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton stockManagementBtn = new JButton("");
		stockManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							stockManagementMenu frame = new stockManagementMenu();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		stockManagementBtn.setIcon(
				new ImageIcon("C:\\Users\\Pete\\OneDrive\\Desktop\\Pete\\Notion Icons\\ready-stock_116x120.png"));
		stockManagementBtn.setBounds(184, 208, 120, 120);
		contentPane.add(stockManagementBtn);

		JLabel lblNewLabel = new JLabel("Stock Manager");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(184, 347, 120, 30);
		contentPane.add(lblNewLabel);

		JButton mailManagementBtn = new JButton("");
		mailManagementBtn
				.setIcon(new ImageIcon("C:\\Users\\Pete\\OneDrive\\Desktop\\Pete\\Notion Icons\\mail_120x120.png"));
		mailManagementBtn.setBounds(423, 208, 120, 120);
		contentPane.add(mailManagementBtn);

		JLabel lblNewLabel_1 = new JLabel("Mail Alert System");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(423, 347, 120, 30);
		contentPane.add(lblNewLabel_1);

		JButton accountManagementBtn = new JButton("");
		accountManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		accountManagementBtn.setIcon(
				new ImageIcon("C:\\Users\\Pete\\OneDrive\\Desktop\\Pete\\Notion Icons\\user-profile_120x120.png"));
		accountManagementBtn.setBounds(669, 208, 120, 120);
		contentPane.add(accountManagementBtn);

		JLabel lblNewLabel_2 = new JLabel("Account Management");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(652, 347, 154, 30);
		contentPane.add(lblNewLabel_2);
	}
}
