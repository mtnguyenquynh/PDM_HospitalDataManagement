package Object;

import java.util.Hashtable;

import BaseClass.BaseObject;
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
 * This class described the tool that we want to record for use in a hospital,
 * such as needle/syringe, syringe, etc. In some scenario, the object may involved 
 * special measurement such as the 500-ml syringe, 100-ml syringe. You should add 
 * them into the 'description' field rather make another attribute to keep track of 
 * it. Unless necessary, build a new class for it.
 * Note that if the unit is fixed, it cannot be changed.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/


public class Tool extends BaseObject {
    // ---------------------------------------------------------------------------------------------------------------------
    private final ToolUnit unit;
    private final Prefix prefix;

    public Tool(String ID, String name, String description, int amount, ToolUnit unit) throws Exception {
        // You may want to add more fields or attributes here.
        super(ID, name, description, amount);
        this.unit = unit;
        this.prefix = Prefix.Tool;
    }

    public Tool(String ID, String name, String description, int amount) throws Exception {
        this(ID, name, description, amount, ToolUnit.GetDefault());
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function
    public ToolUnit GetUnit() { return this.unit; }

    public static Prefix GetPrefix() { return Prefix.Tool; }
    public static String GetPrefixCode() { return Tool.GetPrefix().GetPrefixCode(); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("unit", this.GetUnit().GetName());
        return result;
    }

    public static Tool Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        int number = (int) data.get("number");
        ToolUnit unit = ToolUnit.GetEnum((String) data.get("unit"));
        return new Tool(ID, name, description, number, unit);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public boolean CheckToolID(String ToolID) { 
        if (ToolID == null) { return false; }
        return this.GetID() == ToolID || this.GetID().equals( ToolID); 
    }
    public boolean CheckToolID(Object ToolID) { return this.CheckToolID(ToolID.toString()); }

}
