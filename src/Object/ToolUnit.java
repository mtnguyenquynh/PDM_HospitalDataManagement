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
 * This module described a set/pool of enum-object (which can declared) by the class name/value.
 * which used for the counting of any tool object such as box, unit, pair, package, etc.
 * Note that all the enum constant and its value are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
**/


public enum ToolUnit {
    // ---------------------------------------------------------------------------------------------------------------------
    // Please sort by the alphabetical order of the class name if another developers jump into.
    BOX("Box"),
    COMBO("Combo"), 
    PACKAGE("Package"),
    PAIRS("Pairs"),
    SET("Set"),
    UNIT("Unit"),

    ;

    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here.
    private final String name;
    ToolUnit(String name) { this.name = name; }    
    public String GetName() { return this.name; }

    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the name.
    public static ToolUnit GetEnum(String name) {
        for (ToolUnit p : ToolUnit.values()) {
            String p_name = p.GetName();
            if (p_name.equals(name)) { return p; } 
            if (p_name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p_name.toUpperCase().equals(name.toUpperCase())) { return p; }
            if (name == p.toString()) { return p; }
        }
        return null;
    }
    public static boolean FindEnum(String name) { return ToolUnit.GetEnum(name) != null; }
    public static ToolUnit GetDefault() { return ToolUnit.UNIT; }
}
