package Object;

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
 * used to measure the length of something.
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
 * 2) https://codereview.stackexchange.com/questions/88418/enum-constants-for-convert-units
**/

public enum LengthUnit implements MeasurableUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here: This described the most common units of length used in the hospital, 
    // along with the optimal scale for unit conversion. Set milli-meter to be default with relative 
    // coefficient as one (1).
    MM("Millimeter"), CM("Centimeter"), M("Meter"), KM("Kilometer"),
    IN("Inch"), FT("Foot"), YD("Yard"), MI("Mile"),

    ;

    // ---------------------------------------------------------------------------------------------------------------------
    private final String name;
    private double coefficient = 0.0d;

    LengthUnit(String name) { this.name = name; SetCoefficient(this); }
    LengthUnit(Object name) { this.name = (String) name; SetCoefficient(this);}

    public static double SetCoefficient(LengthUnit unit) { 
        if (unit.coefficient == 0) {
            switch (unit) {
                case MM: unit.coefficient = 1.0;
                case CM: unit.coefficient = 10.0;
                case M: unit.coefficient = 1.0e3; 
                case KM: unit.coefficient = 1.0e6; 
                case IN: unit.coefficient = 25.4; 
                case FT: unit.coefficient = 304.8; 
                case YD: unit.coefficient = 914.4; 
                case MI: unit.coefficient = 1609344.0;
            }
        }
        return unit.coefficient;
    }
    public String GetName() { return this.name; }
    public Object GetNameAsObject() { return (Object) this.GetName(); }
    public double GetCoefficient() { return this.coefficient; }
    public float GetCoefficientAsFloat() { return (float) this.coefficient; }
    public Object GetCoefficientAsObject()  { return (Object) this.coefficient; }
    public int GetCoefficientAsInt() { return (int) this.coefficient; }

    // ----------------------------------------------------------
    // Find the correct enum here
    public static LengthUnit GetEnum(String name) {
        for (LengthUnit p : LengthUnit.values()) {
            String p_name = p.GetName();
            if (p_name.equals(name) || p.GetNameAsObject().equals(name)) { return p; } 
            if (p_name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p_name.toUpperCase().equals(name.toUpperCase())) { return p; }

            if (name == p.toString() || p.toString().equals(name)) { return p; }
        }
        return null;
    }
    public static LengthUnit GetEnum(Object name) { return LengthUnit.GetEnum(name.toString());  }
    public static boolean FindEnum(String name) { return LengthUnit.GetEnum(name) != null; }
    public static boolean FindEnum(Object name) { return LengthUnit.GetEnum(name) != null; }
    public static LengthUnit GetDefault() { return LengthUnit.MM; }

    // ----------------------------------------------------------
    // Do conversion here
    public static double Convert(LengthUnit fromUnit, LengthUnit toUnit) {
        return fromUnit.GetCoefficient() / toUnit.GetCoefficient();
    }
}
