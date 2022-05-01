package Room;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved
 *
 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class simulate what information does a common "room" have in the hospital.
 * For example, the room-code, its (simple) functionality description, its location
 * in the hospital (block A or B, etc). Ok, the important question how to design 
 * an optimal room code ? To mostly cover the meaning of room-code, we visualize 
 * it as this: <prefix>-<room-block>-<room-type>-<room-floor>-<room-number>.
 * 
 * Among that:
 * <prefix>: (String-like) is a prefix, denoted to be "RU" for "Room Unit".
 *           (see in the Prefix.java).
 * <room-block>: Depends on the real-world hospital, but usually an upper-case
 *               character, such as "A" or "B". If the room-block is not character 
 *               (a number), then use two digit-like character to specified the 
 *               room-block such as such as "01" or "02". Note that this is not 
 *               a special character such as "#" or "@".
 * <room-type>: This described the functionalities of the room. For example,
 *              "ICU" for "Intensive Care Unit" or "ER" for "Emergency Room".
 * <room-floor>: This is the floor of the room using the ideology of the US as 
 *               our fake data comes the US-like person. This implies that the 
 *               ground-floor is the first floor (1st). Similarly, we use two 
 *               digit-like characters to specified the floor number.
 * <room-number>: This is the room number in the room-block. As we want the hospital 
 *                can be expandable, we only used three digit-like characters.
 * Note that for all above conditions, if the input is a digit-like, its value 
 * cannot be smaller or equal than zero (0).
 * 
 * Ok, now how to obtain, specify, and construct properly the room-code without
 * vulnerability?
 * To obtain highly guaranteed, we first spilt the code ID into multiple parts using 
 * delimitor "-". And then based on the following definition, we can extract the 
 * wanted string from the code ID.
 * 
 * For example, the code ID is "RU-A-ICU-01-001". Then we can extract the following
 * information from the code ID: 
 * 1) The prefix: "RU"
 * 2) The room-block: "A"
 * 3) The room-type: "ICU"
 * 4) The room-floor: "01"
 * 5) The room-number: "001"        
 * 
 * For developer, we guarantee that the code ID from extraction is always a string.
 * But it flexible the input of the code ID during construction, so if you want to 
 * compute something out of the code ID, you must program by yourself. 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class RoomUnitEnum {
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
    Resource("Rs-"),
    
    RoomUnit("RU-"),
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
    public String GetPrefixCode() { return this.prefix; }

    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the prefix.
    public static Prefix GetEnumByPrefixCode(String prefix_code) {
        String prefixNoDash = prefix_code.replace("-", "");
        for (Prefix p : Prefix.values()) {
            if (p.prefix.equals(prefix_code)) { return p; } 
            if (p.prefix.startsWith(prefixNoDash)) { return p; }
     }
        return null;
    }
    public static boolean FindEnumByPrefix(String prefix_code) { return Prefix.GetEnumByPrefixCode(prefix_code) != null; }

    // -----------------------------
    // Magic here: Declare the enum constant as a class name.
    public static Prefix GetEnumByClassName(String className) {
        for (Prefix p : Prefix.values()) {
            if (p.toString().equals(className)) { return p; }
        }
        return null;
    }
    public static boolean FindEnumByClassName(String className) { return Prefix.GetEnumByClassName(className) != null; }

    // ----------------------------- 
    // Find the correct enum using either the prefix or class-name without the use of `FindEnumBy...` functions
    public static Prefix GetEnum(String inputString) {
        Prefix p = Prefix.GetEnumByPrefixCode(inputString);
        if (p == null) { 
            p = Prefix.GetEnumByClassName(inputString); 
        }
        return p;
    }
}
