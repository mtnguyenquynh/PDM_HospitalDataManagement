package PrefixState;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This file described a set/pool of prefix by the class name.
 * The enum constant must be the same as the class name, while the
 * value of the (enum) constant must be the representation of the class. 
 * Note that all the prefixes are case-sensitive (zero-words duplication).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/23496165/initiating-an-enum-as-value
**/


public enum Prefix {
    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here: Core System for Patient-Medico Interaction
    Patient("Pt-"),
    Doctor("Dt-"),
    Nurse("Ns-"),
    MedicalRecord("MR-"),

    Treatment("Tm-"), 
    TreatmentCode("TmC-"), 
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here: Global Pool to keep track of objects in the hospital.
    Tool("Tl-"),
    Resources("Rs-"),
    Room("Rm-"),
    PatientRoom("PR-"),
    MedicoRoom("MR-"),
    ObjectRoom("OR-"),
    LToolPool("LT-"),
    LResourcesPool("LR-"),
    ;



    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here.
    private String prefix;
    Prefix(String prefix) { this.prefix = prefix; }
    Prefix(Object prefix) { this.prefix = (String) prefix; }

    public String GetPrefix() { return this.prefix; }
    public Object GetPrefixAsObject() { return (Object) this.GetPrefix();}

    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the prefix.
    public static Prefix GetEnumByPrefix(String prefix) {
        String prefixNoDash = prefix.replace("-", "");
        for (Prefix p : Prefix.values()) {
            if (p.prefix.equals(prefix) || p.GetPrefixAsObject().equals(prefix)) { return p; } 
            if (p.prefix.startsWith(prefixNoDash)) { return p; }
     }
        return null;
    }
    public static Prefix GetEnumByPrefix(Object prefix) { return Prefix.GetEnumByPrefix(prefix.toString());  }
    
    public static boolean FindEnumByPrefix(String prefix) { return Prefix.GetEnumByPrefix(prefix) != null; }
    public static boolean FindEnumByPrefix(Object prefix) { return Prefix.GetEnumByPrefix(prefix) != null; }

    // -----------------------------
    // Magic here: Declare the enum constant as a class name.
    public static Prefix GetEnumByClassName(String className) {
        for (Prefix p : Prefix.values()) {
            if (p.toString().equals(className)) { return p; }
        }
        return null;
    }

    public static Prefix GetEnumByClassName(Object className) { return Prefix.GetEnumByClassName(className.toString()); }
    public static boolean FindEnumByClassName(String className) { return Prefix.GetEnumByClassName(className) != null; }
    public static boolean FindEnumByClassName(Object className) { return Prefix.GetEnumByClassName(className) != null; }

    // ----------------------------- 
    // Find the correct enum using either the prefix or class-name without the use of `FindEnumBy...` functions
    public static Prefix GetEnum(String inpuString) {
        Prefix p = Prefix.GetEnumByPrefix(inpuString);
        if (p == null) { 
            p = Prefix.GetEnumByClassName(inpuString); 
        }
        return null;
    }

}


