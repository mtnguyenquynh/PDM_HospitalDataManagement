package BaseClass;

import java.util.Hashtable;

import PrefixState.Prefix;
import Utility.DataUtils;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class described the abstract / generalized version of the base object 
 * such as the tool, resource (medicine/drug), and probably other thing.
 * Note that these object are dead object which does not involve in any
 * high-level logic or object-oriented interaction. It may be involved in other 
 * top-level component such as the functioning module, but not this one, as the
 * project focuses on how we managed the data through the data-classes and 
 * functioning-classes.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class BaseObject extends IntermediateObject {
    // ---------------------------------------------------------------------------------------------------------------------
    private String description;           // This is the description of the object
    private int number;                   // This is the amount or number of the object
    protected Prefix prefix;

    public BaseObject(String ID, String name, String description, int number) throws Exception {
        super(ID, name, description);
        DataUtils.CheckArgumentCondition(number >= 0, "Number cannot be negative.");
        this.number = number;
        this.prefix = Prefix.BaseObject;
    }
    public BaseObject(String ID) throws Exception { this(ID, "", null, 0); }
    public BaseObject(String ID, String name) throws Exception { this(ID, name, null, 0); }
    public BaseObject(String ID, int number) throws Exception { this(ID, "", null, number); }
    public BaseObject(String ID, String name, int number) throws Exception { this(ID, name, null, number); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetDescription() { return this.description; }
    public void SetDescription(String description) { this.description = (description == null) ? "" : description; }

    public int GetNumber() { return this.number; }
    public void SetNumber(int number) { 
        DataUtils.CheckArgumentCondition(number >= 0, "Number cannot be negative.");
        this.number = number; 
    }

    public void IncrementNumber(int number) {
        DataUtils.CheckArgumentCondition(number >= 0, "Number cannot be negative.");
        if (number != 0) { this.number += number; }
    }
    public void DecrementNumber(int number) {
        DataUtils.CheckArgumentCondition(number >= 0, "Number cannot be negative.");
        if (number != 0) {
            DataUtils.CheckArgumentCondition(this.number >= number, "Number cannot be negative.");
            this.number -= number;
        }
    }

    // ----------------------------------------------------------
    public static Prefix GetPrefix() { return Prefix.BaseObject; }
    public static String GetPrefixCode() { return BaseObject.GetPrefix().GetPrefixCode(); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("description", this.GetDescription());
        result.put("number", this.GetNumber());
        return result;
    }

    public static BaseObject Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        int number = (int) data.get("number");
        return new BaseObject(ID, name, description, number);
    }
}
