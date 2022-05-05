package GlobalPool;

import java.util.Hashtable;

import Room.LResourcePool;
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
 * This class is similar as an local resource pool, but it managed all resources available 
 * in the hospital as a manager, included the ones stored in the  storage room. This class 
 * connects to the SQL-DB database, but the description of each instance (in this pool: 
 * Resource) is stored in the module `Object.Resource.json` file.
 * 
 * The functionality of this instance is to:
 * 1) Provide a centralized access/place to all tools available in the hospital.
 * 2) Cross-checking them to all subsets (smaller distributed centers) such as the LResourcePool
 * (including the Storage Room) and report all the changes (including the errors, warnings, 
 * and the creation of new tools) to the GLogger.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class GResourcePool extends LResourcePool {
    
    private final static String FOLDER_DIRECTORY = "database/GlobalPool/";
    private final static String JSON_FILENAME = "GResourcePool.json";

    public GResourcePool(String ID) throws Exception { super(ID); }
    
    public GResourcePool(LResourcePool object_pool) throws Exception { super(object_pool); }

    public GResourcePool(GResourcePool object_pool) throws Exception { super((LResourcePool) object_pool); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter
    public static String GetFolderDirectory() { return GResourcePool.FOLDER_DIRECTORY; }
    public static String GetJsonFilename() { return GResourcePool.JSON_FILENAME; }



    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() { return super.Serialize(); }

    public void SerializeToFile() throws Exception {
        Hashtable<String, Object> data = this.Serialize();
        String dir = GResourcePool.GetFolderDirectory() + "/" + GResourcePool.GetJsonFilename();
        JsonUtils.SaveHashTableIntoJsonFile(dir, data, null);
    }

    public static GResourcePool Deserialize(Hashtable<String, Object> data) throws Exception {
        return new GResourcePool(GResourcePool.Deserialize(data));
    }

    public static GResourcePool DeserializeFromFile() throws Exception {
        String dir = GResourcePool.GetFolderDirectory() + "/" + GResourcePool.GetJsonFilename();
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(dir, null);
        return GResourcePool.Deserialize(data);
    }
}
