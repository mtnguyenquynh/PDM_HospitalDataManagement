package Staff;

import PrefixState.Prefix;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This is a dataclass which describes an instances of a normal staff, 
 * which have the privilege to access the third-layer internal system.
 * But in this case, the "internal system" is not developed.
 * 
 * In this class, we described this object to be more explicitly with the 
 * new field "requirement" according to the question/topic of the project.  
 * 
 * Differed from many other classes, this class does support "Prefix-Setter"
 * as it is not the end of the "responsibility" tree: Doctor, Nurse, etc.
 * Although the difference in priviledge access is available, these people 
 * did not access frequently to the internal system, but just heal and cure 
 * the patient as best as possible. Thus, we deployed "Prefix-Setter" instead.
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
 * 
**/

public class Medico extends Staff {
    
    private DepartmentEnum department;

    public Medico(String ID, String name, String description) throws Exception {
        super(ID, name, description);
        this.prefix = Prefix.Medico;
        this.department = null;
    }

    public Medico(String ID, String name) throws Exception {
        this(ID, name, null);
    }
    

    // --------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    
    // ----------------------------------------------------------
    // Alias function & Support new "prefix"
    private void SetPrefix(Prefix prefix) throws Exception {
        if (prefix != Prefix.Doctor && prefix != Prefix.Nurse && prefix != Prefix.Medico) {
            throw new Exception("Invalid prefix. The accepted prefix is Doctor, Nurse, or Medico.");
        }
        this.prefix = prefix;
    }

    public void SetResponsibility(Prefix prefix) throws Exception { this.SetPrefix(prefix); }

    public Prefix GetResponsibility() { return this.GetThisPrefix(); }
    

    // ----------------------------------------------------------
    // Department

    public DepartmentEnum GetDepartment() { return this.department; }

    public void SetDepartment(DepartmentEnum department) throws Exception { this.department = department; }


}
