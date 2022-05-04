package Treatment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import BaseClass.BaseRecord;
import Object.Resource;
import PrefixState.Prefix;
import Staff.Medico;

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


public class Treatment extends BaseRecord {
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
	private String MedicalRecord_ID; 					// Patient's Data
	private int index; 								    // This represented the index placed in the medical-record		
	private String ClassificationCode;					// This told us the type of the treatment

	// ----------------------------------------------------------
	// Mapping data
	private Hashtable<String, Object> MedicoInfo;		// {Medico_ID} & {ID, name, phone_number}
	private ArrayList<String> Supplementary;			// The directory of supplementary materials
	private Hashtable<String, Object> Resources;		// The drug/medicine information
	private Hashtable<String, Object> Descriptions;		// The desciption of the treatment

	public Treatment(String Patient_ID, String MedicalRecord_ID, String P_Name, String P_Age, 
		String P_Gender, int index, String code, boolean writable) {
		super(Patient_ID, P_Name, P_Age, P_Gender, writable);

		this.MedicalRecord_ID = MedicalRecord_ID;
		this.index = index;
		this.ClassificationCode = code;

		// ----------------------------------------------------------
		this.MedicoInfo = new Hashtable<String, Object>(Treatment.MAX_NUM_MEDICO, 0.75f);
		this.Supplementary = new ArrayList<String>(Treatment.MAX_NUM_SUPPLEMENTARY);
		this.Resources = new Hashtable<String, Object>(Treatment.MAX_NUM_RESOURCES, 0.75f);
		this.Descriptions = new Hashtable<String, Object>(Treatment.MAX_NUM_DESCRIPTIONS, 0.75f);
	}

	public Treatment(String Patient_ID, String MedicalRecord_ID, String P_Name, String P_Age, 
		String P_Gender, int index, String code) {
		this(Patient_ID, MedicalRecord_ID, P_Name, P_Age, P_Gender, index, code, true);
	}

	// ---------------------------------------------------------------------------------------------------------------------
	// Setters
	public void AddMedico(Medico medico) {
		if (!this.IsWritable()) { return; }
		if (!this.GetMedicoInfo().containsKey(medico.GetID())) {
			String[] MedicoInformation = {medico.GetID(), medico.GetName(), medico.GetPhoneNumber()};
			this.GetMedicoInfo().put(medico.GetID(), MedicoInformation);
		}
	}

	public void AddSupplementary(String path) {
		if (!this.IsWritable()) { return; }
		if (!this.GetSupplementary().contains(path)) { this.GetSupplementary().add(path); return ;}
		for (String s : this.GetSupplementary()) { if (s.contains(path)) { return; } }
		this.GetSupplementary().add(path);
	}

	public void AddResource(String ID, String name, int amount) {
		if (!this.IsWritable()) { return; }
		if (!this.GetResources().containsKey(ID)) {
			String[] ResourceInformation = {ID, name, Integer.toString(amount)};
			this.GetResources().put(ID, ResourceInformation);
		}
	}

	public void AddResource(Resource resource, int amount) {
		this.AddResource(resource.GetID(), resource.GetName(), amount);
	}

	public void AddDescription(String description, String writer_name) {
		if (!this.IsWritable()) { return; }
		Description desc = new Description(description, writer_name);
		String[] DescriptionInformation = {desc.GetDateAsString(), desc.GetTimeAsString(), 
										   desc.GetDescription(), desc.GetMedicoName()};
		int index = this.GetDescriptions().size();
		this.GetDescriptions().put(Integer.toString(index), DescriptionInformation);
	}

	// -----------------------------------------------------------
	// Remover
	public void RemoveMedico(String medico_ID) {
		if (!this.IsWritable()) { return; }
		if (this.GetMedicoInfo().containsKey(medico_ID)) { this.GetMedicoInfo().remove(medico_ID); }
	}
	public void RemoveMedico(Medico medico) { this.RemoveMedico(medico.GetID()); }

	public void RemoveSupplementary(String path, boolean force) {
		if (!this.IsWritable()) { return; }
		for (String s : this.GetSupplementary()) {
			if (s.equals(path)) { this.GetSupplementary().remove(s); break; }
			if (force && s.contains(path)) { this.GetSupplementary().remove(s); break; }
		}
	}

	public void RemoveResource(String ID) {
		if (!this.IsWritable()) { return; }
		if (this.GetResources().containsKey(ID)) { this.GetResources().remove(ID); }
	}
	public void RemoveResource(Resource resource) { this.RemoveResource(resource.GetID()); }

	public void UpdateResource(String ID, int amount) {
		if (!this.IsWritable()) { return; }
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
		if (!this.IsWritable()) { return; }
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
	public String GetMedicalRecordID() { return this.MedicalRecord_ID; }

	public int GetTreatmentIndexAsInt() { return this.index; }
	public String GetTreatmentIndexAsString() { 
		return Treatment.prefix.GetPrefixCode() + String.format("%02d", this.GetTreatmentIndexAsInt());
	}

	public String GetClassificationCode() { return this.ClassificationCode; }

	// ----------------------------------------------------------
	public Hashtable<String, Object> GetMedicoInfo() { return this.MedicoInfo; }
	public ArrayList<String> GetSupplementary() { return this.Supplementary; }
	public Hashtable<String, Object> GetResources() { return this.Resources; }
	public Hashtable<String, Object> GetDescriptions() { return this.Descriptions; }

	// ---------------------------------------------------------------------------------------------------------------------
	// Serialization & Deserialization
	public Hashtable<String, Object> Serialize() {
		Hashtable<String, Object> TreatmentInformation = super.Serialize();
		TreatmentInformation.put("MedicalRecordID", this.GetMedicalRecordID());
		TreatmentInformation.put("TreatmentIndex", (Object) this.GetTreatmentIndexAsInt());
		TreatmentInformation.put("ClassificationCode", this.GetClassificationCode());

		Iterator<Entry<String, Object>> iter;

		Hashtable<String, Object> MedicoInfo = this.GetMedicoInfo();
		List<String[]> MedicoInfoList = new ArrayList<String[]>(MedicoInfo.size());
		iter = MedicoInfo.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			MedicoInfoList.add((String[]) entry.getValue());
		}

		TreatmentInformation.put("MedicoInfo", MedicoInfoList.toArray());
		TreatmentInformation.put("Supplementary", this.GetSupplementary().toArray());

		Hashtable<String, Object> Resources = this.GetResources();
		List<String[]> ResourcesList = new ArrayList<String[]>(Resources.size());
		iter = Resources.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			ResourcesList.add((String[]) entry.getValue());
		}
		TreatmentInformation.put("Resources", ResourcesList.toArray());

		Hashtable<String, Object> Descriptions = this.GetDescriptions();
		List<String[]> DescriptionsList = new ArrayList<String[]>(Descriptions.size());
		iter = Descriptions.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			DescriptionsList.add((String[]) entry.getValue());
		}
		TreatmentInformation.put("Descriptions", DescriptionsList.toArray());

		return TreatmentInformation;
	}

	public static Treatment Deserialize(Hashtable<String, Object> data) {
		String Pt_ID = (String) data.get("Pt_ID");
        String Pt_Name = (String) data.get("Pt_Name");
        String Pt_Age = (String) data.get("Pt_Age");
        String Pt_Gender = (String) data.get("Pt_Gender");

		String MedicalRecordID = (String) data.get("MedicalRecordID");
		int TreatmentIndex = Integer.parseInt((String) data.get("TreatmentIndex"));
		String ClassificationCode = (String) data.get("ClassificationCode");

        Treatment record = new Treatment(Pt_ID, MedicalRecordID, Pt_Name, Pt_Age, 
										 Pt_Gender, TreatmentIndex, ClassificationCode, true);
        record.SetDate((String) data.get("date"));
        record.SetTime((String) data.get("time"));

		// Deserialize Medico, Supplementary, Resources, and Descriptions
		Object[] MedicoInfoList = (Object[]) data.get("MedicoInfo");
		for (int i = 0; i < MedicoInfoList.length; i++) {
			String[] MedicoInfoDetail = (String[]) MedicoInfoList[i];
			record.GetMedicoInfo().put(MedicoInfoDetail[0], MedicoInfoDetail);
		}

		Object[] SupplementaryList = (Object[]) data.get("Supplementary");
		for (int i = 0; i < SupplementaryList.length; i++) {
			String SupplementaryDetail = (String) SupplementaryList[i];
			record.GetSupplementary().add(SupplementaryDetail);
		}

		Object[] ResourcesList = (Object[]) data.get("Resources");
		for (int i = 0; i < ResourcesList.length; i++) {
			String[] ResourcesDetail = (String[]) ResourcesList[i];
			record.GetResources().put(ResourcesDetail[0], ResourcesDetail);
		}

		Object[] DescriptionsList = (Object[]) data.get("Descriptions");
		for (int i = 0; i < DescriptionsList.length; i++) {
			String[] DescriptionsDetail = (String[]) DescriptionsList[i];
			record.GetDescriptions().put(Integer.toString(i), DescriptionsDetail);
		}

		if (!(boolean) data.get("writable")) { record.CloseRecord(); }

		return record;
	}

}
