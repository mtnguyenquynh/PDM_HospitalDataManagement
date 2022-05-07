package BaseClass;

import java.util.Hashtable;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This module is a higher-level wrapper of both MedicoRecord and Treatment class which 
 * inherited from the AbstractRecord class. This class is to provided a fixed set of 
 * attribute related to Patient we want to managed by the hospital.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class BaseRecord extends AbstractRecord {
    // ---------------------------------------------------------------------------------------------------------------------
    private String Pt_ID;                               // Patient's Data
	private String Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender; 			// Syncronized information only from patients

    public BaseRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, 
                      String Pt_Gender, boolean writable) {
        super(writable);
        this.Pt_ID = Patient_ID;
        this.Pt_FirstName = Pt_FirstName;
        this.Pt_LastName = Pt_LastName;
        this.Pt_Age = Pt_Age;
        this.Pt_Gender = Pt_Gender;
    }
    
    public BaseRecord(String Patient_ID, String Pt_FirstName, String Pt_LastName, String Pt_Age, String Pt_Gender) {
        this(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, true);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter and Setter
    public String GetPtID() { return this.Pt_ID; }
	public String GetPtFirstName() { return this.Pt_FirstName; }
    public String GetPtLastName() { return this.Pt_LastName; }
	public String GetPtAge() { return this.Pt_Age; }
	public String GetPtGender() { return this.Pt_Gender; }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> data = super.Serialize();
        data.put("Pt_ID", this.GetPtID());
        data.put("Pt_FirstName", this.GetPtFirstName());
        data.put("Pt_LastName", this.GetPtLastName());
        data.put("Pt_Age", this.GetPtAge());
        data.put("Pt_Gender", this.GetPtGender());
        return data;
    }

    public static BaseRecord Deserialize(Hashtable<String, Object> data) {
        String Pt_ID = (String) data.get("Pt_ID");
        String Pt_FirstName = (String) data.get("Pt_FirstName");
        String Pt_LastName = (String) data.get("Pt_LastName");
        String Pt_Age = (String) data.get("Pt_Age");
        String Pt_Gender = (String) data.get("Pt_Gender");
        boolean writable = (boolean) data.get("writable");

        BaseRecord record = new BaseRecord(Pt_ID, Pt_FirstName, Pt_LastName, Pt_Age, Pt_Gender, writable);
        record.SetDate((String) data.get("date"));
        record.SetTime((String) data.get("time"));
        return record;
    }


}
