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
 * This module described an interface for all measurable units such as weight, volume, etc.
 * The basic datatype is double (double-precision floating-point, 64-bits).
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
 * 2) https://codereview.stackexchange.com/questions/88418/enum-constants-for-convert-units
**/


public interface MeasurableUnit {
    
    // ----------------------------------------------------------
    // Getter
    public double GetCoefficient();
    public Object GetCoefficientAsObject();
    public float GetCoefficientAsFloat();
    public int GetCoefficientAsInt();

    // ----------------------------------------------------------
    // Calculate the conversion between two units.
    public static double Convert(MeasurableUnit fromUnit, MeasurableUnit toUnit) {
        return fromUnit.GetCoefficient() / toUnit.GetCoefficient();
    }
}
