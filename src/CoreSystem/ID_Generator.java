package CoreSystem;

import java.util.Hashtable;

import BaseClass.BaseObject;
import BaseClass.IntermediateObject;
import Person.Person;
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
 * and Tool. The ID is casted by the prefix and the six-digit ID itself.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) 
**/

public class ID_Generator {
    // ---------------------------------------------------------------------------------------------------------------------
    // Although it is not 100% correct to load as String[], but we left there to ensure the progress
    // of the project and future software development.

    private final Hashtable<Prefix, String[]> ID_Store;
    private final static String counter = "count";

    private final static String[] RuleSet = {"%06d", "%03d-%05d"};

    private static String[] MergeString(String dir, String filename) {
        return new String[] {"database" + "/" + dir + "/" + filename};
    }

    public ID_Generator() {
        this.ID_Store = new Hashtable<Prefix, String[]>(100, 0.75f);

        this.ID_Store.put(Prefix.Tool, ID_Generator.MergeString("GlobalPool", "Tool.json"));
        this.ID_Store.put(Prefix.Resource, ID_Generator.MergeString("GlobalPool", "Resource.json"));

        this.ID_Store.put(Prefix.Patient, ID_Generator.MergeString("PatientData", "Patient.json"));
        this.ID_Store.put(Prefix.Doctor, ID_Generator.MergeString("MedicoData", "Doctor.json"));
        this.ID_Store.put(Prefix.Nurse, ID_Generator.MergeString("MedicoData", "Nurse.json"));
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Ultility functions to construct the ID
    private String ValidatePrefixAndGetDirectory(IntermediateObject object) {
        Prefix prefix = object.GetThisPrefix();
        String[] ID_Pool = this.ID_Store.get(prefix);
        JsonUtils.CheckArgumentCondition(ID_Pool != null, "The ID Generator is not prepared for this object.");
        return ID_Pool[0];
    }
    
    private String ConstructID(Prefix prefix, int counter, int RuleValue) {
        String Rule = ID_Generator.RuleSet[RuleValue];
        String notation = prefix.GetPrefixCodeNotation();
        long count = Rule.chars().filter(ch -> ch == 'd').count();
        if (count == 1) { return notation + String.format(Rule, counter); }
        
        return notation + String.format(Rule, counter, counter);
    } 

    private String GenerateID(IntermediateObject object, boolean forceUpdate, int RuleValue) throws Exception {
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
        return this.GenerateID(TargetObject, forceUpdate, 0);
    }

    public String GenerateObjectID(BaseObject object) throws Exception {
        return this.GenerateObjectID(object, true);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public String GeneratePersonID(Person person, boolean forceUpdate) throws Exception {
        return this.GenerateID(person, forceUpdate, 1); 
    }

    public String GeneratePersonID(Person person) throws Exception {
        return this.GeneratePersonID(person, true);
    }

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
