package Staff;

import MedicalRecord.MedicalRecord;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This is a enum class which add numerous of departments in a real-world hospital.
 * which support the "department" field stored inside the "Medico" class. 
 * which have the privilege to access the third-layer internal system.
 * But in this case, the "internal system" is not developed.
 * 
 * Another field we want to cover is "requirement" which is a enum to identify the 
 * "working" department in real-world. For example, a doctor can worked in
 * "Cardiology", "Neurology", "Orthopedics", "Accident and Emergency".
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.disabled-world.com/definitions/hospital-departments.php
 * 2) https://www.nursesclass.com/2021/08/departments-in-a-hospital.html
 * 
**/


public enum DepartmentEnum {
    // --------------------------------------------------------------------------------------------------------------------
    // See these two references first and comment later, including Github Copilot
    Accident_Emergency("Accident and Emergency"),
    Administration("Administration"),
    Admissions("Admissions"),
    Anesthetics("Anesthetics"),
    Burn_Center("Burn Center"),
    Cardiology("Cardiology"),
    Casuality("Casuality"),
    CCSD("Central Sterile Services Department (CCSD)"),
    Chaplaincy("Chaplaincy"),
    CCU("Coronary Care Unit (CCU)"),
    Clinical_Engineering("Clinical Engineering"),
    CICU("Cardiac Intensive Care Unit (CICU)"),
    Critical_Care("Critical Care"),
    Dialysis("Dialysis"),
    Diagnostic_Imaging("Diagnostic Imaging"),
    Discharge_Lounge("Discharge Lounge"),
    Elderly_Service("Elderly Service"),
    Finance("Finance"),
    Gastroenterology("Gastroenterology"),
    General_Service("General Service"),
    General_Surgery("General Surgery"),
    Geriatrics("Geriatrics"),
    Gynecology("Gynecology"),
    Hematology("Hematology"),
    Health_Safety("Health and Safety"),
    ICU("Intensive Care Unit (ICU)"),
    ITC("Information Technology Center"),
    HR("Human Resources (HR)"),
    Infection_Control("Infection Control"),
    Information_Management("Information Management"),
    Maternity("Maternity"),
    Medical_Records("Medical Records"),
    Neonatology("Neonatology"),
    Neurology("Neurology"),
    Nephrology("Nephrology"),
    Nursing("Nursing"),
    Nutrition_Dietetics("Nutrition and Dietetics"),
    Obstetrics_Gynecology("Obstetrics and Gynecology"),
    Occupational_Therapy("Occupational Therapy"),
    Oncology("Oncology"),
    Ophthalmology("Ophthalmology"),
    Orthopaedics("Orthopaedics"),
    Otolaryngology("Otolaryngology (ENT)"),
    Pain_Mangement("Pain Management"),
    Patient_Accounts("Patient Accounts"),
    Pediatrics("Pediatrics"),
    Pharmacy("Pharmacy"),
    Physiotherapy("Physiotherapy"),
    Psychiatry("Psychiatry"),
    Purchasing_Supplies("Purchasing and Supplies"),
    Radiology("Radiology"),
    Rheumatology("Rheumatology"),
    Sexual_Health("Sexual Health"),
    Urology("Urology"),
    Xray("X-ray"),

    ;

    // --------------------------------------------------------------------------------------------------------------------
    private final String name;
    DepartmentEnum(String name) { this.name = name; }

    public String GetFullName() { return this.name; }

    public static DepartmentEnum GetEnum(String dept) {
        for (DepartmentEnum deptEnum : DepartmentEnum.values()) {
            String deptEnumString = deptEnum.toString();
            String deptEnumName = deptEnum.GetFullName();
            if (deptEnumString.equals(dept) || deptEnumString == dept) { return deptEnum; }
            if (deptEnumName.equals(dept) || deptEnumName == dept) { return deptEnum; }
        }
        return null;
    }

}
