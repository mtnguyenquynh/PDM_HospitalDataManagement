package BaseClass;

import java.util.Hashtable;

import Utility.DataUtils;
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
 * This class described the abstract object that applied nearly for all built-in object, 
 * which is not limited to the 'dead thing' such as tool, resource (i.e medicine or drug),
 * to even 'living thing' such as patient, doctor, nurse (medico).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class AbstractObject {
    // This is the unique ID and the name of the object
    private static final int SERIALIZATION_CAPACITY = 10000;
    private static final float SERIALIZATION_LOAD_FACTOR = 0.75f;  

    private String ID, name;                    
    public AbstractObject(String ID, String name) throws Exception {
        Utils.CheckArgumentCondition(ID != null, "ID cannot be null.");
        Utils.CheckArgumentCondition(ID.length() > 0, "ID cannot be empty.");
        this.ID = ID;
        this.name = name;
    }

    public AbstractObject(String ID) throws Exception { this(ID, ""); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetID() { return this.ID; }
    public String GetName() { return this.name; }
    public void SetName(String name) throws Exception { this.name = name; }

    public static int GetSerializationCapacity() { return AbstractObject.SERIALIZATION_CAPACITY; }
    public static float GetSerializationLoadFactor() { return AbstractObject.SERIALIZATION_LOAD_FACTOR; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = DataUtils.ForceGetEmptyHashtable(this.getClass());
        result.put("id", this.GetID());
        result.put("name", this.GetName());
        return result;
    }

    public static AbstractObject Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        return new AbstractObject(ID, name);
    }

}
