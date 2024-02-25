package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Import_From_Database;
import backend.Main;
import backend.Upload_To_Database;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class NotEnoughSpace extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NotEnoughSpace dialog = new NotEnoughSpace();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NotEnoughSpace() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblThereIsNo = new JLabel(
					"There is no more space, there are " + NumberOfTyresAdd.result + " tyres left.");
			lblThereIsNo.setHorizontalAlignment(SwingConstants.CENTER);
			lblThereIsNo.setForeground(Color.RED);
			lblThereIsNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblThereIsNo.setBounds(5, 96, 426, 37);
			contentPanel.add(lblThereIsNo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

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
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
