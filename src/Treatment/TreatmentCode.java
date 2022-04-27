package Treatment;
import java.util.*;
import java.nio.*;
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
    private final static String SafeJsonDirectory = "src/Treatment/TreatmentCode.json";


    public static void main(String[] args) {
        if (TreatmentCode.GetNumberOfCode() == 0) {
            TreatmentCode.InitializeTreatmentCodePool();
        }

    } 
    
    // ---------------------------------------------------------------------------------------------------------------------
    // Pool declaration
    public static void InitializeTreatmentCodePool() {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("The pool of treatment code is generated once only at the beginning of the program.");
        
        Pool.put("00-00-0000", "Empty treatment");
        Pool.put("00-00-0001", "X-rays");
        Pool.put("00-00-0002", "Blood Test");
        Pool.put("00-00-0003", "Surgery");
        
        System.out.println("The pool of treatment code is finished execution.");
        System.out.println("----------------------------------------------------------------------------------");
    } 

    public static void LoadJsonDatabase() {
        File json_data = new File(TreatmentCode.MainJsonDirectory); 
        if (json_data.exists()) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("The treatment code database is loaded from the file: " + TreatmentCode.MainJsonDirectory);
            System.out.println("----------------------------------------------------------------------------------");
            try {
                BufferedReader br = new BufferedReader(new FileReader(json_data));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] line_split = line.split(":");
                    String code = line_split[0];
                    String description = line_split[1];
                    Pool.put(code, description);
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } 
            


        else {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("The treatment code database is not found, and will be created.");
            System.out.println("----------------------------------------------------------------------------------");
            TreatmentCode.InitializeTreatmentCodePool();
        }
    }
    
    public static boolean containsKey(String code) { return Pool.containsKey(code); }

    public static boolean IsContainedThisCode(String code) { return TreatmentCode.containsKey(code); }

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

}
