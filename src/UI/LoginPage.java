package UI;


/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class is an interface connecting with the database. Database is located within
 * this folder under the "database/HospitalData.bak" file.
 * 
 * @author Khang
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc
 * 2) https://stackoverflow.com/questions/29316729/cant-connect-to-sql-server-database-using-jdbc
 * 3) Principles of Database Management - Lab 05, 06
 * 4) https://stackoverflow.com/questions/5616898/java-sql-sqlexception-no-suitable-driver-found-for-jdbcmicrosoftsqlserver
**/


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;


public class LoginPage extends JFrame {
    // ---------------------------------------------------------------------------------------------------------------------
    private JLabel DatabaseJLabel;
    private JTextField DatabaseQuery;
    private JLabel UsernameJLabel;
    private JTextField UsernameQuery;
    private JLabel PasswordJLabel;
    private JTextField PasswordQuery;
    
    private JButton Login;
    private JTextArea ResultArea;
    private JScrollPane ResultScrollPane;

    public LoginPage() {
        this.init();
    }

    private void init() {
        // ---------------------------------------------------------------------------------------------------------------------
        // Variable Initialization
        this.DatabaseJLabel = new JLabel("Database");
        this.DatabaseQuery = new JTextField("HospitalData");
        this.UsernameJLabel = new JLabel("Username");
        this.UsernameQuery = new JTextField(null);
        this.PasswordJLabel = new JLabel("Password");
        this.PasswordQuery = new JTextField(null);
        
        this.Login = new JButton("Login");
        this.ResultArea = new JTextArea();
        this.ResultScrollPane = new JScrollPane();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        
        this.ResultScrollPane.setViewportView(this.ResultArea);


    
    }



}
