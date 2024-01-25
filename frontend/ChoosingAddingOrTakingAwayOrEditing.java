package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Main;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChoosingAddingOrTakingAwayOrEditing extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public ChoosingAddingOrTakingAwayOrEditing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Would you like to add tyres, take away tyres, or edit tyres?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(235, 180, 496, 30);
		contentPane.add(lblNewLabel);
		
		JButton takeOutTyresBtn = new JButton("Add / Take out Tyres");
		takeOutTyresBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		takeOutTyresBtn.addActionListener(new ActionListener() {
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
		takeOutTyresBtn.setBounds(286, 297, 180, 40);
		contentPane.add(takeOutTyresBtn);
		
		JButton editDetailsBtn = new JButton("Edit Details");
		editDetailsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				
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
		});
		editDetailsBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		editDetailsBtn.setBounds(506, 297, 180, 40);
		contentPane.add(editDetailsBtn);
	}
}
