package HDM_UI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
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
		frmHospitalDatabaseManagement.setTitle("Patient Data Management");
		frmHospitalDatabaseManagement.setBounds(100, 100, 1066, 744);
		frmHospitalDatabaseManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHospitalDatabaseManagement.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 1052, 334);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\WIN 10\\OneDrive\\Hi\u0300nh a\u0309nh\\Screenshot 2022-05-03 135231.png"));
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(27, 386, 147, 49);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(27, 435, 147, 49);
		frmHospitalDatabaseManagement.getContentPane().add(lblNewLabel_1_1);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(155, 390, 750, 30);
		frmHospitalDatabaseManagement.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 437, 750, 30);
		frmHospitalDatabaseManagement.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = passwordField.getText();
				if(username.equals("pdm")&&password.equals("123")) {
					
				}
				else {
					JOptionPane.showMessageDialog(lblNewLabel, "Invalid password or username!");
				}
			}
		});
		btnNewButton.setBounds(809, 498, 96, 40);
		frmHospitalDatabaseManagement.getContentPane().add(btnNewButton);
	}
}
