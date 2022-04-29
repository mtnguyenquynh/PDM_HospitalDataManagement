package Treatment;
import Utility.Utils;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

/**
* Copyright (C) 2022-2022, HDM-Dev Team
* All Rights Reserved

* This file is part of HDM-Dev Team's project. The contents are
* fully covered, controlled, and acknowledged by the terms of the
* BSD-3 license, which is included in the file LICENSE.md, found
* at the root of the project's source code/tree repository.
**/

/**
 * This file described an implicit set of treatment codes, where 
 * we declared the definition of the our treatment record. Most methods
 * and attributes are the private-static, which shared through out the 
 * program and being managed by the json file ONLY.
 * @author Ichiru Take
 * @version 0.0.1
 * See references:
 * - https://www.tutorialspoint.com/java/java_enum_class.htm
 * - https://www.geeksforgeeks.org/differences-between-hashmap-and-hashtable-in-java/
 * - https://www.geeksforgeeks.org/hashtable-in-java/
 * - https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Hashtable.html 
**/


public class TreatmentCode {
    // The pool here is a collection of key-value pairs, where the key is the code, 
    // and the value is the simple description found describing the code.
    // The key_code is a fixed-length 13-valued string (8 digits separated by 2 dashes, and a 3-valued prefix)
    private final static int capacity = 1000;
    private final static float loadFactor = (float) 0.75f;
    private final static Hashtable<String, Object> Pool = new Hashtable<String, Object>(capacity, loadFactor);
    
    // These two directory are the saved configuration of all treatment codes. 
    private final static String MainJsonDirectory = "src/Treatment/TreatmentCode.json";
    private final static String SafeJsonDirectory = "src/Treatment/TreatmentCode-Restored.json";
    private final static String prefix = "TC-";
    private final static String Name = "TreatmentCode";
    private final static String[] ArgName = {"key_code", "description"};

    public static void main(String[] args) {
        if (TreatmentCode.GetNumberOfCodeAvailable() == 0) {
            boolean status = TreatmentCode.LoadJsonDatabase();
            if (status == false || TreatmentCode.GetNumberOfCodeAvailable() == 0) { 
                TreatmentCode.InitializePool(); 
            } else {
                TreatmentCode.ValidateAllKeyCodeInPool(false);
            }
        }
    } 
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Pool declaration
    private static boolean LoadJsonDatabase() {
        File json_data = new File(TreatmentCode.MainJsonDirectory); 
        String directory = null;
        if (json_data.exists() && directory == null) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("The treatment code database is loaded from the file: " + TreatmentCode.MainJsonDirectory);
            directory = TreatmentCode.MainJsonDirectory;
        } 

        if (directory == null) {
            json_data = new File(TreatmentCode.SafeJsonDirectory); 
            if (json_data.exists()) {
                System.out.println("The treatment code database is loaded from the file: " + TreatmentCode.SafeJsonDirectory);
                directory = TreatmentCode.SafeJsonDirectory;
            }
        }
        
        boolean status = false;
        if (directory != null) {
            try {
                status = Utils.LoadJsonDataIntoHashTable(
                    directory, TreatmentCode.Pool, 
                    TreatmentCode.Name, TreatmentCode.ArgName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The treatment code database is not found, and will be created.");
        }
        System.out.println("----------------------------------------------------------------------------------");
        return status;
    }

    private static void _InitPool_() {
        TreatmentCode.Pool.clear();
        TreatmentCode.Pool.put(TreatmentCode.prefix + "00-00-0000", "Null or Empty treatment");
        TreatmentCode.Pool.put(TreatmentCode.prefix + "00-00-0001", "X-rays");
        TreatmentCode.Pool.put(TreatmentCode.prefix + "00-00-0002", "Blood test");
        TreatmentCode.Pool.put(TreatmentCode.prefix + "00-00-0003", "Surgical operation");
        TreatmentCode.Pool.put(TreatmentCode.prefix + "00-00-0004", "Health-check");
    }

    private static void InitializePool() throws InternalError {
        if (TreatmentCode.GetNumberOfCodeAvailable() != 0) { return ; }
        
        System.out.println("Warning: There is no JSON file found in the provided directory. " + 
                            "This method is called as a restore point.");
        // Step 01: Initialize the pool
        TreatmentCode._InitPool_();

        // Step 02: Validate the pool
        TreatmentCode.ValidateAllKeyCodeInPool(false);

        // Step 03: Save the pool into JSON file for later used
        try {
            Utils.SaveHashTableIntoJsonFile(
                TreatmentCode.SafeJsonDirectory, TreatmentCode.Pool, 
                TreatmentCode.Name, TreatmentCode.ArgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------------------------------------");
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function Only
    public static Hashtable<String, Object> GetPool() { return TreatmentCode.Pool; }

    public static boolean ContainsThisKeyCode(String code) { return TreatmentCode.GetPool().containsKey(code); }
    public static int GetCapacity() { return capacity; }
    public static float GetPreloadFactor() { return loadFactor; }
    public static int GetNumberOfCodeAvailable() { return Pool.size(); }

    public static void Display() {
        Iterator<Entry<String, Object>> iter = TreatmentCode.Pool.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static Object GetValue(String code) { 
        if (TreatmentCode.ContainsThisKeyCode(code)) {
            return TreatmentCode.GetPool().get(code);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Validation Function Only
    public static boolean ValidateKeyCode(String code) {
        if (code == null || code.length() != 13) { return false; }
        if (!code.startsWith(TreatmentCode.prefix)) {return false; }
        if (code.charAt(0) != 'T' || code.charAt(1) != 'C' || code.charAt(2) != '-') { return false; }
        return true;
    }

    public static boolean ValidateAllKeyCodeInPool(boolean skip_error) throws InternalError {
        Iterator<Entry<String, Object>> iter = TreatmentCode.Pool.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String keyCode = entry.getKey();
            if (!TreatmentCode.ValidateKeyCode(keyCode)) {
                String template = "This key code %s (desc=%s) is not valid";
                String message = String.format(template, keyCode, entry.getValue());
                if (skip_error) {
                    System.out.println(message);
                    return false;
                } else {
                    throw new InternalError(message);
                }
            }
        }
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------------------

}
