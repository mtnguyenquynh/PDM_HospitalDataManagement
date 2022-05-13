/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * @author Khang
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc
 * 2) https://stackoverflow.com/questions/29316729/cant-connect-to-sql-server-database-using-jdbc
 * 3) Principles of Database Management - Lab 05, 06
 * 4) https://stackoverflow.com/questions/5616898/java-sql-sqlexception-no-suitable-driver-found-for-jdbcmicrosoftsqlserver
**/

/** This file initialize the GUI to access the hospital database **/
import UI.HospitalQueryApp;
public class Main {
    public static void main(String[] args) {
        HospitalQueryApp window = new HospitalQueryApp();
        window.display();
    }
}
