package GlobalPool;

import java.util.Hashtable;

import Room.LToolPool;
import Utility.JsonUtils;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class is similar as an local tool pool, but it managed all tools available in the 
 * hospital as a manager, included the ones stored in the storage room. This class connects 
 * to the SQL-DB database, but the description of each instance (in this pool: Tool) is 
 * stored in the module `Object.Tool.json` file.
 * 
 * The functionality of this instance is to:
 * 1) Provide a centralized access/place to all tools available in the hospital.
 * 2) Cross-checking them to all subsets (smaller distributed centers) such as the LToolPool
 * (including the Storage Room) and report all the changes (including the errors, warnings, 
 * and the creation of new tools) to the GLogger.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/


public class GToolPool extends LToolPool {
    
    private final static String FOLDER_DIRECTORY = "database/GlobalPool/";
    private final static String JSON_FILENAME = "GToolPool.json";

    public GToolPool(String ID) throws Exception { super(ID); }
    
    public GToolPool(LToolPool object_pool) throws Exception { super(object_pool); }

    public GToolPool(GToolPool object_pool) throws Exception { super((LToolPool) object_pool); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter
    public static String GetFolderDirectory() { return GToolPool.FOLDER_DIRECTORY; }
    public static String GetJsonFilename() { return GToolPool.JSON_FILENAME; }



    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() { return super.Serialize(); }

    public void SerializeToFile() throws Exception {
        Hashtable<String, Object> data = this.Serialize();
        String dir = GToolPool.GetFolderDirectory() + "/" + GToolPool.GetJsonFilename();
        JsonUtils.SaveHashTableIntoJsonFile(dir, data, null);
    }

    public static GToolPool Deserialize(Hashtable<String, Object> data) throws Exception {
        return new GToolPool(LToolPool.Deserialize(data));
    }

    public static GToolPool DeserializeFromFile() throws Exception {
        String dir = GToolPool.GetFolderDirectory() + "/" + GToolPool.GetJsonFilename();
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(dir, null);
        return GToolPool.Deserialize(data);
    }
}
