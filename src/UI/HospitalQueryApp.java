
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
 * 
 * @author Ichiru Take
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
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
        this.btnRun = new JButton();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        txtQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtQueryActionPerformed(evt);
            }
        });

        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane1.setViewportView(txtResult);

        btnRun.setText("Run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtQuery, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRun)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtQuery, GroupLayout.PREFERRED_SIZE, 107,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 33,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnRun))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .addContainerGap()));

        pack();
    }// </editor-fold>

    private void txtQueryActionPerformed(ActionEvent evt) {

    }

    private void btnRunActionPerformed(ActionEvent evt) {
        if (txtQuery.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please input query string!",
                    "Message", JOptionPane.WARNING_MESSAGE);
        }
        txtResult.selectAll();
        txtResult.replaceSelection("");
        String connectionUrl = "jdbc:sqlserver://localhost:1434;databaseName=HospitalData;user=myproject;password=sa;"
                + "encrypt=true;trustServerCertificate=true;";

        try (
                Connection con = DriverManager.getConnection(connectionUrl);
                Statement stmt = con.createStatement();) {
            String SQL = txtQuery.getText();
            ResultSet rs = stmt.executeQuery(SQL);

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
                new HospitalQueryApp().setVisible(true);
            }
        });
    }

}
