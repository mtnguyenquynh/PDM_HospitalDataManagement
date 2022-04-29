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
 * This class described the abstract object that applied nearly for all built-in
 * object, which is not limited to the 'dead thing' such as tool, resource (medicine/drug),
 * to even 'living thing' such as patient, doctor, nurse (medico).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class AbstractObject {
    private String ID, name;                    // This is the unique ID and the name of the object
    public AbstractObject(String ID, String name) {
        Utils.CheckArgumentCondition((ID == null || name == null), "ID or name cannot be negative.");
        this.ID = ID;
        this.name = name;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetID() { return this.ID; }
    public String GetName() { return this.name; }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> serialize() {
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put("id", this.GetID());
        result.put("name", this.GetName());
        return result;
    }

    public static AbstractObject deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        return new AbstractObject(ID, name);
    }
}
