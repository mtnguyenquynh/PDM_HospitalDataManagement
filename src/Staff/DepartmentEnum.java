package Staff;


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
    Accident_Emergency("Accident and Emergency"),
    Admissions("Admissions"),
    Anesthetics("Anesthetics"),
    
    Cardiology("Cardiology"),
    Cardiothoracic_Surgery("Cardiothoracic Surgery"),
    Chest_Surgery("Chest Surgery"),
    Child_Care("Child Care"),
    Child_Nursing("Child Nursing"),
    Critical_Care("Critical Care"),
    Dermatology("Dermatology"),
    Emergency("Emergency"),
    Endocrinology("Endocrinology"),
    Gastroenterology("Gastroenterology"),
    General_Surgery("General Surgery"),
    Gynecology("Gynecology"),
    Hematology("Hematology"),
    Infectious_Disease("Infectious Disease"),
    Internal_Medicine("Internal Medicine"),
    Neonatology("Neonatology"),
    Neurology("Neurology"),
    Obstetrics("Obstetrics"),
    Oncology("Oncology"),
    Ophthalmology("Ophthalmology"),
    Orthopedics("Orthopedics"),
    Otolaryngology("Otolaryngology"),
    Pediatrics("Pediatrics"),
    Physical_Therapy("Physical Therapy"),
    Plastic_Surgery("Plastic Surgery"),
    Podiatry("Podiatry"),
    Psychiatry("Psychiatry"),
    Radiology("Radiology"),
    Rheumatology("Rheumatology"),
    Surgery("Surgery"),
    Urology("Urology");


    // --------------------------------------------------------------------------------------------------------------------
    private String name;
    DepartmentEnum(String name) {
        this.name = name;
    }

}
