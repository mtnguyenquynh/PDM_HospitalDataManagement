package CoreSystem;

import java.util.Hashtable;

import BaseClass.BaseObject;
import BaseClass.IntermediateObject;
import Person.Person;
import Utility.DataUtils;
import Utility.JsonUtils;
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
 * This class described the ID-Generator for dead objects such as the Resource, 
 * and Tool. The ID is casted by the prefix and the counter itself by linking 
 * to the JSON Configuration stored in the "database" folder (locked at ID_Store).
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.w3schools.com/java/java_switch.asp
**/

public class ID_Generator {
    // ---------------------------------------------------------------------------------------------------------------------
    // Although it is not 100% correct to load as String[], but we left there to ensure the progress
    // of the project and future software development.

    private final Hashtable<Prefix, String[]> ID_Store;
    private final static String counter = "count";

    private void InsertToIDStore(Prefix prefix, String directory, String filename) {
        String[] path = {"database" + "/" + directory + "/" + filename};
        this.ID_Store.put(prefix, path);
    } 

    public ID_Generator() {
        this.ID_Store = new Hashtable<Prefix, String[]>(100, 0.75f);

        this.InsertToIDStore(Prefix.Tool, "GlobalPool", "Tool.json");
        this.InsertToIDStore(Prefix.Resource, "GlobalPool", "Resource.json");

        this.InsertToIDStore(Prefix.Patient, "PatientData", "Patient.json");
        this.InsertToIDStore(Prefix.MedicalRecord, "PatientRecord", "MedicalRecord.json");

        this.InsertToIDStore(Prefix.Doctor, "MedicoData", "Doctor.json");
        this.InsertToIDStore(Prefix.Nurse, "MedicoData", "Nurse.json");
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Ultility functions to construct the ID
    private String ValidatePrefixAndGetDirectory(IntermediateObject object) {
        Prefix prefix = object.GetThisPrefix();
        String[] ID_Pool = this.ID_Store.get(prefix);
        DataUtils.CheckArgumentCondition(ID_Pool != null, "The ID Generator is not prepared for this object.");
        return ID_Pool[0];
    }
    
    private String ConstructID(Prefix prefix, int counter, int RuleValue) throws Exception {
        String notation = prefix.GetPrefixCodeNotation();
        switch (RuleValue) {
            case 1: { return notation + String.format("%6d", counter); }
            case 2: {
                String temp = String.format("%8d", counter);
                return notation + temp.substring(0, 3) + "-" + temp.substring(3);
            } 
            case 3: {
                String temp = String.format("%10d", counter);
                return notation + temp.substring(0, 5) + "-" + temp.substring(5);
            }
            default: { throw new Exception("The rule is not supported."); }
        }
    } 

    /**
     * This function is an internal core-function used to generate the ID for the object.
     * The rule is specified by the datatype of the object and the rule-value.
     * See method `void ConstructID()` for more detailed information.
     * @param object (Object): The ID of the object we want to set.
     * @param forceUpdate (bool): If true, this will increment the counter stored in the JSON file.
     * @param RuleValue (int): The current supported rule are ranging from 1 to 3 (1 <= RuleValue <= 3).
     * @return
     * @throws Exception: if the rule is not supported.
     */

    public String _GenerateID_(IntermediateObject object, boolean forceUpdate, int RuleValue) throws Exception {
        // Step 1: Get the prefix and validate whether it is available to create the ID
        String directory = this.ValidatePrefixAndGetDirectory(object);
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(directory, null);

        // Step 2: Construct the ID
        int count = (int) data.get(ID_Generator.counter);
        String ID = this.ConstructID(object.GetThisPrefix(), count, RuleValue);

        // Step 3: Update the counter
        if (forceUpdate) {
            data.put(ID_Generator.counter, count + 1);
            JsonUtils.SaveHashTableIntoJsonFile(directory, data, null);
        }
        return ID; 
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Public functions to generate the ID
    public String GenerateObjectID(BaseObject TargetObject, boolean forceUpdate) throws Exception {
        return this._GenerateID_(TargetObject, forceUpdate, 0);
    }

    public String GenerateObjectID(BaseObject TargetObject) throws Exception {
        return this.GenerateObjectID(TargetObject, true);
    }

    public String GeneratePersonID(Person person, boolean forceUpdate) throws Exception {
        return this._GenerateID_(person, forceUpdate, 1); 
    }

    public String GeneratePersonID(Person person) throws Exception {
        return this.GeneratePersonID(person, true);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    private void ChangeCounter(String class_name, int amount) throws Exception {
        if (!Prefix.FindEnum(class_name)) {
            return ;
        }

        Prefix pr = Prefix.GetEnum(class_name);
        String directory = this.ID_Store.get(pr)[0];
        Hashtable<String, Object> data = JsonUtils.LoadJsonFileToHashtable(directory, null);
                
        int count = (int) data.get(ID_Generator.counter);
        data.put(ID_Generator.counter, count + amount);
        JsonUtils.SaveHashTableIntoJsonFile(directory, data, null);
        return ;
    }

    public void IncreaseCounter(String class_name) {
        try { this.ChangeCounter(class_name, 1); } 
        catch (Exception e) { e.printStackTrace(); }
    }

    public void DecreaseCounter(String class_name) {
        try { this.ChangeCounter(class_name, -1); } 
        catch (Exception e) { e.printStackTrace(); }
    }   

}
