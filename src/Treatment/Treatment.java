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
 * Note that the "index" (integer) attribute started from zero, NOT from 1.
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

	private static final int MAX_INDEX = 3;					    // This is "power-coefficient" of number of Treatments.

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

	public Treatment(String Patient_ID, String MedicalRecord_ID, String Pt_FirstName, String Pt_LastName, 
		String Pt_Age, String Pt_Gender, int index, String code, boolean writable) {
		super(Patient_ID, Pt_FirstName, Pt_LastName, Pt_Age, 
		      Pt_Gender, writable);

		DataUtils.CheckArgumentCondition(index >= -1, "The treatment index must started from -1. If -1, " + 
										 "this treatment may not be available in the medical record.");
		DataUtils.CheckArgumentCondition(TreatmentCode.ContainsThisKeyCode(code), 
										 "This code=" + code + " is not available in any treatment.");
		this.MedicalRecord_ID = MedicalRecord_ID;
		this.index = index;
		this.ClassificationCode = code;

		// ----------------------------------------------------------
		this.MedicoInfo = new Hashtable<String, Object>(Treatment.MAX_NUM_MEDICO, 0.75f);
		this.Supplementary = new ArrayList<String>(Treatment.MAX_NUM_SUPPLEMENTARY);
		this.Resources = new Hashtable<String, Object>(Treatment.MAX_NUM_RESOURCES, 0.75f);
		this.Descriptions = new Hashtable<String, Object>(Treatment.MAX_NUM_DESCRIPTIONS, 0.75f);
	}

	public Treatment(String Patient_ID, String MedicalRecord_ID, String Pt_FirstName, String Pt_LastName,
		String Pt_Age, String Pt_Gender, int index, String code) {
		this(Patient_ID, MedicalRecord_ID, Pt_FirstName, Pt_LastName, Pt_Age, 
		     Pt_Gender, index, code, true);
	}

	// ---------------------------------------------------------------------------------------------------------------------
	// Setters-Advanced
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
	public static int GetMaxTreatmentInMedicalRecords() { return (int) Math.pow(10, Treatment.MAX_INDEX); }

	public String GetMedicalRecordID() { return this.MedicalRecord_ID; }
	
	public String GetStandardizedIndex() {
		int index = this.GetTreatmentIndex();
		// int MaxTreatments = Treatment.GetMaxTreatmentInMedicalRecords();
		// if (index >= MaxTreatments) {
		// 	String[] idxStr = {String.valueOf(index), String.valueOf(MaxTreatments)};
		// 	throw new Exception("The treatment index is too large (index = ." + idxStr[0] + "> " + idxStr[1] + ")");
		// }
		return String.format("%" + String.valueOf(Treatment.MAX_INDEX) + "d", index);
	}

	public String GetDerivedTreatmentID() throws Exception { 
		// This is a derived function and would not be used any thing practical.
		// Don't use this function in your code. This is just an extension for the future.
		// For example, if MedicalRecord_ID is "MR-00-001-00012" and index = 3 
		// --> DerivedTreatmentID = "MR-00-001-00012-003".
		return this.GetMedicalRecordID() + "-" + this.GetStandardizedIndex(); 
	}

	public int GetTreatmentIndex() { return this.index; }

	public void SetTreatmentIndex(int index) {
		if (!this.IsWritable()) { return; }
		DataUtils.CheckArgumentCondition(this.GetTreatmentIndex() >= 0, 
										"Treatment Index must be a non-negative integer.");
		DataUtils.CheckArgumentCondition(index >= 0, "Updated index must be a non-negative integer.");								
		if (this.index == -1) { this.index = index; }
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
		TreatmentInformation.put("TreatmentIndex", (Object) this.GetTreatmentIndex());
		TreatmentInformation.put("ClassificationCode", this.GetClassificationCode());
		
		String directory, folder;
		try { 
			folder = PersonUtils.GetPatientRecordDirectory(this.GetPtFirstName(), false); 
			TreatmentInformation.put("folder", folder); 		// Saved here to prevent failed compilation
		} catch (Exception e) { // This is never called as standardization is done in the Patient class.
			e.printStackTrace();
		}
		
		// Note that at here the directory is: "database/PatientRecord/[FirstName-Tree]/".
		// To reach the true directory, we need to add the "Patient.ID" and "MedicalRecord.ID into it"
		// The result is: "database/PatientRecord/[FirstName-Tree]/[Patient.ID]/[MedicalRecord.ID]/".
		folder = TreatmentInformation.get("folder") + this.GetPtID() + "/" + this.GetMedicalRecordID() + "/";
		TreatmentInformation.put("folder", folder);

		// After that, we needed to deepen down to the "TreatmentIndex"
		// The result is: "database/.../[MedicalRecord.ID]/[Standardized-TreatmentIndex]/".
		String Subfolder = folder + this.GetStandardizedIndex() + "/";
		TreatmentInformation.put("Subfolder", Subfolder); 	// Saved here as cache	

		try {
			directory = Subfolder + "MedicoInfo.json";
			TreatmentInformation.put("MedicoInfo", directory);
			JsonUtils.SaveHashTableIntoJsonFile(directory, this.GetMedicoInfo(), null);


			ArrayList<Object> CastedSupplementary = DataUtils.CastToObjectArrayFromStringArray(this.GetSupplementary());
			directory = Subfolder + "Supplementary.json";
			TreatmentInformation.put("Supplementary", directory);
			JsonUtils.SaveArrayListIntoJsonFile(directory, CastedSupplementary, null);


			directory = Subfolder + "Resources.json";
			TreatmentInformation.put("Resources", directory);
			JsonUtils.SaveHashTableIntoJsonFile(directory, this.GetResources(), null);


			directory = Subfolder + "Descriptions.json";
			TreatmentInformation.put("Descriptions", directory);
			JsonUtils.SaveHashTableIntoJsonFile(directory, this.GetDescriptions(), null);
			

			directory = folder + this.GetStandardizedIndex() + ".json";
			TreatmentInformation.put("Treatment", directory);
			JsonUtils.SaveHashTableIntoJsonFile(directory, TreatmentInformation, null);

		} catch (Exception e) { e.printStackTrace(); }
		return TreatmentInformation;
	}

	public static Treatment Deserialize(Hashtable<String, Object> data) {
		String Pt_ID = (String) data.get("Pt_ID");
        String Pt_FirstName = (String) data.get("Pt_FirstName");
        String Pt_LastName = (String) data.get("Pt_LastName");
        String Pt_Age = (String) data.get("Pt_Age");
        String Pt_Gender = (String) data.get("Pt_Gender");

		String MedicalRecordID = (String) data.get("MedicalRecordID");
		int TreatmentIndex = Integer.parseInt((String) data.get("TreatmentIndex"));
		String ClassificationCode = (String) data.get("ClassificationCode");

        Treatment record = new Treatment(Pt_ID, MedicalRecordID, Pt_FirstName, Pt_LastName, Pt_Age, 
										 Pt_Gender, TreatmentIndex, ClassificationCode, true);
        record.SetDate((String) data.get("date"));
        record.SetTime((String) data.get("time"));

		// Deserialize Medico, Supplementary, Resources, and Descriptions. These are stored in JSON files.
		// So we need to call them
		String MedicoInfo_File = (String) data.get("MedicoInfo");
		String Supplementary_File = (String) data.get("Supplementary");
		String Resources_File = (String) data.get("Resources");
		String Descriptions_File = (String) data.get("Descriptions");

		try {
			record.GetMedicoInfo().putAll(JsonUtils.LoadJsonFileToHashtable(MedicoInfo_File, null));

			ArrayList<Object> Supplementary = JsonUtils.LoadJsonFileToArrayList(Supplementary_File, null);
			record.GetSupplementary().addAll(DataUtils.CastToStringArrayFromObjectArray(Supplementary));

			record.GetResources().putAll(JsonUtils.LoadJsonFileToHashtable(Resources_File, null));
			record.GetDescriptions().putAll(JsonUtils.LoadJsonFileToHashtable(Descriptions_File, null));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!(boolean) data.get("writable")) { record.CloseRecord(); }
		return record;
	}

	public static Treatment DeserializeFromFile(String directory) throws Exception {
		Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(directory, null);
		String VerifyKey = (String) data.get("Treatment");
		DataUtils.CheckCondition(VerifyKey != null, "The loaded file is not a valid treatment record.");
		return Treatment.Deserialize(data);
	}

}
