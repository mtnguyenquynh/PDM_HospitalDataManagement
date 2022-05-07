package image;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GlobalPool_Page {

	private JFrame frame;
	private JTextField txtIdField;
	private JTextField txtPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlobalPool_Page window = new GlobalPool_Page();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GlobalPool_Page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1167, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Global Pool");
		lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		lblNewLabel.setBounds(504, 65, 138, 76);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblID.setBounds(166, 185, 74, 47);
		frame.getContentPane().add(lblID);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblPassword.setBounds(163, 251, 113, 47);
		frame.getContentPane().add(lblPassword);
		
		txtIdField = new JTextField();
		txtIdField.setBounds(390, 198, 266, 29);
		frame.getContentPane().add(txtIdField);
		txtIdField.setColumns(10);
		
		txtPasswordField = new JTextField();
		txtPasswordField.setColumns(10);
		txtPasswordField.setBounds(390, 264, 266, 29);
		frame.getContentPane().add(txtPasswordField);
		
		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnLogin.setBounds(687, 341, 85, 33);
		frame.getContentPane().add(btnLogin);
	}
}
