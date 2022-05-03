package Treatment;
import java.util.*;
import java.util.Map.Entry;

import PrefixState.Prefix;
import Utility.Utils;

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
 * - https://www.geeksforgeeks.org/parse-json-java/
 * - https://stackhowto.com/how-to-read-a-json-file-with-java/
 * - https://attacomsian.com/blog/java-read-write-json-files
**/


public class TreatmentCode {
    // The pool here is a collection of key-value pairs, where the key is the code, 
    // and the value is the simple description found describing the code.
    // The key_code is a fixed-length 13-valued string (8 digits separated by 2 dashes, and a 3-valued prefix)
    private final static int capacity = 1000;
    private final static float loadFactor = (float) 0.75f;
    private final static Hashtable<String, Object> Pool = new Hashtable<String, Object>(capacity, loadFactor);
    
    // These two directory are the saved configuration of all treatment codes. 
    private final static String JsonDirectory = "database/TreatmentCode/TreatmentCode.json";
    private final static Prefix prefix = Prefix.TreatmentCode;

    public static void main(String[] args) {
        if (TreatmentCode.GetNumberOfCodeAvailable() == 0) {
            boolean status = TreatmentCode.LoadJsonDatabase();
            if (status == false || TreatmentCode.GetNumberOfCodeAvailable() == 0) { 
                TreatmentCode.InitializePool(); 
            } else {
                TreatmentCode.ValidateAllKeyCodeInPool(false);
            }
        }
        System.out.println("Number of codes available: " + TreatmentCode.GetNumberOfCodeAvailable());
    } 
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Pool declaration
    private static boolean LoadJsonDatabase() {
        File json_data = new File(TreatmentCode.JsonDirectory); 
        String directory = null;
        boolean status = false;
        
        if (json_data.exists() && directory == null) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("The treatment code database is loaded from the file: " + TreatmentCode.JsonDirectory);
            directory = TreatmentCode.JsonDirectory;

            try {
                ArrayList<Hashtable<String, Object>> array = Utils.SaveJsonDataIntoHashTable(directory, null);
                
                String[] ArgName = TreatmentCode.GetArgName();
                for (Hashtable<String, Object> item : array) {
                    String code = (String) item.get(ArgName[0]);
                    String description = (String) item.get(ArgName[1]);
                    TreatmentCode.GetPool().put(code, (Object) description);
                }
                status = true;
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
        String prefix_code = TreatmentCode.prefix.GetPrefixCode();
        ArrayList<String[]> pool = TreatmentCodeSavedPool.LoadPool();
        for (String[] record : pool) {
            TreatmentCode.Pool.put(prefix_code + record[0], record[1]);
        }
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
            ArrayList<Hashtable<String, Object>> array = TreatmentCode.ConvertPool();
            Utils.SaveHashTableIntoJsonFile(TreatmentCode.JsonDirectory, array, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------------------------------------");
    }


    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function
    public static Hashtable<String, Object> GetPool() { return TreatmentCode.Pool; }

    public static boolean ContainsThisKeyCode(String code) { return TreatmentCode.GetPool().containsKey(code); }
    public static int GetSerializationCapacity() { return capacity; }
    public static float GetSerializationLoadFactor() { return loadFactor; }
    public static int GetNumberOfCodeAvailable() { return Pool.size(); }
    public static String GetClassName() { return TreatmentCode.class.getSimpleName(); }
    public static String[] GetArgName() { return new String[] {"code", "description"}; }

    public static Prefix GetPrefix() { return TreatmentCode.prefix; }
    public static String GetPrefixCode() { return TreatmentCode.GetPrefix().GetPrefixCode(); }

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
    // Validation Function
    public static boolean ValidateKeyCode(String code) {
        if (code == null || code.length() != 10 + TreatmentCode.GetPrefixCode().length()) { return false; }
        if (!code.startsWith(TreatmentCode.GetPrefixCode())) {return false; }
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
    // Converting Function
    private static ArrayList<Hashtable<String, Object>> ConvertPool() {
        ArrayList<Hashtable<String, Object>> array = new ArrayList<Hashtable<String, Object>>(TreatmentCode.GetSerializationCapacity());
        Iterator<Entry<String, Object>> iter = TreatmentCode.GetPool().entrySet().iterator();
        
        String[] ArgName = TreatmentCode.GetArgName();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            Hashtable<String, Object> item = new Hashtable<String, Object>(2, 1.0f);
            item.put(ArgName[0], entry.getKey());
            item.put(ArgName[1], entry.getValue());
            array.add(item);
        }
        return array;
    }


}
