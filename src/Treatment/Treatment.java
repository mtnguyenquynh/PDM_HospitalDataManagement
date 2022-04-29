package Treatment;

import java.time.;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This file described a particular how we directly load the treatment 
 * item/instance in the project. Note that we don't directly simulate 
 * all properties found in a real-world treatment record, but we setup the basic 
 * foundation. For example, the heart beat rate is a number that appeared in some 
 * particular records but not all. If those are having some special correlations to 
 * other, for example, the use in Machine Learning, but today, it is ignored, and was 
 * just noted by the `Description` field.   
 * 
 * 
 * In the treatment record, we store the patient's ID, the medico record's ID,
 * all the involved medicos (ID), some syncronized information such as name, age 
 * and gender, and the optional description if we want to add some fields on it.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.geeksforgeeks.org/new-date-time-api-java8/
 * 2) https://stackoverflow.com/questions/6516320/datetime-datatype-in-java
 * 3) https://www.baeldung.com/java-8-date-time-intro
**/




public class Treatment {
	// ---------------------------------------------------------------------------------------------------------------------
	private String Patient_ID, MedicalRecord_ID; 		// Patient's Data
	private String[] Involved_Medico_ID;				// Medico's Data
	private String P_Name, P_Age, P_Gender; 			// Syncronized information only from patients
	
	// ----------------------------------------------------------
	private String index; 			// This represented the index placed in the medical-record				
	private String code;			// This connected directly to the treatment code pool
	private Date start_day;

	public Treatment(String code){
		this.code = code;
	}
}
