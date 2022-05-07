package BaseClass;

import java.util.Hashtable;

import Utility.DataUtils;
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
        DataUtils.CheckArgumentCondition(ID != null, "ID cannot be null.");
        DataUtils.CheckArgumentCondition(ID.length() > 0, "ID cannot be empty.");
        if (name == null) { name = ""; }
        this.ID = ID;
        this.name = name;
    }

    public AbstractObject(String ID) throws Exception { this(ID, ""); }

    public AbstractObject(int ID, String name) throws Exception { this(String.valueOf(ID), name); }

    public AbstractObject(int ID) throws Exception { this(String.valueOf(ID), ""); }

    public AbstractObject() throws Exception { this(-1, ""); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetID() { return this.ID; }
    public void SetID(String ID) throws Exception {
        DataUtils.CheckArgumentCondition(ID != null, "ID cannot be null.");
        DataUtils.CheckArgumentCondition(ID.length() > 0, "ID cannot be empty.");
        
        if (this.GetID().equals("-1")) { this.ID = ID; }
        throw new Exception("The ID cannot be changed.");
    }

    public String GetName() { return this.name; }
    public void SetName(String name) throws Exception { 
        JsonUtils.CheckArgumentCondition(name != null, "Object's name cannot be null.");
        this.name = name; 
    }

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
