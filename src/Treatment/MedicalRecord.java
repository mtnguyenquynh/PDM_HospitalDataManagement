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
    protected Prefix prefix;

    public MedicalRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, String Pt_Gender, 
                         boolean writable, String MedicalRecord_ID) {
        super(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, writable);
        
        DataUtils.CheckArgumentCondition(MedicalRecord_ID != null, "The MedicalRecord_ID cannot be null.");
        DataUtils.CheckArgumentCondition(MedicalRecord_ID.length() > 0, "The MedicalRecord_ID cannot be empty.");
        this.MedicalRecordID = MedicalRecord_ID;
        this.prefix = MedicalRecord.GetPrefix();
    }

    public MedicalRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, String Pt_Gender, 
                         String MedicalRecord_ID) {
        this(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, true, 
             MedicalRecord_ID);
    }

    public MedicalRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, String Pt_Gender, 
                         boolean writable, int MedicalRecord_ID) {
        this(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, true, 
             String.valueOf(MedicalRecord_ID));        
    }

    public MedicalRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, String Pt_Gender) {
        this(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, true, 
             -1);
    }

    // --------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public String GetMedicoRecordID() {  return this.MedicalRecordID; }
    public void SetMedicoRecordID(String MedicalRecordID) throws Exception { 
        DataUtils.CheckArgumentCondition(MedicalRecordID != null, "ID cannot be null.");
        DataUtils.CheckArgumentCondition(MedicalRecordID.length() > 0, "ID cannot be empty.");
        
        if (this.GetMedicoRecordID().equals("-1")) { this.MedicalRecordID = MedicalRecordID; }
        throw new Exception("The ID cannot be changed.");
    }


    public static Prefix GetPrefix() { return Prefix.MedicalRecord; }
    public Prefix GetThisPrefix() { return this.prefix; }

    public static String GetPrefixCode() { return MedicalRecord.GetPrefix().GetPrefixCode(); }
    public String GetThisPrefixCode() { return this.GetThisPrefix().GetPrefixCode(); }


}
