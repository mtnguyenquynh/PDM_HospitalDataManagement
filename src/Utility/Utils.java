package Utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;
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

}