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

    public Hashtable<String, String> serialize() {
        Hashtable<String, String> result = new Hashtable<String, String>();
        result.put("date", this.GetDateAsString());
        result.put("time", this.GetTimeAsString());
        result.put("description", this.GetDescription());
        result.put("medico_name", this.GetMedicoName());
        return result;
    }

    public static Description deserialize(Hashtable<String, String> data) {
        Description desc = new Description(data.get("description"), data.get("medico_name"));
        desc.SetDate(data.get("date"));
        desc.SetTime(data.get("time"));
        return desc;
    }
}
