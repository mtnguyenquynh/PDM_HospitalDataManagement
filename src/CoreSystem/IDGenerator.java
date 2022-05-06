package CoreSystem;

import java.util.Hashtable;

import BaseClass.BaseObject;
import Utility.JsonUtils;
import PrefixState.Prefix;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class described the ID-Generator for dead objects such as the Resource, 
 * and Tool. The ID is casted by the prefix and the six-digit ID itself.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class IDGenerator {
    // ---------------------------------------------------------------------------------------------------------------------
    // Although it is not 100% correct to load as String[], but we left there to ensure the progress
    // of the project and future software development.

    private final Hashtable<Prefix, String[]> ID_Store;
    private final static String counter = "count";
    public IDGenerator() {
        this.ID_Store = new Hashtable<Prefix, String[]>(100, 0.75f);
        this.ID_Store.put(Prefix.Tool, new String[] {"database/GlobalPool/Tool.json"});
        this.ID_Store.put(Prefix.Resource, new String[] {"database/GlobalPool/Resource.json"});
        this.ID_Store.put(Prefix.BaseObject, new String[] {"database/GlobalPool/BaseObject.json"});
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public String GenerateObjectID(BaseObject object, boolean forceUpdate) throws Exception {
        // Step 1: Get the prefix and Validate whether it is available to create the ID
        Prefix prefix = object.GetThisPrefix();
        String[] ID_Pool = this.ID_Store.get(prefix);
        JsonUtils.CheckArgumentCondition(ID_Pool != null, "The ID Generator is not prepared for this object.");
        
        // Step 2: Loading the directory of the ID pool
        String directory = ID_Pool[0];
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(directory, null);
        int count = (int) data.get(IDGenerator.counter);

        String ID = prefix.GetPrefixCodeNotation() + String.format("%06d", count);

        // Step 3: Update the counter
        if (forceUpdate) {
            data.put(IDGenerator.counter, count + 1);
            JsonUtils.SaveHashTableIntoJsonFile(directory, data, null);
        }
        return ID; 
    }

    public String GenerateObjectID(BaseObject object) throws Exception {
        return this.GenerateObjectID(object, true);
    }

    private void ChangeCounter(String class_name, int amount) throws Exception {
        if (!Prefix.FindEnum(class_name)) {
            return ;
        }

        Prefix pr = Prefix.GetEnum(class_name);
        String directory = this.ID_Store.get(pr)[0];
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(directory, null);
                
        int count = (int) data.get(IDGenerator.counter);
        data.put(IDGenerator.counter, count + amount);
        JsonUtils.SaveHashTableIntoJsonFile(directory, data, null);
        return ;
    }

    public void IncreaseCounter(String class_name) {
        try { this.ChangeCounter(class_name, 1); } 
        catch (Exception e) { e.printStackTrace(); }
    }

    public void DecreaseCounter(String class_name) {
        try { this.ChangeCounter(class_name, -1); } 
        catch (Exception e) { e.printStackTrace(); }
    }   

}
