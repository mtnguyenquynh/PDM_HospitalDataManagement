package Utility;

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
 * See references:
 * - https://howtodoinjava.com/java/library/json-simple-read-write-json-examples
**/

public abstract class Utils {
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

    // ---------------------------------------------------------------------------------------------------------------------
    // Read a JSON file by its path/directory
    public static Object ReadJsonFile(String directory) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader(directory));
            return obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray ReadJsonFileAsArray(String directory) { return (JSONArray) ReadJsonFile(directory); }

    public static JSONObject ReadJsonFileAsObject(String directory) { return (JSONObject) ReadJsonFile(directory); }

    public static boolean WriteJsonFile(String directory, JSONObject data) {
        boolean success = true; // True if the task is successful.
        try (FileWriter file = new FileWriter(directory)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(data.toString()); 
            file.flush();
 
        } catch (IOException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  
        return success;
    }

    public static boolean WriteJsonFile(String directory, JSONArray data) {
        boolean success = true; // True if the task is successful.
        try (FileWriter file = new FileWriter(directory)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(data.toString()); 
            file.flush();
 
        } catch (IOException e) {
            success = false;
            System.out.println("Error: " + e.getMessage());
        }  
        return success;
    }

    // ---------------------------------------------------------------------------------------------------------------------
    /**
     * This function will convert every data from JSONObject into Hashtable.
     * 
     * @param data (JSONObject): The JSONObject you want to convert.
     * @return Hashtable<String, Object>
     **/
    public static Hashtable<String, Object> CastJsonToHashtable(JSONObject data, String name) {
        Hashtable<String, Object> table = new Hashtable<String, Object>(1000, 0.75f);
        // Iterate over keys
        if (name != null && data.size() == 1) {
            Object item = data.get(name);
            return Utils.CastJsonToHashtable((JSONObject) item, null);
        }
        for (Object key : data.keySet()) {
            // Get the value
            Object value = data.get(key);
            table.put(key.toString(), value);
        }
        return table;
    }

    
    public static JSONObject CastHashtableToJson(Hashtable<String, Object> data, String name) {
        JSONObject jsonObject = new JSONObject();
        for (String key : data.keySet()) {
            Object value = data.get(key);
            jsonObject.put(key, value);
        } 
        if (name == null) { return jsonObject; }

        JSONObject JsonObjectWrapper = new JSONObject();
        JsonObjectWrapper.put(name, jsonObject);
        return JsonObjectWrapper;
    }

    /**
     * This function will convert every data from JSONArray into ArrayList.
     * 
     * @param directory (String): The json_directory you want to load.
     * @param name (String): The name of the key wrapping on each object
     * @return ArrayList<Hashtable<String, Object>>
     **/
    public static ArrayList<Hashtable<String, Object>> SaveJsonDataIntoHashTable(
        String directory, String name) throws Exception, FileNotFoundException 
    {    
        ArrayList<Hashtable<String, Object>> data = new ArrayList<Hashtable<String, Object>>(10000);
        JSONArray jsonArray = ReadJsonFileAsArray(directory);
        if (jsonArray == null) { throw new Exception("This JSON file cannot be loaded."); }
        for (Object item : jsonArray) { data.add(Utils.CastJsonToHashtable((JSONObject) item, name)); }
        return data;

    }

    /**
     * This method will save the result everything from the hash table to JSON file.
     * Return True if the task proceeded successfully without any given error.
     * @param directory (String): The directory of JSON file you want to save.
     * @param data (ArrayList<Hashtable<String, Object>>): The data you want to serialize.
     * @param name (String): The name of the key wrapping on each object
     * @return (bool): True if the task proceeded successfully without any given error.
     * 
     **/
    public static boolean SaveHashTableIntoJsonFile(
        String directory, ArrayList<Hashtable<String, Object>> data, 
        String name) throws Exception {
        
        JSONArray jsonArray = new JSONArray();
        for (Hashtable<String, Object> table : data) {
            jsonArray.add(Utils.CastHashtableToJson(table, name));
        }
    
        return Utils.WriteJsonFile(directory, jsonArray); // Write JSON file
    }

    public static boolean SaveHashTableIntoJsonFile(String directory, Hashtable<String, Object> data, 
                                                    String name) throws Exception {
        JSONObject jsonObject = Utils.CastHashtableToJson(data, name);
        return Utils.WriteJsonFile(directory, jsonObject); // Write JSON file
    }

}