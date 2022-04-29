package Treatment;

import java.time.*;

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

public class Description {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String medico_name; 

    public Description(String description, String medico_name) {
        LocalDateTime current = LocalDateTime.now();
        this.date = (LocalDate) current.toLocalDate();
        this.time = (LocalTime) current.toLocalTime();
        this.description = description;
        this.medico_name = medico_name;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Getters and Setters
    public LocalDate GetDate() { return this.date; }
    public LocalTime GetTime() { return this.time; }

    public String GetDescription() { return this.description; }
    public String GetMedicoName() { return this.medico_name; }

    public void SetDescription(String description) { this.description = description; }
    
    // ----------------------------------------------------------
    public int GetDayOfMonth() { return this.date.getDayOfMonth(); }
    public int GetDayOfYear() { return this.date.getDayOfYear(); }
    public int GetDayOfWeek() { return this.date.getDayOfWeek().getValue(); }
    public DayOfWeek GetStringDayOfWeek() { return this.date.getDayOfWeek(); }
    
    public Month GetMonth() { return this.date.getMonth(); }
    public int GetMonthValue() { return this.date.getMonthValue(); }
    public int GetYearValue() { return this.date.getYear(); }

    public int GetHour() { return this.time.getHour(); }
    public int GetMinute() { return this.time.getMinute(); }
    public int GetSecond() { return this.time.getSecond(); }
    public int GetNano() { return this.time.getNano(); }

    // ----------------------------------------------------------
    public void Display() {
        System.out.println("Date: " + this.GetDate() + " (Date: " + this.GetStringDayOfWeek() + ")");
        System.out.println("Time: " + this.GetTime());
        System.out.println("Description: " + this.GetDescription());
        System.out.println("Medico: " + this.GetMedicoName());
    }
}
