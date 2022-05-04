package Person;

import Utility.Utils;

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
 * 2) https://bobbyhadz.com/blog/javascript-check-if-string-contains-special-characters
 * 3) https://metapx.org/slash-s-plus-java/
 * 4) https://stringr.tidyverse.org/articles/regular-expressions.html
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
    // Name Tree
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
        Utils.CheckArgumentCondition(name != null, "The input name is null");

        String NewName = name.trim().replaceAll("\\s+", " ").toLowerCase();

        // Step 02) Do validation: Check if there are any digit-like or special characters
        Utils.CheckArgumentCondition(!NewName.matches("[0-9]+"), 
                                     "The name cannot be a digit-like string.");
        Utils.CheckArgumentCondition(!NewName.matches("[^a-zA-Z0-9.\\s]+"),
                                     "The name cannot contain special characters.");

        // Step 02) For each word, captialize the first character. For example: "harry" -> "Harry"
        String[] words = NewName.split(" ");
        for (String word: words) {
            Utils.CheckArgumentCondition(word.length() != 1 || !word.equals("."), 
                                         "The name cannot contain a dot.");
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
        Utils.CheckArgumentCondition(name != null, "The input name is null");
        if (!IsStandardized) { name = PersonUtils.StandardizeName(name); }
        return name.split(" ").length;
    }

    /**
     * This method is called to find the directory of this person's information stored in the 
     * database. The mechanism is to first split the "FirstName" (which have ONLY one word) 
     * into every letters (in String datatype). Then, we construct a relative name tree
     * at maximum depth of "FIRST_NAME_DEPTH". Note that the name must be standardized and 
     * not contained any dot ("."). Note that the output path is not a full path, but a 
     * relative tree-based path with lower-case character.
     * 
     * For example, (given a standardized name "Harry Potter")
     * 1) "Harry Potter" -> Error (More than one word)
     * 2) "Harry" -> "h/a/r/"
     * 3) "Potter" -> "p/o/t/"
     * 4) "G." -> Error (Contain invalid character)
     * 5) "Ga" -> "g/a/"
     *  
     * 
     * @param FirstName (String) The first name of the person (which has only one word)
     * @param IsStandardized (boolean) Whether the name is assumed to be already standardized or not
     * @return (String) The relative path of the person's information
     * @throws Exception If the name is not valid
     */
    public static String GetPathByFirstName(String FirstName, boolean IsStandardized) throws Exception {
        Utils.CheckArgumentCondition(FirstName != null, "The input name is null");
        if (!IsStandardized) { FirstName = PersonUtils.StandardizeName(FirstName); }
        if (FirstName.contains(".")) { throw new Exception("The name cannot contain a single dot."); }
        if (FirstName.contains(" ")) { throw new Exception("The name must be a single word."); }
        
        int MaxLength = Math.min(PersonUtils.GetFirstNameDepth(), FirstName.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MaxLength; i++) {
            sb.append(FirstName.substring(i, i + 1).toLowerCase() + "/");
        }
        return sb.toString();
    }

    // ----------------------------------------------------------------------------------------------------------------------
    // Email

    /**
     * This method is called to verify to be at least an email address is considered to be valid. 
     * The email structure is something as this: "email_name@supplier". Where as the "email_name"
     * contained letter-like, digit-like, "dot" or "underscore" characters. Meanwhile, the 
     * suppliers only accepted lower-case letter-like and "dot" characters only.
     * 
     * The email address is considered to be valid if and only if:
     * 1) The email address contains only one "@" character between the "email_name" and the "supplier"
     * 2) The email address contains at least one "." character at the "supplier" part. The dot cannot 
     *    be located next to the "@" character or at the end of the string.
     * 
     * 3) The email address contained special characters (including whitespace " ")
     * 3) Validate each part 
     * 
     * 
     * @param email
     * @return
     * @throws Exception
     */

    public static String StandardizeEmail(String email) throws Exception {
        Utils.CheckArgumentCondition(email != null, "The input email is null");
        String NewEmail = email.trim();
        if (NewEmail.length() == 0) { throw new Exception("The email is empty"); }
        
        // Step 01) Validation: Check if there are any "@" or "." characters
        Utils.CheckArgumentCondition(!NewEmail.contains(" "), "The email contain whitespace character.");
        Utils.CheckArgumentCondition(NewEmail.contains("@"), "The email does not contain an '@' character.");
        Utils.CheckArgumentCondition(NewEmail.contains("."), "The email does not contain a '.' character.");

        // Step 02) Validate each-part
        String[] parts = NewEmail.split("\\@");
        Utils.CheckArgumentCondition(parts.length == 2, "The email contain more than one '@' characters.");

        String EmailName = parts[0];
        String EmailSupplier = parts[1];

        // Step 03) Validate the email name
        Utils.CheckArgumentCondition(EmailName.length() > 0, "The email name is empty.");
        Utils.CheckArgumentCondition(!EmailName.matches("[a-zA-Z0-9._]+"), 
                                     "The email name contain invalid special characters.");
        Utils.CheckArgumentCondition(!EmailName.substring(0).matches("[a-zA-Z0-9]"), 
                                     "The email name contain invalid special characters at the beginning.");
        Utils.CheckArgumentCondition(!EmailName.substring(EmailName.length() - 1).matches("[a-zA-Z0-9]"), 
                                     "The email name contain invalid special characters at the end.");                                                     

        // Step 04) Validate the email supplier
        Utils.CheckArgumentCondition(EmailSupplier.contains("."), 
                                     "The email address does not contain a '.' character.");

        Utils.CheckArgumentCondition(EmailSupplier.length() > 0, "The email address is empty.");
        Utils.CheckArgumentCondition(!EmailSupplier.matches("[a-zA-Z._]+"),
                                     "The email address contain invalid special characters.");
        Utils.CheckArgumentCondition(!EmailSupplier.substring(0).matches("[a-zA-Z]"),
                                     "The email address contain invalid special characters at the beginning.");     
        Utils.CheckArgumentCondition(!EmailSupplier.substring(EmailSupplier.length() - 1).matches("[a-zA-Z]"),
                                     "The email address contain invalid special characters at the end.");

        // Step 05) Construct the standardized email
        return EmailName + "@" + EmailSupplier;
    }
    


    public static String StandardizePhoneNumber(String phone) throws Exception {
        // The phone number can have multiple representation formats depending on the region.
        // The phone number can be mistaken as the home number, or the office number.
        // Thus we only generalize the phone number to the following format
        // The phone number can have region code (i.e +84, +86, etc)
        Utils.CheckArgumentCondition(phone != null, "The input phone number is null");
        String NewPhone = phone.trim();

        Utils.CheckArgumentCondition(NewPhone.length() > 0, "The phone number is empty.");
        Utils.CheckArgumentCondition(!NewPhone.matches("[+?][0-9]+"),
                                     "The phone number contain invalid special characters.");
        int length = NewPhone.length();
        if (NewPhone.contains("+")) { length -= 1; }
        
        // This is to prevent magic phone number such as 113, 115 which are the emergency number
        // This condition is not created to fully compatible on all region.
        Utils.CheckArgumentCondition((length >= 8 && length <= 16), 
                                     "The phone number must be between 8 and 16 digits.");

        return NewPhone;
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

    // -----------------------------------------------------------
    // Getter in Advance
    private static String GetMergedDirectory(String directory, String name, boolean IsStandardized) throws Exception {
        try { return directory + "/" + PersonUtils.GetPathByFirstName(name, IsStandardized); } 
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public static String GetMedicoDataDirectory(String name, boolean IsStandardized) throws Exception {
        String directory = PersonUtils.GetMedicoDataDirectory();
        return PersonUtils.GetMergedDirectory(directory, name, IsStandardized);
    }

    public static String GetPatientDataDirectory(String name, boolean IsStandardized) throws Exception {
        String directory = PersonUtils.GetPatientDataDirectory();
        return PersonUtils.GetMergedDirectory(directory, name, IsStandardized);
    }

    public static String GetPatientRecordDirectory(String name, boolean IsStandardized) throws Exception {
        String directory = PersonUtils.GetPatientRecordDirectory();
        return PersonUtils.GetMergedDirectory(directory, name, IsStandardized);
    }

    public static String GetMedicoTaskDirectory(String name, boolean IsStandardized) throws Exception {
        String directory = PersonUtils.GetMedicoTaskDirectory();
        return PersonUtils.GetMergedDirectory(directory, name, IsStandardized);
    }

}
