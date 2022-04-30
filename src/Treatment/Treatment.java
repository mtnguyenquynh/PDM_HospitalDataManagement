package Treatment;

import java.util.ArrayList;
import java.util.Hashtable;

import BaseClass.CreationDateTime;
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
 * This file described a particular how we directly load the treatment 
 * item/instance in the project. Note that we don't directly simulate 
 * all properties found in a real-world treatment record, but we setup the basic 
 * foundation. For example, the heart beat rate is a number that appeared in some 
 * particular records but not all. If those are having some special correlations to 
 * other, for example, the use in Machine Learning, but today, it is ignored, and was 
 * just noted by the `Description` field.   
 * 
 * 
 * In the treatment record, we store the patient's ID, the medico record's ID,
 * all the involved medicos (ID), some syncronized information such as name, age 
 * and gender, and the optional description if we want to add some fields on it.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.geeksforgeeks.org/new-date-time-api-java8/
 * 2) https://stackoverflow.com/questions/6516320/datetime-datatype-in-java
 * 3) https://www.baeldung.com/java-8-date-time-intro
**/


public class Treatment extends CreationDateTime {
	// ---------------------------------------------------------------------------------------------------------------------
	// These attribute fields where we want to pre-allocate memory for our ArrayList (Medico && Descriptions).
	// The number of elements can be more than pre-allocation, but this number should be enough.
	private static final int MAX_NUM_MEDICO = 50;				// 50 medicos are pre-allocated
	private static final int MAX_NUM_DESCRIPTIONS = 100;		// 100 descriptions are pre-allocated
	private static final int MAX_NUM_SUP = 50;					// 50 supplementary materials are pre-allocated
	private static final int MAX_NUM_RESOURCES = 100;			// 100 resources are pre-allocated

	// ---------------------------------------------------------------------------------------------------------------------
	private String Patient_ID, MedicalRecord_ID; 		// Patient's Data
	private ArrayList<String> Involved_Medico_ID;		// Medico's Data
	private String P_Name, P_Age, P_Gender; 			// Syncronized information only from patients
	
	// ----------------------------------------------------------
	private final static String prefix = Prefix.Treatment.GetPrefix();
	private String index; 						// This represented the index placed in the medical-record				
	private String code;					// This connected directly to the treatment code pool
	private ArrayList<Hashtable<String, Object>> descriptions; 	// This is the description of the treatment

	private ArrayList<String> supDirectoryList; 	// This field stored the directory of the supplementary materials (images)
	private ArrayList<Hashtable<String, Object>> resourcesTable; 	// This field records all resources as a table


	public Treatment(String Patient_ID, String MedicalRecord_ID, String P_Name, String P_Age, 
		String P_Gender, int index, String code) {
		super();							// Initialize the CreationDateTime
		this.Patient_ID = Patient_ID;
		this.MedicalRecord_ID = MedicalRecord_ID;
		this.Involved_Medico_ID = new ArrayList<String>(Treatment.MAX_NUM_MEDICO);

		this.P_Name = P_Name;
		this.P_Age = P_Age;
		this.P_Gender = P_Gender;

		// ----------------------------------------------------------
		this.index = Treatment.prefix + Integer.toString(index);
		
		TreatmentCode.ValidateKeyCode(code);
		if (TreatmentCode.ValidateKeyCode(code)) {
			this.code = code;
		} else {
			throw new IllegalArgumentException("Invalid code: " + code);
		}
		this.descriptions = new ArrayList<Hashtable<String, Object>>(Treatment.MAX_NUM_DESCRIPTIONS);

		this.supDirectoryList = new ArrayList<String>(Treatment.MAX_NUM_SUP);
		this.resourcesTable = new ArrayList<Hashtable<String, Object>>(Treatment.MAX_NUM_RESOURCES);
	}

	// ---------------------------------------------------------------------------------------------------------------------
	// Getters and Setters
	public String GetPatientID() { return this.Patient_ID; }
	public String GetMedicalRecordID() { return this.MedicalRecord_ID; }

	public String GetPName() { return this.P_Name; }
	public String GetPAge() { return this.P_Age; }
	public String GetPGender() { return this.P_Gender; }

	public String GetTreatmentIndex() { return this.index; }
	public int GetTreatmentIndexAsInt() { 
		return Integer.parseInt(this.index.replace(Treatment.prefix, ""));
	}
	public String GetTreatmentCode() { return this.code; }

	// ----------------------------------------------------------
	public ArrayList<String> GetAllInvolvedMedicoIDs() { return this.Involved_Medico_ID; }
	public int GetNumMedicoIDs() { return this.GetAllInvolvedMedicoIDs().size(); }
	public String GetMedicoIDAtIndex(int index) { return this.GetAllInvolvedMedicoIDs().get(index); }

	public void AddNewMedicoAtTheEnd(String medico_id) {
		this.Involved_Medico_ID.add(medico_id);
	}

	// -----------------------------
	public ArrayList<Hashtable<String, Object>> GetAllDescriptions() { return this.descriptions; }
	public int GetNumDescriptions() { return this.GetAllDescriptions().size(); }

	public Hashtable<String, Object> GetSerializedDescriptionAtIndex(int index) { 
		return this.GetAllDescriptions().get(index); 
	}
	public Description GetDescriptionAtIndex(int index) {
		return Description.Deserialize(this.GetSerializedDescriptionAtIndex(index));
	}
	/**
	 * This function is used to add a new description to the treatment as an array of string.
	 * @param description (String): A set of description that a medico writes to the treatment.
	 * @param writer (String): The name of the medico who writes the description, it could also 
	 * 						   be the medico's signature.
	 */
	public void AddNewDescriptionAtTheEnd(String description, String writer) {
		Description desc = new Description(description, writer);
		Hashtable<String, Object> serialized_description = desc.Serialize();
		this.descriptions.add(serialized_description);
	}

	// -----------------------------
	public ArrayList<String> GetAllSupDirs() { return this.supDirectoryList; }
	public int GetNumSupDirs() { return this.GetAllSupDirs().size(); }
	public String GetSupDirAtIndex(int index) { return this.GetAllSupDirs().get(index); }
	public void AddNewSupDirAtTheEnd(String sup_dir) { this.supDirectoryList.add(sup_dir); }

	// -----------------------------
	public ArrayList<Hashtable<String, Object>> GetAllResources() { return this.resourcesTable; }
	public int GetNumResources() { return this.GetAllResources().size(); }
	public Hashtable<String, Object> GetSerializedResourceAtIndex(int index) { 
		return this.GetAllResources().get(index); 
	}

	public void AddNewResourceAtTheEnd(String resource_id, int amount) {
		Hashtable<String, Object> new_resource = new Hashtable<String, Object>(2);
		new_resource.put("id", resource_id);
		new_resource.put("amount", Integer.toString(amount));
		this.resourcesTable.add(new_resource);
	}
}
