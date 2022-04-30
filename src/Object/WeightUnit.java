package Object;

import Object.MeasurableUnit;

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
 * used to measure the weight of something.
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
 * 2) https://codereview.stackexchange.com/questions/88418/enum-constants-for-convert-units
**/


public enum WeightUnit implements MeasurableUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here: This described the most common units of weight used in the hospital, 
    // along with the optimal scale for unit conversion. Set milligram to be default with relative 
    // coefficient as one (1).
    UG("Microgram"), MG("Milligram"), G("Gram"), KG("Kilogram"), TON("Ton"),
    OZ("Ounce"), LB("Pound"), ST("Stone"), CARAT("Carat"), 
    ;

    // ---------------------------------------------------------------------------------------------------------------------
    private final String name;
    private double coefficient = 0.0d;

    WeightUnit(String name) { this.name = name; SetCoefficient(this); }
    WeightUnit(Object name) { this.name = (String) name; SetCoefficient(this);}

    public static double SetCoefficient(WeightUnit unit) { 
        if (unit.coefficient == 0) {
            switch (unit) {
                case UG: unit.coefficient = 1e-3;
                case CARAT: unit.coefficient = 0.2;
                case MG: unit.coefficient = 1.0; 
                case OZ: unit.coefficient = 28.349523; 
                case G: unit.coefficient = 1000.0; 
                case LB: unit.coefficient = 453.59237; 
                case ST: unit.coefficient = 6350.29318; 
                case KG: unit.coefficient = 1.0e6; 
                case TON: unit.coefficient = 1.0e9;
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
    // Magic here: Declare the enum constant as a static final field using the name.
    public static WeightUnit GetEnum(String name) {
        for (WeightUnit p : WeightUnit.values()) {
            String p_name = p.GetName();
            if (p_name.equals(name) || p.GetNameAsObject().equals(name)) { return p; } 
            if (p_name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p_name.toUpperCase().equals(name.toUpperCase())) { return p; }

            if (name == p.toString() || p.toString().equals(name)) { return p; }
        }
        return null;
    }
    public static WeightUnit GetEnum(Object name) { return WeightUnit.GetEnum(name.toString());  }

    public static boolean FindEnum(String name) { return WeightUnit.GetEnum(name) != null; }
    public static boolean FindEnum(Object name) { return WeightUnit.GetEnum(name) != null; }

    public static WeightUnit GetDefault() { return WeightUnit.MG; }

    // ----------------------------------------------------------
    // Do conversion here
    public static double Convert(WeightUnit fromUnit, WeightUnit toUnit) {
        return fromUnit.GetCoefficient() / toUnit.GetCoefficient();
    }
}
