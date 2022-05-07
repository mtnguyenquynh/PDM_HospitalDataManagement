package Treatment;

import java.util.ArrayList;
import java.util.Hashtable;
import BaseClass.BaseRecord;
import Object.Resource;
import PrefixState.Prefix;
import Staff.Medico;
import Utility.DataUtils;
import Utility.JsonUtils;
import Person.PersonUtils;


/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class is a small wrapper which controlled the basic behaviour of the "Treatment" class.
 * 
 * 
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/


public class MedicalRecord extends BaseRecord {
    // ---------------------------------------------------------------------------------------------------------------------
    private static final int NUMBER_OF_MAX_TREATMENTS = 100;

    // ----------------------------------------------------------                         
    private String MedicalRecordID;
    private String Medico_ID;

    public MedicalRecord(String Patient_ID, String MedicalRecord_ID, String Pt_FirstName, String Pt_LastName, 
                         String Pt_Age, String Pt_Gender, boolean writable) {
        super(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, writable);
        this.MedicalRecordID = MedicalRecord_ID;
    }

    public MedicalRecord(String Patient_ID, String MedicalRecord_ID, String Pt_FirstName, String Pt_LastName, 
                         String Pt_Age, String Pt_Gender) {
        this(Patient_ID, MedicalRecord_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, true);
    }


}
