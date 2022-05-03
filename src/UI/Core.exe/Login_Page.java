import java.awt.EventQueue;


import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login_Page {

	private JFrame frmHospitalDatabaseManagement;
	private JTextField txtUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Page window = new Login_Page();
					window.frmHospitalDatabaseManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login_Page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHospitalDatabaseManagement = new JFrame();
		frmHospitalDatabaseManagement.setTitle("Hospital Data Management");
		frmHospitalDatabaseManagement.setBounds(0, 0, 1382, 809);
		frmHospitalDatabaseManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHospitalDatabaseManagement.getContentPane().setLayout(null);
		frmHospitalDatabaseManagement.setResizable(true);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(186, -11, 1200, 334);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("src\\UI\\Core.exe\\LoginPage.png"));
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel);
		
		//Handling username field
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(378, 368, 147, 49);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(561, 370, 599, 30);
		frmHospitalDatabaseManagement.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);

		//Handling password field
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setBounds(378, 427, 147, 49);
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel_1_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(561, 429, 599, 30);
		passwordField.setEchoChar('•');
		frmHospitalDatabaseManagement.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBounds(1064, 494, 96, 40);
		btnNewButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(username.equals("pdm") && password.equals("123")) {
					JOptionPane.showMessageDialog(lblNewLabel, 
					"Welcome!","Hospital Database Management System",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(lblNewLabel, 
					"Invalid password or username!","Hospital Database Management System",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frmHospitalDatabaseManagement.getContentPane().add(btnNewButton);
	}
}
