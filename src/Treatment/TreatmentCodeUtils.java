package Treatment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author HDM-Dev Team
 * @version 0.0.1
 * 
 * See references:
 * - https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
**/

public abstract class TreatmentCodeUtils {
    
    /**
     * This function will convert every data from JSONObject into Hashtable.
     * 
     * @param data (JSONObject): The JSONObject you want to convert.
     * @return Hashtable<String, Object>
     **/
    public static Hashtable<String, Object> LoadOneUnitJsonData(JSONObject data) {
        Hashtable<String, Object> table = new Hashtable<String, Object>(1000, 0.75f);
        // Iterate over keys
        for (Object key : data.keySet()) {
            // Get the value
            Object value = data.get(key);
            table.put(key.toString(), value);
        }
        return table;
    }


    /**
     * This function will convert every data from JSONArray into ArrayList.
     * 
     * @param json_directory (String): The json_directory you want to load.
     * @param key_identifier (String): The name of the key wrapping on each object
     * @return ArrayList<Hashtable<String, Object>>
     **/
    public static ArrayList<Hashtable<String, Object>> SaveJsonDataIntoHashTable(
        String json_directory, String key_identifier) throws Exception, FileNotFoundException 
    {    
        ArrayList<Hashtable<String, Object>> data = new ArrayList<Hashtable<String, Object>>(10000);
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(json_directory));
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                if (key_identifier == null) {
                    data.add(LoadOneUnitJsonData(jsonObject));
                } else {
                    try {
                        JSONObject NewJsonObject = (JSONObject) jsonObject.get(key_identifier);
                        data.add(LoadOneUnitJsonData(NewJsonObject));
                    } catch (Exception e) { data.add(LoadOneUnitJsonData(jsonObject)); }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;

    }

    /**
     * This method will save the result everything from the hash table to JSON file.
     * Return True if the task proceeded successfully without any given error.
     * @param json_directory (String): The json_directory you want to load.
     * @param key_identifier (String): The name of the key wrapping on each object
     * @param table (str): The hashed-table to load our value.
     * @param key_wrapper (str): The serialized name of our instance, for example TreatmentCode.
     * @param key_value (str, str): The key-value pair of the json file. The first value is the code name
     *      and the second value is the code value.
     * @return (bool): True if the task proceeded successfully without any given error.
     * @author HDM-Dev-Team, Ichiru Take
     * 
     **/
    public static boolean SaveHashTableIntoJsonFile(
        String json_directory, ArrayList<Hashtable<String, Object>> data, 
        String key_identifier) throws Exception {
        JSONArray jsonArray = new JSONArray();

        for (Hashtable<String, Object> table : data) {
            JSONObject jsonObject = new JSONObject();
            for (String key : table.keySet()) {
                Object value = table.get(key);
                jsonObject.put(key, value);
            }

            JSONObject JsonObjectWrapper = new JSONObject();
            JsonObjectWrapper.put(key_identifier, jsonObject);

            jsonArray.add(jsonObject);
        }
    

        boolean success = true; // True if the task is successful.
        //Write JSON file
        try (FileWriter file = new FileWriter(json_directory)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonArray.toString()); 
            file.flush();
 
        } catch (IOException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  
        
        return success;
    }


}
