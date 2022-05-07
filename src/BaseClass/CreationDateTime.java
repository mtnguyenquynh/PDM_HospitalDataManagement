package BaseClass;

import java.time.*;
import java.util.Hashtable;

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
 * This module describe a custom datetime class which describe the time of 
 * object instantiation by default (initialization/creation time). However,
 * you can re-declare the time by yourself using setter functions.
 * The time set rule is ISO-8601. 
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

public class CreationDateTime {
    private LocalDate date;
    private LocalTime time;

    public CreationDateTime() {
        LocalDateTime current = LocalDateTime.now();
        this.date = (LocalDate) current.toLocalDate();
        this.time = (LocalTime) current.toLocalTime();
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getters and Setters
    public LocalDate GetDate() { return this.date; }
    public String GetDateAsString() { return this.date.toString(); }
    
    public LocalTime GetTime() { return this.time; }
    public String GetTimeAsString() { return this.time.toString(); }

    public void SetDate(String date) { this.date = LocalDate.parse(date); }
    public void SetTime(String time) { this.time = LocalTime.parse(time); }

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
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Serialization & Deserialization
    
    public Hashtable<String, Object> Serialize() {
        Hashtable<String, Object> result = DataUtils.ForceGetEmptyHashtable(this.getClass());
        result.put("date", this.GetDateAsString());
        result.put("time", this.GetTimeAsString());
        return result;
    }

    public static CreationDateTime Deserialize(Hashtable<String, Object> data) {
        CreationDateTime creationDateTime = new CreationDateTime();
        creationDateTime.SetDate((String) data.get("date"));
        creationDateTime.SetTime((String) data.get("time"));
        return creationDateTime;
    }

    
}
