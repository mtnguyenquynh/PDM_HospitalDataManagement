package UI;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class GlobalPool_Page {

	private JFrame frmGP;
	private JTextField ID;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlobalPool_Page window = new GlobalPool_Page();
					window.frmGP.setVisible(true);
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
	 * Initialize the contents of the frmGP.
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmGP = new JFrame();
		frmGP.setBounds(0, 0, width, height);
		frmGP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGP.getContentPane().setLayout(null);
		frmGP.getContentPane().setBackground(new Color(135, 206, 250));

		
		JLabel title = new JLabel("Global Pool");
		title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		title.setBounds(629, 42, 138, 76);
		frmGP.getContentPane().add(title);
		
		//Add ID filed to frame (include label)
		JLabel ID_label = new JLabel("ID");
		ID_label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		ID_label.setBounds(334, 185, 74, 47);
		frmGP.getContentPane().add(ID_label);
		
		ID = new JTextField();
		ID.setBounds(542, 198, 393, 29);
		frmGP.getContentPane().add(ID);
		ID.setColumns(10);

		JLabel password_label = new JLabel("Password");
		password_label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		password_label.setBounds(334, 242, 113, 47);
		frmGP.getContentPane().add(password_label);
		
		//Add password field to frame (include label)
		passwordField = new JPasswordField();
		passwordField.setBounds(542, 255, 393, 29);
        passwordField.setEchoChar('•');
		frmGP.getContentPane().add(passwordField);

		//Initialize login button
		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = ID.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(id.equals("pdm")&&password.equals("123") ) {
					GlobalPool_Menu global = new GlobalPool_Menu();
					global.getClass();
				}
			else {
				JOptionPane.showMessageDialog(title, "Invalid password or username!",
						"Hospital Database Management", JOptionPane.WARNING_MESSAGE);
			}
			}
		});

		//Add login button to frame
		btnLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnLogin.setBounds(822, 332, 113, 47);
		frmGP.getRootPane().setDefaultButton(btnLogin);
        frmGP.getContentPane().add(btnLogin);
		
		
                
	}
}
