package Treatment;

import java.time.*;
import java.util.Hashtable;

import BaseClass.CreationDateTime;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This module describe a set of description attached for the treatment records, 
 * in a particular point of time. There are no identifier in this project and not
 * attached to the database relation. The time set rule is ISO-8601.
 * Note that the datetime written as attribute is the creation time, 
 * not the time displayed the patient's status.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.geeksforgeeks.org/new-date-time-api-java8/
 * 2) https://stackoverflow.com/questions/6516320/datetime-datatype-in-java
 * 3) https://www.baeldung.com/java-8-date-time-intro
 * 4) https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
**/

public class Description extends CreationDateTime {
    private String description;
    private String medico_name; 

    public Description(String description, String medico_name) {
        super();
        this.description = description;
        this.medico_name = medico_name;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Getters and Setters
    public String GetDescription() { return this.description; }
    public String GetMedicoName() { return this.medico_name; }

    public void SetDescription(String description) { this.description = description; }
    
    // ----------------------------------------------------------
    public void Display() {
        super.Display();
        System.out.println("Description: " + this.GetDescription());
        System.out.println("Medico: " + this.GetMedicoName());
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization and Deserialization
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put("date", this.GetDateAsString());
        result.put("time", this.GetTimeAsString());
        result.put("description", this.GetDescription());
        result.put("medico_name", this.GetMedicoName());
        return result;
    }

    public static Description Deserialize(Hashtable<String, Object> data) {
        String description = (String) data.get("description");
        String medico_name = (String) data.get("medico_name");
        Description desc = new Description(description, medico_name);
        desc.SetDate((String) data.get("date"));
        desc.SetTime((String) data.get("time"));
        return new Description(description, medico_name);
    }
}
