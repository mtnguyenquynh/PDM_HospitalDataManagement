package GlobalPool;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This class is similar as an local tool pool, but it managed all tools 
 * available in the hospital (i.e manager), included the ones stored in the 
 * storage room.
 * 
 * The functionality of this approach is to provide a centralized place to 
 * store all data/information about the tools, as well as cross-checking them
 * to all distribution places/centers such as LToolPool.class and report them 
 * to GLogger.class.
 * 
 * This class is connect to the SQL-DB database, but the description of each instance
 * (in this pool: Tool) is stored in the module `Object.Tool.json` file.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class GToolPool {
    private final static String SQL_DB_DIRECTORY = "database/global_pool/"; 
    private final static String TOOL_DEFINITION_FILENAME = "Tool.json";
    private final static String fileName = "tool_pool.json"; // Later definition


    public GToolPool() {
        
    }


    // ---------------------------------------------------------------------------------------------------------------------
    




    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Functions for Local Attributes
    public static String GetSQLDbDirectory() { return GToolPool.SQL_DB_DIRECTORY; }


}
