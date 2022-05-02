package Utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This file contained several helper functions
 * 
 * See references:
 * - https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
**/

public abstract class Utils {
    
    // ---------------------------------------------------------------------------------------------------------------------
    // JSON Processing: Read & Write
    private static void ValidateKeyValue(String[] key_value) throws Exception {
        if (key_value.length != 2) {
            throw new Exception("The value_string array must have only two elements.");
        }
    }
    
    /**
     * This method will load everything from the json file into the 2-valued string hash table.
     * Return True if the task proceeded successfully without any given error.
     * TODO: This cannot be executed
     * 
     * @param json_directory (str): The directory of the json file want to load.
     * @param table (str): The hashed-table to store the result of our value.
     * @param key_wrapper (str): The serialized name of our instance, for example TreatmentCode.
     * @param key_value (str, str): The key-value pair of the json file. The first value is the code name
     *      and the second value is the code value.
     * @return (bool): True if the task proceeded successfully without any given error.
     * @author HDM-Dev-Team, Ichiru Take
     * 
     **/
    public static boolean LoadJsonDataIntoHashTable(
        String json_directory, Hashtable<String, Object> table, 
        String key_wrapper, String[] key_value
    ) throws Exception, FileNotFoundException {
        Utils.ValidateKeyValue(key_value);

        boolean success = true; // True if the task is successful.
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(json_directory));
            JSONArray array = (JSONArray) jsonObject.get(key_wrapper);
            
            for (int idx = 0; idx < array.size(); idx++) {
                JSONObject json_object = (JSONObject) array.get(idx);
                String keyCode = (String) json_object.get(key_value[0]);
                String value = (String) json_object.get(key_value[1]);
                table.put(keyCode, value);
            }
        } catch (IOException | ParseException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  
        return success;
    }

    /**
     * This method will save the result everything from the hash table to JSON file.
     * Return True if the task proceeded successfully without any given error.
     * @param json_directory (str): The directory of the json file want to save.
     * @param table (str): The hashed-table to load our value.
     * @param key_wrapper (str): The serialized name of our instance, for example TreatmentCode.
     * @param key_value (str, str): The key-value pair of the json file. The first value is the code name
     *      and the second value is the code value.
     * @return (bool): True if the task proceeded successfully without any given error.
     * @author HDM-Dev-Team, Ichiru Take
     * 
     **/
    public static boolean SaveHashTableIntoJsonFile(
        String json_directory, Hashtable<String, Object> table, 
        String key_wrapper, String[] key_value
    ) throws Exception {
        Utils.ValidateKeyValue(key_value);
        JSONArray json_file = new JSONArray();
        for (String keyCode: table.keySet()) {
            JSONObject json_object = new JSONObject();
            json_object.put(key_value[0], keyCode);
            json_object.put(key_value[1], table.get(keyCode));

            JSONObject json_wrapper = new JSONObject();
            json_wrapper.put(key_wrapper, json_object);

            json_file.add(json_wrapper);
        }    

        boolean success = true; // True if the task is successful.
        //Write JSON file
        try (FileWriter file = new FileWriter(json_directory)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json_file.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  
        
        return success;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    // Condition-checking
    /**
     * If the following condition is not met, the program will throw an exception.
     * @param condition (boolean): The condition to be checked.
     * @param message (String): The message to be printed if the condition is not met.
     * @throws IllegalArgumentException
     */
    public static void CheckArgumentCondition(boolean condition, String message) throws IllegalArgumentException {
        if (!condition) { throw new IllegalArgumentException(message); }
    }

}