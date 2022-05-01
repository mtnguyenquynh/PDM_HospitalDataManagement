package GlobalPool;

import java.util.ArrayList;
import java.util.Hashtable;

import Object.Tool;
import Object.ToolUnit;

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
 * storage room. This class connects to the SQL-DB database, but the description 
 * of each instance (in this pool: Tool) is stored in the module 
 * `Object.Tool.json` file.
 * 
 * The functionality of this instance is to:
 * 1) Provide a centralized access/place to all tools available in the hospital.
 * 2) Cross-checking them to all distribution smaller centers such as the LToolPool
 * (including the Storage Room) and report all the changes (including the errors, 
 * warnings, and the creation of new tools) to the GLogger.
 * 
 * The functionality of this approach is to provide a centralized place to 
 * store all data/information about the tools, as well as cross-checking them
 * to all distribution places/centers such as LToolPool.class and report them 
 * to GLogger.class.
 * 
 * Note that the pool and the cache are guaranteed to have the fixed-size
 *
 * TODO: GLogger.java will be write later
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class GToolPool {
    // ---------------------------------------------------------------------------------------------------------------------
    // Connection to Directory
    private final static String FOLDER_DIRECTORY = "database/GlobalPool/"; 
    private final static String DEFINITION_FILENAME = "Tool.json";

    // Worked on later
    private final static String DB_FILENAME = "tool_pool.json"; // Later definition
    private final static String ROOM_CODE_DIRECTORY = "database/StorageRoom/";


    // This is used to store a view for modification. This laid a foundation for GResourcePool,
    // which is much more complicated to program.
    private final static int CAPACITY = 10;
    private ArrayList<Tool> LocalPool = new ArrayList<Tool>(CAPACITY); 

    public GToolPool() {
        this._InitPool_();
    }


    

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter to all Tools    
    public Tool CreateTool(String ID, String name, String description, int amount, ToolUnit unit) {
        return new Tool(ID, name, description, amount, unit);
    }

    public boolean CheckIsToolAtIndexANull(int index) { 
        this.CheckIndexInRange(index);
        return this.LocalPool.get(index) == null; 
    }

    /**
     * This function is to safely add a tool inside the (local) array here
     * @param tool (Tool): The considered `Tool` to insert in array
     * @param index (int): The index to insert the tool
     * @param force (boolean): If true, the tool will be inserted even if the index is occupied.
     *                         Otherwise, the insertion is abandoned and IllegalArgumentException \
     *                         is raised.
     * @return Tool
     */
    public Tool AddToolToLocalPool(Tool tool, int index, boolean force) {
        if (tool == null) { this.EmptyToolByIndex(index); return null;}

        this.CheckIndexInRange(index);
        if (this.GetToolByIndex(index) == null) {
            this.LocalPool.add(index, tool);
            return null;
        } else {
            if (force) { return this.LocalPool.set(index, tool); } 
            else { throw new IllegalArgumentException("This index is already occupied."); }
        }
    }

    /**
     * This function is to get a tool inside the (local) array here
     * @param ToolID (String): If ToolID is null or not found, then the method will raise an 
     *                         IllegalArgumentException.
     * @return Tool
     */
    public Tool GetTool(String ToolID) {
        for (Tool tool: this.LocalPool) {
            if (tool != null) {
                 if (tool.CheckToolID(ToolID)) { return tool; }
            }
        }
        throw new IllegalArgumentException("The tool with ID: " + ToolID + " is not found.");
    }

    /**
     * This function is to get a tool inside the (local) array here but accept null output. 
     * @param index (int): The index to get the tool
     * @return Tool (Note that a null output is acceptable)
     */
    public Tool GetToolByIndex(int index) {
        this.CheckIndexInRange(index);
        return this.LocalPool.get(index);
    }


    /**
     * This function is to replace a tool inside the (local) array here
     * @param tool (Tool): The considered `Tool` to insert in array
     * @param index (int): The index to remove the tool
     * @return Tool (the previous tool stored at `index`)
     */
    public Tool ReplaceTool(Tool tool, int index) {
        this.CheckIndexInRange(index);
        return this.LocalPool.set(index, tool);
    }

    /**
     * This function is to remove a tool inside the (local) array here
     * @param index (int): The index to remove the tool
     * @return Tool (the previous tool stored at `index`)
     */
    public Tool EmptyToolByIndex(int index) { return this.ReplaceTool(null, index); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Functions for Local Attributes
    public static String GetFolderDirectory() { return GToolPool.FOLDER_DIRECTORY; }
    public static String GetDefinitionFilename() { return GToolPool.DEFINITION_FILENAME; } 
    public static String GetDBFilename() { return GToolPool.DB_FILENAME; }
    public static int GetCapacity() { return GToolPool.CAPACITY; }

    public ArrayList<Tool> GetPool() { return this.LocalPool; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Initialize the pool & Several Ultility Functions
    private void CleanPool() { this.LocalPool.clear(); }

    private void _InitPool_() { 
        if (this.LocalPool.size() != 0) { this.CleanPool(); }
        for (int i = 0; i < GToolPool.GetCapacity(); i++) {
            this.LocalPool.add(null);
        }
    }

    private void CheckIndexInRange(int index) {
        if (index < 0 || index > GToolPool.GetCapacity()) { 
            throw new IndexOutOfBoundsException("The index must be between 0 and " + (GToolPool.GetCapacity() - 1));
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Initialize the pool 


}
