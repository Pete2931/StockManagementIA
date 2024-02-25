package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Bin;
import backend.Import_From_Database;
import backend.Main;
import backend.Operations;
import backend.Tyre;
import backend.TyreOnShelfRecord;
import backend.Upload_To_Database;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class NumberOfTyresAdd extends JFrame {
	
	public static int result = 0;

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public NumberOfTyresAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("How many tyres do you want to add to this bin?");
		lblNewLabel.setBounds(32, 74, 371, 19);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(152, 140, 132, 20);
		contentPane.add(textField);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int amount = Integer.parseInt(textField.getText());
				
				result = Operations.addTyreToShelf(Main.currentSelection.product_code, amount);

				if (result == 0) {
					
					setVisible(false);
					dispose();
					
					try {
						Upload_To_Database.uploadBins(Main.binHead);
						Upload_To_Database.uploadTyresOnShelves(Main.recordHead);
						Main.recordHead = null;
						Import_From_Database.importTyresOnShelves();
						Upload_To_Database.uploadTyres(Main.tyreHead);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					AddTakeOutTyresWindow.binTotalTable = new Object[Operations
					               			.searchRecordsFromProductCode(Main.recordHead, Main.currentSelection.product_code).size()][2];
					
					AddTakeOutTyresWindow.makeTable();

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
					
				}else {
					
					try {
						NotEnoughSpace dialog = new NotEnoughSpace();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					setVisible(false);
					dispose();
					
				}
				
			}
		});
		okButton.setActionCommand("OK");
		okButton.setBounds(269, 229, 63, 23);
		contentPane.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.setBounds(342, 229, 77, 23);
		contentPane.add(cancelButton);
	}
}
