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
    ToolUnit(Object name) { this.name = (String) name; }
    
    public String GetName() { return this.name; }
    public Object GetNameAsObject() { return (Object) this.GetName(); }

    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the prefix.
    public static ToolUnit GetEnumByName(String name) {
        for (ToolUnit p : ToolUnit.values()) {
            if (p.name.equals(name) || p.GetNameAsObject().equals(name)) { return p; } 
            if (p.name.toLowerCase().equals(name.toLowerCase())) { return p; }
            if (p.name.toUpperCase().equals(name.toUpperCase())) { return p; }
        }
        return null;
    }
    public static ToolUnit GetEnumByName(Object name) { return ToolUnit.GetEnumByName(name.toString());  }

    public static boolean FindEnumByName(String name) { return ToolUnit.GetEnumByName(name) != null; }
    public static boolean FindEnumByName(Object name) { return ToolUnit.GetEnumByName(name) != null; }

    public static ToolUnit GetDefaultToolUnit() { return ToolUnit.UNIT; }
}
