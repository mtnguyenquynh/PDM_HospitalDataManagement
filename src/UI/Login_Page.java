package UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

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

/** This file initialize the GUI to access the hospital database **/

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
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		frmHDM = new JFrame();
		frmHDM.setBounds(0, 0, width, height);
		frmHDM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHDM.getContentPane().setLayout(null);
		frmHDM.setResizable(false);
		frmHDM.setVisible(true);


		// Set Login Page image
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(186, -11, 1200, 334);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Login_Page.class.getResource("/image/LoginPage.png")));
		frmHDM.getContentPane().add(lblNewLabel);

		// Handle username field
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(378, 368, 147, 49);
		lblUsername.setVerticalAlignment(SwingConstants.TOP);
		lblUsername.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		frmHDM.getContentPane().add(lblUsername);

		UsernameField = new JTextField();
		UsernameField.setBounds(561, 370, 299, 30);
		frmHDM.getContentPane().add(UsernameField);
		UsernameField.setColumns(10);

		// Handle password field
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(378, 427, 147, 49);
		lblPassword.setVerticalAlignment(SwingConstants.TOP);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		frmHDM.getContentPane().add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(561, 429, 299, 30);
		passwordField.setEchoChar('•');
		frmHDM.getContentPane().add(passwordField);

		// Login button
		JButton btnLogin = new JButton("Log In");
		btnLogin.setBounds(1064, 494, 96, 40);
		btnLogin.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
                btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = UsernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (username.equals("pdm") && password.equals("123")) {
					Menu_Page menu = new Menu_Page();
					menu.getClass();
				} else {
					JOptionPane.showMessageDialog(lblNewLabel, "Invalid password or username!",
							"Hospital Database Management", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
                frmHDM.getRootPane().setDefaultButton(btnLogin);//Press login with Enter key
		frmHDM.getContentPane().add(btnLogin);
	}
}
