
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

package UI;

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


public class HospitalQueryApp extends JFrame {
    // ---------------------------------------------------------------------------------------------------------------------
    private JButton btnRun;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTextField txtQuery;
    private JTextArea txtResult;

    public HospitalQueryApp() {
        initComponents();
    }

    private void initComponents() {
        // ---------------------------------------------------------------------------------------------------------------------
        // Variable Initialization
        this.jLabel1 = new JLabel("Query: ");
        this.txtQuery = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.txtResult = new JTextArea();
        this.btnRun = new JButton("RUN");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.txtQuery.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) { btnRunActionPerformed(null); }       
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub 
            }
        });

        this.txtResult.setColumns(20);
        this.txtResult.setRows(5);
        this.jScrollPane1.setViewportView(txtResult);

        this.btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });


        GroupLayout layout = new GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(this.jLabel1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(this.txtQuery, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(this.btnRun)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.jScrollPane1, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(this.txtQuery, GroupLayout.PREFERRED_SIZE, 107,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(this.jLabel1, GroupLayout.PREFERRED_SIZE, 33,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(this.btnRun))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addContainerGap()
                                .addComponent(this.jScrollPane1, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }

    private void txtQueryActionPerformed(ActionEvent evt) {

    }

    private void btnRunActionPerformed(ActionEvent evt) {
        if (this.txtQuery.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please input query string!",
                    "Message", JOptionPane.WARNING_MESSAGE);
        }
        this.txtResult.selectAll();
        this.txtResult.replaceSelection("");
        String connectionUrl = "jdbc:sqlserver://localhost:1434;databaseName=HospitalData;user=admin;password=admin;"
                + "encrypt=true;trustServerCertificate=true;";

        try (
                Connection con = DriverManager.getConnection(connectionUrl);
                Statement stmt = con.createStatement();) {
            String SQL = txtQuery.getText();
            ResultSet rs = stmt.executeQuery(SQL);
            System.out.println(rs);

            // Iterate through the data in the result set and display it.
            // process query results
            StringBuilder results = new StringBuilder();
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                results.append(metaData.getColumnName(i)).append("\t");
            }
            results.append("\n");
            // Metadata
            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    results.append(rs.getObject(i)).append("\t");
                }
                results.append("\n");
            }
            txtResult.setText(results.toString());
        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            txtResult.setText(e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HospitalQueryApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginForm window = new LoginForm();
            }
        });
    }
}
