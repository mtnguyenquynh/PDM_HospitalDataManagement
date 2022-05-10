

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class GlobalPool_Page {

	private JFrame frame;
	private JTextField txtIdField;
	private JPasswordField passwordField;

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
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frame = new JFrame();
		frame.setBounds(0, 0, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(135, 206, 250));

		
		JLabel lblNewLabel = new JLabel("Global Pool");
		lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		lblNewLabel.setBounds(629, 42, 138, 76);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblID.setBounds(334, 185, 74, 47);
		frame.getContentPane().add(lblID);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblPassword.setBounds(334, 242, 113, 47);
		frame.getContentPane().add(lblPassword);
		
		txtIdField = new JTextField();
		txtIdField.setBounds(542, 198, 393, 29);
		frame.getContentPane().add(txtIdField);
		txtIdField.setColumns(10);
		
		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtIdField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(id.equals("pdm")&&password.equals("123") ) {
					GlobalPool_Menu global = new GlobalPool_Menu();
					global.getClass();
				}
			else {
				JOptionPane.showMessageDialog(lblNewLabel, "Invalid password or username!",
						"Hospital Database Management", JOptionPane.WARNING_MESSAGE);
			}
			}
		});
		btnLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnLogin.setBounds(822, 332, 113, 47);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(542, 255, 393, 29);
		frame.getContentPane().add(passwordField);
	}
}
