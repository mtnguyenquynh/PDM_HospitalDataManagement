package Treatment;
import java.util.*;
import java.nio.*;
import java.io.*;
import Treatment.TreatmentCodeUtils;

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
 * we declared the definition of the our treatment record.
 * We can add new treatment codes in this file, and then update it
 * but it is another story.
 * See references:
 * - https://www.tutorialspoint.com/java/java_enum_class.htm
 * - https://www.geeksforgeeks.org/differences-between-hashmap-and-hashtable-in-java/
 * - https://www.geeksforgeeks.org/hashtable-in-java/
 * - https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Hashtable.html 
**/






public class TreatmentCode {
    // The pool here is a collection of key-value pairs, where the key is the code, 
    // and the value is the simple description found describing the code.
    // The key is a fixed-length 10-valued string (8 digits, separated by 2 dashes), and the value is a string.
    // The argument `capacity` is the initial capacity of the pool, 
    // the argument `loadFactor` controls a tradeoff between wasted space and the need for 
    // rehash operations, which are time-consuming.
    private final static int capacity = 1000;
    private final static float loadFactor = (float) 0.75f;
    private final static Hashtable<String, String> Pool = new Hashtable<String, String>(capacity, loadFactor);
    
    // These two directory are the saved configuration of all treatment codes. 
    private final static String MainJsonDirectory = "src/Treatment/TreatmentCode.json";
    private final static String SafeJsonDirectory = "src/Treatment/TreatmentCode-Restored.json";
    
    private final static String keyCodeName = "TreatmentCode";
    private final static String[] keyCodeArgumentName = {"key_code", "description"};


    public static void main(String[] args) {
        if (TreatmentCode.GetNumberOfCode() == 0) {
            boolean status = TreatmentCode.LoadJsonDatabase();
            if (TreatmentCode.GetNumberOfCode() == 0) { TreatmentCode.InitializePool(); }
        }

    } 
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Pool declaration
    public static boolean LoadJsonDatabase() {
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
                status = TreatmentCodeUtils.LoadJsonDataIntoHashTable(
                    directory, TreatmentCode.Pool, TreatmentCode.keyCodeName, TreatmentCode.keyCodeArgumentName);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("The treatment code database is not found, and will be created.");
        }
        System.out.println("----------------------------------------------------------------------------------");
        return status;
    }
    
    public static boolean ContainsThisKeyCode(String code) { return Pool.containsKey(code); }

    public static boolean IsContainedThisCode(String code) { return TreatmentCode.ContainsThisKeyCode(code); }

    // ---------------------------------------------------------------------------------------------------------------------
    // Getter Function Only
    public static Hashtable<String, String> GetPool() { return Pool; }

    public static int GetCapacity() { return capacity; }
    public static float GetPreloadFactor() { return loadFactor; }
    public static int GetNumberOfCode() { return Pool.size(); }

    public static void Display() {
        System.out.println("Display the pool of treatment code.");
        for (String key : Pool.keySet()) { System.out.println(key + " :  " + Pool.get(key)); }
    }

    // ---------------------------------------------------------------------------------------------------------------------
    /**
     * This static method is called
     */
    private static void InitializePool() {

    }

}
