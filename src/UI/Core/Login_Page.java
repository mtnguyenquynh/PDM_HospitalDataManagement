import java.awt.EventQueue;


import javax.swing.JFrame;
import java.io.*;
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

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**This file initialize the GUI to access the hospital database**/
public class Login_Page {

	private JFrame frmHDM;
	private JTextField UsernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Page window = new Login_Page();
					window.frmHDM.setVisible(true);
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
		frmHDM = new JFrame();
		frmHDM.setTitle("Hospital Data Management");
		frmHDM.setBounds(0, 0, 1382, 809);
		frmHDM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHDM.getContentPane().setLayout(null);
		frmHDM.setResizable(true);
		JLabel lblNewLabel = new JLabel("");//what is this for?
		lblNewLabel.setBounds(186, -11, 1200, 334);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("src\\LoginPage.png"));
		frmHDM.getContentPane().add(lblNewLabel);
		
		//Handling username field
		JLabel username = new JLabel("Username");
		username.setBounds(378, 368, 147, 49);
		username.setVerticalAlignment(SwingConstants.TOP);
		username.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		frmHDM.getContentPane().add(username);
		
		UsernameField = new JTextField();
		UsernameField.setBounds(561, 370, 599, 30);
		frmHDM.getContentPane().add(UsernameField);
		UsernameField.setColumns(10);

		//Handling password field
		JLabel password = new JLabel("Password");
		password.setBounds(378, 427, 147, 49);
		password.setVerticalAlignment(SwingConstants.TOP);
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmHDM.getContentPane().add(password);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(561, 429, 599, 30);
		passwordField.setEchoChar('•');
		frmHDM.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.setBounds(1064, 494, 96, 40);
		btnLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = UsernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(username.equals("pdm") && password.equals("123")) {
					JOptionPane.showMessageDialog(lblNewLabel, 
					"Welcome!","Hospital Database Management",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(lblNewLabel, 
					"Invalid password or username!","Hospital Database Management",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		frmHDM.getContentPane().add(btnLogin);
	}
}