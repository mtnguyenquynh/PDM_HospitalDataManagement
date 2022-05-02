package BaseClass;

import java.util.Hashtable;

import Utility.Utils;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class inherited from the AbstractObject, which added another field
 * called as "Description". This class is extensively useful when we want to 
 * code multiple objects that have an extra note to carry on but the developers
 * are lazy to write more modules. 
 * 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class IntermediateObject extends AbstractObject {
    private String description;                    
    public IntermediateObject(String ID, String name, String description) {
        super(ID, name);
        this.description = description;
    }
    
    public IntermediateObject(String ID) { this(ID, null, null); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetDescription() { return this.description; }
    public String SetDescription() { return this.description; }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("description", this.GetDescription());
        return result;
    }

    public static IntermediateObject Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        return new IntermediateObject(ID, name, description);
    }
}
