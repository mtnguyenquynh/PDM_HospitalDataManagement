package Treatment;

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
 * This file contained several helper function which support for the
 * module TreatmentCode.java
 * See references:
 * - https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
**/

public class TreatmentCodeUtils {
    
    // ---------------------------------------------------------------------------------------------------------------------
    private static void ValidateKeyValue(String[] key_value) throws Exception {
        if (key_value.length != 2) {
            throw new Exception("The value_string array must have only two elements.");
        }
    }
    
    /**
     * This method will load everything from the json file into the 2-valued string hash table.
     * Return True if the task proceeded successfully without any given error.
     * References: https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
     **/
    
    public static boolean LoadJsonDataIntoHashTable(
        String json_directory, Hashtable<String, String> table, 
        String key_wrapper, String[] key_value 
    ) throws Exception, FileNotFoundException {
        TreatmentCodeUtils.ValidateKeyValue(key_value);

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
        return success;
    }

    /**
     * This method will save the result everything from the hash table to JSON file.
     * References: https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
     **/
    public static boolean SaveHashTableIntoJsonFile(
        String json_directory, Hashtable<String, String> table, 
        String key_wrapper, String[] key_value, String prefix
    ) throws Exception {
        TreatmentCodeUtils.ValidateKeyValue(key_value);
        if (prefix == null) { prefix = ""; }

        JSONArray json_file = new JSONArray();
        for (String key: table.keySet()) {
            JSONObject json_object = new JSONObject();
            json_object.put(key_value[0], key);
            json_object.put(key_value[1], prefix + table.get(key));

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

}
