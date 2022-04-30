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


public class ResourcesUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    public static Enum GetEnum(String name) throws IllegalArgumentException {
        Enum p = null;
        if (p == null) { p = WeightUnit.GetEnum(name); }
        if (p == null) { p = VolumeUnit.GetEnum(name); }
        if (p == null) { p = LengthUnit.GetEnum(name); }
        if (p == null) {throw new IllegalArgumentException("No such enum constant: " + name); }
        return p;
    }
}
