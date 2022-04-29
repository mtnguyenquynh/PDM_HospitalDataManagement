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

public class BaseObject extends AbstractObject {
    // ---------------------------------------------------------------------------------------------------------------------
    private String description;           // This is the description of the object
    private int amount;                   // This is the amount or number of the object
    
    public BaseObject(String ID, String name, String description, int amount) {
        super(ID, name);

        Utils.CheckArgumentCondition(amount >= 0, "Amount/number cannot be negative.");
        this.description = (description == null) ? "": description;
        this.amount = amount;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetDescription() { return this.description; }
    public void SetDescription(String description) { this.description = (description == null) ? "" : description; }

    public int GetAmount() { return this.amount; }
    public void SetAmount(int amount) { this.amount = (amount < 0) ? 0 : amount; }

    public void UpdateAmount(int amount) {
        if (amount < 0) { throw new IllegalArgumentException("Amount/number cannot be negative."); }
        this.amount += amount;
    }
    public void DecrementAmount(int amount) {
        if (amount < 0) { throw new IllegalArgumentException("Amount/number cannot be negative."); }
        this.amount -= amount;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("description", this.GetDescription());
        result.put("amount", this.GetAmount());
        return result;
    }

    public static BaseObject Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        int amount = (int) data.get("amount");
        return new BaseObject(ID, name, description, amount);
    }
}
