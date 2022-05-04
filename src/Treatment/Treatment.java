package Treatment;

import java.util.ArrayList;
import java.util.Hashtable;

import BaseClass.CreationDateTime;
import Object.Resource;
import PrefixState.Prefix;
import Staff.Medico;
import Utility.DataUtils;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class described a single entity of how we directly manage the medical condition
 * of the patient. Note that we don't directly simulate all properties found in a 
 * real-world treatment record, but we setup the basic foundation. For example, the 
 * heart beat rate is a number that appeared in some particular records but not all. 
 * 
 * In the treatment record, we store the patient's ID, the medico record's ID,
 * all the involved medicos (ID), some syncronized information such as name, age 
 * and gender, and the optional description if we want to add some fields on it.
 * 
 * What inside the treatments? 
 * 1) Medico_Information: Mapping the Medico_ID & {ID, name, phone_number}
 * 2) Supplementary: The patient's scanning image (X-ray). Stored by directory.
 * 3) Resources: The drug/medicine information: Mapping the {ID}-{ID, name, amount}
 * 4) Descriptions: The desciption of the treatment: Mapping the {Index} - { Date, Time, Description, Medico_Name }.
 * 	  The {Date, Time} here is its creation time. Not the recording time by patient.
 * 
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
	// Note that we are not deploying these data into the design as the logging to UI must be demo.
	private static final int MAX_NUM_MEDICO = 50;				// 50 medicos are pre-allocated
	private static final int MAX_NUM_SUPPLEMENTARY = 50;		// 50 supplementary materials are pre-allocated
	private static final int MAX_NUM_DESCRIPTIONS = 100;		// 100 descriptions are pre-allocated
	private static final int MAX_NUM_RESOURCES = 100;			// 100 resources are pre-allocated
	private static final Prefix prefix = Prefix.Treatment;		// Prefix for the treatment

	// ---------------------------------------------------------------------------------------------------------------------
	private String Patient_ID, MedicalRecord_ID; 		// Patient's Data
	private String Pt_Name, Pt_Age, Pt_Gender; 			// Syncronized information only from patients
	private int index; 								    // This represented the index placed in the medical-record		
	private String ClassificationCode;					// This told us the type of the treatment
	public boolean isOpen;								// This state allow the user to write information

	// ----------------------------------------------------------
	// Mapping data
	private Hashtable<String, Object> MedicoInfo;		// {Medico_ID} & {ID, name, phone_number}
	private ArrayList<String> Supplementary;			// The directory of supplementary materials
	private Hashtable<String, Object> Resources;		// The drug/medicine information
	private Hashtable<String, Object> Descriptions;		// The desciption of the treatment

	public Treatment(String Patient_ID, String MedicalRecord_ID, String P_Name, String P_Age, 
		String P_Gender, int index, String code) {
		super();							// Initialize the CreationDateTime
		
		this.Patient_ID = Patient_ID;
		this.MedicalRecord_ID = MedicalRecord_ID;
		this.Pt_Name = P_Name;
		this.Pt_Age = P_Age;
		this.Pt_Gender = P_Gender;

		this.index = index;
		this.ClassificationCode = code;
		this.isOpen = false;

		// ----------------------------------------------------------
		this.MedicoInfo = new Hashtable<String, Object>(Treatment.MAX_NUM_MEDICO, 0.75f);
		this.Supplementary = new ArrayList<String>(Treatment.MAX_NUM_SUPPLEMENTARY);
		this.Resources = new Hashtable<String, Object>(Treatment.MAX_NUM_RESOURCES, 0.75f);
		this.Descriptions = new Hashtable<String, Object>(Treatment.MAX_NUM_DESCRIPTIONS, 0.75f);
	}

	// ---------------------------------------------------------------------------------------------------------------------
	// Setters
	public void AddMedico(Medico medico) {
		if (!this.IsTreatmentOpen()) { return; }
		if (!this.GetMedicoInfo().containsKey(medico.GetID())) {
			String[] MedicoInformation = {medico.GetID(), medico.GetName(), medico.GetPhoneNumber()};
			this.GetMedicoInfo().put(medico.GetID(), MedicoInformation);
		}
	}

	public void AddSupplementary(String path) {
		if (!this.IsTreatmentOpen()) { return; }
		if (!this.GetSupplementary().contains(path)) { this.GetSupplementary().add(path); return ;}
		for (String s : this.GetSupplementary()) { if (s.contains(path)) { return; } }
		this.GetSupplementary().add(path);
	}

	public void AddResource(String ID, String name, int amount) {
		if (!this.IsTreatmentOpen()) { return; }
		if (!this.GetResources().containsKey(ID)) {
			String[] ResourceInformation = {ID, name, Integer.toString(amount)};
			this.GetResources().put(ID, ResourceInformation);
		}
	}

	public void AddResource(Resource resource, int amount) {
		if (!this.IsTreatmentOpen()) { return; }
		this.AddResource(resource.GetID(), resource.GetName(), amount);
	}

	public void AddDescription(String description, String writer_name) {
		if (!this.IsTreatmentOpen()) { return; }
		Description desc = new Description(description, writer_name);
		String[] DescriptionInformation = {desc.GetDateAsString(), desc.GetTimeAsString(), 
										   desc.GetDescription(), desc.GetMedicoName()};
		int index = this.GetDescriptions().size();
		this.GetDescriptions().put(Integer.toString(index), DescriptionInformation);
	}

	// -----------------------------------------------------------
	// Remover
	public void RemoveMedico(String medico_ID) {
		if (!this.IsTreatmentOpen()) { return; }
		if (this.GetMedicoInfo().containsKey(medico_ID)) { this.GetMedicoInfo().remove(medico_ID); }
	}
	public void RemoveMedico(Medico medico) { this.RemoveMedico(medico.GetID()); }

	public void RemoveSupplementary(String path, boolean force) {
		if (!this.IsTreatmentOpen()) { return; }
		for (String s : this.GetSupplementary()) {
			if (s.equals(path)) { this.GetSupplementary().remove(s); break; }
			if (force && s.contains(path)) { this.GetSupplementary().remove(s); break; }
		}
	}

	public void RemoveResource(String ID) {
		if (this.GetResources().containsKey(ID)) { this.GetResources().remove(ID); }
	}
	public void RemoveResource(Resource resource) { this.RemoveResource(resource.GetID()); }

	public void UpdateResource(String ID, int amount) {
		if (!this.IsTreatmentOpen()) { return; }
		if (amount == 0) { this.RemoveResource(ID); return; }
		if (this.GetResources().containsKey(ID)) {
			String[] ResourceInformation = (String[]) this.GetResources().get(ID);
			ResourceInformation[2] = Integer.toString(amount);
			this.GetResources().put(ID, ResourceInformation);
		}
	}
	public void UpdateResource(Resource resource, int amount) { this.UpdateResource(resource.GetID(), amount); }

	public void RemoveDescription(String index) {
		// This operation is a little bit tricky since old description can be traversed back for legacy purpose.
		// Thus we can added a new prefix "[Deleted]" to the description.
		if (!this.IsTreatmentOpen()) { return; }
		if (this.GetDescriptions().containsKey(index)) { 
			String[] DescriptionInformation = (String[]) this.GetDescriptions().get(index);
			DescriptionInformation[2] = "[Deleted] " + DescriptionInformation[2];
			this.GetDescriptions().put(index, DescriptionInformation);
		}
	}
	public void IgnoreDescription(String index) { this.RemoveDescription(index); }
	public void RemoveDescription(int index) { this.RemoveDescription(Integer.toString(index)); }
	public void IgnoreDescription(int index) { this.RemoveDescription(Integer.toString(index)); }

	// ---------------------------------------------------------------------------------------------------------------------
	// Getter & Setter 
	public String GetPtID() { return this.Patient_ID; }
	public String GetMedicalRecordID() { return this.MedicalRecord_ID; }

	public String GetPtName() { return this.Pt_Name; }
	public String GetPtAge() { return this.Pt_Age; }
	public String GetPtGender() { return this.Pt_Gender; }

	public int GetTreatmentIndexAsInt() { return this.index; }
	public String GetTreatmentIndexAsString() { 
		return Treatment.prefix.GetPrefixCode() + String.format("%02d", this.GetTreatmentIndexAsInt());
	}

	public String GetTreatmentCode() { return this.ClassificationCode; }

	public boolean IsTreatmentOpen() { return this.isOpen; }
	public void CloseTreatment() { this.isOpen = false; }
	public void OpenTreatment() { this.isOpen = true; }

	// ----------------------------------------------------------
	public Hashtable<String, Object> GetMedicoInfo() { return this.MedicoInfo; }
	public ArrayList<String> GetSupplementary() { return this.Supplementary; }
	public Hashtable<String, Object> GetResources() { return this.Resources; }
	public Hashtable<String, Object> GetDescriptions() { return this.Descriptions; }


}
