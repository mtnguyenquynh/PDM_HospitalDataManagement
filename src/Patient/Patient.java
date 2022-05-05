package Patient;
import Person.Person;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This is a dataclass which describes an instances of a patient.
 * which is also a base/abstract class to have better description on 
 * other classes such as Patient, Doctor, Nurse (medico).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class Patient extends Person {

    public Patient(String ID, String name, String description) throws Exception {
        super(ID, name, description);
    }

    public Patient(String ID, String name) throws Exception {
        super(ID, name, null);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter


    // -----------------------------------------------------------
    // Setter Function
    
}
