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
 * This class recorded all functionalitites for the hospital room
 * which is used on <room-type> of the RoomUnit.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.verywellhealth.com/hospital-floors-and-units-3156895
 * 2) https://doh.wa.gov/public-health-healthcare-providers/healthcare-professions-and-facilities/healthcare-associated-infections/hai-reports/types-hospital-units
 * 3) https://www.gilberter.com/2021/01/29/hospital-departments-abbreviations/
**/

public enum RoomUnitEnum {
    // ---------------------------------------------------------------------------------------------------------------------
    ICU(null, "Intensive Care Unit", "Seriously ill patients"),
    NICU("ICU", "Neonatal Intensive Care Unit", "Newborn-infant patients"),
    NCC("NICU", "Neurological Critical Care Unit", "Neurological patients"),
    PICU("ICU", "Pediatric Intensive Care Unit", "Children (0-5 years)"),
    CCU("ICU", "Coronary Care Unit", "Heart-attack patients"),
    CTU("ICU", "Cardiac Transplant Unit", "Heart-received (transplanted)"),
    SICU("ICU", "Surgical Intensive Care Unit", "Surgical patients"),
    MICU("ICU", "Mental Intensive Care Unit", "Chronic patients"),
    LTICU("ICU", "Long-term Intensive Care Unit", "Long-term patients"),
    PACU("ICU", "Post-Anesthesia Intensive Care Unit", "Patients after anesthesia or surgery"),
    TICU("ICU", "Trauma Intensive Care Unit", "Patients with injuries"),

    // -----------------------------------------------------------
    ER(null, "Emergency Room", "Emergency patients after serious illness or injury."),
    ED("ER", "Emergency Department", "Emergency patients after serious illness or injury."),
    Onc(null, "Oncology", "Patients with cancer."),
    OHR(null, "Open-Hearted Room", "Patients after Heart-surgery."),
    OR(null, "Operating Room", "Patients during surgery."),
    PrO(null, "Pre-Operative Unit", "Patients before surgery"),
    RhU(null, "Rehabilitation Unit", "Recovery patients"),
    LAB(null, "Laboratory", "Patients with lab tests or medical researchers"),
    SDU(null, "Step-Down Unit", "Patients don't need intensive caring"),

    UNIT(null, "Unit", "Unknown funcionality"),
    
    ;

    // ---------------------------------------------------------------------------------------------------------------------
    // Declare enum constants here.
    private String Parent, FullName, Functionality;
    RoomUnitEnum(String Parent, String FullName, String Functionality) { 
        this.Parent = Parent;
        this.FullName = FullName;
        this.Functionality = Functionality;
    }

    public String GetParent() { return this.Parent; }
    public String GetFullName() { return this.FullName; }
    public String GetFunctionality() { return this.Functionality; }


    // ----------------------------------------------------------
    // Magic here: Declare the enum constant as a static final field using the prefix.
    public static RoomUnitEnum GetEnum(String RoomUnitCode) {
        for (RoomUnitEnum RoomUnit : RoomUnitEnum.values()) {
            if (RoomUnit.name().equals(RoomUnitCode)) { return RoomUnit; }
            if (RoomUnitCode == RoomUnit.toString() || RoomUnit.toString().equals(RoomUnitCode)) { return RoomUnit; }
        }
        return null;
    }

    public static boolean FindEnum(String RoomUnitCode) { return RoomUnitEnum.GetEnum(RoomUnitCode) != null; }
}
