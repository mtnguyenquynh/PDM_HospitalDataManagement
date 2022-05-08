package Staff;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Comparator;

import BaseClass.AbstractObject;
import Person.PersonUtils;
import Treatment.MedicalRecord;
import Treatment.Treatment;
import Utility.DataUtils;
import Utility.JsonUtils;
 

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This is a dependency class which describes the working progress at each medico.
 * Each medico have two working progress (or "MedicoTask") which are: "Active" and 
 * "Storage". The difference between these two are the "writable" state, stored as 
 * an attribute of the class "Treatment".
 * 
 * Ok but should we stored the "MdicalRecord" in the "MedicoTask"? YES !!!.
 * From the data perspective, "MedicalRecord" are sharing high similiarity as 
 * in the "Treatment". However, in the "index" field/column, we would set it to 
 * be NONE.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
 * 
**/



public class MedicoTask extends AbstractObject {
    // --------------------------------------------------------------------------------------------------------------------
    // To activate this class, we only needed to capture the Medico_ID and the working type of
    // the "MedicoTask" which are "Active" (True) and "Storage" (False).
    private static final int SERIALIZATION_CAPACITY = 10000;
    private boolean IsActive;
    private ArrayList<String[]> LocalPool;
    public MedicoTask(String ID, boolean IsActive) throws Exception {
        super(ID);
        this.IsActive = IsActive;
        this.LocalPool = new ArrayList<String[]>(MedicoTask.GetSerializationCapacity());
    }

    // --------------------------------------------------------------------------------------------------------------------
    // Getter Function (There are no setter here)
    public static int GetSerializationCapacity() { return MedicoTask.SERIALIZATION_CAPACITY; }
    public boolean GetIsActive() { return this.IsActive; }
    public String GetMedicoID() { return this.GetID(); }

    private ArrayList<String[]> GetLocalPool() { return this.LocalPool; }
    public ArrayList<String[]> GetWorkingTask() { return this.GetLocalPool(); }
    
    public int GetCurrentCapacity() { return this.GetWorkingTask().size(); }
    public static int GetMaxCapacity() { return MedicoTask.GetSerializationCapacity(); }

    public boolean IsEmpty() { return this.GetLocalPool().isEmpty(); }
    public boolean IsPoolHasExtraSlot() { return this.GetCurrentCapacity() < MedicoTask.GetMaxCapacity(); }
    public boolean IsPoolFull() { return !this.IsPoolHasExtraSlot(); }

    // --------------------------------------------------------------------------------------------------------------------
    // Medical Record & Treatment Management
    public boolean IsDistributedToThisMedico(Treatment treatment) {
        return treatment.GetMedicoInfo().containsKey(this.GetMedicoID());
    }

    public boolean IsDistributedToThisMedico(MedicalRecord record) {
        String ID = this.GetMedicoID();
        if (record.GetRDoc_MedicoID().equals(ID)) { return true; }
        if (record.GetRNurse_MedicoID().equals(ID)) { return true; }

        if (record.GetRDoc_MedicoID().equalsIgnoreCase(ID)) { return true; }
        if (record.GetRNurse_MedicoID().equalsIgnoreCase(ID)) { return true; }
        return false;
    }

    private String[] Getter(Treatment treatment) {
        return new String[] {treatment.GetDateAsString(), treatment.GetTimeAsString(), treatment.GetPtID(), 
                             treatment.GetPtFirstName(), treatment.GetMedicalRecordID(), treatment.GetStandardizedIndex()};
    }

    private String[] Getter(MedicalRecord record) {
        return new String[] {record.GetDateAsString(), record.GetTimeAsString(), record.GetPtID(), 
                             record.GetPtFirstName(), record.GetMedicalRecordID(), "NONE"};
    }

    // ----------------------------------------------------------
    // API Function to add a new treatment to the "MedicoTask": Return True if success, False otherwise.
    public boolean AddTask(Treatment treatment) throws Exception {
        if (this.IsPoolFull()) { throw new Exception("MedicoTask is full"); }
        if (this.IsDistributedToThisMedico(treatment)) {
            String[] task = this.Getter(treatment);
            if (!this.GetLocalPool().contains(task)) {
                return this.GetLocalPool().add(task);
            }
        }
        return false;
    }

    public boolean AddTask(MedicalRecord record) throws Exception {
        if (this.IsPoolFull()) { throw new Exception("MedicoTask is full"); }
        if (this.IsDistributedToThisMedico(record)) {
            String[] task = this.Getter(record);
            if (!this.GetLocalPool().contains(task)) {
                return this.GetLocalPool().add(task);
            }
        }
        return false;
    }

    public boolean RemoveTask(Treatment treatment) {
        if (this.IsDistributedToThisMedico(treatment)) {
            boolean res = this.GetLocalPool().remove(this.Getter(treatment));
            if (res) { this.GetLocalPool().ensureCapacity(MedicoTask.GetMaxCapacity()); }
            return res;
        }
        return false;
    }

    public boolean RemoveTask(MedicalRecord record) {
        if (this.IsDistributedToThisMedico(record)) {
            boolean res = this.GetLocalPool().remove(this.Getter(record));
            if (res) { this.GetLocalPool().ensureCapacity(MedicoTask.GetMaxCapacity()); }
            return res;
        }
        return false;
    }

    public void SortTask(boolean reverse) {
        Comparator<String[]> comparator = new Comparator<String[]>() {
            @Override
            public int compare(final String[] o1, final String[] o2) {
                String date_1 = o1[0];
                String date_2 = o2[0];
                String time_1 = o1[1];
                String time_2 = o2[1];
                if (!reverse) {
                    if (!date_1.equals(date_2)) { return date_1.compareTo(date_2); }
                    return time_1.compareTo(time_2);
                } else {
                    if (!date_1.equals(date_2)) { return date_2.compareTo(date_1); }
                    return time_2.compareTo(time_1);
                }
            }
        };
        Collections.sort(this.GetLocalPool(), comparator);
    }

    // --------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public String GetToMedicoTaskFolder() {
        return PersonUtils.GetMedicoTaskDirectory() + "/" + this.GetMedicoID() + "/";
    }

    public String GetToMedicoTaskFile() {
        String filename = this.GetIsActive() ? "Active.json" : "Storage.json";
        return this.GetToMedicoTaskFolder() + filename;
    }
    /**
	 * This function not serializes the "MedicoTask" into the JSONOBject-like object but actually
	 * serialize the "MedicoTask" into a JSON file.
	 * Depending on the "IsActive" value, the file will be named "Active.json" or "Storage.json".
     * It is located at the following folder: "database/MedicoTask/<MedicoID>/". 
	 */   
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> data = super.Serialize();
        data.put("IsActive", this.GetIsActive());
        for (int i = 0; i < this.GetLocalPool().size(); i++) {
            String[] task = this.GetLocalPool().get(i);
            data.put(String.valueOf(i), task);
        }
        
        data.put("folder", this.GetToMedicoTaskFolder());
        try {
            String filename = this.GetToMedicoTaskFile();
            data.put("MedicoTask", filename);
            JsonUtils.SaveHashTableIntoJsonFile(filename, data, null);
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }

    public static MedicoTask Deserialize(Hashtable<String, Object> data) throws Exception {
        String ID = (String) data.get("id");
        boolean IsActive = (boolean) data.get("IsActive");
        MedicoTask medicoTask = new MedicoTask(ID, IsActive);

        Iterator<Entry<String, Object>> iter = data.entrySet().iterator();
        
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            try {
                Integer.parseInt(key);
                String[] task = (String[]) entry.getValue();
                medicoTask.GetLocalPool().add(task);
            } catch (Exception e) {
                // Do nothing
            }
        }
        medicoTask.SortTask(false);
        return medicoTask;
    }

    public static MedicoTask DeserializeFromFile(String diirectory) throws Exception {
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(diirectory, null);
        String VerifyKey = (String) data.get("MedicoTask");
		DataUtils.CheckCondition(VerifyKey != null, "The loaded file is not a valid medical record.");
        return MedicoTask.Deserialize(data);
    }
}
