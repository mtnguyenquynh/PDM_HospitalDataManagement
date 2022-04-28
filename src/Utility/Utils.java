package Utility;

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


import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;



public class Utils {
    
    // ---------------------------------------------------------------------------------------------------------------------
    private static void validate(String[] key_value) throws Exception {
        if (key_value.length != 2) {
            throw new Exception("The value_string array must have only two elements.");
        }
    }
    
    /**
     * This method will load everything from the json file into the 2-valued string hash table.
     * References: https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
     **/
    public static boolean LoadJsonDataIntoHashTable(
        String json_directory, Hashtable<Object, Object> table, 
        String key_wrapper, String[] key_value
    ) throws Exception, FileNotFoundException {
        Utils.validate(key_value);

        boolean success = true; // True if the task is successful.
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(json_directory));
            JSONArray array = (JSONArray) jsonObject.get(key_wrapper);
            
            for (int idx = 0; idx < array.size(); idx++) {
                JSONObject json_object = (JSONObject) array.get(idx);
                String key = (String) json_object.get(key_value[0]);
                String value = (String) json_object.get(key_value[1]);
                table.put(key, value);
            }
        } catch (IOException | ParseException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  

        System.out.println("----------------------------------------------------------------------------------");
        return success;
    }

    /**
     * This method will save the result everything from the hash table to JSON file.
     **/
    public static boolean SaveHashTableIntoJsonFile(
        String json_directory, Hashtable<String, String> table, 
        String key_wrapper, String[] key_value
    ) throws Exception {

        Utils.validate(key_value);

        boolean success = true; // True if the task is successful.



        return success;
        
    }

}
