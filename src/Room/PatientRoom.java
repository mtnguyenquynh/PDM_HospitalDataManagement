package Room;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import BaseClass.AbstractObject;
import Utility.DataUtils;
import Utility.Utils;

import Patient.Patient;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved
 *
 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class is to bind the relationship between the "RoomUnit" with the "ObjectRoom", 
 * "PatientRoom", and "MedicoRoom". In the "ObjectRoom" (which is a combination of the
 * "LToolPool" and "LResourcesPool"). The relationship can be implicitly described below:
 * 
 * Denoted a "RoomUnit" is a single room in the hospital. According to the "Software Architecture, 
 * Desgin & Engineering" and the "Principle of Database Management":
 * 1) PatientRoom: On each room, we assume a bed is for one patient, but since the number of
 *    bed is not fixed (undefined), but because the bed can be either empty or not, so the 
 *    primary key of the "PatientRoom" is the {"RoomUnit.ID", "Patient.ID", "Bed Index"}.
 *  
 * 2) MedicoRoom: In this scenario, the relationship is somehow related to the business logic.
 *    rather than the common ideology. In the "MedicoRoom", we assume that there must be at 
 *    least one medico responsible for that room (can be more than two). Thus the primary 
 *    key is the {"RoomUnit.ID", (Responsible) "Medico.ID"}.
 * 
 * 3) ObjectRoom: In this scenario, the relationship is somehow related to the Medico, but in 
 *    the program, this constraint is undefined. But in a simple manner, there are no different
 *    between the LToolPool and LResourcePool, which are both the collection of <Tool> and 
 *    <Resource>. Thus the primary key of the "ObjectRoom" is the {"RoomUnit.ID", "Object.ID"}.
 * 
 * In the programming scheme of the "Room", we did not intended to use some special methods, but
 * just a basic data-classes wrapper on top of it. Note that we don't store any non-serializable
 * object but just a basic description into it, whhich comprised composite-reference key as 
 * the primary key, and some syncronizable data.
 * 
 * Note that in some special real-world scenario, patient can switch across the bed, but this 
 * information can be viewed as redundant as the possibility of not finding the accurate patient
 * (from the data perspective) is neglibible. We may also have the possibility of switching the 
 * patient from this room to another room, but this is the responsibility of "RoomManager.class".
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class PatientRoom extends AbstractObject {
    private final static int SERIALIZATION_CAPACTITY = 100;
    private final static float SERIALIZATION_LOAD_FACTOR = 0.75f;
    private static final int NumberOfPatientInformation = 2;
    private int NumberOfBeds;

    private Hashtable<Integer, String[]> LocalPool;
    public PatientRoom(String ID, int NumberOfBeds) {
        super(ID);
        if (NumberOfBeds < -1) {NumberOfBeds = 2;}
        this.NumberOfBeds = NumberOfBeds;
        int capacity = PatientRoom.GetSerializationCapacity();
        float loadFactor = PatientRoom.GetSerializationLoadFactor();
        this.LocalPool = new Hashtable<Integer, String[]>(capacity, loadFactor);
        this._InitPool_();
    }

    public PatientRoom(String ID) {this(ID, 3);}            // A common room may have 2-3 beds ?


    // ---------------------------------------------------------------------------------------------------------------------
    // Distribute patient

    /**
     * This method will enforce the patient to laid on this bed regardless who is occupying it.
     * The data of patient who already occupied is unable to keep track, 
     * so we attempted to make this method private.
     * @param patient (Patient): The patient who will lay on this bed.
     * @param BedIndex (int): The index of the bed (bed's ID).
     */

    private void DistributePatientToBed(Patient patient, int BedIndex) {
        this.CheckIsValidBed(BedIndex);
        this.GetLocalPool().put(BedIndex, this.GetPatientInformation(patient));
    }

    /**
     * This method will enforce the patient to laid on this bed regardless who is occupying it.
     * The data of patient who already occupied is unable to keep track, 
     * so we attempted to make this method private.
     * @param patient (Patient): The patient who will lay on this bed.
     * @param BedIndex (int): The index of the bed (bed's ID).
     * @param force (boolean): If True, we must setup an empty bed in this room if found an empty bed.
     *                         Otherwise, we will throw an exception.
     */

    public void DistributePatient(Patient patient, int BedIndex, boolean force) 
           throws RuntimeException {
        if (true) { this.CheckIsValidBed(BedIndex); this.CheckIsValidPatient(patient); }
        // We need to verify if this room have the empty bed;
        // If yes, we can distribute the patient to this bed.
        // If no, we need to find the empty bed and distribute the patient to it.
        if (this.CheckIsContainEmptyBed()) {
            this.DistributePatientToBed(patient, BedIndex);
        } else {
            int EmptyBedIndex = this.GetOneEmptyBed();
            if (force && EmptyBedIndex != -1) {
                this.DistributePatientToBed(patient, EmptyBedIndex);  
            }
        }
        throw new RuntimeException("No empty bed in this room.");
    }
    
    public void DistributePatient(Patient patient, int BedIndex) throws RuntimeException {
        this.DistributePatient(patient, BedIndex, false);
    }

    public String[] KickPatientOutOfThisBed(int BedIndex) {
        this.CheckIsValidBed(BedIndex);
        String[] PatientInformation = this.GetLocalPool().get(BedIndex);
        this.GetLocalPool().replace(BedIndex, this.GetFakeEmptyPatient());
        return PatientInformation;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Patient-related Methods
    public int FindPatient(String SearchingString) {
        // The searching string can be the patient's ID, name, or even SSN 
        // (Social Security Number, which is not currently available).
        Iterator<Entry<Integer, String[]>> iter = this.GetPoolIterator();
        while (iter.hasNext()) {
            Entry<Integer, String[]> entry = iter.next();
            int keyCode = entry.getKey();
            String patient[] = entry.getValue();
            if (patient[0].equals(SearchingString)) { return keyCode; }
            if (patient[1].equals(SearchingString)) { return keyCode; } 
        }
        return -1;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Several Ultility Functions
    private String[] GetFakeEmptyPatient() {
        String[] fakePatient = new String[PatientRoom.NumberOfPatientInformation];
        for (int i = 0; i < PatientRoom.NumberOfPatientInformation; i++) { fakePatient[i] = ""; }
        return fakePatient;
    }

    public Iterator<Entry<Integer, String[]>> GetPoolIterator() {
        return this.GetLocalPool().entrySet().iterator();
    }

    public void CheckIsValidBed(int BedIndex) {
        Utils.CheckArgumentCondition(BedIndex >= 0, "BedIndex cannot be negative.");
        Utils.CheckArgumentCondition(BedIndex < this.GetNumberOfBeds(), 
                                     "BedIndex cannot be larger than the current beds available.");
    }

    public void CheckIsValidPatient(Patient patient) {
        Utils.CheckArgumentCondition(patient != null, "Patient cannot be null.");
    }

    public boolean CheckIfThisBedEmpty(int BedIndex) {
        this.CheckIsValidBed(BedIndex);
        String[] patient = this.GetLocalPool().get(BedIndex);
        return (patient[0] == "" || patient[0].equals("") || patient[0] == null);
    }

    public boolean CheckIsContainEmptyBed() {
        for (int i = 0; i < this.GetNumberOfBeds(); i++) {
            if (this.CheckIfThisBedEmpty(i)) { return true; }
        }
        return false;
    }

    public int GetOneEmptyBed() {
        for (int i = 0; i < this.GetNumberOfBeds(); i++) {
            if (this.CheckIfThisBedEmpty(i)) { return i; }
        }
        return -1;
    }

    public int[] GetAllEmptyBeds() {       
        int[] EmptyBeds = new int[this.GetNumberOfBeds()];
        int EmptyBedsCount = 0;
        for (int i = 0; i < this.GetNumberOfBeds(); i++) {
            if (this.CheckIfThisBedEmpty(i)) {
                EmptyBeds[EmptyBedsCount] = i;
                EmptyBedsCount++;
            }
        }
        return EmptyBeds;
    }

    public String[] GetPatientInformation(Patient patient) {
        Utils.CheckArgumentCondition(patient != null, "Patient cannot be null.");
        String[] patientInformation = new String[PatientRoom.NumberOfPatientInformation];
        patientInformation[0] = patient.GetID();
        patientInformation[1] = patient.GetName();
        // We may want to get age, gender, or some recognizable information but this should be enough

        return patientInformation;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter & Setter Function
    private Hashtable<Integer, String[]> GetLocalPool() { return this.LocalPool; }
    public static int GetSerializationCapacity() { return PatientRoom.SERIALIZATION_CAPACTITY; }
    public static float GetSerializationLoadFactor() { return PatientRoom.SERIALIZATION_LOAD_FACTOR; }
    
    public int GetNumberOfBeds() { return this.NumberOfBeds; }
    public int GetRoomCapacity() { return this.GetNumberOfBeds(); }         // Alias of GetNumberOfBeds()

    public void SetNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        this.NumberOfBeds = NumberOfBeds;
    }

    public void IncrementNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        if (NumberOfBeds > 0) { this.NumberOfBeds += NumberOfBeds; }
    }

    public void DecrementNumberOfBeds(int NumberOfBeds) { 
        Utils.CheckArgumentCondition(NumberOfBeds >= 0, "NumberOfBeds cannot be negative.");
        Utils.CheckArgumentCondition(NumberOfBeds <= this.GetNumberOfBeds(), 
                                     "NumberOfBeds cannot be smaller than the current beds available.");
        if (NumberOfBeds > 0) { this.NumberOfBeds -= NumberOfBeds; }
    }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Initialize the pool & Several Ultility Functions
    private void CleanPool() { this.GetLocalPool().clear(); }

    private void _InitPool_() { 
        if (this.LocalPool.size() != 0) { this.CleanPool(); }
        for (int i = 0; i < this.GetNumberOfBeds(); i++) { this.LocalPool.put(i, this.GetFakeEmptyPatient()); }
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = DataUtils.ForceGetEmptyHashtable(this.getClass());
        Iterator<Entry<Integer, String[]>> iter = this.GetPoolIterator();
        while (iter.hasNext()) {
            Entry<Integer, String[]> entry = iter.next();
            result.put(Integer.toString(entry.getKey()), entry.getValue());
        }
        result.put("id", this.GetID());
        result.put("NumberOfBeds", this.GetNumberOfBeds());
        return result;  
    } 

    public static PatientRoom Deserialize(Hashtable<String, Object> data) {
        PatientRoom room = new PatientRoom((String) data.get("id"), (int) data.get("NumberOfBeds"));
        Iterator<Entry<String, Object>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            if (StringUtils.isNumeric(key)) {
                room.GetLocalPool().put(Integer.parseInt(key), (String[]) entry.getValue());
            }
        }
        return room;
    }

}
