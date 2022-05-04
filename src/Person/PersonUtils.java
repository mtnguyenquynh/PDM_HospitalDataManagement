package Person;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This class provided several ultility functions for the Person class, 
 * but not limited to the Patient.class and Staff.class. 
 * There are some constraints made to ensure the program did not broke down.
 * 
 * @author Ichiru Take
 * @version 0.0.1
 * 
 * References:
 * 1) https://stackoverflow.com/questions/18057962/regex-pattern-including-all-special-characters
 * 
 * 2) https://bobbyhadz.com/blog/javascript-check-if-string-contains-special-characters
 * 
 * 3) https://metapx.org/slash-s-plus-java/
**/

public abstract class PersonUtils {
    // ----------------------------------------------------------------------------------------------------------------------
    // These fields are the default directory of the person's information
    private final static String MEDICO_DATA_DIRECTORY = "database/MedicoData";
    private final static String MEDICO_TASK_DIRECTORY = "database/MedicoTask";
    private final static String PATIENT_DATA_DIRECTORY = "database/PatientData";
    private final static String PATIENT_RECORD_DIRECTORY = "database/PatientRecord";

    // -----------------------------------------------------------
    // This attribute is used to construct a relative name tree to support fast search, query and retrieval
    private final static int FIRST_NAME_DEPTH = 3;
    private final static int MAX_NAME_OF_WORD = 16;                         // This may not be used
    private final static int MAX_CHAR_OF_WORD = 16;                         // This may not be used

    private final static String NUMERIC_CHARS = "[0-9]+";
    private final static String LETTER_CHARS = "[a-zA-Z]+";
    private final static String ALPHANUMERIC_CHARS = "[a-zA-Z0-9]+";
    private final static String SPECIAL_CHARS = "[^a-zA-Z0-9.\\s]+";        // We allowed a dot to minimize too long names


    // ----------------------------------------------------------------------------------------------------------------------
    public static String StandardizeName(String name) throws Exception {
        // This function is attempted to standardize any given name to a standard format
        // 0) First, validate the input name so that there are no digit-like or special characters
        // 1) If not successful, raise the Error. Otherwise, do the following
        // 2) Trim all unnecessary spaces at the first and the last of the string
        // 3) Lowercase all characters
        // 4) For each word, captialize the first character. For example: "harry" -> "Harry"
        // 
        // For example: "  hArRy  " -> "Harry"

        // Step 01) Remove all unnecessary spaces at the first and the last of the string 
        //          and lower-case all characters 
        String NewName = name.trim().replaceAll("\\s+", " ").toLowerCase();

        // Step 02) Do validation: Check if there are any digit-like or special characters
        if (NewName.matches("[0-9]+")) { throw new Exception("The name cannot be a digit-like string"); }
        if (NewName.matches(PersonUtils.GetSpecialChars())) { 
            throw new Exception("The name cannot contain special characters"); 
        }

        // Step 02) For each word, captialize the first character. For example: "harry" -> "Harry"
        String[] words = NewName.split(" ");
        for (String word: words) {
            if (word.length() == 1 && word.equals(".")) { 
                throw new Exception("The name cannot contain a single dot"); 
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String word: words) {
            int length = word.length();
            if (length > PersonUtils.GetMaxCharOfWord()) { 
                String FirstLetter = word.substring(0, 1).toUpperCase();
                String NextLetter = "";
                if (length > PersonUtils.GetMaxCharOfWord() / 2) { 
                    NextLetter = word.substring(1, 2).toLowerCase(); 
                } 
                sb.append(String.format("%s%s. ", FirstLetter, NextLetter));
            } else {
                sb.append(word.substring(0, 1).toUpperCase() + word.substring(1) + " ");
            }
        }

        // The main reason for trimming is that the we have added a space at the final word 
        // and it is not needed.
        return sb.toString().trim(); 
    }

    public static int CountNameLength(String name, boolean IsStandardized) throws Exception {
        // This function is used to count the length of the name
        if (!IsStandardized) { name = PersonUtils.StandardizeName(name); }
        return name.split(" ").length;
    }

    /**
     * This method is called to find the directory of this person's information stored in the 
     * database. The mechanism is to first split the "FirstName" (which have ONLY one word) 
     * into every letters (in String datatype). Then, we construct a relative name tree
     * at maximum depth of "FIRST_NAME_DEPTH". Note that the name must be standardized and 
     * not contained any dot (".").
     * 
     * Note that the output path is not a full path, but a relative tree-based path with 
     * lower-case character.
     * 
     * @param name (Stirng) The first name of the person (which has only one word)
     * @param IsStandardized (boolean) Whether the name is assumed to be already standardized or not
     * @return (String) The relative path of the person's information
     * @throws Exception If the name is not valid
     */
    public static String GetDivisionPath(String name, boolean IsStandardized) throws Exception {
        if (!IsStandardized) { name = PersonUtils.StandardizeName(name); }
        if (name.contains(".")) { throw new Exception("The name cannot contain a single dot."); }
        if (name.contains(" ")) { throw new Exception("The name must be a single word."); }
        
        int MaxLength = Math.min(PersonUtils.GetFirstNameDepth(), name.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MaxLength; i++) {
            sb.append(name.substring(i, i + 1).toLowerCase() + "/");
        }
        return sb.toString();
    }


    // ----------------------------------------------------------------------------------------------------------------------
    // Getter & Setter
    public static String GetMedicoDataDirectory() { return PersonUtils.MEDICO_DATA_DIRECTORY; }
    public static String GetMedicoTaskDirectory() { return PersonUtils.MEDICO_TASK_DIRECTORY; }
    public static String GetPatientDataDirectory() { return PersonUtils.PATIENT_DATA_DIRECTORY; }
    public static String GetPatientRecordDirectory() { return PersonUtils.PATIENT_RECORD_DIRECTORY; }
    
    public static int GetFirstNameDepth() { return PersonUtils.FIRST_NAME_DEPTH; }
    public static int GetMaxNameOfWord() { return PersonUtils.MAX_NAME_OF_WORD; }
    public static int GetMaxCharOfWord() { return PersonUtils.MAX_CHAR_OF_WORD; }

    public static String GetNumericChars() { return PersonUtils.NUMERIC_CHARS; }
    public static String GetLetterChars() { return PersonUtils.LETTER_CHARS; }
    public static String GetAlphanumericChars() { return PersonUtils.ALPHANUMERIC_CHARS; }
    public static String GetSpecialChars() { return PersonUtils.SPECIAL_CHARS; }


}
