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
 * used to measure the volume of something.
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
 * 2) https://codereview.stackexchange.com/questions/88418/enum-constants-for-convert-units
**/

public enum VolumeUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here: This described the most common units of weight used in the hospital, 
    // along with the optimal scale for unit conversion. Set milligram to be default with relative 
    // coefficient as one (1).
    ML("Milli-liter"), L("Liter"), DROPLET("Droplet"),
    OZ("Ounce"), PINT("Pint"), QUART("Quart"), GALLON("Gallon"),
    ;

    // ---------------------------------------------------------------------------------------------------------------------
    private final String name;
    private double coefficient = 0.0d;

    VolumeUnit(String name) { this.name = name; SetCoefficient(this); }
    VolumeUnit(Object name) { this.name = (String) name; SetCoefficient(this);}

    public static double SetCoefficient(VolumeUnit unit) { 
        if (unit.coefficient == 0) {
            switch (unit) {
                case ML: unit.coefficient = 1;
                case L: unit.coefficient = 1000.0;
                case DROPLET: unit.coefficient = 0.001;
                case OZ: unit.coefficient = 29.5735295625;
                case PINT: unit.coefficient = 473.176473;
                case QUART: unit.coefficient = 946.352946;
                case GALLON: unit.coefficient = 3785.411784;
            }
        }
        return unit.coefficient;
    }
    public String GetName() { return this.name; }
    public Object GetNameAsObject() { return (Object) this.GetName(); }
    public double GetCoefficient() { return this.coefficient; }
    public float GetCoefficientAsFloat() { return (float) this.coefficient; }

    // ----------------------------------------------------------
    // Find the correct enum here
    public static VolumeUnit GetEnum(String name) {
        for (VolumeUnit p : VolumeUnit.values()) {
            String p_name = p.GetName();
            if (p_name.equals(name) || p.GetNameAsObject().equals(name)) { return p; } 
            if (p_name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p_name.toUpperCase().equals(name.toUpperCase())) { return p; }

            if (name == p.toString() || p.toString().equals(name)) { return p; }
        }
        return null;
    }
    public static VolumeUnit GetEnum(Object name) { return VolumeUnit.GetEnum(name.toString());  }

    public static boolean FindEnum(String name) { return VolumeUnit.GetEnum(name) != null; }
    public static boolean FindEnum(Object name) { return VolumeUnit.GetEnum(name) != null; }

    public static VolumeUnit GetDefaultVolumetUnit() { return VolumeUnit.ML; }

    // ----------------------------------------------------------
    // Do conversion here
    public static double Convert(VolumeUnit fromUnit, VolumeUnit toUnit) {
        return fromUnit.GetCoefficient() / toUnit.GetCoefficient();
    }
}
