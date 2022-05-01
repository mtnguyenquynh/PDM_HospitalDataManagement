package Object;

import java.lang.Enum;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This module described a set/pool of enum-object (which can declared) 
 * by the class name/value. which used for the counting of any resources 
 * object, which is not limited to weights, volume, length.
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
 * 2) https://stackoverflow.com/questions/8732710/enum-within-an-enum
 * 3) https://stackoverflow.com/questions/10017729/multiple-enum-classes-in-one-java-file (Recommended)
**/


public enum ResourceUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    /**
     * This enums will contained all measurable units in real-world here
     * For each enum constant, declare the unit name (First character is upper), 
     * its (simple-written) type and its coefficient. The enum constant is a simple-written name too.
     * For example, milli-gram, has the defination as this: MG("milli-gram", "weight", 1.0).
     * 
     * The coefficient is used for the calculation of the unit and choose the unit having the "milli" as default (coefficient = 1).
     * 
     * 
     * The enum also have only one unknown unit named "UNIT" whose name = "Unit", type = "None", and the coefficient = 1.
     * The enum constant UNIT have similar properties as "None" in Python, "NaN" (or "Nan") in Arithmetic number, 
     * which does not support coefficient conversion. 
     * If the name of enum constant is dupplicated, add "_" + the first char of the 'type' to the end of the name. 
     * 
     * All convertable units are affected linearly.
     * 
     * @param name (string): The name of the unit.
     * @param type (string): The type of the unit.
     * @param coefficient (double): The coefficient of the unit.
     * 
     * @author Ichiru Take
     * @version 0.0.1
     * 
     */

    // ---------------------------------------------------------------------------------------------------------------------
    UNIT("Unit", "None", 1.0),                  // Unknown unit

    // Weight-Unit
    UG("Micro-gram", "Weight", 1.0e-3),
    CARAT("Carat", "Weight", 0.2), 
    MG("Milli-gram", "Weight", 1.0),
    OZ_W("Ounce", "Weight", 28.349523125),
    LB("Pound", "Weight", 453.59237),
    G("Gram", "Weight", 1000.0),
    KG("Kilo-gram", "Weight", 1.0e3),
    ST("Stone", "Weight", 6350.29318),
    T("Ton", "Weight", 1.0e6),    

    // Volume-Unit
    DROPLET("Droplet", "Volume", 0.05),         // 1 ml = 20 drops
    ML("Milli-liter", "Volume", 1.0),
    TEASPOON("Teaspoon", "Volume", 4.92892159375),
    TABLESPOON("Tablespoon", "Volume", 14.78676478125),
    OZ_V("Ounce", "Volume", 29.5735295625),
    CUP("Cup", "Volume", 236.588237),
    QT("Quart", "Volume", 946.352946),
    L("Liter", "Volume", 1000.0),
    GAL("Gallon", "Volume", 3785.411784),
    
    // Length-unit
    MM("Milli-meter", "Length", 1.0),
    CM("Centi-meter", "Length", 10.0),
    M("Meter", "Length", 1000.0),
    KM("Kilo-meter", "Length", 1.0e3),
    IN("Inch", "Length", 25.4),
    FT("Foot", "Length", 304.8),
    YD("Yard", "Length", 914.4),
    MI("Mile", "Length", 1609344.0),
    
    
    ;

    // ---------------------------------------------------------------------------------------------------------------------
    // Declaration of the enum constant
    private final String name;
    private final String type;
    private final double coefficient;
    ResourceUnit(String name, String type, double coefficient) {
        this.name = name; 
        this.type = type;
        this.coefficient = coefficient; 
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Get-Default Functions
    public String GetName() { return this.name; }

    public String GetType() { return this.type; }

    public double GetCoefficient() { return this.coefficient; }
    public float GetCoefficientAsFloat() { return (float) this.coefficient; }
    public int GetCoefficientAsInt() { return (int) this.coefficient; }

    public static ResourceUnit GetStaticDefault() { return ResourceUnit.UNIT; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Find the correct enum here
    public static ResourceUnit GetEnum(String name, String type) {
        for (ResourceUnit p : ResourceUnit.values()) {
            if (name == p.toString() || p.toString().equals(name)) { return p; }
            if (type == p.toString() || p.toString().equals(type)) { return p; }
            
            String p_name = p.GetName();
            String p_type = p.GetType();

            // Filter to correct enum by the type first, then name later
            if (!p_type.equals(type) && p_type.charAt(0) != type.charAt(0) 
                && p_type.toUpperCase().charAt(0) != type.toUpperCase().charAt(0)) {
                continue;
            }
            // The type is guaranteed to be correct, so we can compare the name
            if (p_name.equals(name)) { return p; } 
            if (p_name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p_name.toUpperCase().equals(name.toUpperCase())) { return p; }
        }
        return null;
    }
    public static boolean FindEnum(String name, String type) { return ResourceUnit.GetEnum(name, type) != null; }
    public static ResourceUnit GetDefault() { return ResourceUnit.UNIT; }

    // ----------------------------------------------------------
    // Do conversion here
    public static double Convert(ResourceUnit fromUnit, ResourceUnit toUnit) {
        if (!fromUnit.GetType().equals(toUnit.GetType()) ) {
            throw new IllegalArgumentException("The two units are not of the same type.");
        }
        return fromUnit.GetCoefficient() / toUnit.GetCoefficient();
    }
    public double Convert(ResourceUnit toUnit) { return ResourceUnit.Convert(this, toUnit); }


}
