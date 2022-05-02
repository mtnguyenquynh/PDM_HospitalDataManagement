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

public class Resource extends BaseObject {
    // ---------------------------------------------------------------------------------------------------------------------
    private final ResourceUnit unit;
    private float price;
    private static final Prefix prefix = Prefix.Resource;
   

    public Resource(String ID, String name, String description, int number, ResourceUnit unit, 
                    float price) {
        // You may want to add more fields or attributes here.
        super(ID, name, description, number);
        this.price = price;
        this.unit = unit;
    }

    public Resource(String ID, String name, String description, int number, ResourceUnit unit, 
                    double price) {
        // You may want to add more fields or attributes here.
        super(ID, name, description, number);
        this.price = (float) price;
        this.unit = unit;
    }

    public Resource(String ID, String name, String description, int number, ResourceUnit unit, 
                    int price) {
        // You may want to add more fields or attributes here.
        super(ID, name, description, number);
        this.price = (float) price;
        this.unit = unit;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Functions
    public float GetPrice() { return this.price; }
    public double GetPriceAsDouble() { return (double) this.price; }
    public int GetPriceAsInt() { return (int) this.price; }
    
    public void SetPrice(float price) { this.price = price; }
    public void SetPrice(double price) { this.price = (float) price; }
    public void SetPrice(int price) { this.price = (float) price; }

    public void UpdatePriceByPercentage(float percentage) { this.price *= (1 + percentage); }
    public void UpdatePriceByFixedCost(float cost) { this.price += cost; }

    // ----------------------------------------------------------
    public ResourceUnit GetUnit() { return this.unit; }
    public String GetUnitAsString() { return this.unit.toString(); }

    public static Prefix GetPrefix() { return Resource.prefix; }
    public static String GetPrefixCode() { return Resource.GetPrefix().GetPrefixCode(); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = super.Serialize();
        result.put("price", this.price);
        result.put("unit_name", this.GetUnit().GetName());
        result.put("unit_type", this.GetUnit().GetType());
        return result;
    }

    public static Resource Deserialize(Hashtable<String, Object> data) {
        String ID = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        int number = (int) data.get("number");
        ResourceUnit unit = ResourceUnit.GetEnum((String) data.get("unit_name"), (String) data.get("unit_type"));
        float price = (float) data.get("price");
        return new Resource(ID, name, description, number, unit, price);
    }

}
