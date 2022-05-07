package Treatment;

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
 * This class is the helper class which shared some similar functions across "MedicalRecord"
 * and "Treatment".
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://www.geeksforgeeks.org/new-date-time-api-java8/
 * 2) https://stackoverflow.com/questions/6516320/datetime-datatype-in-java
 * 3) https://www.baeldung.com/java-8-date-time-intro
**/

public abstract class TreatmentUtils {
    // ---------------------------------------------------------------------------------------------------------------------
    private static final int MAX_INDEX = 3;					    // This is "power-coefficient" of number of Treatments.

    // ---------------------------------------------------------------------------------------------------------------------
    // Getters and Setters
    public static int GetMaxDigitsAvailable() { return TreatmentUtils.MAX_INDEX; }
    public static int GetMaxTreatmentInMedicalRecords() { return (int) Math.pow(10, TreatmentUtils.MAX_INDEX); }
    
    public static String GetStandardizedIndex(int index) {
        try {
            int MaxTreatments = TreatmentUtils.GetMaxTreatmentInMedicalRecords();
            if (index >= MaxTreatments) {
                String[] idxStr = {String.valueOf(index), String.valueOf(MaxTreatments)};
		        throw new Exception("The treatment index is too large (index = ." + idxStr[0] + "> " + idxStr[1] + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
		return String.format("%" + String.valueOf(TreatmentUtils.MAX_INDEX) + "d", index);
    }

    // ---------------------------------------------------------------------------------------------------------------------
    public static String GetToMedicalRecordFolder(Treatment treatment) { 
		try { 
			String folder = PersonUtils.GetPatientRecordDirectory(treatment.GetPtFirstName(), false);
			return folder + treatment.GetPtID() + "/" + treatment.GetMedicalRecordID() + "/";
		} catch (Exception e) { return null; }
	}

    public static String GetToMedicalRecordFolder(MedicalRecord record) { 
		try { 
			String folder = PersonUtils.GetPatientRecordDirectory(record.GetPtFirstName(), false);
			return folder + record.GetPtID() + "/" + record.GetMedicalRecordID() + "/";
		} catch (Exception e) { return null; }
	}

	public static String GetToTreatmentFolder(Treatment treatment) { 
		return treatment.GetToMedicalRecordFolder() + treatment.GetStandardizedIndex() + "/";
	}


}
